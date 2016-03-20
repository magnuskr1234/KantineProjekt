package gwt.client.ui.user;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.sun.java.swing.plaf.windows.resources.windows;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import gwt.shared.ItemDTO;
import gwt.shared.PersonDTO;

public class BasketView extends Composite {

	private static BasketViewUiBinder uiBinder = GWT.create(BasketViewUiBinder.class);
	@UiField FlexTable basketTable;

	interface BasketViewUiBinder extends UiBinder<Widget, BasketView> {
	}

	//Buttons for events
	 private Button controllerEditBtn;
	 private Button controllerDeleteBtn;
	 
	 // Handlers
	  private AddHandler addHandler;
	  private DeleteHandler deleteHandler;
	  
	  // row where event happened
	  private int eventRowIndex;
	  
	// reference to DTO 
	  
	  
	public BasketView() {
		initWidget(uiBinder.createAndBindUi(this));

	    // row event handlers
	    addHandler = new AddHandler();
	    deleteHandler = new DeleteHandler();
	    
	    // buttons for controller event handling
	    controllerEditBtn = new Button();
	    controllerDeleteBtn = new Button();    
	}
	
	  public Button getControllerEditBtn() {
		    return controllerEditBtn;
		  }

		  public Button getControllerDeleteBtn() {
		    return controllerDeleteBtn;
		  }

		  
		  // delete row where delete-event happened
		  public void deleteEventRow() {
		    basketTable.removeRow(eventRowIndex);
		  }
		  
		  // update data in row where edit-event happened 
		  public void updateRow(PersonDTO personDTO) {
			 basketTable.setText(eventRowIndex, 1, personDTO.getName());
			 basketTable.setText(eventRowIndex, 2, "" + personDTO.getPassword()); 
		  }
		  
		  public void addToList(List<ItemDTO> basketList){
			  
			  Window.alert("addtolist - basket og size:" + basketList.size());
			  basketList.add(new ItemDTO("Test", 20));
		  }
		  
	public void pop(List<ItemDTO> basketList){
  // remove table data
  basketTable.removeAllRows();
  // adjust column widths
  basketTable.getFlexCellFormatter().setWidth(0, 0, "25px");
  basketTable.getFlexCellFormatter().setWidth(0, 1, "200px");
  basketTable.getFlexCellFormatter().setWidth(0, 2, "25px");
  basketTable.getFlexCellFormatter().setWidth(0, 3, "100px");
  basketTable.getFlexCellFormatter().setWidth(0, 4, "100px");


  // set headers in flextable
  basketTable.setText(0, 0, "Vare");
  basketTable.setText(0, 1, "Antal");
  basketTable.setText(0, 2, "Pris");
  
  // style table
  basketTable.addStyleName("FlexTable");
  basketTable.getRowFormatter().addStyleName(0,"FlexTable-Header");
  
  
  for (int i=0; i < basketList.size(); i++) {
	  basketTable.setText(i+1, 0, "" + basketList.get(i).getName());
	  basketTable.setText(i+1, 1, Integer.toString(basketList.get(i).getCount()));
      basketTable.setText(i+1, 2, "" + (basketList.get(i).getPrice() * basketList.get(i).getCount()));
   
      Button edit = new Button("Tilføj");
      edit.getElement().setId("addButton");
      basketTable.setWidget(i+1, 3, edit);
      Button delete = new Button("Fjern");
      delete.getElement().setId("deleteButton");
      basketTable.setWidget(i+1, 4, delete);

      // add edit and delete buttons to row
      edit.addClickHandler(addHandler);
      delete.addClickHandler(deleteHandler);
      
    }
	}
	
	 private class DeleteHandler implements ClickHandler {
		    public void onClick(ClickEvent event) {
		      // save event row index
		    	eventRowIndex = basketTable.getCellForEvent(event).getRowIndex();
		      // save person id
		     // personId = Integer.parseInt(t.getText(eventRowIndex, 0));
		      // fire controller delete button event
		      controllerDeleteBtn.fireEvent(new ClickEvent() {});
		    	Window.alert("Vare slettet");
		    }
		  } 
	 
	 private class AddHandler implements ClickHandler {
		    public void onClick(ClickEvent event) {
		      // get rowindex where event happened
		     eventRowIndex = basketTable.getCellForEvent(event).getRowIndex();    
		      // populate personDTO
		    //  personDTO.setId(Integer.parseInt(t.getText(eventRowIndex, 0)));
		    //  personDTO.setName(t.getText(eventRowIndex, 1));
		    //  personDTO.setAge(Integer.parseInt(t.getText(eventRowIndex, 2))); 
		      // fire controller edit button event
		      controllerEditBtn.fireEvent(new ClickEvent() {}); 
		    	Window.alert("En ekstra tilføjet");
		    }
		    
	 }

}
