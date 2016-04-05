package gwt.client.logic;

import java.util.List;

import gwt.client.ui.MainView;
import gwt.client.ui.user.BasketView;
import gwt.client.ui.user.UserHeaderView;
import gwt.client.ui.user.UserHistoryView;
import gwt.client.ui.user.UserMenuView;
import gwt.client.ui.user.UserView;
import gwt.client.logic.*;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootLayoutPanel;

import gwt.client.service.ItemService;
import gwt.client.service.ItemServiceAsync;
import gwt.client.service.PersonService;
import gwt.client.service.PersonServiceAsync;

import gwt.shared.ItemDTO;
import gwt.shared.PersonDTO;

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
	private ItemServiceAsync itemDAO;
	private PersonServiceAsync personServiceCall;
	
	// reference to data transfer object
	private PersonDTO personDTO;
	private ItemDTO itemDTO;

	public int currentPersonId;
	public PersonDTO currentPerson;
	
	public UserController(MainView mainView, PersonServiceAsync personServiceCall, ItemServiceAsync itemDAO){
		// Instantiate pagecontroller
		this.mainView = mainView;
		this.itemDAO = itemDAO;
		this.personServiceCall = personServiceCall;
		
		// Referncer til subview for user
		basketView = mainView.getUserView().getBasketView();
		userHeaderView = mainView.getuserHeaderView();
		userHistoryView = mainView.getUserView().getUserHistoryView();
		userMenuView = mainView.getUserView().getUserMenuView();
		userView = mainView.getUserView();
		
		// Add userHeader handlers
		userHeaderView.getBtnHistory().addClickHandler(new HistoryHandler());
		userHeaderView.getBtnMainMenu().addClickHandler(new UserReturnToMenuHandler());
		
		//Basketview buttons
		basketView.getCancelBtn().addClickHandler(new BuyHandler());
		basketView.getControllerDeleteBtn().addClickHandler(new UpdateBasketHandler());
		basketView.getBuyBtn().addClickHandler(new BuyHandler());
		
		// basket update
		userMenuView.getAddToBasketBtn().addClickHandler(new UpdateBasketHandler());
		
	}

	public void setCurrentPerson(PersonDTO currentPerson){
		this.currentPerson = currentPerson;
		this.currentPersonId = currentPerson.getId();
	}
	
	private class BuyHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// Cancel purchase button click
			if(event.getSource() == basketView.getCancelBtn()){
		
				// Update saldofields
				UserMenuView.setcuSaldo(UserMenuView.getcuSaldo() + basketView.getSum());
				
				// Empty basketTable
				basketView.emptyTable();
				
				// change widget
				mainView.changeWidget(basketView);

			}
			// Confirm purchase button 
			if(event.getSource() == basketView.getBuyBtn()){
				
				if(basketView.getBasketTable().getRowCount() > 3){
					
				for (int i = 0; i < UserMenuView.tempItemList.size(); i++) {
					for (int j = 0; j < UserMenuView.tempItemList.get(i).getCount(); j++) {						
						// Save to history 
						itemDAO.saveItemToHistory(currentPersonId, UserMenuView.tempItemList.get(i).getName(), UserMenuView.tempItemList.get(i).getPrice(),
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
					
				// Update saldo for person 
				personServiceCall.updatePerson(basketView.getNewSaldo(), currentPersonId, new AsyncCallback<Void>(){

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Serverfejl :" + caught.getMessage());						
					}

					@Override
					public void onSuccess(Void result) {
						Window.alert("Tak for købet");
						UserMenuView.tempItemList.clear();
						basketView.setCurrentUserSaldo(basketView.getNewSaldo());
						mainView.updateSaldoHeader(basketView.getNewSaldo());
						mainView.showUserHeader();
						userView.showBasketWidget();
					}				

				}); 
				}else{
						Window.alert("Tilføj først noget til kurven!");
				}
			}
		}
	}
	
	
	private class UpdateBasketHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			//basketView.validateSaldo();
			if(event.getSource() == userMenuView.getAddToBasketBtn() ){	
				
				userView.showBasketWidget();
			}
			
			if( event.getSource() == basketView.getControllerDeleteBtn()){
				basketView.deleteEventRow();
				userView.showBasketWidget();
			}

		}
	}
	
	
	// Back to menu handler
	private class UserReturnToMenuHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (event.getSource() == userHeaderView.getBtnMainMenu()) {

				itemDAO.getItems(new AsyncCallback<List<ItemDTO>>() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Serverfejl :" + caught.getMessage());
					}

					@Override
					public void onSuccess(List<ItemDTO> result) {
						mainView.getUserView().showMenuView(result);
					}
				});

			}
		}
	}
	
	
	// Show history handler
	private class HistoryHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (event.getSource() == userHeaderView.getBtnHistory()) {
				mainView.getUserView().showHistoryView();

				itemDAO.getHistoryList(currentPersonId, new AsyncCallback<List<ItemDTO>>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Serverfejl" + caught.getMessage());
					}

					@Override
					public void onSuccess(List<ItemDTO> result) {
						userHistoryView.pop(result);
					}
				});

				mainView.getUserView().showHistoryView();
			}
		}
	}



}
