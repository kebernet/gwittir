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
import com.google.gwt.user.client.Window;
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
    boolean big = false;

    /** Creates a new instance of ExampleEntryPoint */
    public ExampleEntryPoint() {
    }


    public void onModuleLoad() {
    	WindowContext.INSTANCE.initialize(new WindowContextCallback(){

			public void onInitialized() {
				Window.alert(WindowContext.INSTANCE.get("TestValue"));
				WindowContext.INSTANCE.put("TestValue", "I am saved to the window context");
			}
    		
    	});	
    	FlowController.setHistoryManager(new SimpleSessionHistoryManager());
    	
    	FlowContext context = new FlowContext();
    	context.add("stream", new ViewSourcePanel(new StreamingExample(), StreamingExample.class));
        context.add("animation", new ViewSourcePanel(new AnimationExample(), AnimationExample.class));
        context.add("gridform", new ViewSourcePanel(new GridFormExample(), GridFormExample.class));
    	context.add("softscroll", new ViewSourcePanel(new SoftScrollAreaExample(), SoftScrollAreaExample.class));
        context.add("table",new ViewSourcePanel(new TableExample(), TableExample.class));
        context.add("keys", new ViewSourcePanel(new KeyBindingExample(), KeyBindingExample.class) );
        context.add("flickr", new ViewSourcePanel(new FlickrExample(), FlickrExample.class));
        
        SimplePanel sp = new SimplePanel();
        FlowController.setFlowContext(sp, context);
       
        
        FlowTabPanel tabs = new FlowTabPanel(sp, context);
        tabs.addTab("flickr", new Label("Flickr"), null);
        tabs.addTab("gridform", new Label("GridForm"), null);
        tabs.addTab("animation", new Label("FX"), null);
        tabs.addTab("table", new Label("Table"), null);
        tabs.addTab("keys", new Label("KeyBinding"), null);
        tabs.addTab("softscroll", new Label("SoftScrollArea"), null);
        tabs.addTab("stream", new Label("StreamRPC"), null);
        
        
        RootPanel.get().add(tabs);
        RootPanel.get().add(sp);
        
        
        
        
        

        
        

        
        

        /*inal VerticalPanel vp2 = new VerticalPanel();
        vp2.add(new PopupDatePicker());

        Calendar cal = new Calendar();
        vp2.add(cal);
        tp.add(vp2, "Calendar");
        cal.addCalendarListener(new CalendarListener() {
                public boolean onDateClicked(Calendar calendar, Date date) {
                    vp2.add(new Label("Clicked " + date));

                    return true;
                }
            });
        vp2.add(new DatePicker());

        

       
        Label hasContext = new Label("RightClickMe");
        GWT.log("Before constructor", null);

        ContextMenuPanel ctx = new ContextMenuPanel(hasContext);
        ctx.addMenuItemWidget(new Button("Item 1"));
        ctx.addMenuItemWidget(new Button("Item 2"));
        vp = new VerticalPanel();

        //vp.add( ctx );
        Button ctxB = new MenuItem("Rick roll");
        ctx = new ContextMenuPanel(ctxB);
        ctx.addMenuItemWidget(new MenuItem("Item 1"));
        ctx.addMenuItemWidget(new MenuItem("Item 2"));

        SubMenu sub = new SubMenu("Sub");
        sub.addMenuItemWidget(new MenuItem("Sub Item 1"));
        sub.addMenuItemWidget(new MenuItem("Sub Item 2"));

        SubMenu sub2 = new SubMenu("Sub2");
        sub2.addMenuItemWidget(new MenuItem("Sub2 Item 1"));
        sub2.addMenuItemWidget(new MenuItem("Sub2 Item 2"));
        sub.addMenuItemWidget(sub2);
        ctx.addMenuItemWidget(sub);
        vp.add(ctx);
        tp.add(vp, "Context Menu");

        DateTime dt = new DateTime();
        int month = dt.getMonthOfYear();
        DateTimeFormat dtf = DateTimeFormat.getFormat("zzz");
        GWT.log(month + " " + dt + " " + dtf.format(new Date()), null);
        */
    }

    private native void alertSelection() /*-{
    var selectionObject = $wnd.getSelection();
    var range = $doc.createRange();
    range.setStart(selectionObject.anchorNode,selectionObject.anchorOffset);
    range.setEnd(selectionObject.focusNode,selectionObject.focusOffset);
    var frag = range.cloneContents();
    for( x in frag ){
    alert( x );
    }

    }-*/;
}
