package com.totsp.gwittir.example.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import com.totsp.gwittir.client.flow.FlowContext;
import com.totsp.gwittir.client.flow.FlowController;
import com.totsp.gwittir.client.flow.FlowEvent;
import com.totsp.gwittir.client.flow.FlowEventListener;
import com.totsp.gwittir.client.ui.AbstractBoundWidget;
import com.totsp.gwittir.client.ui.BoundWidget;
import com.totsp.gwittir.client.ui.Label;
import com.totsp.gwittir.client.ui.TextArea;


public class ViewSourcePanel extends AbstractBoundWidget<Object> {
    private BoundWidget<?> view;
    private Class clazz;
    private SimplePanel managed = new SimplePanel();
    private TextArea source = new TextArea();
    private VerticalPanel panel = new VerticalPanel();

    public ViewSourcePanel(BoundWidget<?> view, Class clazz) {
        super();

        source.setValue("loading...");
        source.setWidth("800px");
        source.setHeight("500px");
        this.clazz = clazz;
        super.initWidget(panel);
        this.setWidth("100%");

        FlowContext context = new FlowContext();
        context.add("main", view);
        context.add("source", source);

        final Label toggle = new Label();
        toggle.setValue("view source");
        context.addFlowEventListener(
            new FlowEventListener() {
                public void onFlowEvent(FlowEvent evt) {
                    GWT.log("To: " + evt.getToName(), null);

                    if (evt.getToName()
                               .equals("main")) {
                        toggle.setValue("view source");
                    } else {
                        toggle.setValue("view example");
                    }
                }
            });
        FlowController.setFlowContext(managed, context);

        toggle.addClickListener(
            new ClickListener() {
                public void onClick(Widget sender) {
                    if (toggle.getValue()
                                  .endsWith("source")) {
                        toggle.setValue("view example");
                        FlowController.call(managed, "source", null);
                    } else {
                        toggle.setValue("view source");
                        FlowController.call(managed, "main", null);
                    }
                }
            });
        panel.add(toggle);
        panel.add(managed);
        panel.setCellHorizontalAlignment(toggle, HasHorizontalAlignment.ALIGN_RIGHT);
        FlowController.call(managed, "main", null, false);
    }

    public void setValue(Object value) {
        // TODO Auto-generated method stub
    }

    public Object getValue() {
        // TODO Auto-generated method stub
        return null;
    }

    public void onAttach() {
        super.onAttach();

        if (source.getValue()
                      .startsWith("loading")) {
            ReadSourceServiceAsync.Util.INSTANCE.getSource(
                this.clazz.toString(),
                new AsyncCallback<String>() {
                    public void onFailure(Throwable caught) {
                        Window.alert(caught.toString());
                    }

                    public void onSuccess(String result) {
                        source.setValue(result);
                    }
                });
        }
    }
}
