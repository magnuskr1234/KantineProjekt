package gwt.client.ui.admin;

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
import com.sun.java.swing.plaf.windows.resources.windows;

import gwt.shared.PersonDTO;

import java.util.List;

/**
 * Flextable bliver oprettet her og der bliver lavet en liste af alle brugere i
 * systemet fra databasen.
 * 
 * @author magnusrasmussen
 *
 */
public class ShowUserListView extends Composite {

	private static ShowUserListUiBinder uiBinder = GWT.create(ShowUserListUiBinder.class);

	@UiField
	public FlexTable userTable;

	interface ShowUserListUiBinder extends UiBinder<Widget, ShowUserListView> {
	}

	// Buttons for events
	private Button controllerEditBtn;
	private Button controllerDeleteBtn;

	// row where event happened
	private int eventRowIndex;
	private String AdminStatus = null;

	// id of person where event happened
	public int personId;
	public static double saldoUpdate;
	
	public static double getSaldoUpdate(){
		return saldoUpdate;
	}

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}
	
	public void setSaldo(double saldo){
		this.saldoUpdate = saldoUpdate;
	}

	// Handlers
	private EditHandler editHandler;
	private DeleteHandler deleteHandler;

	private PersonDTO personDTO;

	public ShowUserListView() {
		initWidget(uiBinder.createAndBindUi(this));
		// row event handlers
		editHandler = new EditHandler();
		deleteHandler = new DeleteHandler();

		// buttons for controller event handling
		controllerEditBtn = new Button();
		controllerDeleteBtn = new Button();

		personDTO = new PersonDTO();

		/*
		 * try { db.savePerson(person) ; } catch (Exception e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */
	}

	public Button getControllerEditBtn() {
		return controllerEditBtn;
	}

	public Button getControllerDeleteBtn() {
		return controllerDeleteBtn;
	}

	public PersonDTO getPersonDTO() {
		return personDTO;
	}

	 // delete row where delete-event happened
	  public void deleteEventRow() {
	    userTable.removeRow(eventRowIndex);
	  }
	  
	  // update data in row where edit-event happened 
	  public void updateRow(PersonDTO personDTO) {
	    userTable.setText(eventRowIndex, 1, personDTO.getName());
	    userTable.setText(eventRowIndex, 2, "" + personDTO.getSaldo()); 
	  }
	/**
	 * Flextable bliver tilføjet rækker samt værdier.
	 * 
	 * @throws Exception
	 */

	public void pop(List<PersonDTO> pList) {
		// remove table data
		userTable.removeAllRows();

		// adjust column widths
		userTable.getFlexCellFormatter().setWidth(0, 0, "50px");
		userTable.getFlexCellFormatter().setWidth(0, 1, "110px");
		userTable.getFlexCellFormatter().setWidth(0, 2, "110px");
		userTable.getFlexCellFormatter().setWidth(0, 3, "50px");
		userTable.getFlexCellFormatter().setWidth(0, 4, "75px");
		userTable.getFlexCellFormatter().setWidth(0, 5, "50px");
		userTable.getFlexCellFormatter().setWidth(0, 6, "50px");

		// set headers in flextable
		userTable.setText(0, 0, "Id");
		userTable.setText(0, 1, "Brugernavn");
		userTable.setText(0, 2, "Adgangskode");
		userTable.setText(0, 3, "Saldo");
		userTable.setText(0, 4, "Administrator");

		// style table
		userTable.addStyleName("FlexTable");
		userTable.getRowFormatter().addStyleName(0, "FlexTable-Header");

		for (int i = 0; i < pList.size(); i++) {
			userTable.setText(i + 1, 0, "" + pList.get(i).getId());
			userTable.setText(i + 1, 1, pList.get(i).getName());
			userTable.setText(i + 1, 2, "" + pList.get(i).getPassword());
			userTable.setText(i + 1, 3, "" + pList.get(i).getSaldo());
			
			if (pList.get(i).getAdminStatus() == 1){
				AdminStatus = "Ja";
			}else{
				AdminStatus = "Nej";
			}
			
			userTable.setText(i + 1, 4, AdminStatus); 
		}

		// Knapper til at slette bruger og opdatere saldo blive tilføjet til
		// hver række.pList.size()
		for (int i = 0; i < pList.size(); i++) {
			Button edit = new Button("Tilføj til saldo");
			edit.getElement().setId("editButton");
			userTable.setWidget(i + 1, 5, edit);
			
			Button delete = new Button("Slet bruger");
			delete.getElement().setId("deleteButton");
			userTable.setWidget(i + 1, 6, delete);

			// add edit and delete buttons to row
			edit.addClickHandler(editHandler);
			delete.addClickHandler(deleteHandler);
		}
		
		
	}

	
	// Handler til at håndtere et tryk på knappen "Slet bruger"
	private class DeleteHandler implements ClickHandler {
		public void onClick(ClickEvent event) {
			// save event row index
			eventRowIndex = userTable.getCellForEvent(event).getRowIndex();

			// save person id
			personId = Integer.parseInt(userTable.getText(eventRowIndex, 0));

			// fire controller delete button event
			controllerDeleteBtn.fireEvent(new ClickEvent() {
			});

		}
	}

	// Handler til at håndtere et tryk på knappen "Opdater saldo"
	private class EditHandler implements ClickHandler {
		public void onClick(ClickEvent event) {
			
			// get rowindex where event happened
			eventRowIndex = userTable.getCellForEvent(event).getRowIndex();
			// populate personDTO
			 personDTO.setId(Integer.parseInt(userTable.getText(eventRowIndex, 0)));
			 personDTO.setName(userTable.getText(eventRowIndex, 1));
			 personDTO.setSaldo(Double.parseDouble(userTable.getText(eventRowIndex, 3))); 
			 
			// save person id
			setPersonId( Integer.parseInt(userTable.getText(eventRowIndex, 0)));
			
				
			 saldoUpdate = Double.parseDouble(userTable.getText(eventRowIndex, 3));
			// fire controller edit button event
			controllerEditBtn.fireEvent(new ClickEvent() {});
			
			
		}
	}

}
