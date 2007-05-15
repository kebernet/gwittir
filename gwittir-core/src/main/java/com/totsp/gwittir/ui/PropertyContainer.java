/*
 * PropertyContainer.java
 *
 * Created on April 12, 2007, 12:43 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.totsp.gwittir.ui;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.totsp.gwittir.Validator;

/**
 *
 * @author cooper
 */
public class PropertyContainer {
    
    private BoundWidget widget;
    private Label label;
    private PopupPanel help;
    private Validator validator;
    
    
    /** Creates a new instance of PropertyContainer */
    public PropertyContainer(final BoundWidget widget, final Label label, final PopupPanel help, final Validator validator) {
        init(widget, label, help, validator);
    }
    
    protected PropertyContainer(){
        
    }
    
    protected void init(final BoundWidget widget, final Label label, final PopupPanel help, final Validator validator){
        this.setWidget(widget);
        this.setLabel(label);
        this.setHelp(help);
        this.setValidator( validator );
        this.getLabel().addClickListener( new ClickListener(){
            public void onClick(Widget sender) {
                help.setPopupPosition( sender.getAbsoluteLeft(), sender.getAbsoluteTop() + sender.getOffsetHeight() );
            }
            
        });
    }
    
    public BoundWidget getWidget() {
        return widget;
    }
    
    public void setWidget(BoundWidget widget) {
        this.widget = widget;
    }
    
    public Label getLabel() {
        return label;
    }
    
    public void setLabel(Label label) {
        this.label = label;
    }
    
    public PopupPanel getHelp() {
        return help;
    }
    
    public void setHelp(PopupPanel help) {
        this.help = help;
    }
    
    public Validator getValidator() {
        return validator;
    }
    
    public void setValidator(Validator validator) {
        this.validator = validator;
    }
    
}
