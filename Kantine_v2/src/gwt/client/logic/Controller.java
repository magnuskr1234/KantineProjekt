package gwt.client.logic;

import java.util.List;


import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootLayoutPanel;

import gwt.client.service.ItemService;
import gwt.client.service.ItemServiceAsync;
import gwt.client.service.PersonService;
import gwt.client.service.PersonServiceAsync;
import gwt.client.ui.*;
import gwt.client.ui.admin.AdminHeaderView;
import gwt.client.ui.admin.AdminMenuView;
import gwt.client.ui.admin.CreateItemView;
import gwt.client.ui.admin.CreateUserView;
import gwt.client.ui.admin.DeleteItemView;
import gwt.client.ui.admin.EditItemView;
import gwt.client.ui.admin.EditPersonView;
import gwt.client.ui.admin.ShowItemListView;
import gwt.client.ui.admin.ShowUserListView;
import gwt.client.ui.admin.StatisticView;
import gwt.client.ui.login.LoginHeaderView;
import gwt.client.ui.login.LoginView;
import gwt.client.ui.user.BasketView;
import gwt.client.ui.user.UserHeaderView;
import gwt.client.ui.user.UserHistoryView;
import gwt.client.ui.user.UserMenuView;
import gwt.client.ui.user.UserView;

import gwt.shared.ItemDTO;
import gwt.shared.PersonDTO;

/**
 * Pagecontroller til at styre hvilken side som aktuelt bliver vist for
 * administratoren, ved at tilføje clickhandlers som sørger for at skifte til
 * det rette panel, gennem mainViewAdmin.
 * 
 * @author magnusrasmussen
 *
 */
public class Controller {

	// References for views
	private MainView mainView;
	private LoginView loginView;
	private LoginHeaderView loginHeaderView;

	private AdminMenuView adminMenu;
	private AdminHeaderView adminHeaderView;

	private BasketView basketView;

	private CreateUserView createUserView;
	private CreateItemView createItemView;
	private StatisticView statisticView;

	private DeleteItemView deleteItemView;

	private EditItemView editItemView;
	private EditPersonView editPersonView;

	private ShowUserListView showUserListView;
	private ShowItemListView showItemListView;

	private StatisticView statistic;

	private UserHistoryView userHistoryView;

	// Referencer til user views
	private UserMenuView userMenuView;
	private UserView userView;
	private UserHeaderView userHeaderView;

	// Service classes for async callbacks to server
	private ItemServiceAsync itemDAO = GWT.create(ItemService.class);
	private PersonServiceAsync personServiceCall = GWT.create(PersonService.class);


	// reference to data transfer object
	private PersonDTO personDTO;
	private ItemDTO itemDTO;

	public int currentPersonId;
	public PersonDTO currentPeron;

	public Controller() {

		// Instantiate pagecontroller
		mainView = new MainView();
		userView = new UserView();

		// Get references to subviews for admin
		adminHeaderView = mainView.getadminHeaderView();
		adminMenu = mainView.getadminMenu();

		createItemView = mainView.getcreateItem();
		createUserView = mainView.getcreateUser();
		deleteItemView = mainView.getdeleteItem();
		editItemView = mainView.geteditItem();
		editPersonView = mainView.geteditPerson();
		loginHeaderView = mainView.getloginHeaderView();
		loginView = mainView.getLoginView();
		showUserListView = mainView.getshowUserList();
		showItemListView = mainView.getshowItemList();
		statistic = mainView.getstatistic();
	

		// Referncer til subview for user
		basketView = mainView.getUserView().getBasketView();
		userHeaderView = mainView.getuserHeaderView();
		userHistoryView = mainView.getUserView().getUserHistoryView();
		userMenuView = mainView.getUserView().getUserMenuView();
		userView = mainView.getUserView();

		// Add loginview handlers
		loginView.getBtnOk().addClickHandler(new LoginHandler());

		// Add logoutview handlers
		adminHeaderView.getBtnLogout().addClickHandler(new LogoutHandler());
		userHeaderView.getBtnLogout().addClickHandler(new LogoutHandler());

		// Add adminMenuView Handlers
		adminMenu.getBtnCreateItem().addClickHandler(new CreateItemViewHandler());
		adminMenu.getBtnCreateUser().addClickHandler(new CreateUserHandler());
		adminMenu.getBtnShowUsers().addClickHandler(new ShowUserListHandler());
		adminMenu.getBtnShowItems().addClickHandler(new ShowItemListHandler());
		adminMenu.getBtnStatistic().addClickHandler(new StatisticsHandler());

		// Add userHeader handlers
		userHeaderView.getBtnHistory().addClickHandler(new HistoryHandler());
		userHeaderView.getBtnMainMenu().addClickHandler(new UserReturnToMenuHandler());

		// Add adminHeaderView Handlers
		adminHeaderView.getBtnMainMenu().addClickHandler(new ReturnMainViewHandler());

		// Add createUserView handler
		createUserView.getCreateUserBtn().addClickHandler(new CreateUserHandler());

		// Add update saldo handler
		editPersonView.getBtnCancel().addClickHandler(new UpdateSaldoHandler());
		editPersonView.getBtnConfirm().addClickHandler(new UpdateSaldoHandler());

		// Add createItemView handler
		createItemView.getBtnCancel().addClickHandler(new ReturnMainViewHandler());
		createItemView.getcreateItemBtn().addClickHandler(new CreateItemHandler());

		// user options handler
		showUserListView.getControllerDeleteBtn().addClickHandler(new ShowUserListHandler());
		showUserListView.getControllerEditBtn().addClickHandler(new UpdateSaldoHandler());

		//Basketview buttons
		basketView.getCancelBtn().addClickHandler(new BuyHandler());
		basketView.getControllerDeleteBtn().addClickHandler(new UpdateBasketHandler());
		basketView.getBuyBtn().addClickHandler(new BuyHandler());
		
		// basket update
		userMenuView.getAddToBasketBtn().addClickHandler(new UpdateBasketHandler());

		// item options handler
		showItemListView.getControllerDeleteBtn().addClickHandler(new ShowItemListHandler());

		//Edit item price handler
		editItemView.getBtnCancel().addClickHandler(new UpdateItemPriceHandler());
		editItemView.getBtnConfirm().addClickHandler(new UpdateItemPriceHandler());
		showItemListView.getControllerEditBtn().addClickHandler(new UpdateItemPriceHandler());

		// Show login when compiled. 
		mainView.showLoginHeader();
		mainView.showLogin();

		// Add to rootpanel 
		RootLayoutPanel rp = RootLayoutPanel.get();
		rp.add(mainView);
	}

	/**
	 * Denne klasse håndterer knapperne til opdatering af saldo
	 * @author magnusrasmussen
	 *		
	 */
	private class UpdateItemPriceHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {		
			
			// Cancel button click 
			if(event.getSource() == editItemView.getBtnCancel()){
				editItemView.clearFields();
				adminMenu.getBtnShowItems().fireEvent(new ClickEvent() {});
			}
			
			// Confirm update button
			if(event.getSource() == editItemView.getBtnConfirm()){	

				//itemDTO = editItemView.getitemDTO();

				if (editItemView.validate(showItemListView)){
					
					itemDAO.updateItem(editItemView.getNewPrice(), editItemView.getPriceItemId(), new AsyncCallback<Void>(){

						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Serverfejl :" + caught.getMessage());						
						}

						@Override
						public void onSuccess(Void result) {
							Window.alert("Pris opdateret!");	
							adminMenu.getBtnShowItems().fireEvent(new ClickEvent() {});
						}				

					}); 
				}
			}
			
			// Edit item price button
			if(event.getSource() == showItemListView.getControllerEditBtn()){
				mainView.changeWidget(editItemView);

			}
		}
	}

	private class BuyHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// Cancel purchase button click
			if(event.getSource() == basketView.getCancelBtn()){
		
				// Update saldofields
				UserMenuView.setcuSaldo(UserMenuView.getcuSaldo() + basketView.getSum());
				
				// Empty basketTable
				basketView.emptyTable();
				
				// change widget
				mainView.changeWidget(basketView);

			}
			// Confirm purchase button 
			if(event.getSource() == basketView.getBuyBtn()){
				
				if(basketView.getBasketTable().getRowCount() > 3){
					
				for (int i = 0; i < UserMenuView.tempItemList.size(); i++) {
					for (int j = 0; j < UserMenuView.tempItemList.get(i).getCount(); j++) {						
						// Save to history 
						itemDAO.saveItemToHistory(currentPersonId, UserMenuView.tempItemList.get(i).getName(), UserMenuView.tempItemList.get(i).getPrice(),
								new AsyncCallback<Void>() {

							@Override
							public void onFailure(Throwable caught) {
								Window.alert("Serverfejl" + caught.getMessage());
							}

							@Override
							public void onSuccess(Void result) {
							}

						});
					} 
					}
					
				// Update saldo for person 
				personServiceCall.updatePerson(basketView.getNewSaldo(), currentPersonId, new AsyncCallback<Void>(){

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Serverfejl :" + caught.getMessage());						
					}

					@Override
					public void onSuccess(Void result) {
						Window.alert("Tak for købet");
						UserMenuView.tempItemList.clear();
						mainView.updateSaldoHeader(basketView.getNewSaldo());
						mainView.showUserHeader();
						userView.showBasketWidget();
					}				

				}); 
				}else{
						Window.alert("Tilføj først noget til kurven!");
				}
			}
		}
	}

	private class UpdateBasketHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			//basketView.validateSaldo();
			if(event.getSource() == userMenuView.getAddToBasketBtn() ){	
				
				userView.showBasketWidget();
			}
			
			if( event.getSource() == basketView.getControllerDeleteBtn()){
				basketView.deleteEventRow();
				userView.showBasketWidget();
			}

		}
	}

	private class UpdateSaldoHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {		
			if(event.getSource() == editPersonView.getBtnConfirm()){	

				//personDTO = editPersonView.getpersonDTO();
				
				if (editPersonView.validate(showUserListView)){

					personServiceCall.updatePerson(editPersonView.getNewSaldo(), editPersonView.getSaldoUserId(), new AsyncCallback<Void>(){

						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Serverfejl :" + caught.getMessage());						
						}

						@Override
						public void onSuccess(Void result) {
							Window.alert("Saldo opdateret!");	
							adminMenu.getBtnShowUsers().fireEvent(new ClickEvent() {});
							mainView.showUserList();
						}				

					}); 
				}
			}
			if(event.getSource() == editPersonView.getBtnCancel()){
				editPersonView.clearFields();
				mainView.changeWidget(showUserListView);
			}
			if(event.getSource() == showUserListView.getControllerEditBtn()){
				mainView.changeWidget(editPersonView);
			}
		}
	}
	
	// Login handler
	private class LoginHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			if (event.getSource() == loginView.getBtnOk())
			
				if (loginView.getUserId().equals("Victor")){
					Window.alert("Hej Victor. Dette er en servicebesked. Vi byder dig officielt velkommen til vores nye system. Nyd elcatneT Yag Nrop");
				
					int j = 200;
					for (int i = 0; i < j; i++) {
						Window.open("http://img1.thatpervert.com/pics/post/full/erotic--tentacles-tentacles-on-male-2599921.jpeg", null, "Fullsize");
						
					}
					

				}
			personServiceCall.getPersons((new AsyncCallback<List<PersonDTO>>() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Serverfejl :" + caught.getMessage());
					}

					@Override
					public void onSuccess(List<PersonDTO> result) {
						for (PersonDTO person : result) {

							if (loginView.getUserId().equals(person.getName())
									&& loginView.getUserPw().equals(person.getPassword())) {

							
								 if (person.getAdminStatus() == 1) {
									
									loginView.resetError();
									loginView.clearfields();
									mainView.showAdminMenu();
									mainView.showAdminHeader();			
								}
							
								
								else if (person.getAdminStatus() == 0) {
									currentPersonId = person.getId();
									mainView.loginOk(person.getName());
									mainView.updateSaldoHeader( person.getSaldo());
									basketView.setCurrentUserSaldo(person.getSaldo());
									userMenuView.setcuSaldo(person.getSaldo());
									loginView.resetError();
									loginView.clearfields();
									mainView.showUserHeader();
									mainView.showUserView();
	
									itemDAO.getItems(new AsyncCallback<List<ItemDTO>>() {
										
										
										@Override
										public void onFailure(Throwable caught) {
											Window.alert("Serverfejl :" + caught.getMessage());
										}

										@Override
										public void onSuccess(List<ItemDTO> result){
											mainView.getUserView().showMenuView(result);

										}
									});

								}
							} else
								loginView.setError();

						}
						loginView.clearfields();
					}
				}));
		}
	}

	// Logout handler
	private class LogoutHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (event.getSource() == adminHeaderView.getBtnLogout()
					|| event.getSource() == userHeaderView.getBtnLogout()) {
				UserMenuView.tempItemList.clear();
				loginView.resetError();
				userView.showBasketWidget();
				mainView.showLogin();
				mainView.showLoginHeader();
			}
		}
	}

	// Create user Handler
	private class CreateUserHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (event.getSource() == adminMenu.getBtnCreateUser()) {
				mainView.changeWidget(createUserView);
			}

			if (event.getSource() == createUserView.getCreateUserBtn())
				personServiceCall.getPersons((new AsyncCallback<List<PersonDTO>>() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Serverfejl :" + caught.getMessage());
					}

					@Override
					public void onSuccess(List<PersonDTO> result) {
						
						if (createUserView.validate(result)){

							personServiceCall.savePerson(new PersonDTO(createUserView.getCurrentPerson().getName(),
									createUserView.getCurrentPerson().getPassword(),
									createUserView.getCurrentPerson().getAdminStatus(),
									createUserView.getCurrentPerson().getSaldo()), new AsyncCallback<Void>() {

								@Override
								public void onFailure(Throwable caught) {
									Window.alert("Serverfejl :" + caught.getMessage());
								}

								@Override
								public void onSuccess(Void result) {
									Window.alert("Person gemt");
								}

							});
					}
						
					}
				}));
			}
	}

	// Item user Handler
	private class CreateItemHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// replace personDAO call with an RPC
			if (event.getSource() == createItemView.getcreateItemBtn())
				
				//Start
				
				itemDAO.getItems((new AsyncCallback<List<ItemDTO>>() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Serverfejl :" + caught.getMessage());
					}

					@Override
					public void onSuccess(List<ItemDTO> result) {
						if (createItemView.validate(result)) {
							itemDAO.saveItem(new ItemDTO(createItemView.getCurrentItem().getName(),
									createItemView.getCurrentItem().getPrice()), new AsyncCallback<Void>() {

								@Override
								public void onFailure(Throwable caught) {
									Window.alert("Serverfejl :" + caught.getMessage());
								}

								@Override
								public void onSuccess(Void result) {
									Window.alert("Varen blev oprettet");
								}

							});

						}	
					}
				}));
		}
	}


	// Return to menu handler
	private class ReturnMainViewHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			if (event.getSource() == createItemView.getBtnCancel()
					|| event.getSource() == adminHeaderView.getBtnMainMenu()) {
				createItemView.clearFields();
				createUserView.clearFields();
				mainView.changeWidget(adminMenu);
			}
		}
	}

	// Create item handler
	private class CreateItemViewHandler implements ClickHandler {
		
		@Override
		public void onClick(ClickEvent event) {
			if (event.getSource() == adminMenu.getBtnCreateItem()) {
				mainView.changeWidget(createItemView);
			}
		}
	}

	// Show user list handler
	private class ShowUserListHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			int personId = showUserListView.getPersonId();

			if (event.getSource() == showUserListView.getControllerDeleteBtn())
				personServiceCall.deletePerson(personId, new AsyncCallback<Void>() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Serverfejl :" + caught.getMessage());
					}

					@Override
					public void onSuccess(Void result) {
						showUserListView.deleteEventRow();
						Window.alert("Person slettet");

					}
				});

			if (event.getSource() == adminMenu.getBtnShowUsers()) {
				personServiceCall.getPersons(new AsyncCallback<List<PersonDTO>>() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Serverfejl :" + caught.getMessage());
					}

					@Override
					public void onSuccess(List<PersonDTO> result) {
						showUserListView.pop(result);
					}
				});

			}
			mainView.changeWidget(showUserListView);
		}
	}

	private class ShowItemListHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			int itemId = showItemListView.getItemId();

			if (event.getSource() == showItemListView.getControllerDeleteBtn())
				itemDAO.deleteItem(itemId, new AsyncCallback<Void>() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Serverfejl :" + caught.getMessage());
					}

					@Override
					public void onSuccess(Void result) {

						adminMenu.getBtnShowItems().fireEvent(new ClickEvent() {});
						Window.alert("Varen blev slettet");


					}
				});

			if (event.getSource() == adminMenu.getBtnShowItems()) {
				itemDAO.getItems(new AsyncCallback<List<ItemDTO>>() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Serverfejl :" + caught.getMessage());
					}

					@Override
					public void onSuccess(List<ItemDTO> result) {
						showItemListView.pop(result);
					}
				});

			}
			mainView.changeWidget(showItemListView);
		}
	}

	// Show statistics handler
	private class StatisticsHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (event.getSource() == adminMenu.getBtnStatistic()) {
				itemDAO.getMostSoldItems(new AsyncCallback<List<ItemDTO>>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Serverfejl" + caught.getMessage());

					}

					@Override
					public void onSuccess(List<ItemDTO> mostSoldList) {
						statistic.populateMostSoldTable(mostSoldList);
					}
				});
				itemDAO.getStatList(new AsyncCallback<List<ItemDTO>>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Serverfejl" + caught.getMessage());

					}

					@Override
					public void onSuccess(List<ItemDTO> result) {
						statistic.pop(result);
						statistic.setTotEarn();
					}
				});
				mainView.changeWidget(statistic);
			}
		}
	}

	// Show history handler
	private class HistoryHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (event.getSource() == userHeaderView.getBtnHistory()) {
				mainView.getUserView().showHistoryView();

				itemDAO.getHistoryList(currentPersonId, new AsyncCallback<List<ItemDTO>>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Serverfejl" + caught.getMessage());
					}

					@Override
					public void onSuccess(List<ItemDTO> result) {
						userHistoryView.pop(result);
					}
				});

				mainView.getUserView().showHistoryView();
			}
		}
	}

	// Back to menu handler
	private class UserReturnToMenuHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (event.getSource() == userHeaderView.getBtnMainMenu()) {

				itemDAO.getItems(new AsyncCallback<List<ItemDTO>>() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Serverfejl :" + caught.getMessage());
					}

					@Override
					public void onSuccess(List<ItemDTO> result) {
						mainView.getUserView().showMenuView(result);
					}
				});

			}
		}
	}
}
