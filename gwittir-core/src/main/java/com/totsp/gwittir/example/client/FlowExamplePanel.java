package com.totsp.gwittir.example.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.totsp.gwittir.client.action.Action;
import com.totsp.gwittir.client.action.BindingAction;
import com.totsp.gwittir.client.beans.Binding;
import com.totsp.gwittir.client.flow.FlowContext;
import com.totsp.gwittir.client.flow.FlowController;
import com.totsp.gwittir.client.flow.SimpleSessionHistoryManager;
import com.totsp.gwittir.client.ui.AbstractBoundWidget;
import com.totsp.gwittir.client.ui.BoundWidget;
import com.totsp.gwittir.client.ui.Button;
import com.totsp.gwittir.client.ui.Label;

public class FlowExamplePanel extends SimplePanel {
	
	public FlowExamplePanel(){
		super();
		//Create some state names
		final List<String> states = new ArrayList<String>();
		for(String state : 
			new String[]{ "one", "two", "three" }
		){
			states.add(state);
		}
		//Create some models for the states
		final List<MyClass> models = new ArrayList<MyClass>();	
		for(int i=0; i < 3; i++){
			MyClass c = new MyClass();
			c.setEmailAddress("test"+i+"@test.test");
			models.add(c);
		}
		
		//Create a bindingAction for our views
		BindingAction bindingAction = new BindingAction(){
			
			private Binding b;
			
			public void bind(BoundWidget widget) {
				if(widget instanceof Page)
					b.bind();
				
			}

			public void set(BoundWidget widget) {
				if(widget instanceof Page){
					b = new Binding( widget, "label.value", widget, "model.emailAddress");
					b.setLeft();
				}
			}

			public void unbind(BoundWidget widget) {
				if(widget instanceof Page)
					b.unbind();
			}

			public void execute(BoundWidget widget) {
				if( widget instanceof Button ){
					Button b = (Button) widget;
					Page p = (Page) ((Widget) widget).getParent().getParent();
					int index = states.indexOf( p.getName() );
					if( b.getText().equals("prev") ){
						index --;
					} else {
						index ++;
					}
					if(index < 0 || index >= states.size() ){
						return;
					} else {
						FlowController.call(p, states.get(index), models.get(index));
					}
				}
			}
			
		};
		//Create a new FlowContext
		FlowContext context = new FlowContext();
		for(int i=0; i < states.size(); i++ ){
			//Add a state for each name and the action we want to use
			context.add(states.get(i), new Page(states.get(i)), bindingAction);
		}
		
		//Register the flow context for this widget
		FlowController.setFlowContext(this, context);
		//Add a history manager
		FlowController.setHistoryManager( new SimpleSessionHistoryManager());
		//Call the first state
		FlowController.call(this, states.get(0), models.get(0));
	}
	
	
	public static class Page extends AbstractBoundWidget<Object> {

		private Label label = new Label();
		private Button next = new Button("next");
		private Button prev = new Button("prev");
		private String name;
		private MyClass value;
		
		public Page(String name){
			super();
			this.name = name;
			VerticalPanel panel = new VerticalPanel();
			panel.add(label);
			panel.add(next);
			panel.add(prev);
			super.initWidget(panel);
		}
		
		public Label getLabel(){
			return this.label;
		}
		
		public String getName(){
			return this.name;
		}
		
		public Object getValue() {
			return  null;
		}

		public void setValue(Object value) {
			return;
		}
		
		public void setAction(Action a){
			super.setAction(a);
			next.setAction(a);
			prev.setAction(a);
			
		}
		
		
		
	}

}
