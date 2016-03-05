package gwt.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class ShowUserList extends Composite {

	private static ShowUserListUiBinder uiBinder = GWT.create(ShowUserListUiBinder.class);

	interface ShowUserListUiBinder extends UiBinder<Widget, ShowUserList> {
	}

	public ShowUserList() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
