package gwt.client.ui.user;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import gwt.shared.ItemDTO;

/**
 * Basketview contains the basket to show what the user has added to the basket, and shows buttons for buying the items and cancel 
 * @author magnusrasmussen
 *z
 */
public class BasketView extends Composite {

	private static BasketViewUiBinder uiBinder = GWT.create(BasketViewUiBinder.class);
	@UiField
	FlexTable basketTable;
	@UiField
	Button buyBtn;
	@UiField
	Button cancelBtn;

	interface BasketViewUiBinder extends UiBinder<Widget, BasketView> {
	}

	// Buttons for events
	private Button controllerDeleteBtn;

	// Handlers
	private DeleteHandler deleteHandler;

	// row where event happened
	private int eventRowIndexDelete;

	private double currentSaldo;

	private double newSaldo;

	private double sum;
	
	// reference to DTO
	ItemDTO itemDTO;
	
	/**
	 * Constructor for basketview
	 */
	public BasketView() {
		initWidget(uiBinder.createAndBindUi(this));
	
		itemDTO = new ItemDTO();
		deleteHandler = new DeleteHandler();

		// Button for controller event handling
		controllerDeleteBtn = new Button();

	}

	/**
	 * Getters and setters for basketview
	 * @return
	 */
	public Button getCancelBtn() {
		return cancelBtn;
	}

	public Button getBuyBtn() {
		return buyBtn;
	}
	
	public FlexTable getBasketTable(){
		return basketTable;
	}

	public Button getControllerDeleteBtn() {
		return controllerDeleteBtn;
	}

	public double getCurrentUserSaldo(){
		return currentSaldo;
	}

	public void setCurrentUserSaldo(double saldo) {
		this.currentSaldo = saldo;
	}
	
	
	public void setNewSaldo(double newSaldo){
		this.newSaldo = newSaldo;
	}
	public double getNewSaldo() {
		return newSaldo;
	}

	public double getSum() {
		return sum;
	}

	public void setSum(double s) {
		sum += s;
	}
	// delete row where delete-event happened
	public void deleteEventRow() {
		basketTable.removeRow(eventRowIndexDelete + 1);
	}

	// Empty basket table
	public void emptyTable() {
		UserMenuView.tempItemList.clear();
		populateBasket(UserMenuView.tempItemList);

	}

	public void clearSum() {
		sum = 0;
	}

	// Method used to add info to basket flextable
	public void populateBasket(List<ItemDTO> selectedItems) {

		// remove table data
		basketTable.removeAllRows();
		clearSum();
		
		// adjust column widths
		basketTable.getFlexCellFormatter().setWidth(0, 0, "50px");
		basketTable.getFlexCellFormatter().setWidth(0, 1, "50px");
		basketTable.getFlexCellFormatter().setWidth(0, 2, "25px");
		basketTable.getFlexCellFormatter().setWidth(0, 3, "50px");
		
		// set headers in flextable
		basketTable.setText(0, 0, "Vare");
		basketTable.setText(0, 1, "Antal");
		basketTable.setText(0, 2, "Pris");

		// style table
		basketTable.addStyleName("FlexTable");
		basketTable.getRowFormatter().addStyleName(0, "FlexTable-Header");
		basketTable.getRowFormatter().addStyleName(selectedItems.size() + 1, "FlexTable-Header");
		basketTable.getRowFormatter().addStyleName(selectedItems.size() + 2, "FlexTable-Header");

		// for loop for adding data to flextable 
		for (int i = 0; i < selectedItems.size(); i++) {
			basketTable.setText(i + 1, 0, selectedItems.get(i).getName());
			basketTable.setText(i + 1, 1, Integer.toString(selectedItems.get(i).getCount()));
			basketTable.setText(i + 1, 2, Double.toString((selectedItems.get(i).getPrice() * selectedItems.get(i).getCount())));
			setSum(selectedItems.get(i).getPrice() * selectedItems.get(i).getCount());

			basketTable.setText(selectedItems.size() + 1, 0, "I alt: ");
			basketTable.setText(selectedItems.size() + 1, 2, "" + getSum());
			basketTable.setText(selectedItems.size() + 2, 0, "Saldo efter køb: ");
			basketTable.setText(selectedItems.size() + 2, 2, "" + (getCurrentUserSaldo() - sum));

			Button controllerDeleteBtn = new Button("Fjern");
			controllerDeleteBtn.getElement().setId("deleteButton");
			basketTable.setWidget(i + 1, 3, controllerDeleteBtn);
			
			// add delete buttons to row
			controllerDeleteBtn.addClickHandler(deleteHandler);
		}

		setNewSaldo(currentSaldo - sum);
	}
	
	/**
	 *  Handler for delete item from basket
	 * @author magnusrasmussen
	 *
	 */
	private class DeleteHandler implements ClickHandler {
		public void onClick(ClickEvent event) {
			// Clear sum
			clearSum();
			
			// Evntrowindex 
			eventRowIndexDelete = 0;
			
			// save event row index
			eventRowIndexDelete = basketTable.getCellForEvent(event).getRowIndex() - 1;
		
			// Set updated sum
			setSum(-UserMenuView.tempItemList.get(eventRowIndexDelete).getPrice());
			
			// remove from list
			UserMenuView.tempItemList.remove(eventRowIndexDelete);
			
			// Set current saldo
			UserMenuView.setcuSaldo(UserMenuView.getcuSaldo() + Double.parseDouble(basketTable.getText(eventRowIndexDelete+1, 2)));
			
			// Fireevent for user controller 
			controllerDeleteBtn.fireEvent(new ClickEvent() {

			});
		}

	}

}
