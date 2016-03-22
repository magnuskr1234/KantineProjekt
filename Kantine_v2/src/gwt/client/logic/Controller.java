package gwt.client.logic;

import java.sql.Date;
import java.util.ArrayList;
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
import gwt.client.ui.admin.DeleteUserView;
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

	private DeleteItemView deleteItemView;
	private DeleteUserView deleteUserView;

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

	// Service
	private PersonServiceAsync personDAO = GWT.create(PersonService.class);
	private ItemServiceAsync itemDAO = GWT.create(ItemService.class);
	private PersonServiceAsync personupdate = GWT.create(PersonService.class);
	//

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
		deleteUserView = mainView.getdeleteUserView();
		editItemView = mainView.geteditItem();
		editPersonView = mainView.geteditPerson();
		loginHeaderView = mainView.getloginHeaderView();
		loginView = mainView.getLoginView();
		showUserListView = mainView.getshowUserList();
		showItemListView = mainView.getshowItemList();
		statistic = mainView.getstatistic();
		userHistoryView = mainView.getUserView().getUserHistoryView();
		
		

		// Referncer til subview for user
		basketView = mainView.getUserView().getBasketView();
		userMenuView = mainView.getUserView().getUserMenuView();
		userView = mainView.getUserView();
		userHeaderView = mainView.getuserHeaderView();

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

		/*
		 * Tilføj user menu handlers
		 * userView.getUserMenuView().getKaffeBtn().addClickHandler(new
		 * AddKaffeToBasketHandler());
		 * userView.getUserMenuView().getBananBtn().addClickHandler(new
		 * AddBananToBasketHandler());
		 */

		// Add userHeader handlers
		userHeaderView.getBtnHistory().addClickHandler(new HistoryHandler());
		userHeaderView.getBtnMainMenu().addClickHandler(new UserReturnToMenuHandler());

		// Add adminHeaderView Handlers

		adminHeaderView.getBtnMainMenu().addClickHandler(new ReturnMainViewHandler());

		// Add createUserView handler
		createUserView.getCreateUserBtn().addClickHandler(new CreateUserHandler());
		createUserView.getBtnCancel().addClickHandler(new ReturnMainViewHandler());
		
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
		
		// Vis admin menu til at starte med
		mainView.showLoginHeader();
		mainView.showLogin();

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
			
			if(event.getSource() == editItemView.getBtnCancel()){
				adminMenu.getBtnShowItems().fireEvent(new ClickEvent() {});
			}
			
			if(event.getSource() == editItemView.getBtnConfirm()){	
				
				itemDTO = editItemView.getitemDTO();
			 
				
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
	     
						if(event.getSource() == showItemListView.getControllerEditBtn()){
							mainView.changeWidget(editItemView);
							
						}
		}
		
		
	}
	
	private class BuyHandler implements ClickHandler {
		
		@Override
		public void onClick(ClickEvent event) {
			if(event.getSource() == basketView.getCancelBtn()){
				basketView.emptyTable();
				mainView.changeWidget(basketView);
				
			}
			
			if(event.getSource() == basketView.getBuyBtn()){
				
				for (int i = 0; i < UserMenuView.tempItemList.size(); i++) {
					for (int j = 0; j < UserMenuView.tempItemList.get(i).getCount(); j++) {

						itemDAO.saveItemToHistory(currentPersonId, UserMenuView.tempItemList.get(i).getId(),
								new AsyncCallback<Void>() {

									@Override
									public void onFailure(Throwable caught) {
										Window.alert("Det får vi ikke brug for" + caught.getMessage());

									}

									@Override
									public void onSuccess(Void result) {
										

									}

								});

					}
				}
				
				personupdate.updatePerson(basketView.getNewSaldo(), currentPersonId, new AsyncCallback<Void>(){

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Serverfejl :" + caught.getMessage());						
					}

					@Override
					public void onSuccess(Void result) {
						
						Window.alert("nu saldo" + basketView.getNewSaldo() );
						Window.alert("sum: " + basketView.getSum());
						basketView.clearSum();
						
					}				
					
				}); 
				
				
				

				
				
			}
			
			Window.alert("Tak for købet");
			UserMenuView.tempItemList.clear();
			userView.showBasketWidget();
			
			
		}
	
	}

	
	private class UpdateBasketHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			
			
			
			if(event.getSource()== userMenuView.getAddToBasketBtn()){	
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
				
				personDTO = editPersonView.getpersonDTO();
			      

				if (editPersonView.validate(showUserListView)){
				// editPersonView.txtSaldo.setStyleName("textBox");
			      
			   //    getsaldo = Double.parseDouble(editPersonView.txtSaldo.getText());
			       
			      // update DTO object
			    
			    //   getsaldo += ShowUserListView.saldoUpdate;
			       
			      //personDTO.setId(56); 
			      //personDTO.setSaldo(getsaldo);
			     
			      
					personupdate.updatePerson(editPersonView.getNewSaldo(), editPersonView.getSaldoUserId(), new AsyncCallback<Void>(){

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

				personDAO.getPersons((new AsyncCallback<List<PersonDTO>>() {
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
									mainView.loginOk(loginView.getUserId());
									loginView.resetError();
									loginView.clearfields();
									mainView.showAdminMenu();
									mainView.showAdminHeader();
									

								}

								else if (person.getAdminStatus() == 0) {
									currentPersonId = person.getId();
									mainView.loginOk(person.getName());
									basketView.setCurrentUserSaldo(person.getSaldo());
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
										}
										);
									
								}
							} else
								loginView.setError();

						}
						loginView.clearfields();
					}
				}));

			// for PersonDTO person : personDAO.getPersons(callback);
			
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
			// replace personDAO call with an RPC
			if (event.getSource() == createUserView.getCreateUserBtn()) {
				if (createUserView.validate()) {

					personDAO.savePerson(new PersonDTO(createUserView.getCurrentPerson().getName(),
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

		}
	}

	// Item user Handler
	private class CreateItemHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// replace personDAO call with an RPC
			if (event.getSource() == createItemView.getcreateItemBtn()) {
				if (createItemView.validate()) {

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

		}
	}

	// Delete item handler
	/*
	 * private class DeleteItemHandler implements ClickHandler{
	 * 
	 * @Override public void onClick(ClickEvent event){ if (event.getSource() ==
	 * adminMenu.getBtnDeleteItem()){
	 * 
	 * deleteItemView.pop(); mainView.changeWidget(deleteItemView); } } }
	 */

	// Delete user view


	// Return to menu handler
	private class ReturnMainViewHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			if (event.getSource() == createUserView.getBtnCancel() || event.getSource() == createItemView.getBtnCancel()
					|| event.getSource() == adminHeaderView.getBtnMainMenu()) {
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
				personDAO.deletePerson(personId, new AsyncCallback<Void>() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Serverfejl :" + caught.getMessage());
					}

					@Override
					public void onSuccess(Void result) {
						showUserListView.deleteEventRow();
					//	mainView.changeWidget(showUserListView);
						Window.alert("Person slettet");

					}
					
				
				});

			if (event.getSource() == adminMenu.getBtnShowUsers()) {
				List<PersonDTO> result = new ArrayList<PersonDTO>();

				personDAO.getPersons(new AsyncCallback<List<PersonDTO>>() {
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
				List<ItemDTO> result = new ArrayList<ItemDTO>();

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
						Window.alert("Får vi ikke brug for");
						
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
	
	

	// Add to basket

	/*
	 * private class AddKaffeToBasketHandler implements ClickHandler{
	 * 
	 * @Override public void onClick(ClickEvent event){ if (event.getSource() ==
	 * userView.getUserMenuView().getKaffeBtn()){
	 * mainView.getUserView().AddItemToBasket(new ItemDTO("Kaffe", 20));
	 * mainView.getUserView().showBasketWidget();
	 * //mainView.getUserView().showHistoryView(); //Window.alert("Hej"); } }
	 * 
	 * 
	 * }
	 */

	/*
	 * private class AddBananToBasketHandler implements ClickHandler{
	 * 
	 * @Override public void onClick(ClickEvent event){ if (event.getSource() ==
	 * userView.getUserMenuView().getBananBtn()){
	 * mainView.getUserView().AddItemToBasket(new ItemDTO("Banan", 10));
	 * mainView.getUserView().showBasketWidget();
	 * //mainView.getUserView().showHistoryView(); } }
	 * 
	 * 
	 * }
	 */

}
