package gwt.client.ui.user;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;

import gwt.shared.ItemDTO;

/** 
 * This view contains all the elements for the user menu. 
 * @author magnusrasmussen
 *
 */
public class UserMenuView extends Composite {

	private static UserMenuViewUiBinder uiBinder = GWT.create(UserMenuViewUiBinder.class);
	@UiField FlexTable itemTable;

	interface UserMenuViewUiBinder extends UiBinder<Widget, UserMenuView> {
	}

	// Buttons for events
	private Button addToBasketBtn;
	private BasketView basketView;
	
	// row where event happened
	private int eventRowIndex;

	// id of person where event happened
	public int itemId;
	public String name;

	private static double cuSaldo;
	
	// List to hold item objects 
	public static List<ItemDTO> tempItemList  = new ArrayList<ItemDTO>();


	public Button getAddToBasketBtn() {
		return addToBasketBtn;
	}
	
	public Button getControllerEditBtn() {
		return addToBasketBtn;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	// Handlers
	private AddToBasketHandler addToBasketHandler;
	private ItemDTO itemDTO;



	public UserMenuView() {
		initWidget(uiBinder.createAndBindUi(this));
		
		addToBasketHandler = new AddToBasketHandler();

		// buttons for controller event handling
		addToBasketBtn = new Button();
	}
	
	
	
	public static void setcuSaldo(double s) {
		cuSaldo = s;
	}
	
	public static double getcuSaldo(){
		return cuSaldo;
	}
	
	//Flextable gets populated with rows and values. 
	public void populateUserMenu(List<ItemDTO> pList) {
		// remove table data
		itemTable.removeAllRows();
		
		// adjust column widths
		itemTable.getFlexCellFormatter().setWidth(0, 0, "25px");
		itemTable.getFlexCellFormatter().setWidth(0, 1, "200px");
		itemTable.getFlexCellFormatter().setWidth(0, 2, "25px");
		itemTable.getFlexCellFormatter().setWidth(0, 3, "100px");

		// set headers in flextable
		itemTable.setText(0, 0, "Id");
		itemTable.setText(0, 1, "Navn");
		itemTable.setText(0, 2, "Pris");

		// style table
		itemTable.addStyleName("FlexTable");
		itemTable.getRowFormatter().addStyleName(0, "FlexTable-Header");

		for (int i = 0; i < pList.size(); i++) {
			itemTable.setText(i + 1, 0, "" + pList.get(i).getId());
			itemTable.setText(i + 1, 1, pList.get(i).getName());
			itemTable.setText(i + 1, 2, "" + pList.get(i).getPrice());
		}

		// Buttons for adding item to basket, is added to each row. 
		for (int i = 0; i < pList.size(); i++) {
			Button addToBasketBtn = new Button("Tilføj");
			addToBasketBtn.getElement().setId("editBtn");
			itemTable.setWidget(i + 1, 3, addToBasketBtn);
			
			//Style buttons
			addToBasketBtn.setStylePrimaryName("topNavBtn");

			// add edit and delete buttons to row
			addToBasketBtn.addClickHandler(addToBasketHandler);
		}
	}

	// Handler til at håndtere et tryk på knappen "tilføj"
	private class AddToBasketHandler implements ClickHandler {
		
		@Override
		public void onClick(ClickEvent event) {	
			
			boolean addItem = true;
			itemDTO = new ItemDTO();
			basketView = new BasketView();
			
			// get rowindex where event happened
			eventRowIndex = itemTable.getCellForEvent(event).getRowIndex();
			
			// populate itemDTO
			itemDTO.setId(Integer.parseInt(itemTable.getText(eventRowIndex, 0)));
			itemDTO.setName(itemTable.getText(eventRowIndex, 1));
			itemDTO.setPrice(Double.parseDouble(itemTable.getText(eventRowIndex, 2))); 
		
			//check if user have sufficient funds. 
			if(saldoCheck()){
			// save item id
			setItemId( Integer.parseInt(itemTable.getText(eventRowIndex, 0)));

			for (ItemDTO itemname : tempItemList){	
				if(itemname.getName().equals(itemDTO.getName())){
					addItem = false;
					itemname.setCount(itemname.getCount()+1);
				}
			}

			if (addItem){
				tempItemList.add(itemDTO);
			}
			
			basketView.populateBasket(tempItemList);
			addToBasketBtn.fireEvent(new ClickEvent() {
			});
			}
		
		}
	}
	
	// Saldo check
	public boolean saldoCheck(){
	
		if(cuSaldo >= itemDTO.getPrice()){
			//Update current saldo
			setcuSaldo(cuSaldo - itemDTO.getPrice());
			return true;
		}else{
			Window.alert("Ikke nok penge");
		}return false;
	}

	public void deleteFromList(int i){
		tempItemList.remove(i);
	}
}
