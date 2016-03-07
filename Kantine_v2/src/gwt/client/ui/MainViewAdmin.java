package gwt.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckLayoutPanel;
import com.google.gwt.user.client.ui.Widget;


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
public class MainViewAdmin extends Composite {

	private static MainViewAdminUiBinder uiBinder = GWT.create(MainViewAdminUiBinder.class);
	//Layout panel
	@UiField DeckLayoutPanel contentPanel;
	@UiField DeckLayoutPanel adminHeader;

	// Content views
	private adminMenu adminMenu;
	private CreateItemView createItem;
	private CreateUser createUser;
	private DeleteItemView deleteItem;
	private DeleteUserView deleteUser;
	private EditPersonView editPerson;
	private LoginView loginView;
	private ShowUserList showUserList;
	private StatisticView statistic;
	
	// Header views
	private AdminHeaderView adminHeaderView;

	interface MainViewAdminUiBinder extends UiBinder<Widget, MainViewAdmin> {
	}

	public MainViewAdmin() {
		initWidget(uiBinder.createAndBindUi(this));	
		
		//Make content view
		adminMenu = new adminMenu();
		createItem = new CreateItemView();
		createUser = new CreateUser();
		deleteItem = new DeleteItemView();
		deleteUser = new DeleteUserView();
		editPerson = new EditPersonView();
		showUserList = new ShowUserList();
		statistic = new StatisticView();
		
		// Header & Login 
		adminHeaderView = new AdminHeaderView();
		loginView = new LoginView();
	   
		//add contentviews to decklayout panel
		contentPanel.add(adminMenu);
		contentPanel.add(createItem);
		contentPanel.add(createUser);	
		contentPanel.add(deleteItem);
		contentPanel.add(deleteUser);
		contentPanel.add(editPerson);
		contentPanel.add(showUserList);
		contentPanel.add(statistic);
	
		// Add Header & login 
		adminHeader.add(adminHeaderView);		
		contentPanel.add(loginView);
		
		// Initially show login view
		adminHeader.showWidget(adminHeaderView);
		contentPanel.showWidget(adminMenu);			
	
	}
	
	// Show content widget
	public void changeWidget(Widget w){
		contentPanel.showWidget(w);
	}
	
	// Get view
	public adminMenu getadminMenu() {
		return adminMenu;
	}
	
	public CreateItemView getcreateItem(){
		return createItem;
	}
	
	public CreateUser getcreateUser(){
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
	
	public ShowUserList getshowUserList(){
		return showUserList;
	}
	
	public StatisticView getstatistic(){
		return statistic;
	}
	
	public LoginView getLoginView() {
	    return loginView;
	  }
	
	public AdminHeaderView getadminHeaderView(){
		return adminHeaderView;
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
		adminHeader.showWidget(adminHeaderView);
	}

	//Login 
	public void adminLogin(){
		changeWidget(loginView);
		
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
