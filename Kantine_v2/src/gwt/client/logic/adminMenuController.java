package gwt.client.logic;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootLayoutPanel;

import gwt.client.ui.*;




public class adminMenuController {

	// References for views
	private MainViewAdmin mainViewAdmin;
	
	private adminMenu adminMenu;
	private AdminHeaderView adminHeaderView;
	
	private CreateUser createUserView;
	private CreateItemView createItemView;
	
	private DeleteItemView deleteItemView;
	private DeleteUserView deleteUserView;
	
	private EditPersonView editPersonView;
	private LoginView loginView;
	
	private ShowUserList showUserListView;
	
	private StatisticView statistic;
	
	
	// Hardcoded login details
	 private final String userId = "Peter";
	 private final String userPw = "1234";
	 
	 
	public adminMenuController(){
		
		//Instantiate pagecontroller
		mainViewAdmin = new MainViewAdmin();
		
		//Get references to subviews
		adminHeaderView = mainViewAdmin.getadminHeaderView();
		adminMenu = mainViewAdmin.getadminMenu(); 
		createItemView = mainViewAdmin.getcreateItem();
		createUserView = mainViewAdmin.getcreateUser();
		deleteItemView = mainViewAdmin.getdeleteItem();
		deleteUserView = mainViewAdmin.getdeleteUserView();
		editPersonView = mainViewAdmin.geteditPerson();
		loginView = mainViewAdmin.getLoginView();
		showUserListView = mainViewAdmin.getshowUserList();
		statistic = mainViewAdmin.getstatistic();
		
		//Add loginview handlers
		loginView.getBtnOk().addClickHandler(new LoginHandler());
	    loginView.getBtnCancel().addClickHandler(new LoginHandler());
	    
	    //Add adminMenuView Handlers
	    adminMenu.getBtnCreateItem().addClickHandler(new CreateItemViewHandler());		
		adminMenu.getBtnCreateUser().addClickHandler(new CreateUserHandler());
		adminMenu.getBtnDeleteItem().addClickHandler(new DeleteItemHandler());
		adminMenu.getBtnDeleteUser().addClickHandler(new DeleteUserHandler());
		adminMenu.getBtnShowUsers().addClickHandler(new ShowUserListHandler());
		adminMenu.getBtnStatistic().addClickHandler(new StatisticsHandler());
		
		adminHeaderView.getBtnLogout().addClickHandler(new LogoutHandler());
		adminHeaderView.getBtnMainMenu().addClickHandler(new ReturnMainViewHandler());
		
		//Add createUserView handler		
		createUserView.getBtnCancel().addClickHandler(new ReturnMainViewHandler());
		
		//Add createItemView handler
		createItemView.getBtnCancel().addClickHandler(new ReturnMainViewHandler());
		
		
		
		

	
		RootLayoutPanel rp = RootLayoutPanel.get();
		rp.add(mainViewAdmin);
	}
	
	// Logout handler
	private class LogoutHandler implements ClickHandler{
		
		@Override
		public void onClick(ClickEvent event){
			if (event.getSource() == adminHeaderView.getBtnLogout()){
				mainViewAdmin.changeWidget(loginView);
				
			}	
		}
	}
	
	// Create user Handler
	private class CreateUserHandler implements ClickHandler{
		
		@Override
		public void onClick(ClickEvent event){
			if (event.getSource() == adminMenu.getBtnCreateUser()){
				mainViewAdmin.changeWidget(createUserView);
			}			
		}
	}
	
	// Delete item handler
	private class DeleteItemHandler implements ClickHandler{
		
		@Override
		public void onClick(ClickEvent event){
			if (event.getSource() == adminMenu.getBtnDeleteItem()){
				mainViewAdmin.changeWidget(deleteItemView);
			}			
		}
	}
	
	// Delete user view
	private class DeleteUserHandler implements ClickHandler{
		
		@Override
		public void onClick(ClickEvent event){
			if (event.getSource() == adminMenu.getBtnDeleteUser()){
				mainViewAdmin.changeWidget(deleteUserView);
			}			
		}
	}
	
	// Return to menu handler
	private class ReturnMainViewHandler implements ClickHandler{
		@Override
		public void onClick(ClickEvent event){
			if (event.getSource() == createUserView.getBtnCancel() || event.getSource() == createItemView.getBtnCancel() || 
					event.getSource() == adminHeaderView.getBtnMainMenu()){
				mainViewAdmin.changeWidget(adminMenu);
			}
		}
	}
	
	// Create item handler
	private class CreateItemViewHandler implements ClickHandler{
		
		@Override
		public void onClick(ClickEvent event){
			if (event.getSource() == adminMenu.getBtnCreateItem()){
				mainViewAdmin.changeWidget(createItemView);
			}
		}
	}
	
	//Show user list handler
	private class ShowUserListHandler implements ClickHandler{
		
		@Override
		public void onClick(ClickEvent event){
			if (event.getSource() == adminMenu.getBtnShowUsers()){
				showUserListView.pop();
				mainViewAdmin.changeWidget(showUserListView);
			}			
		}
	}
	
	//Show statistics handler
	private class StatisticsHandler implements ClickHandler{
		
		@Override
		public void onClick(ClickEvent event){
			if (event.getSource() == adminMenu.getBtnStatistic()){
				mainViewAdmin.changeWidget(statistic);
			}			
		}
	}
	
	// Login handler
	  private class LoginHandler implements ClickHandler {

		    @Override
		    public void onClick(ClickEvent event) {
		      if (event.getSource() == loginView.getBtnOk())
		        if (loginView.getUserId().equals(userId) && loginView.getUserPw().equals(userPw)) {
		          mainViewAdmin.loginOk(loginView.getUserId());
		          loginView.resetError();
		          loginView.clearfields();		
		          mainViewAdmin.showAdminMenu();
		          mainViewAdmin.showAdminHeader();
		        } else
		          loginView.setError();
		      else if (event.getSource() == loginView.getBtnCancel()) {
		        loginView.resetError();
		        loginView.clearfields();
		        mainViewAdmin.loginCancel();
		      }
		    }
		  }
	
	
}
