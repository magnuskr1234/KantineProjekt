package gwt.client.ui.admin;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

public class AdminView extends Composite {

	private static AdminViewUiBinder uiBinder = GWT.create(AdminViewUiBinder.class);
	@UiField DeckLayoutPanel headerPanel;
	@UiField DeckLayoutPanel contentPanel;	
	
	private AdminMenuView adminMenu;
	private CreateItemView createItem;
	private CreateUserView createUser;	
	private DeleteItemView deleteItem;
	private EditItemView editItem;
	private EditPersonView editPerson;
	private ShowUserListView showUserList;
	private ShowItemListView showItemList;
	private StatisticView statistic;
	
	private AdminHeaderView adminHeaderView;
	
	interface AdminViewUiBinder extends UiBinder<Widget, AdminView> {
	}

	public AdminView() {
		initWidget(uiBinder.createAndBindUi(this));
		//Make content view
				adminMenu = new AdminMenuView();
				createItem = new CreateItemView();
				createUser = new CreateUserView();		
				deleteItem = new DeleteItemView();
				editItem = new EditItemView();
				editPerson = new EditPersonView();
				showUserList = new ShowUserListView();
				showItemList = new ShowItemListView();
				statistic = new StatisticView();
				
				// Header & Login 
				adminHeaderView = new AdminHeaderView();
				
				//add contentviews to decklayout panel
				contentPanel.add(adminMenu);
				contentPanel.add(createItem);
				contentPanel.add(createUser);	
				contentPanel.add(deleteItem);
				contentPanel.add(editItem);
				contentPanel.add(editPerson);
				contentPanel.add(showUserList);
				contentPanel.add(showItemList);
				contentPanel.add(statistic);
				
				// Add Header & login 
				headerPanel.add(adminHeaderView);		
				
				contentPanel.showWidget(adminMenu);
				headerPanel.showWidget(adminHeaderView);
		
	}
	
	// Get view
		public AdminMenuView getadminMenu() {
			return adminMenu;
		}

		public CreateItemView getcreateItem(){
			return createItem;
		}

		public CreateUserView getcreateUser(){
			return createUser;
		}

		public DeleteItemView getdeleteItem(){
			return deleteItem;
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
		
		// Show content widget
		public void changeWidget(Widget w){
			contentPanel.showWidget(w);
		}
		
		// Headers
		public AdminHeaderView getadminHeaderView(){
			return adminHeaderView;
		}

		// Show Create Item
		public void showCreateItem(){
			changeWidget(createItem);
		}

		// Show Create User
		public void showCreateUser(){
			changeWidget(createUser);
		}

		// Show delete item
		public void showDeleteItem(){
			changeWidget(deleteItem);
		}

		// Show edit item
		public void showEditItem(){
			changeWidget(editItem);
		}

		// Show Edit Person
		public void showEditPerson(){
			changeWidget(editPerson);
		}

		// Show User List
		public void showUserList(){
			changeWidget(showUserList);
		}

		// Show statistic
		public void showStatistic(){
			changeWidget(statistic);
		}

		//Show Admin Menu 
		public void showAdminMenu(){
			changeWidget(adminMenu);
		}

		//Show admin Header
		public void showAdminHeader(){
			headerPanel.showWidget(adminHeaderView);
		}
}
