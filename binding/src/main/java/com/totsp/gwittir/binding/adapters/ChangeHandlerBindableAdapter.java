/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.binding.adapters;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.shared.HandlerRegistration;

/**
 *
 * @author rcooper
 */
public class ChangeHandlerBindableAdapter extends BindableAdapter {
    private ChangeHandler handler = new ChangeHandler(){

        public void onChange(ChangeEvent event) {
            update();
        }

    };
    private HasChangeHandlers wrapped;
    private HandlerRegistration registration;
    public ChangeHandlerBindableAdapter(HasChangeHandlers wrapped){
        super(wrapped);
    }

    @Override
    protected void initListener() {
        this.registration = this.wrapped.addChangeHandler(handler);
    }

    @Override
    protected void stopListener() {
        this.registration.removeHandler();
    }

}
