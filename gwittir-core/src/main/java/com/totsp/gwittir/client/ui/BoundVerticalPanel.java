package com.totsp.gwittir.client.ui;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.totsp.gwittir.client.ui.util.ActionTypeFactory;
import com.totsp.gwittir.client.ui.util.BoundWidgetTypeFactory;

public class BoundVerticalPanel<T> extends AbstractCollectionContainer<T> {

	public BoundVerticalPanel(BoundWidgetTypeFactory factory, ActionTypeFactory actionFactory) {
		super(new VerticalPanel(), factory, actionFactory);
                this.setStyleName("gwittir-BoundVerticalPanel");
	}
	
	

}
