/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.totsp.gwittir.client.action;

import com.totsp.gwittir.client.beans.Binding;
import com.totsp.gwittir.client.beans.BindingBuilder;
import com.totsp.gwittir.client.beans.SourcesPropertyChangeEvents;
import com.totsp.gwittir.client.ui.BoundWidget;
import com.totsp.gwittir.client.util.StringConverter;


/**
 * Binds a String adapted value on property name from the model object to the .value of the
 * widget.
 * @author robert.cooper
 */
public class GenericBindingAction implements BindingAction {
    private static final StringConverter CONVERTERS = new StringConverter();
    private final Class clazz;
    private final String propertyName;
    private Binding binding;

    public GenericBindingAction(final String propertyName, final Class clazz) {
        this.propertyName = propertyName;
        this.clazz = clazz;
    }

    @Override
    public void bind(BoundWidget widget) {
        this.binding.bind();
    }

    @Override
    public void execute(BoundWidget widget) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void set(BoundWidget widget) {
        SourcesPropertyChangeEvents model = (SourcesPropertyChangeEvents) widget.getModel();
        this.binding = BindingBuilder.bind(widget)
                                     .onLeftProperty("value")
                                     .convertLeftWith(CONVERTERS.from(clazz))
                                     .toRight(model)
                                     .onRightProperty(propertyName)
                                     .convertRightWith(CONVERTERS.to(clazz))
                                     .toBinding();
        this.binding.setLeft();
    }

    @Override
    public void unbind(BoundWidget widget) {
        if (this.binding != null) {
            this.binding.unbind();
            this.binding = null;
        }
    }
}
