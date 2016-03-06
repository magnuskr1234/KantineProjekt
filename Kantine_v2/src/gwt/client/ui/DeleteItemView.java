package gwt.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class DeleteItemView extends Composite {

	private static DeleteItemViewUiBinder uiBinder = GWT.create(DeleteItemViewUiBinder.class);

	interface DeleteItemViewUiBinder extends UiBinder<Widget, DeleteItemView> {
	}

	public DeleteItemView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
