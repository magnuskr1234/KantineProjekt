package gwt.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class LoginHeaderView extends Composite {

	private static LoginHeaderViewUiBinder uiBinder = GWT.create(LoginHeaderViewUiBinder.class);

	interface LoginHeaderViewUiBinder extends UiBinder<Widget, LoginHeaderView> {
	}

	public LoginHeaderView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
