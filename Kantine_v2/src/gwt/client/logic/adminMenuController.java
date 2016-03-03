package gwt.client.logic;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootLayoutPanel;

import gwt.client.ui.*;

public class adminMenuController {

	private MainViewAdmin mainViewAdmin;
	private adminMenu adminMenu;
	private CreateUser createUserView;
	
	public adminMenuController(){
		mainViewAdmin = new MainViewAdmin();
		
		RootLayoutPanel rp = RootLayoutPanel.get();
		rp.add(mainViewAdmin);
		
		adminMenu = mainViewAdmin.getadminMenu();
		
		createUserView = mainViewAdmin.getcreateUser();
		
		createUserView.getBtnCancel().addClickHandler(new ReturnMainViewHandler());
		
		
		adminMenu.getBtnCreateUser().addClickHandler(new CreateUserHandler());
		
		//Jeg tester git!!!!!! - AleXander - Jeg skal ikke indtaste kode når jeg pusher og puller :-P 
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
	
}
