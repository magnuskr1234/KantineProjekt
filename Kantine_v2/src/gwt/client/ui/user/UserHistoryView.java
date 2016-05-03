package gwt.client.ui.user;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import gwt.shared.ItemDTO;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlexTable;

/**
 * View for showing users purchase history
 *
 */
public class UserHistoryView extends Composite {

	private static UserHistoryViewUiBinder uiBinder = GWT.create(UserHistoryViewUiBinder.class);
	@UiField FlexTable historyTable;

	interface UserHistoryViewUiBinder extends UiBinder<Widget, UserHistoryView> {
	}

	// Contructor
	public UserHistoryView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	// Populate flextable with history for current user
	public void populateUserHistory(List<ItemDTO> dataList) {
		// remove table data
		historyTable.removeAllRows();

		// adjust column widths
		historyTable.getFlexCellFormatter().setWidth(0, 0, "50px");
		historyTable.getFlexCellFormatter().setWidth(0, 1, "50px");
		historyTable.getFlexCellFormatter().setWidth(0, 2, "25px");
		historyTable.getFlexCellFormatter().setWidth(0, 3, "50px");

		// style table
		historyTable.addStyleName("FlexTable");
		historyTable.getRowFormatter().addStyleName(0, "FlexTable-Header");

		// Loop to add data to flextable 
		for (int i = 0; i < dataList.size(); i++) {
			historyTable.setText(i + 1, 0, "" + dataList.get(i).getDate());
			historyTable.setText(i + 1, 1, dataList.get(i).getName());
			if (dataList.get(i).getName().equals("Tilføjet til Saldo")){
				historyTable.setText(i + 1, 2, "" +(dataList.get(i).getPrice()));
			}else{
				historyTable.setText(i + 1, 2, "" +(-(dataList.get(i).getPrice())));
			}			

			historyTable.setText(i + 1, 3, "" + dataList.get(i).getSaldo());
		}
	}
}
