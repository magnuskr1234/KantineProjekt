package gwt.client.logic;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import gwt.client.service.ItemService;
import gwt.client.service.ItemServiceAsync;
import gwt.client.service.PersonService;
import gwt.client.service.PersonServiceAsync;
import gwt.client.ui.MainView;
import gwt.client.ui.login.LoginView;
import gwt.client.ui.user.UserMenuView;
import gwt.shared.ItemDTO;
import gwt.shared.PersonDTO;

/**
 * The main controller handles the login process
 * @author magnusrasmussen
 *
 */
public class MainController {

	// References for views
	private MainView mainView;
	private LoginView loginView;
	private UserMenuView userMenuView;

	// Service classes for async callbacks to server
	private PersonServiceAsync personServiceCall = GWT.create(PersonService.class);
	private ItemServiceAsync itemServiceCall = GWT.create(ItemService.class);

	//public int currentPersonId;
	public PersonDTO currentPerson;

	// Reference for controllers
	private AdminController adminController;
	private UserController userController;

	/**
	 * Contructor for maincontroller. 
	 * @param mainView
	 */
	public MainController(MainView mainView) {
		//Instantiate pagecontroller
		this.mainView = mainView;

		// Reference
		loginView = mainView.getLoginView();

		//Add clickhandler for login button
		loginView.getBtnOk().addClickHandler(new LoginHandler());

		// Create controllers 
		adminController = new AdminController(mainView, personServiceCall, itemServiceCall);	
		userController = new UserController(mainView, personServiceCall, itemServiceCall);

	}

	/**
	 * Clickhandler for login. 
	 * @author magnusrasmussen
	 *
	 */
	private class LoginHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
				personServiceCall.getPerson(loginView.getUserId().toLowerCase(), loginView.getUserPw(), new AsyncCallback<PersonDTO>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Serverfejl :" + caught.getMessage());
					}

					@Override
					public void onSuccess(PersonDTO person) {
						
						//Check if user is null
						if(person == null){
							mainView.getLoginView().setError();
							
							// Check admin status for user
						} else if (person.getAdminStatus() == 1) {
							adminController.setCurrentPerson(person);
							loginView.resetError();
							mainView.changeView(mainView.getAdminView());
							mainView.getAdminView().changeWidget(mainView.getAdminView().getadminMenu());
						
						} else if (person.getAdminStatus() == 0) {
							userController.setCurrentPerson(person);
							mainView.getUserView().loginOk(person.getName());
							mainView.getUserView().updateSaldoHeader(person.getSaldo());
							mainView.getUserView().getBasketView().setCurrentUserSaldo(person.getSaldo());
							UserMenuView.setcuSaldo(person.getSaldo());
							loginView.resetError();
							mainView.changeView(mainView.getUserView());
							mainView.getUserView().changeWidget(mainView.getUserView().getUserMenuView());

							// Make RPC call to get all items.
							itemServiceCall.getItems(new AsyncCallback<List<ItemDTO>>() {

								@Override
								public void onFailure(Throwable caught) {
									Window.alert("Serverfejl :" + caught.getMessage());
								}

								@Override
								public void onSuccess(List<ItemDTO> result) {
									//Populate user menu with items from database
									mainView.getUserView().showMenuView(result);
								}
							});
						}	
						//Clear login fields
						loginView.clearfields();
			
					}
				});
		}
	}
}
