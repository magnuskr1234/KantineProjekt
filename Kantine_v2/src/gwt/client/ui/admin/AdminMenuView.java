package gwt.client.ui.admin;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;

public class AdminMenuView extends Composite {

	private static adminMenuUiBinder uiBinder = GWT.create(adminMenuUiBinder.class);
	@UiField Button createItem;
	@UiField Button createUser;
	@UiField Button showItems;
	@UiField Button showUsers;
	@UiField Button statistic;	

	interface adminMenuUiBinder extends UiBinder<Widget, AdminMenuView> {
	}

	public AdminMenuView() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public Button getBtnCreateUser() {
		return createUser;
	}
	
	public Button getBtnCreateItem() {
		return createItem;
	}

	public Button getBtnDeleteUser() {
		return showItems;
	}

	public Button getBtnStatistic() {
		return statistic;
	}

	public Button getBtnShowUsers() {
		return showUsers;
	}
	
	public Button getBtnShowItems() {
		return showItems;
	}
}
