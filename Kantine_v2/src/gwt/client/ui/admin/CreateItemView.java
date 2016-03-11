package gwt.client.ui.admin;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.event.dom.client.ClickEvent;

public class CreateItemView extends Composite {

	private static CreateItemViewUiBinder uiBinder = GWT.create(CreateItemViewUiBinder.class);

	@UiField Button btnCancel;
	
	interface CreateItemViewUiBinder extends UiBinder<Widget, CreateItemView> {
	}

	public CreateItemView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public Button getBtnCancel(){
		return btnCancel;
	}
}
