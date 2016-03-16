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
import gwt.client.ui.admin.ShowUserListView;
import gwt.client.ui.admin.StatisticView;
import gwt.client.ui.user.UserHeaderView;
import gwt.client.ui.user.UserMenuView;
import gwt.client.ui.user.UserView;
import gwt.server.PersonDB;
import gwt.server.PersonServiceImpl;
import gwt.shared.ItemDTO;

import gwt.shared.PersonDTO;


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
	
	// Service 
	private PersonServiceAsync personDAO = GWT.create(PersonService.class);

	// 
	
	
	 //reference to data transfer object
	 private PersonDTO personDTO;
	 
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
		
		
		
		/*Tilføj user menu handlers
		userView.getUserMenuView().getKaffeBtn().addClickHandler(new AddKaffeToBasketHandler());
		userView.getUserMenuView().getBananBtn().addClickHandler(new AddBananToBasketHandler());*/
		
		//Add userHeader handlers
		userHeaderView.getBtnHistory().addClickHandler(new HistoryHandler());
		userHeaderView.getBtnMainMenu().addClickHandler(new UserReturnToMenuHandler());
		
		// Add adminHeaderView Handlers
		
		adminHeaderView.getBtnMainMenu().addClickHandler(new ReturnMainViewHandler());
		
		//Add createUserView handler	
		createUserView.getCreateUserBtn().addClickHandler(new CreateUserHandler());
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
			          userMenuView.populateData(null);
			          mainView.showUserView();
		        } else
		          loginView.setError();
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
		        // replace personDAO call with an RPC
		   if(event.getSource() == createUserView.getCreateUserBtn()){
			   if (createUserView.validate()){
				   
				   personDAO.savePerson(new PersonDTO(createUserView.getCurrentPerson().getName(), createUserView.getCurrentPerson().getPassword(), createUserView.getCurrentPerson().getAdminStatus(), createUserView.getCurrentPerson().getSaldo()), new AsyncCallback<Void>() {
				       
						 
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
			//	showUserListView.pop();
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

				
				personDAO.getSize(new AsyncCallback<Integer>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						Window.alert(caught +" hej");
					}

					@Override
					public void onSuccess(Integer result) {
						Window.alert(result + "");
						
					}
				});
				
				/*try {
					showUserListView.pop();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
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
	
	/*private class AddKaffeToBasketHandler implements ClickHandler{
		@Override
		public void onClick(ClickEvent event){
			if (event.getSource() == userView.getUserMenuView().getKaffeBtn()){
				mainView.getUserView().AddItemToBasket(new ItemDTO("Kaffe", 20));
				mainView.getUserView().showBasketWidget();
				//mainView.getUserView().showHistoryView();
				//Window.alert("Hej");
			}
		}


	}*/
	
	/*private class AddBananToBasketHandler implements ClickHandler{
		@Override
		public void onClick(ClickEvent event){
			if (event.getSource() == userView.getUserMenuView().getBananBtn()){
				mainView.getUserView().AddItemToBasket(new ItemDTO("Banan", 10));
				mainView.getUserView().showBasketWidget();
				//mainView.getUserView().showHistoryView();
			}
		}


	}*/
	
	


	

}
