/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.client.beans;

import com.google.gwt.user.client.ui.SourcesChangeEvents;
import com.totsp.gwittir.client.beans.Binding.BindingInstance;
import com.totsp.gwittir.client.beans.Binding.DefaultPropertyChangeListener;
import com.totsp.gwittir.client.beans.interfaces.SetBindingOptions;
import com.totsp.gwittir.client.beans.interfaces.SetBindingOptionsRight;
import com.totsp.gwittir.client.beans.interfaces.SetObject;
import com.totsp.gwittir.client.beans.interfaces.SetProperty;
import com.totsp.gwittir.client.beans.interfaces.SetPropertyRight;
import com.totsp.gwittir.client.beans.interfaces.SetRight;
import com.totsp.gwittir.client.beans.interfaces.SetRightConverter;
import com.totsp.gwittir.client.beans.interfaces.SetValidateOrFinish;
import com.totsp.gwittir.client.beans.interfaces.SetValidateOrRight;
import com.totsp.gwittir.client.beans.interfaces.SetValidationFeedback;
import com.totsp.gwittir.client.validator.ValidationFeedback;
import com.totsp.gwittir.client.validator.Validator;

/**
 *
 * @author kebernet
 */

public class BindingBuilder implements SetRightConverter, SetValidateOrFinish, SetObject, SetBindingOptions, SetProperty, SetValidationFeedback, SetValidateOrRight, SetBindingOptionsRight, SetPropertyRight {

    
    private Binding parentBinding = new Binding();
    private BindingInstance left;
    private BindingInstance right;
    private Object temp;
    private boolean workingOnRight = false;;
    private BindingBuilder(){

    }


    public static SetObject appendChildToBinding(Binding parent){
        BindingBuilder builder = new BindingBuilder();
        parent.getChildren().add(builder.parentBinding);
        return builder;
    }


    public static SetProperty bindLeft(SourcesChangeEvents object){
        GWTBindableAdapter a = new GWTBindableAdapter(object);
        return bindLeft(a);
    }

    public static SetProperty bindLeft(Bindable object){
        BindingBuilder builder = new BindingBuilder();
        builder.temp = object;
        return builder;
    }

    public SetProperty bind(Bindable object){
        this.temp = object;
        System.out.println(object);
        return this;
    }


    public SetValidateOrRight convertLeftWith(Converter converter) {
        
            left.converter = converter;
       
        return this;
    }

    public SetValidateOrFinish convertRightWith(Converter converter){
         right.converter = converter;
         return this;
    }


    public SetValidationFeedback validateWith(Validator validator) {
        if(!workingOnRight){
            this.left.validator = validator;
        } else {
            this.right.validator = validator;
        }
        return this;
    }

    public SetPropertyRight to(Bindable o) {
        this.temp = o;
        this.workingOnRight = true;
        return this;
    }

    public SetBindingOptions property(String propertyName) {
        if(!workingOnRight ){
            this.left = parentBinding.createBindingInstance((Bindable) this.temp, propertyName);
        } else {
            this.right = parentBinding.createBindingInstance((Bindable) this.temp, propertyName);
        }
        temp = null;
        return this;
    }



    public SetRight notifiedWith(ValidationFeedback feedback) {
        if(!this.workingOnRight){
            this.left.feedback = feedback;
        } else {
            this.right.feedback = feedback;
        }
        return this;
    }

    

    public SetBindingOptionsRight toProperty(String propertyName) {
        this.workingOnRight = true;
        return (SetBindingOptionsRight) this.property(propertyName);
    }

    public Binding toBinding(){
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




}
