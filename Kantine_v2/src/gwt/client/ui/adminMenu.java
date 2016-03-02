package gwt.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.event.dom.client.ClickEvent;

public class adminMenu extends Composite {

	private static adminMenuUiBinder uiBinder = GWT.create(adminMenuUiBinder.class);
	@UiField Button deleteItem;
	@UiField Button createItem;
	@UiField Button createUser;
	@UiField Button deleteUser;
	@UiField Button statistic;
	@UiField Button showUsers;

	interface adminMenuUiBinder extends UiBinder<Widget, adminMenu> {
	}

	public adminMenu() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public Button getBtnCreateUser() {
		return createUser;
	}
}
