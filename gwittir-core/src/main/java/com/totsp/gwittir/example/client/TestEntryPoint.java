package com.totsp.gwittir.example.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;

import com.totsp.gwittir.client.flow.FlowContext;
import com.totsp.gwittir.client.flow.FlowController;
import com.totsp.gwittir.client.flow.SimpleSessionHistoryManager;
import com.totsp.gwittir.client.ui.FlowTabPanel;
import com.totsp.gwittir.client.ui.Label;


public class TestEntryPoint implements EntryPoint {
    public void onModuleLoad() {
        //FlowExamplePanel panel = new FlowExamplePanel();
        //RootPanel.get().add(panel);
        SimplePanel content = new SimplePanel();
        FlowContext context = new FlowContext();
        context.add("first", new Label("This is the first panel."));
        context.add("second", new Label("This is the second panel."));
        context.add("third", new Label("This is the third panel."));

        FlowController.setFlowContext(content, context);
        FlowController.setHistoryManager(new SimpleSessionHistoryManager());

        FlowTabPanel panel = new FlowTabPanel(content, context);
        panel.addTab("first", new Label("1st"), null);
        panel.addTab("second", new Label("2nd"), null);
        panel.addTab("third", new Label("3rd"), null);

        RootPanel.get()
                 .add(panel);
        RootPanel.get()
                 .add(content);
        panel.activateTab("first");
    }
}
