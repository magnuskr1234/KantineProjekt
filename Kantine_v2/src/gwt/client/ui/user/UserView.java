package gwt.client.ui.user;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.shared.ItemDTO;

/** 
 * This view contains all views for the user. 
 * @author magnusrasmussen
 *
 */
public class UserView extends Composite {

	private static UserViewUiBinder uiBinder = GWT.create(UserViewUiBinder.class);
	@UiField DeckLayoutPanel contentPanel;
	@UiField DeckLayoutPanel basket;
	@UiField DeckLayoutPanel headerPanel;

	//Content Views
	private UserMenuView userMenuView;
	private BasketView basketView;
	private UserHistoryView userHistoryView;

	//Header view
	private UserHeaderView userHeaderView;
	
	interface UserViewUiBinder extends UiBinder<Widget, UserView> {
	}

	/**
	 * Contructor for userview
	 */
	public UserView() {
		initWidget(uiBinder.createAndBindUi(this));

		//Make content view
		userMenuView = new UserMenuView();
		basketView = new BasketView();
		userHistoryView = new UserHistoryView();
		
		//User header 
		userHeaderView = new UserHeaderView();

		//add contentviews to decklayout panel
		basket.add(basketView);
		contentPanel.add(userMenuView);
		contentPanel.add(userHistoryView);

		//Header 
		headerPanel.add(userHeaderView);
		
		// Initially show menu view
		contentPanel.showWidget(userMenuView);
		headerPanel.showWidget(userHeaderView);
		basketView.populateBasket(UserMenuView.tempItemList);
		basket.showWidget(basketView);
	}

	// Show content widget
	public void changeWidget(Widget panel){
		contentPanel.showWidget(panel);
	}

	// Show user menu and populate with data
	public void showMenuView(List<ItemDTO> pList){
		userMenuView.populateUserMenu(pList);
		contentPanel.showWidget(userMenuView);
	}

	// Show basket and populate with data
	public void showBasketWidget(){
		basketView.populateBasket(UserMenuView.tempItemList);
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
	
	// Set saldo for header
	public void updateSaldoHeader(double saldo){
		userHeaderView.setSaldo(saldo);
	}
}
