/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.totsp.gwittir.client.beans;

import com.google.gwt.user.client.ui.SourcesChangeEvents;

import com.totsp.gwittir.client.beans.Binding.BindingInstance;
import com.totsp.gwittir.client.beans.Binding.DefaultPropertyChangeListener;
import com.totsp.gwittir.client.beans.interfaces.Finish;
import com.totsp.gwittir.client.beans.interfaces.SetBindingOptionsLeft;
import com.totsp.gwittir.client.beans.interfaces.SetBindingOptionsRight;
import com.totsp.gwittir.client.beans.interfaces.SetConverterRight;
import com.totsp.gwittir.client.beans.interfaces.SetLeft;
import com.totsp.gwittir.client.beans.interfaces.SetPropertyLeft;
import com.totsp.gwittir.client.beans.interfaces.SetPropertyRight;
import com.totsp.gwittir.client.beans.interfaces.SetRight;
import com.totsp.gwittir.client.beans.interfaces.SetValidateOrFinish;
import com.totsp.gwittir.client.beans.interfaces.SetValidateOrRight;
import com.totsp.gwittir.client.beans.interfaces.SetValidationFeedbackLeft;
import com.totsp.gwittir.client.beans.interfaces.SetValidationFeedbackRight;
import com.totsp.gwittir.client.beans.interfaces.SetValidatorRight;
import com.totsp.gwittir.client.validator.ValidationFeedback;
import com.totsp.gwittir.client.validator.Validator;


/**
 *
 * @author kebernet
 */
public class BindingBuilder implements SetConverterRight, SetValidateOrFinish, SetLeft, SetBindingOptionsLeft,
    SetPropertyLeft, SetValidationFeedbackLeft, SetValidationFeedbackRight, SetValidatorRight, SetValidateOrRight,
    SetBindingOptionsRight, SetPropertyRight {
    private Binding parentBinding = new Binding();
    private BindingInstance left;
    private BindingInstance right;
    private Object temp;
    private boolean workingOnRight = false;

    private BindingBuilder() {
    }

    public static SetLeft appendChildToBinding(Binding parent) {
        BindingBuilder builder = new BindingBuilder();
        parent.getChildren()
              .add(builder.parentBinding);

        return builder;
    }

    public static SetPropertyLeft bind(SourcesChangeEvents object) {
        GWTBindableAdapter a = new GWTBindableAdapter(object);

        return bind(a);
    }

    public static SetPropertyLeft bind(Bindable object) {
        BindingBuilder builder = new BindingBuilder();
        builder.temp = object;

        return builder;
    }

    public SetPropertyLeft bindLeft(Bindable object) {
        this.temp = object;
        System.out.println(object);

        return this;
    }

    public SetValidateOrRight convertLeftWith(Converter converter) {
        left.converter = converter;

        return this;
    }

    public SetValidateOrFinish convertRightWith(Converter converter) {
        right.converter = converter;

        return this;
    }

    public SetBindingOptionsLeft onLeftProperty(String propertyName) {
        this.left = parentBinding.createBindingInstance((Bindable) this.temp, propertyName);

        temp = null;

        return this;
    }

    public SetRight notifiedWithLeft(ValidationFeedback feedback) {
        this.left.feedback = feedback;

        return this;
    }

    public Finish notifiedWithRight(ValidationFeedback feedback) {
        this.right.feedback = feedback;

        return this;
    }

    public SetBindingOptionsRight onRightProperty(String propertyName) {
        this.right = parentBinding.createBindingInstance((Bindable) this.temp, propertyName);
        this.workingOnRight = true;

        return this;
    }

    public Binding toBinding() {
        this.parentBinding.left = this.left;
        this.parentBinding.right = this.right;
        this.left.listener = new DefaultPropertyChangeListener(left, right);
        this.right.listener = new DefaultPropertyChangeListener(right, left);
        assert left.object != null : "Left object null";
        assert left.property != null : "Left property null";
        assert left.listener != null : "Left listener null";
        assert right.object != null : "Right object null";
        assert right.property != null : "Right property null";
        assert right.listener != null : "Right listener null";

        return this.parentBinding;
    }

    public SetPropertyRight toRight(Bindable o) {
        this.temp = o;
        this.workingOnRight = true;

        return this;
    }

    public SetValidationFeedbackLeft validateLeftWith(Validator validator) {
        this.left.validator = validator;

        return this;
    }

    public SetValidationFeedbackRight validateRightWith(Validator validator) {
        this.right.validator = validator;

        return this;
    }
}
