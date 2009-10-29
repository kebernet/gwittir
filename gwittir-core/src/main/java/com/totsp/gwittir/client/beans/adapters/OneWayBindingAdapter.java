/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.client.beans.adapters;

/**
 *
 * @author rcooper
 */
public class OneWayBindingAdapter extends BindableAdapter {

    public OneWayBindingAdapter(Object wrapped){
        super(wrapped);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void stopListener() {
        
    }

}
