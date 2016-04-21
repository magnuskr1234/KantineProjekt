package gwt.client.ui.admin;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;

import gwt.shared.ItemDTO;
import com.google.gwt.user.client.ui.Label;

public class StatisticView extends Composite {

	private static StatisticViewUiBinder uiBinder = GWT.create(StatisticViewUiBinder.class);
	@UiField FlexTable statTable;
	@UiField Label totearn;
	@UiField FlexTable mostSoldTable;
	
	double sum;

	interface StatisticViewUiBinder extends UiBinder<Widget, StatisticView> {
	}

	public StatisticView() {
		initWidget(uiBinder.createAndBindUi(this));
		
	}
	
	public void setTotEarn(){
		totearn.setText("Total omsætning: " + sum + " kr." );
	}
	
	public void clearStatSum(){
		sum = 0;
	}
	
	public void populateMostSoldTable(List<ItemDTO> mostSoldList){
		
		// remove table data
		mostSoldTable.removeAllRows();

		// adjust column widths
		mostSoldTable.getFlexCellFormatter().setWidth(0, 0, "100px");
		mostSoldTable.getFlexCellFormatter().setWidth(0, 1, "100px");

		// set headers in flextable
		mostSoldTable.setText(0, 0, "Vare");
		mostSoldTable.setText(0, 1, "Antal salg");

		// style table
		mostSoldTable.addStyleName("FlexTable");
		mostSoldTable.getRowFormatter().addStyleName(0, "FlexTable-Header");


		for (int i = 0; i < mostSoldList.size(); i++) {
			mostSoldTable.setText(i + 1, 0, mostSoldList.get(i).getName());
			mostSoldTable.setText(i + 1, 1, "" + mostSoldList.get(i).getTimesSold());
		}

	}
	
	// Populate flextable with history for current user
	public void pop(List<ItemDTO> pList) {
		
		// remove table data
		statTable.removeAllRows();

		// adjust column widths
		statTable.getFlexCellFormatter().setWidth(0, 0, "50px");
		statTable.getFlexCellFormatter().setWidth(0, 1, "50px");
		statTable.getFlexCellFormatter().setWidth(0, 2, "50px");
		statTable.getFlexCellFormatter().setWidth(0, 3, "50px");	

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
			statTable.setText(i + 1, 2, "" +pList.get(i).getPrice() + " kr.");
			sum += pList.get(i).getPrice();
			statTable.setText(i + 1, 3, "" + pList.get(i).getDate());
		}

	}


}
