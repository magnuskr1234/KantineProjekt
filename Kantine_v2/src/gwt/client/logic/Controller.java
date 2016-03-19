package gwt.client.logic;

import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.javascript.host.Console;
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
import gwt.client.ui.admin.EditPersonView;
import gwt.client.ui.admin.ShowItemListView;
import gwt.client.ui.admin.ShowUserListView;
import gwt.client.ui.admin.StatisticView;
import gwt.client.ui.login.LoginHeaderView;
import gwt.client.ui.login.LoginView;
import gwt.client.ui.user.UserHeaderView;
import gwt.client.ui.user.UserMenuView;
import gwt.client.ui.user.UserView;
import gwt.server.PersonDB;
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

	private CreateUserView createUserView;
	private CreateItemView createItemView;

	private DeleteItemView deleteItemView;
	private DeleteUserView deleteUserView;

	private EditPersonView editPersonView;

	private ShowUserListView showUserListView;
	private ShowItemListView showItemListView;

	private StatisticView statistic;

	// Referencer til user views
	private UserMenuView userMenuView;
	private UserView userView;
	private UserHeaderView userHeaderView;

	// Service
	private PersonServiceAsync personDAO = GWT.create(PersonService.class);
	private ItemServiceAsync itemDAO = GWT.create(ItemService.class);

	//

	// reference to data transfer object
	private PersonDTO personDTO;


	public Controller() {

		// Instantiate pagecontroller
		mainView = new MainView();

		// Get references to subviews for admin
		adminHeaderView = mainView.getadminHeaderView();
		adminMenu = mainView.getadminMenu();
		createItemView = mainView.getcreateItem();
		createUserView = mainView.getcreateUser();
		deleteItemView = mainView.getdeleteItem();
		deleteUserView = mainView.getdeleteUserView();
		editPersonView = mainView.geteditPerson();
		loginHeaderView = mainView.getloginHeaderView();
		loginView = mainView.getLoginView();
		showUserListView = mainView.getshowUserList();
		showItemListView = mainView.getshowItemList();
		statistic = mainView.getstatistic();

		// Referncer til subview for user
		userMenuView = mainView.getuserMenu();
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

		showUserListView.getControllerDeleteBtn().addClickHandler(new ShowUserListHandler());
		showUserListView.getControllerEditBtn().addClickHandler(new UpdateSaldoHandler());

		showItemListView.getControllerDeleteBtn().addClickHandler(new ShowItemListHandler());

		// Vis admin menu til at starte med
		mainView.showAdminHeader();
		mainView.showAdminMenu();

		RootLayoutPanel rp = RootLayoutPanel.get();
		rp.add(mainView);
	}

	/**
	 * Denne klasse håndterer knapperne til opdatering af saldo
	 * @author magnusrasmussen
	 *		
	 */
	private class UpdateSaldoHandler implements ClickHandler {
		
		@Override
		public void onClick(ClickEvent event) {		
			if(event.getSource() == editPersonView.getBtnConfirm()){	
				if(editPersonView.validate())
				personDTO = editPersonView.getpersonDTO();
				
					Window.alert("tillykke du er noget så langt");
					personDAO.updatePerson(personDTO, new AsyncCallback<Void>(){

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Serverfejl :" + caught.getMessage());						
					}

					@Override
					public void onSuccess(Void result) {
						Window.alert("Saldo opdateret!");					
					}				
				}); 
				
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
									mainView.loginOk(loginView.getUserId());
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
			if (event.getSource() == createItemView.getcreateItemBtn()) {
				mainView.changeWidget(createItemView);
			}
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
									Window.alert("Person gemt");
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
	private class DeleteUserHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (event.getSource() == adminMenu.getBtnDeleteUser()) {
				// showUserListView.pop();
				mainView.changeWidget(showUserListView);
			}

		}
	}

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

						mainView.changeWidget(showItemListView);
						Window.alert("vare slettet");

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
