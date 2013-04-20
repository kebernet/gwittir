/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.binding.adapters;

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
