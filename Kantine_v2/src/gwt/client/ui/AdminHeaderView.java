package gwt.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Button;

public class AdminHeaderView extends Composite {

	private static AdminHeaderViewUiBinder uiBinder = GWT.create(AdminHeaderViewUiBinder.class);
	@UiField Label userNameLabel;
	@UiField Button btnMainMenu;
	@UiField Button btnLogout;
	

	interface AdminHeaderViewUiBinder extends UiBinder<Widget, AdminHeaderView> {
	}

	public AdminHeaderView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public Button getBtnMainMenu() {
		return btnMainMenu;
	}
	
	public Button getBtnLogout(){
		return btnLogout;
	}
}
