package gwt.client.ui.admin;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import gwt.shared.FieldVerifier;
import gwt.shared.ItemDTO;

/**
 * This view contains the view to create items
 *
 */
public class CreateItemView extends Composite {

	private static CreateItemViewUiBinder uiBinder = GWT.create(CreateItemViewUiBinder.class);

	@UiField Button createItemBtn;
	@UiField TextBox txtName;
	@UiField TextBox txtPrice;

	public ItemDTO iDTO;
	interface CreateItemViewUiBinder extends UiBinder<Widget, CreateItemView> {
	}

	public CreateItemView() {
		initWidget(uiBinder.createAndBindUi(this));

		this.iDTO = new ItemDTO();

		txtName.getElement().setPropertyString("placeholder", "Indtast produktnavn");
		txtPrice.getElement().setPropertyString("placeholder", "Indtast pris");
	}

	// validate entered data 
	public boolean validate() {
		// check if all fields are valid
		if (FieldVerifier.isValidName(txtName.getText()) && FieldVerifier.isValidPrice(txtPrice.getText())) {

			txtName.setStyleName("textBox");
			txtPrice.setStyleName("textBox");

			// update DTO object			
			iDTO.setName(txtName.getText());
			iDTO.setPrice(Double.parseDouble(txtPrice.getText()));

			// clear fields
			txtName.setText("");
			txtPrice.setText("");

			return true;
		}
		else{
			return false;
		}


	}

	// Clear fields
	public void clearFields(){
		txtName.setText("");
		txtPrice.setText("");
	}

	// Getters
	public Button getcreateItemBtn(){
		return createItemBtn;
	}

	// return data entered 
	public ItemDTO getCurrentItem() {
		return iDTO;
	}
}
