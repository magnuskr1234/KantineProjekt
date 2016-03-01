package gwt.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class CreateUser extends Composite {

	private static CreateUserUiBinder uiBinder = GWT.create(CreateUserUiBinder.class);

	interface CreateUserUiBinder extends UiBinder<Widget, CreateUser> {
	}

	public CreateUser() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
