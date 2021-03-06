package gwt.client.ui.admin;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.shared.FieldVerifier;
import gwt.shared.ItemDTO;

import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Button;
/**
 * This class contains the view to edit an item price
 *
 */
public class EditItemView extends Composite {

	private static EditItemViewUiBinder uiBinder = GWT.create(EditItemViewUiBinder.class);
	@UiField TextBox txtPrice;
	@UiField Button btnConfirm;
	@UiField Button btnCancel;


	ItemDTO iDTO = new ItemDTO();
	double newPrice;
	int priceItemId;

	interface EditItemViewUiBinder extends UiBinder<Widget, EditItemView> {
	}
	public EditItemView() {
		initWidget(uiBinder.createAndBindUi(this));

		txtPrice.getElement().setPropertyString("placeholder", "Indtast ny pris");

	}

	// Gettters and setters
	public void setItemDTO ( ItemDTO iDTO){
		this.iDTO = iDTO;
	}

	public ItemDTO getitemDTO() {
		return iDTO;
	}

	public TextBox getTxtPrice() {
		return txtPrice;
	}

	public Button getBtnConfirm() {
		return btnConfirm;
	}


	public Button getBtnCancel() {
		return btnCancel;
	}

	public double getNewPrice(){
		return newPrice;
	}

	public int getPriceItemId(){
		return priceItemId;
	}

	// validate entered data
	public boolean validate(ShowItemListView itemList) {

		// check if all fields are valid			
		if (FieldVerifier.isValidSaldo(txtPrice.getText())){

			txtPrice.setStyleName("textBox");
			newPrice = Double.parseDouble(txtPrice.getText());

			// update DTO object
			priceItemId = itemList.getItemId();
			txtPrice.setText("");
			return true;
		}
		if(!FieldVerifier.isValidSaldo(txtPrice.getText()))
			txtPrice.setStyleName("textBox-invalidEntry");
		return false;
	}    

	// Clear fields
	public void clearFields(){
		txtPrice.setText("");
	}
}
