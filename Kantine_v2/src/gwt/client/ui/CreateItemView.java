package gwt.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class CreateItemView extends Composite {

	private static CreateItemViewUiBinder uiBinder = GWT.create(CreateItemViewUiBinder.class);

	interface CreateItemViewUiBinder extends UiBinder<Widget, CreateItemView> {
	}

	public CreateItemView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
