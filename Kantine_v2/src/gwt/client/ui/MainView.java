package gwt.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.client.admin.ui.AdminHeaderView;
import gwt.client.admin.ui.CreateItemView;
import gwt.client.admin.ui.CreateUserView;
import gwt.client.admin.ui.DeleteItemView;
import gwt.client.admin.ui.DeleteUserView;
import gwt.client.admin.ui.EditPersonView;
import gwt.client.admin.ui.ShowUserListView;
import gwt.client.admin.ui.StatisticView;
import gwt.client.admin.ui.AdminMenuView;
import gwt.client.user.ui.UserHeaderView;
import gwt.client.user.ui.UserMenuView;
import gwt.client.user.ui.UserView;

import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.event.dom.client.ClickEvent;

/**
 * Her bliver de forskellige content view tilføjet til decklayoutpanel for content og header. 
 * @author magnusrasmussen
 *
 */
public class MainView extends Composite {

	private static MainViewnUiBinder uiBinder = GWT.create(MainViewnUiBinder.class);
	//Layout panel
	@UiField DeckLayoutPanel contentPanel;
	@UiField DeckLayoutPanel headerPanel;

	// Content views
	private AdminMenuView adminMenu;
	private CreateItemView createItem;
	private CreateUserView createUser;
	private DeleteItemView deleteItem;
	private DeleteUserView deleteUser;
	private EditPersonView editPerson;
	private LoginView loginView;
	private ShowUserListView showUserList;
	private StatisticView statistic;
	private UserMenuView userMenu;
	private UserView userView;
	
	// Header views
	private AdminHeaderView adminHeaderView;
	private LoginHeaderView loginHeaderView;
	private UserHeaderView userHeaderView;

	interface MainViewnUiBinder extends UiBinder<Widget, MainView> {
	}

	public MainView() {
		initWidget(uiBinder.createAndBindUi(this));	
		
		//Make content view
		adminMenu = new AdminMenuView();
		createItem = new CreateItemView();
		createUser = new CreateUserView();
		deleteItem = new DeleteItemView();
		deleteUser = new DeleteUserView();
		editPerson = new EditPersonView();
		showUserList = new ShowUserListView();
		statistic = new StatisticView();
		userMenu = new UserMenuView();
		userView = new UserView();
		
		
		// Header & Login 
		adminHeaderView = new AdminHeaderView();
		userHeaderView = new UserHeaderView();
		loginView = new LoginView();
		loginHeaderView = new LoginHeaderView();
	   
		//add contentviews to decklayout panel
		contentPanel.add(adminMenu);
		contentPanel.add(createItem);
		contentPanel.add(createUser);	
		contentPanel.add(deleteItem);
		contentPanel.add(deleteUser);
		contentPanel.add(editPerson);
		contentPanel.add(showUserList);
		contentPanel.add(statistic);
		contentPanel.add(userMenu);
		contentPanel.add(userView);
	
		// Add Header & login 
		headerPanel.add(adminHeaderView);		
		headerPanel.add(userHeaderView);
		headerPanel.add(loginHeaderView);
		contentPanel.add(loginView);
		
		
		// Initially show login view
		headerPanel.showWidget(loginHeaderView);
		contentPanel.showWidget(loginView);
	
	}
	
	// Show content widget
	public void changeWidget(Widget w){
		contentPanel.showWidget(w);
	}
	
	// Get view
	public AdminMenuView getadminMenu() {
		return adminMenu;
	}
	
	public CreateItemView getcreateItem(){
		return createItem;
	}
	
	public CreateUserView getcreateUser(){
		return createUser;
	}
	
	public DeleteItemView getdeleteItem(){
		return deleteItem;
	}

	public DeleteUserView getdeleteUserView(){
		return deleteUser;
	}
	public EditPersonView geteditPerson(){
		return editPerson;
	}
	
	public ShowUserListView getshowUserList(){
		return showUserList;
	}
	
	public StatisticView getstatistic(){
		return statistic;
	}
	
	public UserMenuView getuserMenu(){
		return userMenu;
	}
	
	public UserView getUserView(){
		return userView;
	}
	
	public LoginView getLoginView() {
	    return loginView;
	  }
	
	
	// Headers
	public AdminHeaderView getadminHeaderView(){
		return adminHeaderView;
	}
	
	public LoginHeaderView getloginHeaderView(){
		return loginHeaderView;
	}
	
	public UserHeaderView getuserHeaderView(){
		return userHeaderView;
	}
	
	// Show Create Item
	public void showCreateItem(){
		changeWidget(createItem);
	}
	
	// Show Create User
	public void showCreateUser(){
		changeWidget(createUser);
	}
	
	// Show delete item
	public void showDeleteItem(){
		changeWidget(deleteItem);
	}
	
	//Show Delete user
	public void showDeleteUser(){
		changeWidget(deleteUser);
	}
	// Show Edit Person
	public void showEditPerson(){
		changeWidget(editPerson);
	}
	
	// Show User List
	public void showUserList(){
		changeWidget(showUserList);
	}
	
	// Show User Menu
	public void showUserMenu(){
		changeWidget(userMenu);
	}
	
	// Show User view
	public void showUserView(){
		changeWidget(userView);
	}
	
	// Show statistic
	public void showStatistic(){
		changeWidget(statistic);
	}
	
	//Show Admin Menu 
	public void showAdminMenu(){
		changeWidget(adminMenu);
	}
	
	//Show admin Header
	public void showAdminHeader(){
		headerPanel.showWidget(adminHeaderView);
	}
	
	//Show login header
	public void showLoginHeader(){
		headerPanel.showWidget(loginHeaderView);
	}
	
	//Show user header
	public void showUserHeader(){
		headerPanel.showWidget(userHeaderView);
	}

	//Login 
	public void showLogin(){
		changeWidget(loginView);
		headerPanel.showWidget(loginHeaderView);
		
	}
	
	// Admin Logout
	public void adminLogout(){
		
	}
	
	  public void loginOk(String user) {
		 	
		  }

		  // login has been cancelled - update pages
		  public void loginCancel() {
		  
		  }
}
