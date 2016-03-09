package gwt.client.admin.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class StatisticView extends Composite {

	private static StatisticViewUiBinder uiBinder = GWT.create(StatisticViewUiBinder.class);

	interface StatisticViewUiBinder extends UiBinder<Widget, StatisticView> {
	}

	public StatisticView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
