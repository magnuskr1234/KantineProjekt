package gwt.client.ui.admin;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;

import gwt.shared.ItemDTO;

public class StatisticView extends Composite {

	private static StatisticViewUiBinder uiBinder = GWT.create(StatisticViewUiBinder.class);
	@UiField FlexTable statTable;

	interface StatisticViewUiBinder extends UiBinder<Widget, StatisticView> {
	}

	public StatisticView() {
		initWidget(uiBinder.createAndBindUi(this));
		
	}
	
	// Populate flextable with history for current user
	public void pop(List<ItemDTO> pList) {
		
		// remove table data
		statTable.removeAllRows();

		// adjust column widths
		statTable.getFlexCellFormatter().setWidth(0, 0, "50px");
		statTable.getFlexCellFormatter().setWidth(0, 1, "50px");
		statTable.getFlexCellFormatter().setWidth(0, 2, "25px");
		statTable.getFlexCellFormatter().setWidth(0, 3, "50px");

		// set headers in flextable
		statTable.setText(0, 0, "Bruger");
		statTable.setText(0, 1, "Vare");
		statTable.setText(0, 2, "Pris");
		statTable.setText(0, 3, "Dato");

		// style table
		statTable.addStyleName("FlexTable");
		statTable.getRowFormatter().addStyleName(0, "FlexTable-Header");


		for (int i = 0; i < pList.size(); i++) {
			
			if (pList.get(i).getUser() != null && !pList.get(i).getUser().isEmpty()){
				statTable.setText(i + 1, 0, pList.get(i).getUser());
			}else{
				statTable.setText(i + 1, 0, "Bruger slettet");
			}
			statTable.setText(i + 1, 1, pList.get(i).getName());
			statTable.setText(i + 1, 2, "" +pList.get(i).getPrice());
			statTable.setText(i + 1, 3, "" + pList.get(i).getDate());
		}

	}


}
