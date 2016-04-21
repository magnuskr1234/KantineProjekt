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

	// Service classes for async callbacks to server
	private PersonServiceAsync personServiceCall = GWT.create(PersonService.class);
	private ItemServiceAsync itemServiceCall = GWT.create(ItemService.class);

	// reference to data transfer object
	private PersonDTO personDTO;
	private ItemDTO itemDTO;

	//public int currentPersonId;
	public PersonDTO currentPerson;

	private AdminController adminController;
	private UserController userController;

	public MainController(MainView mainView) {
		//Instantiate pagecontroller
		this.mainView = mainView;

		// Reference
		loginView = mainView.getLoginView();

		//Add clickhandler for login button
		loginView.getBtnOk().addClickHandler(new LoginHandler());

		adminController = new AdminController(mainView, personServiceCall, itemServiceCall);	
		userController = new UserController(mainView, personServiceCall, itemServiceCall);

	}

	// Login handler
	private class LoginHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			if (event.getSource() == loginView.getBtnOk())
				personServiceCall.getPerson(loginView.getUserId(), loginView.getUserPw(), new AsyncCallback<PersonDTO>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Serverfejl :" + caught.getMessage());
					}

					@Override
					public void onSuccess(PersonDTO person) {

						if(person == null){
							mainView.getLoginView().setError();

						} else if (person.getAdminStatus() == 1) {
							adminController.setCurrentPerson(person);
							loginView.resetError();
							loginView.clearfields();
							mainView.changeView(mainView.getAdminView());
							mainView.getAdminView().changeWidget(mainView.getAdminView().getadminMenu());
						
						} else if (person.getAdminStatus() == 0) {

							userController.setCurrentPerson(person);
							//currentPersonId = person.getId();
							mainView.getUserView().loginOk(person.getName());
							mainView.getUserView().updateSaldoHeader(person.getSaldo());
							mainView.getUserView().getBasketView().setCurrentUserSaldo(person.getSaldo());
							UserMenuView.setcuSaldo(person.getSaldo());
							loginView.resetError();
							loginView.clearfields();
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
