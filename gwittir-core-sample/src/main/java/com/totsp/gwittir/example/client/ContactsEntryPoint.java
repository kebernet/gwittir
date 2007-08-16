/*
 * ExampleEntryPoint.java
 *
 * Created on April 12, 2007, 4:47 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.totsp.gwittir.example.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

import com.totsp.gwittir.client.flow.FlowContext;
import com.totsp.gwittir.client.flow.FlowController;
import com.totsp.gwittir.client.flow.SimpleSessionHistoryManager;
import com.totsp.gwittir.client.fx.ui.SlideTransitionSimplePanel;
import com.totsp.gwittir.client.ui.BoundWidget;
import com.totsp.gwittir.client.ui.HasWidget;
import com.totsp.gwittir.client.ui.util.BoundWidgetProvider;


/**
 *
 * @author cooper
 */
public class ContactsEntryPoint implements EntryPoint {
    /** Creates a new instance of ExampleEntryPoint */
    public ContactsEntryPoint() {
    }
    
    public void onModuleLoad() {
        FlowContext context = new FlowContext();
        context.add("browse", new BoundWidgetProvider(){
            public BoundWidget get(){
                return new ContactBrowser();
            }
        }, new ContactBrowserAction()
        )
        .add("contactEdit", new ContactEdit(), new ContactEditAction());
        
        SlideTransitionSimplePanel p = new SlideTransitionSimplePanel();
        p.setWidth( "500px");
        
        FlowController.setFlowContext((HasWidget) p, context);
        FlowController.setHistoryManager(new SimpleSessionHistoryManager());
        
        RootPanel.get().add(p);
        FlowController.call(p, "browse", null);
        
        /*
        SimplePanel myPanel = new SimplePanel();
        FlowContext myDealersContext = new FlowContext();
        context.add( "list", new MyDealerListScreen(), new MyDealerListScreenAction() )
               .add( "dealer", new DealerEditScreen(), new DealerEditAction());
        FlowController.setFlowContext( myPanel, myDealersContext );
        List dealers = dealerFreezer.getDealers();
                FlowController.call( RootPanel.get(), "list", dealer );
            }
 
        });*/
        
        
        
        
        
    }
}
