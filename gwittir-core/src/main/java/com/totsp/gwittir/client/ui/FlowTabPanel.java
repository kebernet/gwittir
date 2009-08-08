package com.totsp.gwittir.client.ui;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TableListener;
import com.google.gwt.user.client.ui.Widget;
import com.totsp.gwittir.client.flow.FlowContext;
import com.totsp.gwittir.client.flow.FlowController;
import com.totsp.gwittir.client.flow.FlowEvent;
import com.totsp.gwittir.client.flow.FlowEventListener;


public class FlowTabPanel extends Composite {

	public static final String GWITTIR_FLOW_TAB_PANEL_TABACTIVE = "gwittir-FlowTabPanel-tabactive";

	public static final String GWITTIR_FLOW_TAB_PANEL_TAB = "gwittir-FlowTabPanel-tab";

	public static final String GWITTIR_FLOW_TAB_PANEL_SPACER = "gwittir-FlowTabPanel-spacer";

	public static final String GWITTIR_FLOW_TAB_PANEL_RIGHT = "gwittir-FlowTabPanel-right";

	public static final String GWITTIR_FLOW_TAB_PANEL_LEFT = "gwittir-FlowTabPanel-left";
	
	private Map<Widget, Object> modelMap = new HashMap<Widget, Object>();
	private Map<Widget, String> activityMap = new HashMap<Widget, String>();
	private Map<String, Integer> cellMap = new HashMap<String, Integer>();
	
	private FlexTable table = new FlexTable();
	private Widget managedWidget;

	public FlowTabPanel(Widget managedWidget, FlowContext context ) {
		this.managedWidget = managedWidget;
		table.setCellPadding(0);
		table.setCellSpacing(0);
		table.insertRow(0);
		table.addCell(0);
		table.getCellFormatter()
				.setStyleName(0, 0, GWITTIR_FLOW_TAB_PANEL_LEFT);
		table.setWidget(0,0, new Image(GWT.getModuleBaseURL()+"clear.cache.gif"));
		table.addCell(0);
		table.getCellFormatter().setStyleName(0, 1,
				GWITTIR_FLOW_TAB_PANEL_RIGHT);
		table.setWidget(0,1, new Image(GWT.getModuleBaseURL()+"clear.cache.gif"));
		table.addTableListener(this.listener);
		super.initWidget(table);
		this.setStyleName("gwittir-FlowTabPanel");
		context.addFlowEventListener( new FlowEventListener(){

			public void onFlowEvent(FlowEvent evt) {
				String activity = evt.getToName();
				Integer cell = cellMap.get(activity);
				if( cell != null ){
					setActive(cell);
				}
			}
			
		});
	}

	public void activateTab(String activityName){
		Integer cell = cellMap.get(activityName);
		if( cell != null ){
			listener.onCellClicked(table, 0, cell);
		}
	}
	
	public void addTab(String activityName, Widget tabWidget, Object model) {
		int right = table.getCellCount(0) - 1;
		if (right != 1) {
			table.insertCell(0, right);
			table.getCellFormatter().setStyleName(0, right,
					GWITTIR_FLOW_TAB_PANEL_SPACER);
			Image i = new Image(GWT.getModuleBaseURL()+"clear.cache.gif");
			i.setWidth("0px");
			table.setWidget(0,right, i);
			right++;
		}
		table.insertCell(0, right);
		table.getCellFormatter().setStyleName(0, right,
				GWITTIR_FLOW_TAB_PANEL_TAB);
		table.setWidget(0, right, tabWidget);
		activityMap.put(tabWidget, activityName);
		modelMap.put(tabWidget, model);
		cellMap.put( activityName, right);
	}
	
	private void setActive(int cell){
		for (int i = 0; i < table.getCellCount(0); i++) {
			if (table.getCellFormatter().getStyleName(0, i).equals(
					GWITTIR_FLOW_TAB_PANEL_TABACTIVE)) {
				table.getCellFormatter().setStyleName(0, i,
						GWITTIR_FLOW_TAB_PANEL_TAB);
			}
		}
		table.getCellFormatter().setStyleName(0, cell,
				GWITTIR_FLOW_TAB_PANEL_TABACTIVE);
	}
	
	private final TableListener listener = new TableListener() {

		public void onCellClicked(SourcesTableEvents sender, int row, int cell) {
			assert sender == table : "Table instance changed unexpectedly.";
			Widget internal = table.getWidget(row, cell);
			String activity = activityMap.get(internal);
			if (activity == null)
				return;

			Object model = modelMap.get(internal);
			if (FlowController.call(managedWidget, activity, model)) {
				setActive(cell);
			}

		}

	};
}
