package gwt.client.ui;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.client.ui.admin.AdminView;
import gwt.client.ui.login.LoginView;
import gwt.client.ui.user.UserView;

/**
 * Mainview is the container for all the other views. 
 *
 */
public class MainView extends Composite{

	// The main panel in this class
	private DeckLayoutPanel contentPanel;

	// The three main panels of the application
	private LoginView loginView;
	private UserView userView;
	private AdminView adminView;

	/**
	 * The constructor of this class which is creating an instance of the panels and
	 * adding them to the DeckLayoutPanel
	 */
	public MainView() {
		contentPanel = new DeckLayoutPanel();

		loginView = new LoginView();
		contentPanel.add(loginView);

		userView = new UserView();
		contentPanel.add(userView);

		adminView = new AdminView();
		contentPanel.add(adminView);

		//The panel that is shown when the application starts
		contentPanel.showWidget(loginView);

		//Init the main widget in this class
		initWidget(contentPanel);
	}

	/**
	 * This method is used to change the mainView
	 * @param panel
	 */
	public void changeView(Widget panel) {
		contentPanel.showWidget(panel);
	}

	/**
	 * Getters to change panel. 
	 * @return
	 */
	public LoginView getLoginView() {
		return loginView;
	}

	public UserView getUserView() {
		return userView;
	}

	public AdminView getAdminView() {
		return adminView;
	}

}
