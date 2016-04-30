package gwt.client.ui.admin;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.shared.PersonDTO;
import gwt.shared.FieldVerifier;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Button;

/**
 * This class contains the view to update an users saldo 
 * @author magnusrasmussen
 *
 */
public class EditUserView extends Composite {

	private static EditPersonViewUiBinder uiBinder = GWT.create(EditPersonViewUiBinder.class);

	@UiField TextBox txtSaldo;
	@UiField Button btnConfirm;
	@UiField Button btnCancel;

	PersonDTO pDTO;
	double newSaldo;
	int saldoUserId;
	double getSaldo;

	interface EditPersonViewUiBinder extends UiBinder<Widget, EditUserView> {
	}

	public EditUserView() {
		initWidget(uiBinder.createAndBindUi(this));

		txtSaldo.getElement().setPropertyString("placeholder", "Indtast beløb");


	}

	// getters 
	public void setpersonDTO (PersonDTO pDTO) {
		this.pDTO =  pDTO;

	}

	// return data entered 
	public PersonDTO getpersonDTO() {
		return pDTO;
	}

	public TextBox getTxtSaldo() {
		return txtSaldo;
	}

	public Button getBtnConfirm() {
		return btnConfirm;
	}


	public Button getBtnCancel() {
		return btnCancel;
	}

	public double getNewSaldo(){
		return newSaldo;
	}

	public int getSaldoUserId(){
		return saldoUserId;
	}
	
	public double getSaldo(){
		return getSaldo;		
	}

	public void clearFields(){
		txtSaldo.setText("");
	}

	// Validate entered data
	public boolean validate(ShowUserListView su) {
		// check if all fields are valid
		if (FieldVerifier.isValidSaldo(txtSaldo.getText())){

			getSaldo = Double.parseDouble(txtSaldo.getText());

			txtSaldo.setStyleName("textBox");
			
			newSaldo = getSaldo + ShowUserListView.saldoUpdate;

			saldoUserId = su.getPersonId();
			txtSaldo.setText("");
			return true;
		}
		else{
			return false;
		}

	}

}

