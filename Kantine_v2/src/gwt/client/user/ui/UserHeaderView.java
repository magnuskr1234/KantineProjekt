package gwt.client.user.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class UserHeaderView extends Composite {

	private static UserHeaderViewUiBinder uiBinder = GWT.create(UserHeaderViewUiBinder.class);

	interface UserHeaderViewUiBinder extends UiBinder<Widget, UserHeaderView> {
	}

	public UserHeaderView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
