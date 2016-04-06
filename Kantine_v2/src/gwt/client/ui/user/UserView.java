package gwt.client.ui.user;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.shared.ItemDTO;


public class UserView extends Composite {

	private static UserViewUiBinder uiBinder = GWT.create(UserViewUiBinder.class);
	@UiField DeckLayoutPanel contentPanel;
	@UiField DeckLayoutPanel basket;
	@UiField DeckLayoutPanel headerPanel;

	//Content Views
	private UserMenuView userMenuView;
	private BasketView basketView;
	private UserHistoryView userHistoryView;

	//Header
	private UserHeaderView userHeaderView;
	
	interface UserViewUiBinder extends UiBinder<Widget, UserView> {
	}

	public UserView() {
		initWidget(uiBinder.createAndBindUi(this));

		//Make content view
		userMenuView = new UserMenuView();
		basketView = new BasketView();
		userHistoryView = new UserHistoryView();
		
		//header
		userHeaderView = new UserHeaderView();

		//add contentviews to decklayout panel
		contentPanel.add(userMenuView);
		basket.add(basketView);
		contentPanel.add(userHistoryView);

		//Header 
		headerPanel.add(userHeaderView);
		
		// Initially show menu view
		contentPanel.showWidget(userMenuView);
		headerPanel.showWidget(userHeaderView);
		basketView.pop(UserMenuView.tempItemList);
		basket.showWidget(basketView);
		
		
	}

	// Show content widget
	public void changeWidget(Widget w){
		contentPanel.showWidget(w);
	}



	public void showHistoryView(){
		contentPanel.showWidget(userHistoryView);
	}

	public void showMenuView(List<ItemDTO> pList){
		userMenuView.pop(pList);
		contentPanel.showWidget(userMenuView);
	}

	public void showBasketWidget(){
		basketView.pop(UserMenuView.tempItemList);
		basket.showWidget(basketView);
	}
	//Show user header
		public void showUserHeader(){
			headerPanel.showWidget(userHeaderView);
		}

	// Get views
	public UserMenuView getUserMenuView(){
		return userMenuView;
	}

	public BasketView getBasketView(){
		return basketView;
	}

	public UserHistoryView getUserHistoryView(){
		return userHistoryView;
	}
	
	public UserHeaderView getuserHeaderView(){
		return userHeaderView;
	}
	
	// Login ok
	public void loginOk(String user) {
		userHeaderView.setUser(user);
	}
	
	// Set saldo 
	public void updateSaldoHeader(double saldo){
		userHeaderView.setSaldo(saldo);
	}


}
