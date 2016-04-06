package gwt.client.ui.admin;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * This view contains all contentviews for admin
 * @author magnusrasmussen
 *
 */
public class AdminView extends Composite {

	private static AdminViewUiBinder uiBinder = GWT.create(AdminViewUiBinder.class);
	@UiField DeckLayoutPanel headerPanel;
	@UiField DeckLayoutPanel contentPanel;	

	//References for subviews
	private AdminMenuView adminMenu;
	private CreateItemView createItem;
	private CreateUserView createUser;	
	private EditItemView editItem;
	private EditPersonView editPerson;
	private ShowUserListView showUserList;
	private ShowItemListView showItemList;
	private StatisticView statistic;

	private AdminHeaderView adminHeaderView;

	interface AdminViewUiBinder extends UiBinder<Widget, AdminView> {
	}

	// Contructor
	public AdminView() {
		initWidget(uiBinder.createAndBindUi(this));

		//Make content view
		adminMenu = new AdminMenuView();
		createItem = new CreateItemView();
		createUser = new CreateUserView();	
		editItem = new EditItemView();
		editPerson = new EditPersonView();
		showUserList = new ShowUserListView();
		showItemList = new ShowItemListView();
		statistic = new StatisticView();

		// Header
		adminHeaderView = new AdminHeaderView();

		//Add contentviews to decklayout panel
		contentPanel.add(adminMenu);
		contentPanel.add(createItem);
		contentPanel.add(createUser);
		contentPanel.add(editItem);
		contentPanel.add(editPerson);
		contentPanel.add(showUserList);
		contentPanel.add(showItemList);
		contentPanel.add(statistic);

		// Add Header to headerpanel decklayoutpanel
		headerPanel.add(adminHeaderView);		

		//Initially show admin menu and adminheader
		contentPanel.showWidget(adminMenu);
		headerPanel.showWidget(adminHeaderView);

	}

	// Change content widget
	public void changeWidget(Widget w){
		contentPanel.showWidget(w);
	}
	
	// Get views for admin functions. 
	public AdminMenuView getadminMenu() {
		return adminMenu;
	}

	public CreateItemView getcreateItem(){
		return createItem;
	}

	public CreateUserView getcreateUser(){
		return createUser;
	}

	public EditItemView geteditItem(){
		return editItem;
	}
	public EditPersonView geteditPerson(){
		return editPerson;
	}

	public ShowUserListView getshowUserList(){
		return showUserList;
	}

	public ShowItemListView getshowItemList(){
		return showItemList;
	}

	public StatisticView getstatistic(){
		return statistic;
	}

	// Headers
	public AdminHeaderView getadminHeaderView(){
		return adminHeaderView;
	}

	//Show admin Header
	public void showAdminHeader(){
		headerPanel.showWidget(adminHeaderView);
	}
}
