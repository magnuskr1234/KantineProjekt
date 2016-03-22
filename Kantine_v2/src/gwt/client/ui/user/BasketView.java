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
import gwt.shared.ItemDTO;
import gwt.shared.PersonDTO;


public class BasketView extends Composite {

	private static BasketViewUiBinder uiBinder = GWT.create(BasketViewUiBinder.class);
	@UiField FlexTable basketTable;
	@UiField Button buyBtn;
	@UiField Button cancelBtn;

	



	interface BasketViewUiBinder extends UiBinder<Widget, BasketView> {
	}

	//Buttons for events
	 private Button controllerEditBtn;
	 private Button controllerDeleteBtn;
	
	 
	 private UserMenuView um;
	 // Handlers
	 
	  private DeleteHandler deleteHandler;
	  
	  // row where event happened
	  private int eventRowIndex;
	  
	
	  private int eventRowIndexDelete;
	  
	  private double currentSaldo;
	  
	  private double newSaldo;
	  
	  private double sum;
	
	  private double tempSum;

	// reference to DTO 
	  ItemDTO itemDTO;
	//  private ItemDTO itemDTO;
	  
	public BasketView() {
		initWidget(uiBinder.createAndBindUi(this));
		um = new UserMenuView();
	    // row event handlers
	    itemDTO = new ItemDTO();
	    deleteHandler = new DeleteHandler();
	    
	    // buttons for controller event handling
	    controllerEditBtn = new Button();
	    controllerDeleteBtn = new Button();    
	    
	   // itemDTO = new ItemDTO();
	}
	
	public Button getCancelBtn(){
		return cancelBtn;
	}
	
	public Button getBuyBtn() {
		return buyBtn;
	}


	  public Button getControllerEditBtn() {
		    return controllerEditBtn;
		  }

		  public Button getControllerDeleteBtn() {
		    return controllerDeleteBtn;
		  }

		  																				
		  // delete row where delete-event happened
		  public void deleteEventRow() {
		    basketTable.removeRow(eventRowIndexDelete+1);
		  }
		  
		  public void emptyTable(){
			  UserMenuView.tempItemList.clear();
			  pop( UserMenuView.tempItemList);
			  
		  }
		  
		  // update data in row where edit-event happened 
		  public void updateRow(PersonDTO personDTO) {
			 basketTable.setText(eventRowIndex, 1, personDTO.getName());
			 basketTable.setText(eventRowIndex, 2, "" + personDTO.getPassword()); 
		  }
		  
	
		  
		  public void setCurrentUserSaldo(double saldo){
			  currentSaldo = saldo;
		  }
		  
		  public double getCurrentUserSaldo(){
			  return currentSaldo;
		  }
		  
		  public double getNewSaldo(){
			  return newSaldo;
		  }
		  
		  public double getSum(){
			  return sum;
		  }
		  
		  public void setSum(double s){
			  sum += s;
		  }
		  
		  public void clearSum(){
			  sum = 0;
		  }
		  

		  
	public void pop(List<ItemDTO> um){
		
		
  // remove table data
		basketTable.removeAllRows();
  // adjust column widths
		
  basketTable.getFlexCellFormatter().setWidth(0, 0, "50px");
  basketTable.getFlexCellFormatter().setWidth(0, 1, "50px");
  basketTable.getFlexCellFormatter().setWidth(0, 2, "25px");
  basketTable.getFlexCellFormatter().setWidth(0, 3, "50px");
  
 /* for(int i = 0; i < um.size(); i++){
	  for( int j = 0; j<um.get(i).getCount(); j++)
	  setSum(um.get(i).getPrice()); 
  }*/

 
  
  // set headers in flextable
  basketTable.setText(0, 0, "Vare");
  basketTable.setText(0, 1, "Antal");
  basketTable.setText(0, 2, "Pris");


  
  
  // style table
  basketTable.addStyleName("FlexTable");
  basketTable.getRowFormatter().addStyleName(0,"FlexTable-Header");
  basketTable.getRowFormatter().addStyleName(um.size()+1,"FlexTable-Header");
  basketTable.getRowFormatter().addStyleName(um.size()+2,"FlexTable-Header");
  

  
  for (int i=0; i < um.size(); i++) {
	 
	  basketTable.setText(i+1, 0, um.get(i).getName());
	  basketTable.setText(i+1, 1, Integer.toString(um.get(i).getCount()));
      basketTable.setText(i+1, 2, Double.toString((um.get(i).getPrice() * um.get(i).getCount())));
     
     // sum += Double.parseDouble(basketTable.getText(i, 2));
      basketTable.setText(um.size()+1, 0, "I alt: ");
     // basketTable.setText(um.size()+1, 2, "" + sum);
      basketTable.setText(um.size()+2, 0, "Saldo efter køb: ");
      basketTable.setText(um.size()+2, 2, "" + (currentSaldo - sum));
   

      Button controllerDeleteBtn = new Button("Fjern");
      controllerDeleteBtn.getElement().setId("deleteButton");
      basketTable.setWidget(i+1, 3, controllerDeleteBtn);

      // add edit and delete buttons to row
     
      controllerDeleteBtn.addClickHandler(deleteHandler);
      
     
    }

  
  		newSaldo = (currentSaldo - sum);  
	}
	
/*	public void calc (double addToSum){
		 for (int j = 0; j < basketTable.getRowCount(); j++){
			  	
			 tempSum = Double.parseDouble(basketTable.getText(j+2, 2));
			 
			 setSum(addToSum);
		  }
	}
	
	*/
	private class DeleteHandler implements ClickHandler {
		    public void onClick(ClickEvent event) {
		    	
		    	clearSum();
		    	eventRowIndexDelete = 0;
		      // save event row index
		    	eventRowIndexDelete = basketTable.getCellForEvent(event).getRowIndex() - 1;
		      // save person id
		     // personId = Integer.parseInt(t.getText(eventRowIndex, 0));
		      // fire controller delete button event
		    	
		    	//um.deleteFromList(eventRowIndexDelete);
		    	
		    	/*
		    	
		    	if (UserMenuView.tempItemList.get(eventRowIndexDelete).getCount < 1){
		    		
		    	}
		    	
		    	*/
		    	setSum(-UserMenuView.tempItemList.get(eventRowIndexDelete).getPrice());

		    	UserMenuView.tempItemList.remove(eventRowIndexDelete);
		    	controllerDeleteBtn.fireEvent(new ClickEvent() {
		    	
		    	});
		    
		    	
		    	
		    	Window.alert("Vare fjernet fra kurv");	
		    }
		    
	 }

}
