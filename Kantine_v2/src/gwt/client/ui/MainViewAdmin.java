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
	private CreateUser createUser;

	interface MainViewAdminUiBinder extends UiBinder<Widget, MainViewAdmin> {
	}

	public MainViewAdmin() {
		initWidget(uiBinder.createAndBindUi(this));
		adminMenu = new adminMenu();
		createUser = new CreateUser();
		contentPanel.add(adminMenu);
		contentPanel.add(createUser);
		
		
		contentPanel.showWidget(adminMenu);
	}
	
	public void adminMenu(){
		contentPanel.showWidget(adminMenu);
	}
	
	public void createUser() {
		contentPanel.showWidget(createUser);
	}
	
	public adminMenu getadminMenu() {
		return adminMenu;
	}
	
	public CreateUser getcreateUser(){
		return createUser;
	}


}
