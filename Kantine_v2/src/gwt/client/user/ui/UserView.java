package gwt.client.user.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.DockPanel;

public class UserView extends Composite {

	private static UserViewUiBinder uiBinder = GWT.create(UserViewUiBinder.class);
	@UiField DeckLayoutPanel contentPanel;
	@UiField DeckLayoutPanel basket;

	//Content Views
	private UserMenuView userMenuView;
	private BasketView basketView;
	private UserHistoryView userHistoryView;
	
	//Header Views
	
	

	interface UserViewUiBinder extends UiBinder<Widget, UserView> {
	}

	public UserView() {
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
		basketView.pop();
		contentPanel.showWidget(userMenuView);
		basket.showWidget(basketView);
		
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
	
	// Get views
	public UserMenuView getUserMenuView(){
		return userMenuView;
	}
	
	public BasketView getBasketView(){
		return basketView;
	}

}
