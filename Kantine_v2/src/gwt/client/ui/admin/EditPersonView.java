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

	
	PersonDTO pDTO;
	
	interface EditPersonViewUiBinder extends UiBinder<Widget, EditPersonView> {
	}

	public EditPersonView() {
		initWidget(uiBinder.createAndBindUi(this));
	
	}
	
	  public void setpersonDTO (PersonDTO pDTO) {
		    this.pDTO =  pDTO;
		    // update text boxes
		    pDTO.getId();
		    pDTO.getName();
		    txtSaldo.setText("" + pDTO.getSaldo());
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


	
	 public boolean validate() {
		    // check if all fields are valid
		 if (FieldVerifier.isValidSaldo(txtSaldo.getText())){
		   // double getsaldo;
		      txtSaldo.setStyleName("textBox");
		     // getsaldo = Double.parseDouble(txtSaldo.getText());
		       
		      // update DTO object
		    Window.alert("1");
		     //  getsaldo += ShowUserListView.saldoUpdate;
		      pDTO.setId(56); 
		      Window.alert("2");
		      pDTO.setSaldo(Double.parseDouble(txtSaldo.getText()));
		      Window.alert("3");
		      return true;
		 }
		 
		 if(!FieldVerifier.isValidSaldo(txtSaldo.getText()))
			 Window.alert("Fieldverifier problem");
			 return false;
		      
	 	} 
	  
}

