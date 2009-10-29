/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.totsp.gwittir.client.beans;

import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.user.client.ui.SourcesChangeEvents;

import com.totsp.gwittir.client.beans.Binding.BindingInstance;
import com.totsp.gwittir.client.beans.Binding.DefaultPropertyChangeListener;
import com.totsp.gwittir.client.beans.adapters.ChangeHandlerBindableAdapter;
import com.totsp.gwittir.client.beans.adapters.GWTBindableAdapter;
import com.totsp.gwittir.client.beans.adapters.OneWayBindingAdapter;
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
import com.totsp.gwittir.client.validator.CompositeValidationFeedback;
import com.totsp.gwittir.client.validator.CompositeValidator;
import com.totsp.gwittir.client.validator.ValidationFeedback;
import com.totsp.gwittir.client.validator.Validator;


/** The BindingBuilder is a semi-"literate programming" style builder for constructing bindings.
 *
 * @author <a href="kebernet@gmail.com">Robert "kebernet" Cooper</a>
 */
public class BindingBuilder implements SetConverterRight, SetValidateOrFinish, SetLeft, SetBindingOptionsLeft, SetPropertyLeft, SetValidationFeedbackLeft,
    SetValidationFeedbackRight, SetValidatorRight, SetValidateOrRight, SetBindingOptionsRight, SetPropertyRight {
    private Binding workBinding = new Binding();
    private BindingInstance left;
    private BindingInstance right;
    private Object temp;
    private Binding parent;

    private BindingBuilder() {
    }

    public static SetLeft appendChildToBinding(Binding parent) {
        BindingBuilder builder = new BindingBuilder();
        parent.getChildren()
              .add(builder.workBinding);
        builder.parent = parent;
        return builder;
    }

    public static SetPropertyLeft bind(SourcesPropertyChangeEvents object) {
        BindingBuilder builder = new BindingBuilder();
        builder.temp = object;

        return builder;
    }

    public static SetPropertyLeft bindOnPropertyChangeEvents(SourcesPropertyChangeEvents object){
        return bind(object);
    }

    public static SetPropertyLeft bindOnChangeEvents(SourcesChangeEvents object) {
        GWTBindableAdapter a = new GWTBindableAdapter(object);

        return bind(a);
    }

    public static SetPropertyLeft bindOnChangeHandler(HasChangeHandlers object) {
        ChangeHandlerBindableAdapter a = new ChangeHandlerBindableAdapter(object);

        return bind(a);
    }

    public SetPropertyLeft bindLeft(SourcesPropertyChangeEvents object) {
        this.temp = object;

        return this;
    }

    public SetPropertyLeft bindLeftOnChangeEvents(SourcesChangeEvents o) {
        this.temp = new GWTBindableAdapter(o);

        return this;
    }

    public SetPropertyLeft bindLeftOnChangeHandler(HasChangeHandlers o) {
        this.temp = new ChangeHandlerBindableAdapter(o);

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

    public SetRight notifiedWithLeft(ValidationFeedback feedback) {
        this.left.feedback = feedback;

        return this;
    }

    public SetRight notifiedWithLeft(ValidationFeedback... feedbacks) {
        this.left.feedback = new CompositeValidationFeedback(feedbacks);

        return this;
    }

    public Finish notifiedWithRight(ValidationFeedback feedback) {
        this.right.feedback = feedback;

        return this;
    }

    public Finish notifiedWithRight(ValidationFeedback... feedbacks) {
        this.right.feedback = new CompositeValidationFeedback(feedbacks);

        return this;
    }

    public SetBindingOptionsLeft onLeftProperty(String propertyName) {
        this.left = workBinding.createBindingInstance((SourcesPropertyChangeEvents) this.temp, propertyName);

        temp = null;

        return this;
    }

    public SetBindingOptionsRight onRightProperty(String propertyName) {
        this.right = workBinding.createBindingInstance((SourcesPropertyChangeEvents) this.temp, propertyName);

        return this;
    }

    public Binding toBinding() {
        this.workBinding.left = this.left;
        this.workBinding.right = this.right;
        this.left.listener = new DefaultPropertyChangeListener(left, right);
        this.right.listener = new DefaultPropertyChangeListener(right, left);
        assert left.object != null : "Left object null";
        assert left.property != null : "Left property null";
        assert left.listener != null : "Left listener null";
        assert right.object != null : "Right object null";
        assert right.property != null : "Right property null";
        assert right.listener != null : "Right listener null";

        return this.parent == null ? this.workBinding: this.parent;
        
    }

    public SetLeft and() {
        if(parent == null){
            parent = this.toBinding(); // first one, so save it.
        } else {
            this.toBinding(); //this was appended, so lets just finalize it.
        }
        return BindingBuilder.appendChildToBinding( this.parent );
    }

    public SetPropertyRight toRight(SourcesPropertyChangeEvents o) {
        this.temp = o;

        return this;
    }

    public SetPropertyRight toRightOnChangeEvents(SourcesChangeEvents o) {
        this.temp = new GWTBindableAdapter(o);

        return this;
    }

    public SetPropertyRight toRightOnChangeHandler(HasChangeHandlers o) {
        this.temp = new ChangeHandlerBindableAdapter(o);

        return this;
    }

    public SetPropertyRight toRightOneWay(Object o){
        this.temp = new OneWayBindingAdapter(o);

        return this;
    }

    public SetValidationFeedbackLeft validateLeftWith(Validator validator) {
        this.left.validator = validator;

        return this;
    }

    public SetValidationFeedbackLeft validateLeftWith(Validator... validators) {
        this.left.validator = new CompositeValidator(validators);

        return this;
    }

    public SetValidationFeedbackRight validateRightWith(Validator validator) {
        this.right.validator = validator;

        return this;
    }

    public SetValidationFeedbackRight validateRightWith(Validator... validators) {
        this.right.validator = new CompositeValidator(validators);

        return this;
    }


}
