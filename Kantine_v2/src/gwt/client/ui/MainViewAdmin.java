package gwt.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.event.dom.client.ClickEvent;

public class MainViewAdmin extends Composite {

	private static MainViewAdminUiBinder uiBinder = GWT.create(MainViewAdminUiBinder.class);
	@UiField HorizontalPanel adminHeader;
	@UiField DeckLayoutPanel contentPanel;
	
	private adminMenu adminMenu;

	interface MainViewAdminUiBinder extends UiBinder<Widget, MainViewAdmin> {
	}

	public MainViewAdmin() {
		initWidget(uiBinder.createAndBindUi(this));
		adminMenu = new adminMenu();
		contentPanel.add(adminMenu);
		
		
		contentPanel.showWidget(adminMenu);
	}


}
