package gwt.client.ui.user;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class UserHistoryView extends Composite {

	private static UserHistoryViewUiBinder uiBinder = GWT.create(UserHistoryViewUiBinder.class);

	interface UserHistoryViewUiBinder extends UiBinder<Widget, UserHistoryView> {
	}

	public UserHistoryView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
