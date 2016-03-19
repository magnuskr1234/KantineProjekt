package gwt.client.ui.admin;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.shared.PersonDTO;
import gwt.shared.FieldVerifier;

import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;

public class EditPersonView extends Composite {

	private static EditPersonViewUiBinder uiBinder = GWT.create(EditPersonViewUiBinder.class);
	@UiField TextBox txtSaldo;
	@UiField Button btnConfirm;
	@UiField Button btnCancel;

	
	PersonDTO personDTO;
	
	
	
	  public void setpersonDTO (PersonDTO personDTO) {
		    this.personDTO = new PersonDTO();
		    personDTO.setAdminStatus(1);
		    personDTO.setId(56);
		    personDTO.setName("Kevin");
		    personDTO.setPassword("1234");
		    personDTO.setSaldo(2);
		    // update text boxes
		    
		    txtSaldo.setText("" + personDTO.getSaldo());
		  }
		  
		  // return data entered 
		  public PersonDTO getpersonDTO() {
		    return personDTO;
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
	
	 public void validate() {
		    // check if all fields are valid
		    double getsaldo;
		      txtSaldo.setStyleName("textBox");
		      
		       getsaldo = Double.parseDouble(txtSaldo.getText());
		       
		      // update DTO object
		    
		       getsaldo += ShowUserListView.saldoUpdate;
		       
		      personDTO.setId(56); 
		      personDTO.setSaldo(getsaldo );
		
		      
	 	} 
	  
}

