package gwt.client.ui.user;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;

import gwt.shared.ItemDTO;


import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

public class UserMenuView extends Composite {

	private static UserMenuViewUiBinder uiBinder = GWT.create(UserMenuViewUiBinder.class);

	@UiField FlexTable storeItems;
	interface UserMenuViewUiBinder extends UiBinder<Widget, UserMenuView> {
	}

	// Buttons for controller event
	  private Button controllerAddToBasketBtn;



	  // handlers for row events

	  private AddToBasketHandler addToBasketHandler;

	  // row where event happened
	  private int eventRowIndex;
	  
	  // id of person where event happened
	  private int itemId;

	  // person DTO
	  private ItemDTO itemDTO;
	  
	  
	  public UserMenuView() {
	    initWidget(uiBinder.createAndBindUi(this));

	    // personDTO to hold row data
	  //  itemDTO = new ItemDTO();
	    
	    // row event handlers
	   
	    addToBasketHandler = new AddToBasketHandler();
	    
	    // buttons for controller event handling
	   
	    controllerAddToBasketBtn = new Button();
	  }


	  public ItemDTO getItemDTO() {
	    return itemDTO;
	  }


	  public int getItemId() {
	    return itemId;
	  }


	  public Button getControllerAddToBasketBtn() {
	    return controllerAddToBasketBtn;
	  }
	  
	  public void populateData(List<ItemDTO> pList) {
	    
	    // remove table data
		  storeItems.removeAllRows();
	        
	    // adjust column widths
		  storeItems.getFlexCellFormatter().setWidth(0, 0, "25px");
		  storeItems.getFlexCellFormatter().setWidth(0, 1, "200px");
		  storeItems.getFlexCellFormatter().setWidth(0, 2, "25px");



	    // set headers in flextable
		  storeItems.setText(0, 0, "Navn");
		  storeItems.setText(0, 1, "Pris");
	 
	    
	    // style table
		  storeItems.addStyleName("FlexTable");
		  storeItems.getRowFormatter().addStyleName(0,"FlexTable-Header");
	      
	    
	    
	    // populate table and add edit and delete anchor to each row KAN TILFØJES NÅR DB ER LAVET
	/*    for (int i=0; i < pList.size(); i++) {
	      storeItems.setText(i+1, 0, "" + pList.get(i).getName());
	      storeItems.setText(i+1, 1, pList.get(i).getprice());
	     
	     
	      Button delete = new Button("Tilføj");
	      addToBasket.getElement().setId("addButton");
	      storeItems.setWidget(i+1, 4, addToBasket);

	      // add edit and delete buttons to row
	      addToBasket.addClickHandler(AddToBasketHandler);
	    }*/
	  }
	  

	 
	  private class AddToBasketHandler implements ClickHandler {
	    public void onClick(ClickEvent event) {
	      // save event row index
	    //  eventRowIndex = addToBasket.getCellForEvent(event).getRowIndex();
	      // save item id
	    //  itemId = Integer.parseInt(storeItems.getText(eventRowIndex, 0));
	      // fire controller delete button event
	    //  controllerDeleteBtn.fireEvent(new ClickEvent() {});
	    	
	    	Window.alert("Tilføjet til kurv");
	    }
	  } 
	
}
