package com.totsp.gwittir.example.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.totsp.gwittir.client.flow.FlowContext;
import com.totsp.gwittir.client.flow.FlowController;
import com.totsp.gwittir.client.flow.SimpleSessionHistoryManager;
import com.totsp.gwittir.client.ui.BoundWidget;
import com.totsp.gwittir.client.ui.util.BoundWidgetProvider;


/**
 *  Gwittir sample EntryPoint.
 *
 * @author cooper
 */
public class ContactsEntryPoint implements EntryPoint {
    /** Creates a new instance of ExampleEntryPoint */
    
    public static final String BROWSE = "browse";
    public static final String EDIT = "edit";
    
    public ContactsEntryPoint() {
    }
    
    public void onModuleLoad() {
        
        // create a FlowContext 
        FlowContext context = new FlowContext();        
        
        // add a named BoundWidgetProvider with action for BROWSE (ContactBrowserAction)
        context.add(BROWSE, new BoundWidgetProvider(){
            public BoundWidget get(){
                return new ContactBrowser();
            }
        }, new ContactBrowserAction());
              
        // add a named BoundWidgetProvider with action for BROWSE
        context.add("contactEdit", new ContactEdit(), new ContactEditAction());
        
        SimplePanel p = new SimplePanel();
        p.setWidth( "500px");
        
        // set FlowContext and HistoryManager into FlowController
        FlowController.setFlowContext(p, context);
        FlowController.setHistoryManager(new SimpleSessionHistoryManager());
        
        RootPanel.get().add(p);
        FlowController.call(p, BROWSE, null);        
    }
}
