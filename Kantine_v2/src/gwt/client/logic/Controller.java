package gwt.client.logic;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootLayoutPanel;

import gwt.client.admin.ui.AdminHeaderView;
import gwt.client.admin.ui.CreateItemView;
import gwt.client.admin.ui.CreateUserView;
import gwt.client.admin.ui.DeleteItemView;
import gwt.client.admin.ui.DeleteUserView;
import gwt.client.admin.ui.EditPersonView;
import gwt.client.admin.ui.ShowUserListView;
import gwt.client.admin.ui.StatisticView;
import gwt.client.admin.ui.AdminMenuView;
import gwt.client.ui.*;
import gwt.client.user.ui.UserHeaderView;
import gwt.client.user.ui.UserMenuView;
import gwt.client.user.ui.UserView;
import gwt.shared.ItemDTO;

/**
 * Pagecontroller til at styre hvilken side som aktuelt bliver vist for administratoren, ved at tilføje clickhandlers
 * som sørger for at skifte til det rette panel, gennem mainViewAdmin. 
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
	
	private StatisticView statistic;
	
	// Referencer til user views
	private UserMenuView userMenuView;
	private UserView userView;
	private UserHeaderView userHeaderView;
	 
	// Hardcoded login details for user
		private final String userId = "Peter";
		private final String userPw = "1234";
		
		// Hardcoded login for admin
		private final String adminId = "Hans";
		private final String adminPw = "1234";
	 
	public Controller(){
		
		//Instantiate pagecontroller
		mainView = new MainView();
		
		//Get references to subviews for admin
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
		statistic = mainView.getstatistic();
		
		
		//Referncer til subview for user
		userMenuView = mainView.getuserMenu();
		userView = mainView.getUserView();
		userHeaderView = mainView.getuserHeaderView();
		
		//Add loginview handlers
		loginView.getBtnOk().addClickHandler(new LoginHandler());
		loginView.getBtnCancel().addClickHandler(new LoginHandler());	
		
		//Add logoutview handlers
		adminHeaderView.getBtnLogout().addClickHandler(new LogoutHandler());
		userHeaderView.getBtnLogout().addClickHandler(new LogoutHandler());
	    
	    //Add adminMenuView Handlers
	    adminMenu.getBtnCreateItem().addClickHandler(new CreateItemViewHandler());		
		adminMenu.getBtnCreateUser().addClickHandler(new CreateUserHandler());
		adminMenu.getBtnDeleteItem().addClickHandler(new DeleteItemHandler());
		adminMenu.getBtnDeleteUser().addClickHandler(new DeleteUserHandler());
		adminMenu.getBtnShowUsers().addClickHandler(new ShowUserListHandler());
		adminMenu.getBtnStatistic().addClickHandler(new StatisticsHandler());
		
		//Tilføj user menu handlers
		userView.getUserMenuView().getKaffeBtn().addClickHandler(new AddKaffeToBasketHandler());
		userView.getUserMenuView().getBananBtn().addClickHandler(new AddBananToBasketHandler());
		
		//Add userHeader handlers
		userHeaderView.getBtnHistory().addClickHandler(new HistoryHandler());
		userHeaderView.getBtnMainMenu().addClickHandler(new UserReturnToMenuHandler());
		
		// Add adminHeaderView Handlers
		
		adminHeaderView.getBtnMainMenu().addClickHandler(new ReturnMainViewHandler());
		
		//Add createUserView handler		
		createUserView.getBtnCancel().addClickHandler(new ReturnMainViewHandler());
		
		//Add createItemView handler
		createItemView.getBtnCancel().addClickHandler(new ReturnMainViewHandler());
		
		
		
		// Vis admin menu til at starte med
		mainView.showLogin();
		mainView.showLoginHeader();
		
		
		RootLayoutPanel rp = RootLayoutPanel.get();
		rp.add(mainView); 
	}
	
	// Login handler
	  private class LoginHandler implements ClickHandler {

		    @Override
		    public void onClick(ClickEvent event) {
		      if (event.getSource() == loginView.getBtnOk())
		        if (loginView.getUserId().equals(adminId) && loginView.getUserPw().equals(adminPw)) {
		          mainView.loginOk(loginView.getUserId());
		          loginView.resetError();
		          loginView.clearfields();		
		          mainView.showAdminHeader();
		          mainView.showAdminMenu();	          
		          
		        } else if (loginView.getUserId().equals(userId) && loginView.getUserPw().equals(userPw)){
		            mainView.loginOk(loginView.getUserId());
			          loginView.resetError();
			          loginView.clearfields();
			          mainView.showUserHeader();
			          mainView.showUserView();
		        } else
		          loginView.setError();
		      else if (event.getSource() == loginView.getBtnCancel()) {
		        loginView.resetError();
		        loginView.clearfields();
		        mainView.loginCancel();
		      }
		    }
		  }	
	
	// Logout handler
	private class LogoutHandler implements ClickHandler{
		
		@Override
		public void onClick(ClickEvent event){
			if (event.getSource() == adminHeaderView.getBtnLogout() || event.getSource() == userHeaderView.getBtnLogout() ){
				mainView.showLogin();
				mainView.showLoginHeader();
				
			}	
		}
	}
	
	// Create user Handler
	private class CreateUserHandler implements ClickHandler{
		
		@Override
		public void onClick(ClickEvent event){
			if (event.getSource() == adminMenu.getBtnCreateUser()){
				mainView.changeWidget(createUserView);
			}			
		}
	}
	
	// Delete item handler
	private class DeleteItemHandler implements ClickHandler{
		
		@Override
		public void onClick(ClickEvent event){
			if (event.getSource() == adminMenu.getBtnDeleteItem()){
				
				deleteItemView.pop();
				mainView.changeWidget(deleteItemView);
			}			
		}
	}
	
	// Delete user view
	private class DeleteUserHandler implements ClickHandler{
		
		@Override
		public void onClick(ClickEvent event){
			if (event.getSource() == adminMenu.getBtnDeleteUser()){
				showUserListView.pop();
				mainView.changeWidget(showUserListView);
			}			
		}
	}
	
	// Return to menu handler
	private class ReturnMainViewHandler implements ClickHandler{
		@Override
		public void onClick(ClickEvent event){
			if (event.getSource() == createUserView.getBtnCancel() || event.getSource() == createItemView.getBtnCancel() || 
					event.getSource() == adminHeaderView.getBtnMainMenu()){
				mainView.changeWidget(adminMenu);
			}
		}
	}
	
	// Create item handler
	private class CreateItemViewHandler implements ClickHandler{
		
		@Override
		public void onClick(ClickEvent event){
			if (event.getSource() == adminMenu.getBtnCreateItem()){
				mainView.changeWidget(createItemView);
			}
		}
	}
	
	//Show user list handler
	private class ShowUserListHandler implements ClickHandler{
		
		@Override
		public void onClick(ClickEvent event){
			if (event.getSource() == adminMenu.getBtnShowUsers()){
				showUserListView.pop();
				mainView.changeWidget(showUserListView);
			}			
		}
	}
	
	//Show statistics handler
	private class StatisticsHandler implements ClickHandler{
		
		@Override
		public void onClick(ClickEvent event){
			if (event.getSource() == adminMenu.getBtnStatistic()){
				mainView.changeWidget(statistic);
			}			
		}
	}
	
	//Show history handler
	private class HistoryHandler implements ClickHandler{
		
		@Override
		public void onClick(ClickEvent event){
			if (event.getSource() == userHeaderView.getBtnHistory()){
				mainView.getUserView().showHistoryView();
			}
		}
	}
	//Back to menu handler
	private class UserReturnToMenuHandler implements ClickHandler{
		
		@Override
		public void onClick(ClickEvent event){
			if (event.getSource() == userHeaderView.getBtnMainMenu()){
				mainView.getUserView().showMenuView();
			}
		}
	}
	
	//Add to basket
	
	private class AddKaffeToBasketHandler implements ClickHandler{
		@Override
		public void onClick(ClickEvent event){
			if (event.getSource() == userView.getUserMenuView().getKaffeBtn()){
				mainView.getUserView().AddItemToBasket(new ItemDTO("Kaffe", 20));
				mainView.getUserView().showBasketWidget();
				//mainView.getUserView().showHistoryView();
				//Window.alert("Hej");
			}
		}


	}
	
	private class AddBananToBasketHandler implements ClickHandler{
		@Override
		public void onClick(ClickEvent event){
			if (event.getSource() == userView.getUserMenuView().getBananBtn()){
				mainView.getUserView().AddItemToBasket(new ItemDTO("Banan", 10));
				mainView.getUserView().showBasketWidget();
				//mainView.getUserView().showHistoryView();
				//Window.alert("Hej");
			}
		}


	}
	
	

	

}
