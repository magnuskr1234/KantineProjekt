package gwt.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.event.dom.client.ClickEvent;

/**
 * UiBinder der opbygger layout til login 
 * @author magnusrasmussen
 *
 */
public class LoginView extends Composite {

	private static LoginViewUiBinder uiBinder = GWT.create(LoginViewUiBinder.class);
	@UiField TextBox txtUid;
	@UiField TextBox txtPw;
	@UiField Button btnOk;
	@UiField Label lblLoginError;
	

	interface LoginViewUiBinder extends UiBinder<Widget, LoginView> {
	}
	

	/**
	 * Login constructor
	 */
	public LoginView() {
		initWidget(uiBinder.createAndBindUi(this));
		lblLoginError.setVisible(false);
	}
	
	
	public void setError() {
		lblLoginError.setVisible(true);
		txtUid.setStyleName("textBox-invalidEntry");
		txtPw.setStyleName("textBox-invalidEntry");
	}
	
	public void resetError() {
		lblLoginError.setVisible(false);
		txtUid.setStyleName("textBox");
		txtPw.setStyleName("textBox");
	}

	public void clearfields() {
		txtUid.setText("");
		txtPw.setText("");
	}
	
	
	// getters for textfields and buttons
	public String getUserId() {
		return txtUid.getText();
	}
	
	public String getUserPw() {
		return txtPw.getText();
	}
	
	public Button getBtnOk() {
		return btnOk;
	}

	@UiHandler("btnOk")
	void onBtnOkClick(ClickEvent event) {
	}

	
}