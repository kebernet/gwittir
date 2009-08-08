package com.totsp.gwittir.example.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.totsp.gwittir.client.beans.AbstractModelBean;
import com.totsp.gwittir.client.ui.BoundVerticalPanel;
import com.totsp.gwittir.client.ui.Button;
import com.totsp.gwittir.client.ui.table.BoundTable;
import com.totsp.gwittir.client.ui.table.Field;
import com.totsp.gwittir.client.ui.util.BoundWidgetTypeFactory;
import com.totsp.gwittir.client.ui.util.ChangeMarkedTypeFactory;
import com.totsp.gwittir.client.util.WindowContext;
import com.totsp.gwittir.client.validator.CompositeValidator;
import com.totsp.gwittir.client.validator.NotNullValidator;
import com.totsp.gwittir.client.validator.PopupValidationFeedback;
import com.totsp.gwittir.client.validator.ValidationException;
import com.totsp.gwittir.client.validator.Validator;

public class ClientStorageExample extends BoundVerticalPanel<Object>{
	
	private final List<ContextItem> model = new ArrayList<ContextItem>();
	private final ChangeMarkedTypeFactory factory = new ChangeMarkedTypeFactory();
	private final BoundTable table;
	public ClientStorageExample(){
		super(new BoundWidgetTypeFactory(), null);
		
		Field[] cols = new Field[2];
        cols[0] = new Field("name", "Name", null,
                "A name", null, new CompositeValidator()
        					.add( NotNullValidator.INSTANCE )
        					.add( NAME_VALIDATOR ),
                new PopupValidationFeedback(PopupValidationFeedback.BOTTOM));
        cols[1] = new Field("value", "Value", null,
                "The Value of the field");
        table = new BoundTable(BoundTable.HEADER_MASK +
               BoundTable.NO_SELECT_COL_MASK + 
               BoundTable.NO_SELECT_ROW_MASK
               , factory, cols, this.model);
        table.setWidth("400px");
        
        HorizontalPanel hp = new HorizontalPanel();
        
        hp.add(new Button("New Item", new ClickListener(){

			public void onClick(Widget sender) {
				table.add( new ContextItem() );
			}
        	
        }));
        hp.add( new Button("Save", new ClickListener(){

			public void onClick(Widget sender) {
				saveModel();
			}
        	
        }));
        add(hp);
        add(table);
        loadModel();
	}
	
	private void loadModel(){
		model.clear();
		GWT.log(""+WindowContext.INSTANCE.keySet().size(), null);
		for(String key : WindowContext.INSTANCE.keySet() ){
			model.add( new ContextItem(key, WindowContext.INSTANCE.get(key)));
		}
		table.setValue(this.model);
		factory.setMarking(false);
	}
	
	private void saveModel(){
		for(ContextItem item : model ){
			GWT.log(""+this.factory.hasBeenEdited(item), null);
			if(this.factory.hasBeenEdited(item)){
				WindowContext.INSTANCE.put(item.getName(), item.getValue());
				this.factory.reset(item);
			}
		}
	}
	
	
	private static final Validator NAME_VALIDATOR = new Validator() {

		public Object validate(Object value) throws ValidationException {
			if(!value.toString().matches("[a-zA-Z0-9]*") ){
				throw new ValidationException("Alphanumeric only and no whitespace!");
			}
			return value;
		}
		
	};

	public static class ContextItem extends AbstractModelBean {
		
		private String name;
		private String value;
		
		public ContextItem(){
			super();
		}
		
		public ContextItem(String name, String value){
			this.name = name;
			this.value = value;
		}
		
		public void setValue(String value) {
			this.changeSupport.firePropertyChange("value", this.value, this.value = value);
		}
		public String getValue() {
			return value;
		}
		public void setName(String name) {
			this.changeSupport.firePropertyChange("name", this.name ,this.name = name);
		}
		public String getName() {
			return name;
		}
		
	}
	
}
