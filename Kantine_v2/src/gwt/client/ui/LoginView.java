package gwt.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;


public class LoginView extends Composite {

	private static LoginViewUiBinder uiBinder = GWT.create(LoginViewUiBinder.class);
	@UiField TextBox txtUid;
	@UiField TextBox txtPw;
	@UiField Button btnOk;
	@UiField Button btnCancel;
	@UiField Label lblLoginError;
	

	interface LoginViewUiBinder extends UiBinder<Widget, LoginView> {
	}

	
	public LoginView() {
		this.btnOk = new Button();
		this.txtUid = new TextBox();
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
	
	public Button getBtnCancel() {
		return btnCancel;
	}
}