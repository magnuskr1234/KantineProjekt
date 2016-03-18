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

public class EditPersonView extends Composite {

	private static EditPersonViewUiBinder uiBinder = GWT.create(EditPersonViewUiBinder.class);
	@UiField TextBox txtSaldo;
	@UiField Button btnConfirm;
	@UiField Button btnCancel;

	
	private PersonDTO pDTO;
	
	  public void setpersonDTO (PersonDTO pDTO) {
		    this.pDTO = pDTO;
		    // update text boxes
		    txtSaldo.setText("" + pDTO.getSaldo());
		  }
		  
		  // return data entered 
		  public PersonDTO getpDTO() {
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

	interface EditPersonViewUiBinder extends UiBinder<Widget, EditPersonView> {
	}

	public EditPersonView() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	  public boolean validate() {
		    // check if all fields are valid
		    if (FieldVerifier.isValidSaldo(txtSaldo.getText())) {
		      txtSaldo.setStyleName("textBox");

		      // update DTO object
		     
		      pDTO.setSaldo(pDTO.getSaldo() + Double.parseDouble(txtSaldo.getText() ));
		    }
		      return true;
		    }
	  
}

