package gwt.client.ui.admin;

import com.google.gwt.core.client.GWT;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.shared.PersonDTO;
import gwt.shared.FieldVerifier;

import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.CheckBox;

public class CreateUserView extends Composite {

	private static CreateUserUiBinder uiBinder = GWT.create(CreateUserUiBinder.class);
	@UiField Button btnCancel;
	@UiField Button createUserBtn;
	@UiField TextBox txtName;
	@UiField TextBox txtPassword;
	@UiField TextBox txtSaldo;
	@UiField CheckBox radioAdmin;

	public PersonDTO pDTO;
	
	interface CreateUserUiBinder extends UiBinder<Widget, CreateUserView> {
	}

	public CreateUserView() {
		initWidget(uiBinder.createAndBindUi(this));
		// make new DTO object
				this.pDTO = new PersonDTO();
		
	}
	
	public Button getBtnCancel(){
		return btnCancel;
	}
	
	public Button getCreateUserBtn(){
		return createUserBtn;
	}
	
	public boolean validate() {
		// check if all fields are valid
		if (FieldVerifier.isValidName(txtName.getText()) && FieldVerifier.isValidSaldo(txtSaldo.getText())) {
			
			txtName.setStyleName("textBox");
			txtSaldo.setStyleName("textBox");
			

			
			// update DTO object
			
			pDTO.setName(txtName.getText());
			pDTO.setPassword(txtPassword.getText());
			pDTO.setSaldo(Double.parseDouble(txtSaldo.getText()));
			// 1 = true and 0 = false
			pDTO.setAdminStatus((radioAdmin.getValue()) ? 1 : 0);
			
			// clear fields
			txtName.setText("");
			txtPassword.setText("");
			txtSaldo.setText("");

			return true;
		}

		// update fields error state
		if (!FieldVerifier.isValidName(txtName.getText())) 
			txtName.setStyleName("textBox-invalidEntry");
		else
			txtName.setStyleName("textBox");
		if (!FieldVerifier.isValidSaldo(txtSaldo.getText())) 
			txtSaldo.setStyleName("textBox-invalidEntry");
		else
			txtSaldo.setStyleName("textBox");

		return false;
	}

	// return data entered 
	public PersonDTO getCurrentPerson() {
		return pDTO;
	}
}
