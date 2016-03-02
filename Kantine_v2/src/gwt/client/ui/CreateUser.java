package gwt.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.event.dom.client.ClickEvent;

public class CreateUser extends Composite {

	private static CreateUserUiBinder uiBinder = GWT.create(CreateUserUiBinder.class);
	@UiField Button btnCancel;

	interface CreateUserUiBinder extends UiBinder<Widget, CreateUser> {
	}

	public CreateUser() {
		initWidget(uiBinder.createAndBindUi(this));
		// Jeg skriver lige i denne klasse for at teste om Git virker.. Men det gør det jo ;-)
		
		//Prøve med slettet ssh nøgle
	}
	
	public Button getBtnCancel(){
		return btnCancel;
	}
}
