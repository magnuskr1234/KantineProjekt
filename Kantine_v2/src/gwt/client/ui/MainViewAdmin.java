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
	@UiField DeckLayoutPanel contentPanel;
	@UiField DeckLayoutPanel adminHeader;
	
	private adminMenu adminMenu;
	private CreateUser createUser;
	private AdminHeaderView adminHeaderView;

	interface MainViewAdminUiBinder extends UiBinder<Widget, MainViewAdmin> {
	}

	public MainViewAdmin() {
		initWidget(uiBinder.createAndBindUi(this));
		adminMenu = new adminMenu();
		createUser = new CreateUser();
		adminHeaderView = new AdminHeaderView();
		contentPanel.add(adminMenu);
		contentPanel.add(createUser);
		adminHeader.add(adminHeaderView);		
		
		adminHeader.showWidget(adminHeaderView);
		contentPanel.showWidget(adminMenu);
	}
	
	public void changeWidget(Widget w){
		contentPanel.showWidget(w);
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
