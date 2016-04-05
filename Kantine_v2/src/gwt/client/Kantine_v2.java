package gwt.client;


import gwt.client.logic.UserController;
import gwt.client.ui.MainView;
import gwt.client.logic.AdminController;
import gwt.client.logic.MainController;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootLayoutPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Kantine_v2 implements EntryPoint {

	@Override
	public void onModuleLoad() {
		// Instantiate pagecontroller
		MainView mainView = new MainView();
		
		// Add to rootpanel 
		RootLayoutPanel.get().add(mainView);
		
		new MainController(mainView);
	}
		
}
