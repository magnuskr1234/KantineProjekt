package gwt.client.ui.user;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.event.dom.client.ClickEvent;

public class UserHeaderView extends Composite {

	private static UserHeaderViewUiBinder uiBinder = GWT.create(UserHeaderViewUiBinder.class);
	@UiField Button btnHistory;
	@UiField Button btnMainMenu;
	@UiField Button btnLogout;

	interface UserHeaderViewUiBinder extends UiBinder<Widget, UserHeaderView> {
	}

	public UserHeaderView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public Button getBtnMainMenu() {
		return btnMainMenu;
	}
	
	public Button getBtnLogout(){
		return btnLogout;
	}
	
	public Button getBtnHistory(){
		return btnHistory;
	}
}
