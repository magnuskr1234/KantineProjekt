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
	
	//Header Views
	
	

	interface UserViewUiBinder extends UiBinder<Widget, UserView> {
	}

	public UserView() {
		initWidget(uiBinder.createAndBindUi(this));
		
		//Make content view
		userMenuView = new UserMenuView();
		basketView = new BasketView();
		
		
		//add contentviews to decklayout panel
		contentPanel.add(userMenuView);
		basket.add(basketView);
		
		// Initially show menu view
		basketView.pop();
		contentPanel.showWidget(userMenuView);
		basket.showWidget(basketView);
		
	}
	
	// Show content widget
	public void changeWidget(Widget w){
		contentPanel.showWidget(w);
	}
	
	// Get views
	public UserMenuView getUserMenuView(){
		return userMenuView;
	}
	
	public BasketView getBasketView(){
		return basketView;
	}

}
