package gwt.client.ui;

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


import java.util.List;


public class ShowUserList extends Composite {

	private static ShowUserListUiBinder uiBinder = GWT.create(ShowUserListUiBinder.class);

	@UiField FlexTable userTable;
	interface ShowUserListUiBinder extends UiBinder<Widget, ShowUserList> {
	}

	
	//Buttons for events
	 private Button controllerEditBtn;
	 private Button controllerDeleteBtn;
	 
	 // Handlers
	  private EditHandler editHandler;
	  private DeleteHandler deleteHandler;
	 
	public ShowUserList() {
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
    userTable.removeAllRows();
        
	
    // adjust column widths
    userTable.getFlexCellFormatter().setWidth(0, 0, "25px");
    userTable.getFlexCellFormatter().setWidth(0, 1, "200px");
    userTable.getFlexCellFormatter().setWidth(0, 2, "25px");
    userTable.getFlexCellFormatter().setWidth(0, 3, "100px");
    userTable.getFlexCellFormatter().setWidth(0, 4, "100px");


    // set headers in flextable
    userTable.setText(0, 0, "Id");
    userTable.setText(0, 1, "Brugernavn");
    userTable.setText(0, 2, "Saldo");
    
    // style table
    userTable.addStyleName("FlexTable");
    userTable.getRowFormatter().addStyleName(0,"FlexTable-Header");
    
    
    userTable.setText(1, 0, "0" );
    userTable.setText(1, 1, "Magnus");
    userTable.setText(1, 2, "200" );
    
    userTable.setText(2, 0, "0" );
    userTable.setText(2, 1, "Alexander med flere");
    userTable.setText(2, 2, "1" );
    
    
    for (int i=0; i < 2; i++){
    Button edit = new Button("Opdater saldo");
    edit.getElement().setId("editButton");
    userTable.setWidget(i+1, 3, edit);
    Button delete = new Button("Slet bruger");
    delete.getElement().setId("editButton");
    userTable.setWidget(i+1, 4, delete);

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
		    	Window.alert("Bruger slettet");
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
		    	Window.alert("Opdatering af saldo");
		    }
		  }
}
