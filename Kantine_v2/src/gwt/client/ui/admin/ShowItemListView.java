package gwt.client.ui.admin;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;
import gwt.shared.ItemDTO;

/**
 * This class conatins the view to show the itemlist
 * @author magnusrasmussen
 *
 */
public class ShowItemListView extends Composite {

	private static ShowItemListViewUiBinder uiBinder = GWT.create(ShowItemListViewUiBinder.class);

	@UiField
	public FlexTable itemTable;
	@UiField FlexTable headerTable;

	interface ShowItemListViewUiBinder extends UiBinder<Widget, ShowItemListView> {
	}

	// Buttons for events
	private Button controllerEditBtn;
	private Button controllerDeleteBtn;

	// row where event happened
	private int eventRowIndex;

	// id of person where event happened
	public int itemId;

	// Handlers
	private EditHandler editHandler;
	private DeleteHandler deleteHandler;

	private ItemDTO itemDTO;

	public ShowItemListView() {
		initWidget(uiBinder.createAndBindUi(this));
		// row event handlers
		editHandler = new EditHandler();
		deleteHandler = new DeleteHandler();

		// buttons for controller event handling
		controllerEditBtn = new Button();
		controllerDeleteBtn = new Button();

		itemDTO = new ItemDTO();
	}


	// getter and setter
	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	
	public Button getControllerEditBtn() {
		return controllerEditBtn;
	}

	public Button getControllerDeleteBtn() {
		return controllerDeleteBtn;
	}

	public ItemDTO getItemDTO() {
		return itemDTO;
	}

	// delete row where delete-event happened
	public void deleteEventRow() {
		itemTable.removeRow(eventRowIndex);
	}

	// update data in row where edit-event happened 
	public void updateRow(ItemDTO itemDTO) {
		itemTable.setText(eventRowIndex, 1, itemDTO.getName());
		itemTable.setText(eventRowIndex, 2, "" + itemDTO.getPrice()); 
	}
	
	/**
	 * Flextable is populated whit data 
	 * 
	 */
	public void populateItemList(List<ItemDTO> pList) {
		
		// remove table data
		itemTable.removeAllRows();
		
		// adjust column widths
		headerTable.getFlexCellFormatter().setWidth(0, 0, "110px");
		headerTable.getFlexCellFormatter().setWidth(0, 1, "110px");
		headerTable.getFlexCellFormatter().setWidth(0, 2, "110px");
		headerTable.getFlexCellFormatter().setWidth(0, 3, "100px");
		headerTable.getFlexCellFormatter().setWidth(0, 4, "110px");
		
		//Header
		headerTable.setText(0, 0, "Id");
		headerTable.setText(0, 1, "Navn");
		headerTable.setText(0, 2, "Pris");

		// adjust column widths
		itemTable.getFlexCellFormatter().setWidth(0, 0, "110px");
		itemTable.getFlexCellFormatter().setWidth(0, 1, "110px");
		itemTable.getFlexCellFormatter().setWidth(0, 2, "110px");
		itemTable.getFlexCellFormatter().setWidth(0, 3, "110px");
		itemTable.getFlexCellFormatter().setWidth(0, 4, "110px");

		// style table
		itemTable.addStyleName("FlexTable");
		itemTable.setStylePrimaryName("FlexTable");
	
		// data is added 
		for (int i = 0; i < pList.size(); i++) {
			itemTable.setText(i + 1, 0, "" + pList.get(i).getId());
			itemTable.setText(i + 1, 1, pList.get(i).getName());
			itemTable.setText(i + 1, 2, "" + pList.get(i).getPrice());
		}

		// Buttons is added to each row
		for (int i = 0; i < pList.size(); i++) {

			Button edit = new Button("Opdater pris");
			edit.getElement().setId("editButton");
			itemTable.setWidget(i + 1, 3, edit);

			Button delete = new Button("Slet vare");
			delete.getElement().setId("deleteButton");
			itemTable.setWidget(i + 1, 4, delete);

			// add edit and delete buttons to row
			edit.addClickHandler(editHandler);
			delete.addClickHandler(deleteHandler);
		}
	}

	/**
	 * Handler to control button click to delete item 
	 * @author magnusrasmussen
	 *
	 */
	private class DeleteHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			// save event row index
			eventRowIndex = itemTable.getCellForEvent(event).getRowIndex();

			// save person id
			itemId = Integer.parseInt(itemTable.getText(eventRowIndex, 0));

			// fire controller delete button event
			controllerDeleteBtn.fireEvent(new ClickEvent() {
			});

		}
	}

	/**
	 * Handler to control edit event 
	 * @author magnusrasmussen
	 *
	 */
	private class EditHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			// get rowindex where event happened
			eventRowIndex = itemTable.getCellForEvent(event).getRowIndex();
			// populate itemDTO
			itemDTO.setId(Integer.parseInt(itemTable.getText(eventRowIndex, 0)));
			itemDTO.setName(itemTable.getText(eventRowIndex, 1));
		//	itemDTO.setPrice(Integer.parseInt(itemTable.getText(eventRowIndex, 2)));

			// Save item id
			setItemId(Integer.parseInt(itemTable.getText(eventRowIndex, 0)));

			// fire controller edit button event
			controllerEditBtn.fireEvent(new ClickEvent() {});

		}
	}

}
