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
import gwt.client.ui.MainView;
import gwt.client.ui.admin.AdminHeaderView;
import gwt.client.ui.admin.AdminMenuView;
import gwt.client.ui.admin.AdminView;
import gwt.client.ui.login.LoginHeaderView;
import gwt.client.ui.login.LoginView;
import gwt.client.ui.user.BasketView;
import gwt.client.ui.user.UserHeaderView;
import gwt.client.ui.user.UserMenuView;
import gwt.client.ui.user.UserView;
import gwt.shared.ItemDTO;
import gwt.shared.PersonDTO;

/**
 * The main controller handles the login process
 * @author magnusrasmussen
 *
 */
public class MainController {

	// References for views
	private AdminView adminView;
	private MainView mainView;
	private LoginView loginView;
	private LoginHeaderView loginHeaderView;
	private UserMenuView userMenuView;
	private BasketView basketView;
	private AdminMenuView adminMenu;
	private UserView userView;
	private UserHeaderView userHeaderView;

	private AdminHeaderView adminHeaderView;

	// Service classes for async callbacks to server
	private PersonServiceAsync personServiceCall = GWT.create(PersonService.class);
	private ItemServiceAsync itemDAO = GWT.create(ItemService.class);

	// reference to data transfer object
	private PersonDTO personDTO;
	private ItemDTO itemDTO;

	public int currentPersonId;
	public PersonDTO currentPerson;

	private AdminController adminController;
	private UserController userController;

	public MainController(MainView mainView) {
		
		this.mainView = mainView;

		loginView = mainView.getLoginView();
		loginView.getBtnOk().addClickHandler(new LoginHandler());
	/*	userView = new UserView();
		adminView = new AdminView();
		//loginHeaderView = mainView.getloginHeaderView();
		loginView = mainView.getLoginView();
		
		// Fejl her
		basketView = userView.getBasketView();
		Window.alert("-0,5.");
		userMenuView = userView.getUserMenuView();
		
		
		adminHeaderView = adminView.getadminHeaderView();
		adminMenu = adminView.getadminMenu();

		userMenuView = userView.getUserMenuView();
		
		userHeaderView = userView.getuserHeaderView();

		// Add loginview handlers
		loginView.getBtnOk().addClickHandler(new LoginHandler());
		
		// Add logoutview handlers
		adminHeaderView.getBtnLogout().addClickHandler(new LogoutHandler());
		userHeaderView.getBtnLogout().addClickHandler(new LogoutHandler());

		Window.alert("0.");
		// Show login when compiled.
		mainView.changeView(loginView);
		mainView.changeView(loginHeaderView);
	*/
	
		adminController = new AdminController(mainView, personServiceCall, itemDAO);
	
		userController = new UserController(mainView, personServiceCall, itemDAO);

	}

	// Login handler
	private class LoginHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			if (event.getSource() == loginView.getBtnOk())

				
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
								adminController.setCurrentPerson(person);
								loginView.resetError();
								loginView.clearfields();
								mainView.changeView(mainView.getAdminView());
								/*
								adminView.showAdminMenu();
								adminView.showAdminHeader();
								*/
							}

							else if (person.getAdminStatus() == 0) {
						
								userController.setCurrentPerson(person);
								currentPersonId = person.getId();
								mainView.getUserView().loginOk(person.getName());
								mainView.getUserView().updateSaldoHeader(person.getSaldo());
								mainView.getUserView().getBasketView().setCurrentUserSaldo(person.getSaldo());
								userMenuView.setcuSaldo(person.getSaldo());
								loginView.resetError();
								loginView.clearfields();
								mainView.changeView(mainView.getUserView());
								
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
				mainView.changeView(loginView);
				mainView.changeView(loginHeaderView);
			}
		}
	}

}
