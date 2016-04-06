package gwt.client.logic;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.client.service.ItemServiceAsync;
import gwt.client.service.PersonServiceAsync;
import gwt.client.ui.MainView;
import gwt.client.ui.admin.AdminHeaderView;
import gwt.client.ui.admin.AdminMenuView;
import gwt.client.ui.admin.AdminView;
import gwt.client.ui.admin.CreateItemView;
import gwt.client.ui.admin.CreateUserView;
import gwt.client.ui.admin.EditItemView;
import gwt.client.ui.admin.EditPersonView;
import gwt.client.ui.admin.ShowItemListView;
import gwt.client.ui.admin.ShowUserListView;
import gwt.client.ui.admin.StatisticView;
import gwt.client.ui.user.UserMenuView;
import gwt.shared.ItemDTO;
import gwt.shared.PersonDTO;

/**
 * The admin controller handles the admin views and logic.
 * @author magnusrasmussen
 *
 */
public class AdminController {
	
	private AdminView adminView;
	private MainView mainView;
	private AdminMenuView adminMenu;
	private AdminHeaderView adminHeaderView;

	private CreateUserView createUserView;
	private CreateItemView createItemView;
	private StatisticView statisticView;

	private EditItemView editItemView;
	private EditPersonView editPersonView;

	private ShowUserListView showUserListView;
	private ShowItemListView showItemListView;

	private StatisticView statistic;

	// Service classes for async callbacks to server
	private ItemServiceAsync itemDAO;
	private PersonServiceAsync personServiceCall;


	// reference to data transfer object
	private PersonDTO personDTO;
	private ItemDTO itemDTO;

	public int currentPersonId;
	public PersonDTO currentPeron;
	
	public AdminController(MainView mainView, PersonServiceAsync personServiceCall, ItemServiceAsync itemDAO){
		
		this.mainView = mainView;
		this.adminView = mainView.getAdminView();
		this.itemDAO = itemDAO;
		this.personServiceCall = personServiceCall;
		
		adminHeaderView = adminView.getadminHeaderView();
		adminMenu = adminView.getadminMenu();
		
		createItemView = adminView.getcreateItem();
		createUserView = adminView.getcreateUser();
		editItemView = adminView.geteditItem();
		editPersonView = adminView.geteditPerson();
		
		
		showUserListView = adminView.getshowUserList();
		showItemListView = adminView.getshowItemList();
		statistic = adminView.getstatistic();
		
		
		
		// Add adminMenuView Handlers
		adminMenu.getBtnCreateItem().addClickHandler(new CreateItemViewHandler());
		adminMenu.getBtnCreateUser().addClickHandler(new CreateUserHandler());
		adminMenu.getBtnShowUsers().addClickHandler(new ShowUserListHandler());
		adminMenu.getBtnShowItems().addClickHandler(new ShowItemListHandler());
		adminMenu.getBtnStatistic().addClickHandler(new StatisticsHandler());
		
		
		// Add adminHeaderView Handlers
		adminHeaderView.getBtnMainMenu().addClickHandler(new ReturnMainViewHandler());
	 	adminHeaderView.getBtnLogout().addClickHandler(new LogoutHandler());
		
		// Add createUserView handler
		createUserView.getCreateUserBtn().addClickHandler(new CreateUserHandler());

		// Add update saldo handler
		editPersonView.getBtnCancel().addClickHandler(new UpdateSaldoHandler());
		editPersonView.getBtnConfirm().addClickHandler(new UpdateSaldoHandler());

		// Add createItemView handler
		createItemView.getcreateItemBtn().addClickHandler(new CreateItemHandler());

		// user options handler
		showUserListView.getControllerDeleteBtn().addClickHandler(new ShowUserListHandler());
		showUserListView.getControllerEditBtn().addClickHandler(new UpdateSaldoHandler());
		
		// item options handler
		showItemListView.getControllerDeleteBtn().addClickHandler(new ShowItemListHandler());

		//Edit item price handler
		editItemView.getBtnCancel().addClickHandler(new UpdateItemPriceHandler());
		editItemView.getBtnConfirm().addClickHandler(new UpdateItemPriceHandler());
		showItemListView.getControllerEditBtn().addClickHandler(new UpdateItemPriceHandler());
		

		
		
		
		
		
	}
	
	public void setCurrentPerson(PersonDTO currentPerson){
		this.currentPeron = currentPerson;
		this.currentPersonId = currentPerson.getId();
	}
	// Logout handler
	private class LogoutHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
		mainView.changeView(mainView.getLoginView());
		}
	}
	
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
				adminView.changeWidget(editItemView);

			}
		}
	}
	
	
	private class UpdateSaldoHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {		
			if(event.getSource() == editPersonView.getBtnConfirm()){	

				
				
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
							adminView.changeWidget(showUserListView);
						}				

					}); 
				}
			}
			if(event.getSource() == editPersonView.getBtnCancel()){
				editPersonView.clearFields();
				adminView.changeWidget(showUserListView);
			}
			if(event.getSource() == showUserListView.getControllerEditBtn()){
				adminView.changeWidget(editPersonView);
			}
		}
	}
	
	
	private class CreateUserHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (event.getSource() == adminMenu.getBtnCreateUser()) {
				adminView.changeWidget(createUserView);
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
	
	
	private class CreateItemHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			
			if (event.getSource() == createItemView.getcreateItemBtn())
				
		
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
			if (event.getSource() == adminHeaderView.getBtnMainMenu()) {
				createItemView.clearFields();
				createUserView.clearFields();
				adminView.changeWidget(adminMenu);
			}
		}
	}

	// Create item handler
	private class CreateItemViewHandler implements ClickHandler {
		
		@Override
		public void onClick(ClickEvent event) {
			if (event.getSource() == adminMenu.getBtnCreateItem()) {
				adminView.changeWidget(createItemView);
			}
		}
	}

	// Show user list handler
	private class ShowUserListHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			int personId = showUserListView.getPersonId();

			if (event.getSource() == showUserListView.getControllerDeleteBtn())
				if(Window.confirm("Er du sikker på at du vil slette brugeren?"))
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
			adminView.changeWidget(showUserListView);
		}
	}

	private class ShowItemListHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			int itemId = showItemListView.getItemId();

			if (event.getSource() == showItemListView.getControllerDeleteBtn())
				if(Window.confirm("Er du sikker på at du vil slette varen?"))
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
			adminView.changeWidget(showItemListView);
		}
	}

	// Show statistics handler
	private class StatisticsHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (event.getSource() == adminMenu.getBtnStatistic()) {
				statistic.clearStatSum();
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
				adminView.changeWidget(statistic);
			}
		}
	}
}
