package gwt.client.ui.user;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import gwt.shared.ItemDTO;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlexTable;

public class UserHistoryView extends Composite {

	private static UserHistoryViewUiBinder uiBinder = GWT.create(UserHistoryViewUiBinder.class);
	@UiField FlexTable historyTable;

	interface UserHistoryViewUiBinder extends UiBinder<Widget, UserHistoryView> {
	}

	public UserHistoryView() {
		initWidget(uiBinder.createAndBindUi(this));
	}


	// Populate flextable with history for current user
	public void pop(List<ItemDTO> pList) {
		// remove table data
		historyTable.removeAllRows();

		// adjust column widths
		historyTable.getFlexCellFormatter().setWidth(0, 0, "50px");
		historyTable.getFlexCellFormatter().setWidth(0, 1, "25px");
		historyTable.getFlexCellFormatter().setWidth(0, 2, "50px");


		// style table
		historyTable.addStyleName("FlexTable");
		historyTable.getRowFormatter().addStyleName(0, "FlexTable-Header");


		for (int i = 0; i < pList.size(); i++) {
			historyTable.setText(i + 1, 0, pList.get(i).getName());
			historyTable.setText(i + 1, 1, "" +pList.get(i).getPrice());
			historyTable.setText(i + 1, 2, "" + pList.get(i).getDate());
		}

	}


}
