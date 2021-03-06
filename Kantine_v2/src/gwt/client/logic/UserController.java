package gwt.client.logic;

import java.util.List;

import gwt.client.ui.MainView;
import gwt.client.ui.user.BasketView;
import gwt.client.ui.user.UserHeaderView;
import gwt.client.ui.user.UserHistoryView;
import gwt.client.ui.user.UserMenuView;
import gwt.client.ui.user.UserView;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import gwt.client.service.ItemServiceAsync;
import gwt.client.service.PersonServiceAsync;

import gwt.shared.ItemDTO;
import gwt.shared.PersonDTO;

/**
 * The user controller handles the different user views and logic.
 *
 */
public class UserController {

	// References for views
	private MainView mainView;
	private BasketView basketView;
	private UserHistoryView userHistoryView;

	// Referencer til user views
	private UserMenuView userMenuView;
	private UserView userView;
	private UserHeaderView userHeaderView;

	// Service classes for async callbacks to server
	private ItemServiceAsync itemServiceCall;
	private PersonServiceAsync personServiceCall;

	public int currentPersonId;
	public PersonDTO currentPerson;

	public double balanceSaldo = 0;
	public double timesBrought = 0;
	public double priceToPay = 0;

	public UserController(MainView mainView, PersonServiceAsync personServiceCall, ItemServiceAsync itemServiceCall) {
		// Instantiate pagecontroller
		this.mainView = mainView;
		this.userView = mainView.getUserView();
		this.itemServiceCall = itemServiceCall;
		this.personServiceCall = personServiceCall;

		// Referncer til subview for user
		basketView = userView.getBasketView();
		userHeaderView = userView.getuserHeaderView();
		userHistoryView = userView.getUserHistoryView();
		userMenuView = userView.getUserMenuView();

		// Add userHeader handlers
		userHeaderView.getBtnHistory().addClickHandler(new HistoryHandler());
		userHeaderView.getBtnMainMenu().addClickHandler(new UserReturnToMenuHandler());
		userHeaderView.getBtnLogout().addClickHandler(new LogoutHandler());

		// Basketview buttons
		basketView.getCancelBtn().addClickHandler(new BuyHandler());
		basketView.getControllerDeleteBtn().addClickHandler(new UpdateBasketHandler());
		basketView.getBuyBtn().addClickHandler(new BuyHandler());

		// basket update
		userMenuView.getAddToBasketBtn().addClickHandler(new UpdateBasketHandler());
	}

	public void setCurrentPerson(PersonDTO currentPerson) {
		this.currentPerson = currentPerson;
		this.currentPersonId = currentPerson.getId();
	}

	/**
	 *  Logout handler
	 *
	 */
	private class LogoutHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			mainView.changeView(mainView.getLoginView());
		}
	}

	/**
	 * Clickhandler for buy
	 *
	 */
	private class BuyHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// Cancel purchase button click
			if (event.getSource() == basketView.getCancelBtn()) {

				// Update saldofields
				UserMenuView.setcuSaldo(UserMenuView.getcuSaldo() + basketView.getSum());

				// Empty basketTable
				basketView.emptyTable();

				// change widget
				userView.changeWidget(basketView);

			}

			// Confirm purchase button
			if (event.getSource() == basketView.getBuyBtn()) {

				// Check if basket is empty
				if (basketView.getBasketTable().getRowCount() > 3) {
					if (Window.confirm("Er du sikker på at du vil købe?"))
						balanceSaldo = 0;
					timesBrought = 0;
					priceToPay = 0;
					balanceSaldo = basketView.getNewSaldo() + basketView.getSum();

					for (int i = 0; i < UserMenuView.tempItemList.size(); i++) {
						for (int j = 0; j < UserMenuView.tempItemList.get(i).getCount(); j++) {

							// Save to history
							timesBrought = UserMenuView.tempItemList.get(i).getCount(); 
							priceToPay = UserMenuView.tempItemList.get(i).getPrice();
							balanceSaldo -= priceToPay;

							// Save item to history RPC call
							itemServiceCall.saveItemToHistory(currentPersonId,
									UserMenuView.tempItemList.get(i).getName(),
									UserMenuView.tempItemList.get(i).getPrice(), balanceSaldo,
									new AsyncCallback<Void>() {

								@Override
								public void onFailure(Throwable caught) {
									Window.alert("Serverfejl" + caught.getMessage());
								}

								@Override
								public void onSuccess(Void result) {
								}

							});
						}
					}

					// Update saldo for person after purchase
					personServiceCall.updatePerson(basketView.getNewSaldo(), currentPersonId,
							new AsyncCallback<Void>() {

						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Serverfejl :" + caught.getMessage());
						}

						@Override
						public void onSuccess(Void result) {
							Window.alert("Tak for købet");
							UserMenuView.tempItemList.clear(); // Clear basket arraylist
							basketView.setCurrentUserSaldo(basketView.getNewSaldo()); // set new saldo
							userView.updateSaldoHeader(basketView.getNewSaldo()); // Update saldo in header
							userView.showUserHeader(); //Update header with new saldo
							userView.showBasketWidget(); // Show cleared basket
						}

					});
				} else {
					Window.alert("Tilføj først noget til kurven!");
				}
			}
		}
	}

	/**
	 * Clickhandler for adding items to basket
	 *
	 */
	private class UpdateBasketHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			// If add button
			if (event.getSource() == userMenuView.getAddToBasketBtn())
				userView.showBasketWidget();

			// If delete button
			if (event.getSource() == basketView.getControllerDeleteBtn()) {
				basketView.deleteEventRow();
				userView.showBasketWidget();
			}

		}
	}

	/**
	 * Clickhandler for returning to menu
	 *
	 */
	private class UserReturnToMenuHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			itemServiceCall.getItems(new AsyncCallback<List<ItemDTO>>() {
				@Override
				public void onFailure(Throwable caught) {
					Window.alert("Serverfejl :" + caught.getMessage());
				}

				@Override
				public void onSuccess(List<ItemDTO> result) {
					userView.showMenuView(result);
				}
			});

		}
	}

	/**
	 * Clickhandler for showing history 
	 *
	 */
	private class HistoryHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			userView.changeWidget(userHistoryView);

			itemServiceCall.getHistoryList(currentPersonId, new AsyncCallback<List<ItemDTO>>() {

				@Override
				public void onFailure(Throwable caught) {
					Window.alert("Serverfejl" + caught.getMessage());
				}

				@Override
				public void onSuccess(List<ItemDTO> result) {
					userHistoryView.populateUserHistory(result);
				}
			});

			// Show updated history view
			userView.changeWidget(userHistoryView);
		}
	}
}
