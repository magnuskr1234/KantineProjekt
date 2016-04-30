package gwt.client;

import gwt.client.ui.MainView;
import gwt.client.logic.MainController;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootLayoutPanel;

/**
 * Entry point classes defines on module load. 
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
