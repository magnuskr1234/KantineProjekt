package gwt.client.ui.user;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import gwt.server.dal.ItemDAO;
import gwt.shared.ItemDTO;

public class BasketView extends Composite {

	private static BasketViewUiBinder uiBinder = GWT.create(BasketViewUiBinder.class);
	@UiField FlexTable basketTable;

	interface BasketViewUiBinder extends UiBinder<Widget, BasketView> {
	}

	//Buttons for events
	 private Button controllerEditBtn;
	 private Button controllerDeleteBtn;
	 
	 // Handlers
	  private EditHandler editHandler;
	  private DeleteHandler deleteHandler;
	  
	// reference to DTO 
	  
	  
	public BasketView() {
		initWidget(uiBinder.createAndBindUi(this));

	    // row event handlers
	    editHandler = new EditHandler();
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

	public void pop(List<ItemDTO> pList){
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
  
  
  for (int i=0; i < pList.size(); i++) {
	  basketTable.setText(i+1, 0, "" + pList.get(i).getName());
	  basketTable.setText(i+1, 1, Integer.toString(pList.get(i).getCount()));
      basketTable.setText(i+1, 2, "" + (pList.get(i).getPrice() * pList.get(i).getCount()));
   
      Button edit = new Button("Tilføj");
      edit.getElement().setId("editButton");
      basketTable.setWidget(i+1, 3, edit);
      Button delete = new Button("Fjern");
      delete.getElement().setId("editButton");
      basketTable.setWidget(i+1, 4, delete);

      // add edit and delete buttons to row
      edit.addClickHandler(editHandler);
      delete.addClickHandler(deleteHandler);
      
    }
  
  
 /* for (int i=0; i < 2; i++){
  Button edit = new Button("Tilføj");
  edit.getElement().setId("editButton");
  basketTable.setWidget(i+1, 3, edit);
  Button delete = new Button("Fjern");
  delete.getElement().setId("editButton");
  basketTable.setWidget(i+1, 4, delete);

  // add edit and delete buttons to row
  edit.addClickHandler(editHandler);
  delete.addClickHandler(deleteHandler);
  }*/
	}
	
	 private class DeleteHandler implements ClickHandler {
		    public void onClick(ClickEvent event) {
		      // save event row index
		      //eventRowIndex = t.getCellForEvent(event).getRowIndex();
		      // save person id
		     // personId = Integer.parseInt(t.getText(eventRowIndex, 0));
		      // fire controller delete button event
		      //controllerDeleteBtn.fireEvent(new ClickEvent() {});
		    	Window.alert("Vare slettet");
		    }
		  } 
	 
	 private class EditHandler implements ClickHandler {
		    public void onClick(ClickEvent event) {
		      // get rowindex where event happened
		    //  eventRowIndex = t.getCellForEvent(event).getRowIndex();    
		      // populate personDTO
		    //  personDTO.setId(Integer.parseInt(t.getText(eventRowIndex, 0)));
		    //  personDTO.setName(t.getText(eventRowIndex, 1));
		    //  personDTO.setAge(Integer.parseInt(t.getText(eventRowIndex, 2))); 
		      // fire controller edit button event
		     // controllerEditBtn.fireEvent(new ClickEvent() {}); 
		    	Window.alert("En ekstra tilføjet");
		    }
		    
	 }

}
