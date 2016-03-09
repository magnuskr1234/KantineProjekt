package gwt.client.logic;

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

/**
 * Pagecontroller til at styre hvilken side som aktuelt bliver vist for administratoren, ved at tilføje clickhandlers
 * som sørger for at skifte til det rette panel, gennem mainViewAdmin. 
 * @author magnusrasmussen
 *
 */
public class AdminMenuController {

	// References for views
	private MainView mainViewAdmin;
	
	private AdminMenuView adminMenu;
	private AdminHeaderView adminHeaderView;
	
	private CreateUserView createUserView;
	private CreateItemView createItemView;
	
	private DeleteItemView deleteItemView;
	private DeleteUserView deleteUserView;
	
	private EditPersonView editPersonView;
	
	
	private ShowUserListView showUserListView;
	
	private StatisticView statistic;
	 
	 
	public AdminMenuController(){
		
		//Instantiate pagecontroller
		mainViewAdmin = new MainView();
		
		//Get references to subviews
		adminHeaderView = mainViewAdmin.getadminHeaderView();
		adminMenu = mainViewAdmin.getadminMenu(); 
		createItemView = mainViewAdmin.getcreateItem();
		createUserView = mainViewAdmin.getcreateUser();
		deleteItemView = mainViewAdmin.getdeleteItem();
		deleteUserView = mainViewAdmin.getdeleteUserView();
		editPersonView = mainViewAdmin.geteditPerson();
		
		showUserListView = mainViewAdmin.getshowUserList();
		statistic = mainViewAdmin.getstatistic();
		

	    
	    //Add adminMenuView Handlers
	    adminMenu.getBtnCreateItem().addClickHandler(new CreateItemViewHandler());		
		adminMenu.getBtnCreateUser().addClickHandler(new CreateUserHandler());
		adminMenu.getBtnDeleteItem().addClickHandler(new DeleteItemHandler());
		adminMenu.getBtnDeleteUser().addClickHandler(new DeleteUserHandler());
		adminMenu.getBtnShowUsers().addClickHandler(new ShowUserListHandler());
		adminMenu.getBtnStatistic().addClickHandler(new StatisticsHandler());
		
		// Add adminHeaderView Handlers
		adminHeaderView.getBtnLogout().addClickHandler(new LogoutHandler());
		adminHeaderView.getBtnMainMenu().addClickHandler(new ReturnMainViewHandler());
		
		//Add createUserView handler		
		createUserView.getBtnCancel().addClickHandler(new ReturnMainViewHandler());
		
		//Add createItemView handler
		createItemView.getBtnCancel().addClickHandler(new ReturnMainViewHandler());
		
		mainViewAdmin.showAdminMenu();
		mainViewAdmin.showAdminHeader();
		
		RootLayoutPanel rp = RootLayoutPanel.get();
		rp.add(mainViewAdmin); 
	}
	
	// Logout handler
	private class LogoutHandler implements ClickHandler{
		
		@Override
		public void onClick(ClickEvent event){
			if (event.getSource() == adminHeaderView.getBtnLogout()){
				mainViewAdmin.getLoginView();
				
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
				
				deleteItemView.pop();
				mainViewAdmin.changeWidget(deleteItemView);
			}			
		}
	}
	
	// Delete user view
	private class DeleteUserHandler implements ClickHandler{
		
		@Override
		public void onClick(ClickEvent event){
			if (event.getSource() == adminMenu.getBtnDeleteUser()){
				showUserListView.pop();
				mainViewAdmin.changeWidget(showUserListView);
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
	

}
