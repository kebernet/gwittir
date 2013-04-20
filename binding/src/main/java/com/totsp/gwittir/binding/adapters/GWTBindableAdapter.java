/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.binding.adapters;

import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.SourcesChangeEvents;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @author kebernet
 */
public class GWTBindableAdapter extends BindableAdapter {

    private ChangeListener listener;
    private SourcesChangeEvents watched;

    public GWTBindableAdapter(SourcesChangeEvents watched){
        super(watched);
        this.watched = watched;
    }

    public GWTBindableAdapter(SourcesChangeEvents watched, String... properties){
        super(watched, properties);
        this.watched = watched;
    }

    @Override
    protected void initListener() {
        this.listener = new ChangeListener() {
                    public void onChange(Widget sender) {
                        update();
                    }
                };
        this.watched.addChangeListener(listener);
    }

    
    @Override
    protected void stopListener() {
       this.watched.removeChangeListener(this.listener);
    }

}
