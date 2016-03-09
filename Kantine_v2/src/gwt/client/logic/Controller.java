package gwt.client.logic;


import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.RootLayoutPanel;

import gwt.client.ui.LoginView;
import gwt.client.ui.MainView;
import gwt.client.*;

/**
 * Denne klasses formål er at håndtere login processen og sørge for at få skiftet til den enten brugermenu eller administratormenu.
 * @author magnusrasmussen
 *
 */
public class Controller {
	 AdminMenuController am;
	
	// Views referencer
	private MainView mainView;
	private LoginView loginView;
	
	// Hardcoded login details for user
	private final String userId = "Peter";
	private final String userPw = "1234";
	
	// Hardcoded login for admin
	private final String adminId = "Hans";
	private final String adminPw = "1234";
	
	
	public Controller(){
		
	//Instantiate pagecontroller
	mainView = new MainView();
	 
	loginView = mainView.getLoginView();

	 
	//Add loginview handlers
	loginView.getBtnOk().addClickHandler(new LoginHandler());
	loginView.getBtnCancel().addClickHandler(new LoginHandler());	
	
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
		          am = new AdminMenuController();	
		          
		          
		        } else if (loginView.getUserId().equals(userId) && loginView.getUserPw().equals(userPw)){
		            mainView.loginOk(loginView.getUserId());
			          loginView.resetError();
			          loginView.clearfields();		
			          mainView.showUserMenu();
			          mainView.showUserHeader();
		        } else
		          loginView.setError();
		      else if (event.getSource() == loginView.getBtnCancel()) {
		        loginView.resetError();
		        loginView.clearfields();
		        mainView.loginCancel();
		      }
		    }
		  }	
}


