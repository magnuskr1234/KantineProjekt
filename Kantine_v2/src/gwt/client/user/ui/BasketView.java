package gwt.client.user.ui;

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

	public void pop(){
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
  
  
  basketTable.setText(1, 0, "Kød" );
  basketTable.setText(1, 1, "1");
  basketTable.setText(1, 2, "25" );
  
  basketTable.setText(2, 0, "Banan" );
  basketTable.setText(2, 1, "2");
  basketTable.setText(2, 2, "5" );
  
  
  for (int i=0; i < 2; i++){
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
