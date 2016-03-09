package gwt.client.admin.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class DeleteUserView extends Composite {

	private static DeleteUserViewUiBinder uiBinder = GWT.create(DeleteUserViewUiBinder.class);

	interface DeleteUserViewUiBinder extends UiBinder<Widget, DeleteUserView> {
	}

	public DeleteUserView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
