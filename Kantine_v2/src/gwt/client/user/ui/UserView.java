package gwt.client.user.ui;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.server.dal.ItemDAO;
import gwt.shared.ItemDTO;

import com.google.gwt.user.client.ui.DockPanel;

public class UserView extends Composite {

	private static UserViewUiBinder uiBinder = GWT.create(UserViewUiBinder.class);
	@UiField DeckLayoutPanel contentPanel;
	@UiField DeckLayoutPanel basket;

	//Content Views
	private UserMenuView userMenuView;
	private BasketView basketView;
	private UserHistoryView userHistoryView;
	ArrayList<ItemDTO> basketList = new ArrayList<ItemDTO>();
	
	public void AddItemToBasket(ItemDTO item){
		basketList.add(item);
	}
	
	
	//Header Views
	
	

	interface UserViewUiBinder extends UiBinder<Widget, UserView> {
	}

	public UserView() {
		
		basketList.add(new ItemDTO("Kaffe", 20));
		basketList.add(new ItemDTO("Kaffe", 20));
		basketList.add(new ItemDTO("Banan", 30));
		basketList.add(new ItemDTO("Sodavand", 40));
		initWidget(uiBinder.createAndBindUi(this));
		
		//Make content view
		userMenuView = new UserMenuView();
		basketView = new BasketView();
		userHistoryView = new UserHistoryView();
		
		
		//add contentviews to decklayout panel
		contentPanel.add(userMenuView);
		basket.add(basketView);
		contentPanel.add(userHistoryView);
		
		
		

		
		// Initially show menu view
		
		contentPanel.showWidget(userMenuView);
		showBasketWidget();
		
	}
	
	// Show content widget
	public void changeWidget(Widget w){
		contentPanel.showWidget(w);
	}
	
	public void showHistoryView(){
		contentPanel.showWidget(userHistoryView);
	}
	
	public void showMenuView(){
		contentPanel.showWidget(userMenuView);
	}
	
	public void showBasketWidget(){
		basketView.pop(basketList);
		basket.showWidget(basketView);
	}
	
	// Get views
	public UserMenuView getUserMenuView(){
		return userMenuView;
	}
	
	public BasketView getBasketView(){
		return basketView;
	}
	


}
