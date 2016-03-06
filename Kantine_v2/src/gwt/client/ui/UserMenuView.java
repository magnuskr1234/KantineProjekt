package gwt.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class UserMenuView extends Composite {

	private static UserMenuViewUiBinder uiBinder = GWT.create(UserMenuViewUiBinder.class);

	interface UserMenuViewUiBinder extends UiBinder<Widget, UserMenuView> {
	}

	public UserMenuView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
