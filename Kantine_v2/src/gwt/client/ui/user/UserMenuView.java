package gwt.client.ui.user;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.event.dom.client.ClickEvent;

public class UserMenuView extends Composite {

	private static UserMenuViewUiBinder uiBinder = GWT.create(UserMenuViewUiBinder.class);
	@UiField Button btnKaffe;
	@UiField Button btnBanan;

	interface UserMenuViewUiBinder extends UiBinder<Widget, UserMenuView> {
	}

	public UserMenuView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public Button getKaffeBtn() {
		return btnKaffe;
	}

	public Button getBananBtn() {
		return btnBanan;
	}
}
