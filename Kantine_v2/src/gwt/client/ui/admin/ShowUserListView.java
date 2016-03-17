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

	// id of person where event happened
	public int personId;

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
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

	/**
	 * Flextable bliver tilføjet rækker samt værdier.
	 * 
	 * @throws Exception
	 */

	public void pop(List<PersonDTO> pList) {
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
		userTable.getRowFormatter().addStyleName(0, "FlexTable-Header");

		for (int i = 0; i < pList.size(); i++) {
			userTable.setText(i + 1, 0, "" + pList.get(i).getId());
			userTable.setText(i + 1, 1, pList.get(i).getName());
			userTable.setText(i + 1, 2, "" + pList.get(i).getSaldo());
		}

		// Knapper til at slette bruger og opdatere saldo blive tilføjet til
		// hver række.
		for (int i = 0; i < pList.size(); i++) {
			Button edit = new Button("Opdater saldo");
			edit.getElement().setId("editButton");
			userTable.setWidget(i + 1, 3, edit);
			Button delete = new Button("Slet bruger");
			delete.getElement().setId("deleteButton");
			userTable.setWidget(i + 1, 4, delete);

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
			// eventRowIndex = t.getCellForEvent(event).getRowIndex();
			// populate personDTO
			// personDTO.setId(Integer.parseInt(t.getText(eventRowIndex, 0)));
			// personDTO.setName(t.getText(eventRowIndex, 1));
			// personDTO.setAge(Integer.parseInt(t.getText(eventRowIndex, 2)));
			// fire controller edit button event
			// controllerEditBtn.fireEvent(new ClickEvent() {});
			Window.alert("Opdatering af saldo");
		}
	}

}
