/*
 * ExampleEntryPoint.java
 *
 * Created on August 3, 2007, 3:51 PM
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 */
package com.totsp.gwittir.example.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;

import com.totsp.gwittir.client.flow.FlowContext;
import com.totsp.gwittir.client.flow.FlowController;
import com.totsp.gwittir.client.flow.SimpleSessionHistoryManager;
import com.totsp.gwittir.client.ui.FlowTabPanel;
import com.totsp.gwittir.client.ui.Label;
import com.totsp.gwittir.client.util.WindowContext;
import com.totsp.gwittir.client.util.WindowContext.WindowContextCallback;


/**
 *
 * @author <a href="mailto:cooper@screaming-penguin.com">Robert "kebernet" Cooper</a>
 */
public class ExampleEntryPoint implements EntryPoint {
    /** Creates a new instance of ExampleEntryPoint */
    public ExampleEntryPoint() {
    }

    public void onModuleLoad() {
        WindowContext.INSTANCE.initialize(
            new WindowContextCallback() {
                public void onInitialized() {
                    start();
                }
            });
    }

    public void start() {
        FlowController.setHistoryManager(new SimpleSessionHistoryManager());

        FlowContext context = new FlowContext();
        context.add("stream", new ViewSourcePanel(new StreamingExample(), StreamingExample.class));
        context.add("animation", new ViewSourcePanel(new AnimationExample(), AnimationExample.class));
        context.add("gridform", new ViewSourcePanel(new GridFormExample(), GridFormExample.class));
        context.add("softscroll", new ViewSourcePanel(new SoftScrollAreaExample(), SoftScrollAreaExample.class));
        context.add("table", new ViewSourcePanel(new TableExample(), TableExample.class));
        context.add("keys", new ViewSourcePanel(new KeyBindingExample(), KeyBindingExample.class));
        context.add("flickr", new ViewSourcePanel(new FlickrExample(), FlickrExample.class));
        context.add("contextmenu", new ViewSourcePanel(new ContextMenuExample(), ContextMenuExample.class));
        context.add("storage", new ViewSourcePanel(new ClientStorageExample(), ClientStorageExample.class));

        SimplePanel sp = new SimplePanel();
        FlowController.setFlowContext(sp, context);

        FlowTabPanel tabs = new FlowTabPanel(sp, context);
        tabs.addTab("flickr", new Label("Flickr"), null);
        tabs.addTab("storage", new Label("Client Side Storage"), null);
        tabs.addTab("gridform", new Label("GridForm"), null);
        tabs.addTab("animation", new Label("FX"), null);
        tabs.addTab("table", new Label("Table"), null);
        tabs.addTab("keys", new Label("KeyBinding"), null);
        tabs.addTab("softscroll", new Label("SoftScrollArea"), null);
        tabs.addTab("stream", new Label("StreamRPC"), null);
        tabs.addTab("contextmenu", new Label("Context Menu"), null);

        RootPanel.get()
                 .add(tabs);
        RootPanel.get()
                 .add(sp);

        /*FlashStorage.initialize(new StartupCallback(){

                        public void onStart() {
                                try{
                                FlashStorage storage = FlashStorage.getInstance();
                                Map<String, String> vals = storage.getLocal("gwittir");
                                Window.alert(vals.get("test"));
                                vals.put("test", "test");
                                storage.setLocal("gwittir", vals);
                                storage.flushAll();
                                } catch(Exception e){
                                        GWT.log(null, e);
                                }
                        }

        });

        Button b = new Button("click", new ClickListener(){

                        public void onClick(Widget sender) {
                                FlashStorage storage = FlashStorage.getInstance();
                                Map<String, String> vals = storage.getLocal("gwittir");
                        }

        });
        RootPanel.get().add(b);*/
    }
}
