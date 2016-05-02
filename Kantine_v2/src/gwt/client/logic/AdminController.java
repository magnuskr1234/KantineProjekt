package gwt.client.logic;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import gwt.client.service.ItemServiceAsync;
import gwt.client.service.PersonServiceAsync;
import gwt.client.ui.MainView;
import gwt.client.ui.admin.AdminHeaderView;
import gwt.client.ui.admin.AdminMenuView;
import gwt.client.ui.admin.AdminView;
import gwt.client.ui.admin.CreateItemView;
import gwt.client.ui.admin.CreateUserView;
import gwt.client.ui.admin.EditItemView;
import gwt.client.ui.admin.EditUserView;
import gwt.client.ui.admin.ShowItemListView;
import gwt.client.ui.admin.ShowUserListView;
import gwt.client.ui.admin.StatisticView;
import gwt.shared.ItemDTO;
import gwt.shared.PersonDTO;

/**
 * The admin controller handles the admin views and logic.
 * 
 * @author magnusrasmussen
 *
 */
public class AdminController {

	// References for views
	private AdminView adminView;
	private MainView mainView;
	private AdminMenuView adminMenu;
	private AdminHeaderView adminHeaderView;
	private CreateUserView createUserView;
	private CreateItemView createItemView;
	private StatisticView statisticView;
	private EditItemView editItemView;
	private EditUserView editUserView;
	private ShowUserListView showUserListView;
	private ShowItemListView showItemListView;

	// Service classes for async callbacks to server
	private ItemServiceAsync itemServiceCall;
	private PersonServiceAsync personServiceCall;

	// Currentperson 
	public int currentPersonId;
	public PersonDTO currentPeron;

	/**
	 * Constructor for admincontroller
	 * @param mainView
	 * @param personServiceCall
	 * @param itemServiceCall
	 */
	public AdminController(MainView mainView, PersonServiceAsync personServiceCall, ItemServiceAsync itemServiceCall) {

		this.mainView = mainView;
		this.adminView = mainView.getAdminView();
		this.itemServiceCall = itemServiceCall;
		this.personServiceCall = personServiceCall;

		adminHeaderView = adminView.getadminHeaderView();
		adminMenu = adminView.getadminMenu();
		createItemView = adminView.getcreateItem();
		createUserView = adminView.getcreateUser();
		editItemView = adminView.geteditItem();
		editUserView = adminView.geteditPerson();
		showUserListView = adminView.getshowUserList();
		showItemListView = adminView.getshowItemList();
		statisticView = adminView.getstatistic();

		// Add adminMenuView Handlers
		adminMenu.getBtnCreateItem().addClickHandler(new CreateItemViewHandler());
		adminMenu.getBtnCreateUser().addClickHandler(new CreateUserHandler());
		adminMenu.getBtnShowUsers().addClickHandler(new ShowUserListHandler());
		adminMenu.getBtnShowItems().addClickHandler(new ShowItemListHandler());
		adminMenu.getBtnStatistic().addClickHandler(new StatisticsHandler());

		// Add adminHeaderView Handlers
		adminHeaderView.getBtnMainMenu().addClickHandler(new ReturnMainViewHandler());
		adminHeaderView.getBtnLogout().addClickHandler(new LogoutHandler());

		// Add createUserView handler
		createUserView.getCreateUserBtn().addClickHandler(new CreateUserHandler());

		// Add update saldo handler
		editUserView.getBtnCancel().addClickHandler(new UpdateSaldoHandler());
		editUserView.getBtnConfirm().addClickHandler(new UpdateSaldoHandler());

		// Add createItemView handler
		createItemView.getcreateItemBtn().addClickHandler(new CreateItemHandler());

		// Add user options handler
		showUserListView.getControllerDeleteBtn().addClickHandler(new ShowUserListHandler());
		showUserListView.getControllerEditBtn().addClickHandler(new UpdateSaldoHandler());

		// Add item options handler
		showItemListView.getControllerDeleteBtn().addClickHandler(new ShowItemListHandler());

		// Edit item price handler
		editItemView.getBtnCancel().addClickHandler(new UpdateItemPriceHandler());
		editItemView.getBtnConfirm().addClickHandler(new UpdateItemPriceHandler());
		showItemListView.getControllerEditBtn().addClickHandler(new UpdateItemPriceHandler());
	}

	// Set current person
	public void setCurrentPerson(PersonDTO currentPerson) {
		this.currentPeron = currentPerson;
		this.currentPersonId = currentPerson.getId();
	}

	// Logout handler
	private class LogoutHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			mainView.changeView(mainView.getLoginView());
		}
	}

	/**
	 * Clickhandler for to handle price update for an item
	 * @author magnusrasmussen
	 *
	 */
	private class UpdateItemPriceHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {		

			// Cancel button click 
			if(event.getSource() == editItemView.getBtnCancel()){
				editItemView.clearFields();
				adminMenu.getBtnShowItems().fireEvent(new ClickEvent() {});
			}

			// Confirm update button
			if(event.getSource() == editItemView.getBtnConfirm()){	
				if (editItemView.validate(showItemListView)){

					itemServiceCall.updateItem(editItemView.getNewPrice(), editItemView.getPriceItemId(), new AsyncCallback<Void>(){

						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Serverfejl :" + caught.getMessage());
						}

						@Override
						public void onSuccess(Void result) {
							Window.alert("Pris opdateret!");
							adminMenu.getBtnShowItems().fireEvent(new ClickEvent() {
							});
						}

					});
				}}

			// Edit item price button
			if(event.getSource()==showItemListView.getControllerEditBtn()){
				adminView.changeWidget(editItemView);

			}}}

	/**
	 * Clickhandler for updating a user saldo. 
	 * @author magnusrasmussen
	 *
	 */
	private class UpdateSaldoHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// Button for confirmation of edit user.
			if (event.getSource() == editUserView.getBtnConfirm()) {

				// If edit user validates
				if (editUserView.validate(showUserListView)) {

					personServiceCall.updatePerson(editUserView.getNewSaldo(), editUserView.getSaldoUserId(),
							new AsyncCallback<Void>() {

						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Serverfejl :" + caught.getMessage());
						}

						@Override
						public void onSuccess(Void result) {
							Window.alert("Saldo opdateret!");
							adminMenu.getBtnShowUsers().fireEvent(new ClickEvent() {
							});
							adminView.changeWidget(showUserListView);
							itemServiceCall.saveItemToHistory(editUserView.getSaldoUserId(), "Tilføjet til Saldo",
									editUserView.getSaldo(), editUserView.getNewSaldo(), new AsyncCallback<Void>() {

								@Override
								public void onFailure(Throwable caught) {
									Window.alert("Serverfejl" + caught.getMessage());
								}

								@Override
								public void onSuccess(Void result) {
								}

							});
						}

					});
				}
			}

			//If source is cancel
			if (event.getSource() == editUserView.getBtnCancel()) {
				editUserView.clearFields();
				adminView.changeWidget(showUserListView);
			}

			// If source is from admin menu 
			if (event.getSource() == showUserListView.getControllerEditBtn()) {
				adminView.changeWidget(editUserView);
			}
		}
	}

	/**
	 * Clickhandler for creating a user
	 * @author magnusrasmussen
	 *
	 */
	private class CreateUserHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// If source is createuser from admin 
			if (event.getSource() == adminMenu.getBtnCreateUser()) {
				adminView.changeWidget(createUserView);
			}

			// If source is create new user from createuserview. 
			if (event.getSource() == createUserView.getCreateUserBtn())
				personServiceCall.getPersons((new AsyncCallback<List<PersonDTO>>() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Serverfejl :" + caught.getMessage());
					}

					@Override
					public void onSuccess(List<PersonDTO> result) {

						if (createUserView.validate(result)) {
							personServiceCall.savePerson(new PersonDTO(createUserView.getCurrentPerson().getName().toLowerCase(),
									createUserView.getCurrentPerson().getPassword(),
									createUserView.getCurrentPerson().getAdminStatus(),
									createUserView.getCurrentPerson().getSaldo()),new AsyncCallback<Void>() {

								@Override
								public void onFailure(Throwable caught) {
									Window.alert("Serverfejl :" + caught.getMessage());
								}

								@Override
								public void onSuccess(Void result) {
									Window.alert("Person gemt");
								}

							});
						}

					}
				}));
		}
	}

	/**
	 * Clickhandler for creating items
	 * @author magnusrasmussen
	 *
	 */
	private class CreateItemHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// If button source is create item
			if (event.getSource() == createItemView.getcreateItemBtn()){

				if (createItemView.validate()){
					itemServiceCall.getItem(createItemView.getCurrentItem().getName(), new AsyncCallback<ItemDTO>(){

						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Serverfejl :" + caught.getMessage());
						}

						@Override
						public void onSuccess(ItemDTO item) {
							//Check if user is null
							if(item != null){
								Window.alert("Varen findes allerede");
							} else {
								itemServiceCall.saveItem(new ItemDTO(createItemView.getCurrentItem().getName(),
										createItemView.getCurrentItem().getPrice()), new AsyncCallback<Void>() {

									@Override
									public void onFailure(Throwable caught) {
										Window.alert("Serverfejl :" + caught.getMessage());
									}

									@Override
									public void onSuccess(Void result) {
										Window.alert("Varen blev oprettet");
									}

								});
							} 

						}
					});
				}
			}
		}

	}
	/**
	 * CLickhandler for returning to mainmenu. 
	 * @author magnusrasmussen
	 *
	 */
	private class ReturnMainViewHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			createItemView.clearFields();
			createUserView.clearFields();
			adminView.changeWidget(adminMenu);
		}
	}

	/**
	 * Clickhandler to show vire for creating new item
	 * @author magnusrasmussen
	 *
	 */
	private class CreateItemViewHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			adminView.changeWidget(createItemView);
		}
	}

	/**
	 * Clickhandler for showing userlist. 
	 * @author magnusrasmussen
	 *
	 */
	private class ShowUserListHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			int personId = showUserListView.getPersonId();

			// If source is button to delete a user from the list
			if (event.getSource() == showUserListView.getControllerDeleteBtn())
				if (Window.confirm("Er du sikker på at du vil slette brugeren?"))
					personServiceCall.deletePerson(personId, new AsyncCallback<Void>() {
						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Serverfejl :" + caught.getMessage());
						}

						@Override
						public void onSuccess(Void result) {
							showUserListView.deleteEventRow();
							Window.alert("Person slettet");

						}
					});

			// Source for adminmenu to show all users
			if (event.getSource() == adminMenu.getBtnShowUsers()) {
				personServiceCall.getPersons(new AsyncCallback<List<PersonDTO>>() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Serverfejl :" + caught.getMessage());
					}

					@Override
					public void onSuccess(List<PersonDTO> result) {
						showUserListView.populateUserList(result);
					}
				});

			}
			adminView.changeWidget(showUserListView);
		}
	}

	/**
	 * Clickhandler for showing itemlist 
	 * @author magnusrasmussen
	 *
	 */
	private class ShowItemListHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			int itemId = showItemListView.getItemId();

			// Source for deleting an item
			if (event.getSource() == showItemListView.getControllerDeleteBtn())
				if (Window.confirm("Er du sikker på at du vil slette varen?"))
					itemServiceCall.deleteItem(itemId, new AsyncCallback<Void>() {
						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Serverfejl :" + caught.getMessage());
						}

						@Override
						public void onSuccess(Void result) {

							adminMenu.getBtnShowItems().fireEvent(new ClickEvent() {
							});
							Window.alert("Varen blev slettet");

						}
					});
			// source for showing itemlist view. 
			if (event.getSource() == adminMenu.getBtnShowItems()) {
				itemServiceCall.getItems(new AsyncCallback<List<ItemDTO>>() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Serverfejl :" + caught.getMessage());
					}

					@Override
					public void onSuccess(List<ItemDTO> result) {
						showItemListView.populateItemList(result);
					}
				});

			}
			adminView.changeWidget(showItemListView);
		}
	}

	/**
	 * Clickhandler for statistics
	 * @author magnusrasmussen
	 *
	 */
	private class StatisticsHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			statisticView.clearStatSum();
			itemServiceCall.getMostSoldItems(new AsyncCallback<List<ItemDTO>>() {

				@Override
				public void onFailure(Throwable caught) {
					Window.alert("Serverfejl" + caught.getMessage());

				}

				@Override
				public void onSuccess(List<ItemDTO> mostSoldList) {
					statisticView.populateMostSoldTable(mostSoldList);
				}
			});
			itemServiceCall.getStatList(new AsyncCallback<List<ItemDTO>>() {

				@Override
				public void onFailure(Throwable caught) {
					Window.alert("Serverfejl" + caught.getMessage());

				}

				@Override
				public void onSuccess(List<ItemDTO> result) {
					statisticView.populateHistory(result);
					statisticView.setTotEarn();
				}
			});
			adminView.changeWidget(statisticView);
		}

	}}
