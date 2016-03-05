package gwt.client.logic;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootLayoutPanel;

import gwt.client.ui.*;
import project62.logic.Controller.LoginHandler;



public class adminMenuController {

	private MainViewAdmin mainViewAdmin;
	private adminMenu adminMenu;
	private CreateUser createUserView;
	private CreateItemView createItemView;
	private LoginView loginView;
	
	  private final String userId = "Peter";
	  private final String userPw = "1234";
	public adminMenuController(){
		mainViewAdmin = new MainViewAdmin();
		
		RootLayoutPanel rp = RootLayoutPanel.get();
		rp.add(mainViewAdmin);
		
		 loginView = mainViewAdmin.getLoginView();
		adminMenu = mainViewAdmin.getadminMenu(); //Hvorfor skal den være her? fordi
		loginView = mainViewAdmin.getLoginView();
		createUserView = mainViewAdmin.getcreateUser();
		
		createUserView.getBtnCancel().addClickHandler(new ReturnMainViewHandler());
		
		adminMenu.getBtnCreateItem().addClickHandler(new CreateItemViewHandler());
		
		
		adminMenu.getBtnCreateUser().addClickHandler(new CreateUserHandler());
		
		
		loginView.getBtnOk().addClickHandler(new LoginHandler());
	    loginView.getBtnCancel().addClickHandler(new LoginHandler());

	
	}
	
	private class CreateUserHandler implements ClickHandler{
		
		@Override
		public void onClick(ClickEvent event){
			if (event.getSource() == adminMenu.getBtnCreateUser()){
				mainViewAdmin.changeWidget(createUserView);
			}
			
		}

	}
	
	private class ReturnMainViewHandler implements ClickHandler{
		@Override
		public void onClick(ClickEvent event){
			if (event.getSource() == createUserView.getBtnCancel()){
				mainViewAdmin.changeWidget(adminMenu);
			}
		}
	}
	
	private class CreateItemViewHandler implements ClickHandler{
		
		@Override
		public void onClick(ClickEvent event){
			if (event.getSource() == adminMenu.getBtnCreateItem()){
				mainViewAdmin.changeWidget(createItemView);
			}
		}
	}
	
	  private class LoginHandler implements ClickHandler {

		    @Override
		    public void onClick(ClickEvent event) {
		      if (event.getSource() == loginView.getBtnOk())
		        if (loginView.getUserId().equals(userId) && loginView.getUserPw().equals(userPw)) {
		          mainViewAdmin.loginOk(loginView.getUserId());
		          loginView.resetError();
		          loginView.clearfields();					
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
