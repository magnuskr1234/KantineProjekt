package gwt.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;

public class BasketView extends Composite {

	private static BasketViewUiBinder uiBinder = GWT.create(BasketViewUiBinder.class);
	@UiField Button btnCancel;
	@UiField Button btnBuy;

	interface BasketViewUiBinder extends UiBinder<Widget, BasketView> {
	}

	public BasketView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
