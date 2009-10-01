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
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.totsp.gwittir.client.beans.AbstractModelBean;
import com.totsp.gwittir.client.beans.Binding;
import com.totsp.gwittir.client.flow.FlowContext;
import com.totsp.gwittir.client.flow.FlowController;
import com.totsp.gwittir.client.flow.SimpleSessionHistoryManager;
import com.totsp.gwittir.client.fx.DragAndDropContext;
import com.totsp.gwittir.client.fx.DropListener;
import com.totsp.gwittir.client.ui.FlowTabPanel;




/**
 *
 * @author <a href="mailto:cooper@screaming-penguin.com">Robert "kebernet" Cooper</a>
 */
public class ExampleEntryPoint implements EntryPoint {
    
    /** Creates a new instance of ExampleEntryPoint */
    public ExampleEntryPoint() {
    }


    public void onModuleLoad(){
    	/*WindowContext.INSTANCE.initialize(new WindowContextCallback(){

			public void onInitialized() {
				start();
			}
    		
    	});*/
        try{
        start();
        } catch(Exception e){
            e.printStackTrace();
        }

    }

    public void start(){

        HorizontalPanel hp = new HorizontalPanel();
        hp.setWidth("100%");
        final Label draggable = new Label("DRAG ME");
        final Label droppable = new Label("DROP HERE");
        DragAndDropContext d = new DragAndDropContext();

        d.makeDraggable(draggable, true);
        d.makeDroppable(droppable);
        d.addDropListener(droppable, new DropListener(){

            public boolean onDrop(Widget dropped) {
                RootPanel.get().add(dropped);
                droppable.getElement().getStyle().setBackgroundColor("white");
                return true;
            }

            public boolean onHover(Widget hovered) {
                droppable.getElement().getStyle().setBackgroundColor("green");
                return true;
            }

            public boolean onEndHover(Widget hovered) {
                droppable.getElement().getStyle().setBackgroundColor("white");
                return true;
            }

            public boolean canDrop(Widget dropped) {
                GWT.log("canDrop: "+dropped, null);
                return true;
            }

        });

        hp.add(draggable);
        hp.add(droppable);
        RootPanel.get().add(hp);




    }


    public void xstart() {
    	
    	
    	FlowController.setHistoryManager(new SimpleSessionHistoryManager());
    	
    	FlowContext context = new FlowContext();
    	context.add("stream", new ViewSourcePanel(new StreamingExample(), StreamingExample.class));
        context.add("animation", new ViewSourcePanel(new AnimationExample(), AnimationExample.class));
        context.add("gridform", new ViewSourcePanel(new GridFormExample(), GridFormExample.class));
    	context.add("softscroll", new ViewSourcePanel(new SoftScrollAreaExample(), SoftScrollAreaExample.class));
        context.add("table",new ViewSourcePanel(new TableExample(), TableExample.class));
        context.add("keys", new ViewSourcePanel(new KeyBindingExample(), KeyBindingExample.class) );
        context.add("flickr", new ViewSourcePanel(new FlickrExample(), FlickrExample.class));
        context.add("contextmenu", new ViewSourcePanel( new ContextMenuExample(), ContextMenuExample.class));
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
        
        RootPanel.get().add(tabs);
        RootPanel.get().add(sp);
        
        
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


     /*public void testBasicDotted() throws Exception {
        Person mark = new Person("Mark", "Lanford", 31);
        mark.setSpouse(new Person("Benay", "Krissel", 37));
        Person benayCopy = new Person();
        Binding b = new Binding(benayCopy, "firstName", mark, "spouse.firstName");
        b.setLeft();
        this.assertEquals(mark.getSpouse().getFirstName(), benayCopy.getFirstName());
        b.bind();
        mark.getSpouse().setFirstName("change");
        this.assertEquals("change", benayCopy.getFirstName());

        Person doe = new Person("Jane", "Doe", 99);
        mark.setSpouse(doe);
        this.assertEquals(mark.getSpouse().getFirstName(), benayCopy.getFirstName());
        mark.getSpouse().setFirstName("change");
        this.assertEquals("change", benayCopy.getFirstName());

        b.unbind();

        // Test Array bindings with descriminators
        Person childCopy = new Person();
        Person[] children1 = {new Person("Delaney", "Krissel", 12),
            new Person("Jonny", "Doe", 1),
            new Person("Jenny", "Nobody", 3)
        };

        Person[] children2 = {new Person("Jonny", "Doe", 1),
            new Person("Delaney", "Krissel", 12),
            new Person("Timmy", "Nobody", 3)
        };

        mark.setChildren(children1);
        b = new Binding(childCopy, "firstName", mark, "children[2].firstName");
        b.setLeft();
        this.assertEquals("Jenny", childCopy.getFirstName());
        b.bind();
        children1[2].setFirstName("Becky");
        this.assertEquals("Becky", childCopy.getFirstName());
        mark.setChildren(children2);
        this.assertEquals("Timmy", childCopy.getFirstName());
        b.unbind();

        b = new Binding(childCopy, "firstName", mark, "children[lastName=Doe].firstName");
        b.setLeft();
        this.assertEquals("Jonny", childCopy.getFirstName());
        b.bind();
        b.unbind();


        //
        // Make sure cleanups worked.
        //
        Person pcsTest = new Person();
        PropertyChangeListener l = new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent arg0) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

        };
        pcsTest.addPropertyChangeListener("firstName", l);
        pcsTest.removePropertyChangeListener("firstName", l);
        this.assertEquals(0, pcsTest.getPropertyChangeListeners().length );
        this.assertEquals(0, childCopy.getPropertyChangeListeners().length);
        this.assertEquals(0, mark.getPropertyChangeListeners().length);
        this.assertEquals(0, mark.getSpouse().getPropertyChangeListeners().length);

        for (int i = 0; i < children1.length; i++) {
            this.assertEquals(0, children1[i].getPropertyChangeListeners().length);
        }
        for (int i = 0; i < children2.length; i++) {
            this.assertEquals(0, children2[i].getPropertyChangeListeners().length);
        }

    }*/


    public static void testMattRead() throws Exception {
       MockModel model1 = new MockModel();
       model1.setTestProp("model1");

       MockModel model2 = new MockModel();
       model2.setTestProp("model2");

       Binding b = new Binding(model1, "testProp", model2,
"testProp");
       b.bind();
       GWT.log( ""+model1, null);
       GWT.log( ""+model2, null);
       assert "model1".equals(model1.getTestProp());
       assert "model2".equals(model2.getTestProp());

       b.setRight();

       assert "model1".equals( model1.getTestProp());
       assert "model1".equals(model2.getTestProp());

       // WORKS UP TO THIS POINT

       model1.setTestProp("different");
       GWT.log( ""+model1, null);
       GWT.log( ""+model2, null);
       assert "different".equals( model1.getTestProp());
       assert "different".equals( model2.getTestProp()); // FAILS
    }

    public static class MockModel extends AbstractModelBean {

    private String testProp;

    public static final String PROP_TESTPROP = "testProp";

    /**
     * Get the value of testProp
     *
     * @return the value of testProp
     */
    public String getTestProp() {
        return this.testProp;
    }

    /**
     * Set the value of testProp
     *
     * @param newtestProp new value of testProp
     */
    public void setTestProp(String newtestProp) {
        String oldtestProp = testProp;
        this.testProp = newtestProp;
        this.changeSupport.firePropertyChange(PROP_TESTPROP, oldtestProp, newtestProp);
    }

    }
    
}
