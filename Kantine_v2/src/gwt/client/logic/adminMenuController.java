package gwt.client.logic;

import com.google.gwt.user.client.ui.RootLayoutPanel;

import gwt.client.ui.*;

public class adminMenuController {

	private MainViewAdmin mainViewAdmin;
	private adminMenu adminMenu;
	
	public adminMenuController(){
		mainViewAdmin = new MainViewAdmin();
		
		RootLayoutPanel rp = RootLayoutPanel.get();
		rp.add(mainViewAdmin);
		
		//Jeg tester git!!!!!! - AleXander - Jeg skal ikke indtaste kode når jeg pusher og puller :-P 
	}
	
}
