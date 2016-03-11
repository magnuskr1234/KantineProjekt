package gwt.client.ui.admin;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class EditPersonView extends Composite {

	private static EditPersonViewUiBinder uiBinder = GWT.create(EditPersonViewUiBinder.class);

	interface EditPersonViewUiBinder extends UiBinder<Widget, EditPersonView> {
	}

	public EditPersonView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
