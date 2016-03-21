package gwt.client.ui.admin;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.shared.PersonDTO;
import gwt.shared.FieldVerifier;
import gwt.client.ui.admin.*;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;

public class EditPersonView extends Composite {

	private static EditPersonViewUiBinder uiBinder = GWT.create(EditPersonViewUiBinder.class);
	@UiField TextBox txtSaldo;
	@UiField Button btnConfirm;
	@UiField Button btnCancel;

	
	PersonDTO pDTO = new PersonDTO();
	double newSaldo;
	int saldoUserId;
	
	interface EditPersonViewUiBinder extends UiBinder<Widget, EditPersonView> {
	}

	public EditPersonView() {
		initWidget(uiBinder.createAndBindUi(this));
		
	}
	
	  public void setpersonDTO (PersonDTO pDTO) {
		    this.pDTO =  pDTO;
		    // update text boxes
		   
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


	
	 public boolean validate(ShowUserListView su) {
		    // check if all fields are valid
		 if (FieldVerifier.isValidSaldo(txtSaldo.getText())){
			
			 
		    double getsaldo;
		      txtSaldo.setStyleName("textBox");
		      getsaldo = Double.parseDouble(txtSaldo.getText());
		       
		      // update DTO object
		      
		     
		       
		       
		       newSaldo = getsaldo += ShowUserListView.saldoUpdate;
		       
		       saldoUserId = su.getPersonId();
		      txtSaldo.setText("");
		      return true;
		 }
		 
		 if(!FieldVerifier.isValidSaldo(txtSaldo.getText()))
			 txtSaldo.setStyleName("textBox-invalidEntry");
			 return false;
		      
	 	} 
	  
}

