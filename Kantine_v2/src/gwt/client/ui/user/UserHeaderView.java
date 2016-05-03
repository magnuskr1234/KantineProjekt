package gwt.client.ui.user;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;

/**
 * View to show the userheader
 *
 */
public class UserHeaderView extends Composite {

	private static UserHeaderViewUiBinder uiBinder = GWT.create(UserHeaderViewUiBinder.class);
	@UiField Button btnHistory;
	@UiField Button btnMainMenu;
	@UiField Button btnLogout;
	@UiField Label userNameLabel;
	@UiField Label saldoLabel;

	interface UserHeaderViewUiBinder extends UiBinder<Widget, UserHeaderView> {
	}

	public UserHeaderView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	// Set saldo in header
	public void setSaldo(double saldo){
		saldoLabel.setText("Saldo: " + saldo + " Kr.");
	}

	// Set username in header
	public void setUser(String user) {
		userNameLabel.setText("Velkommen "+ user);
	}

	// Getters for buttons 
	public Button getBtnMainMenu() {
		return btnMainMenu;
	}

	public Button getBtnLogout(){
		return btnLogout;
	}

	public Button getBtnHistory(){
		return btnHistory;
	}
}
