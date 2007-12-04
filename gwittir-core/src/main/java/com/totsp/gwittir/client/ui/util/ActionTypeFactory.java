/*
 * ActionTypeFactory.java
 *
 * Created on November 30, 2007, 2:35 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.totsp.gwittir.client.ui.util;

import com.totsp.gwittir.client.action.Action;
import java.util.HashMap;

/**
 *
 * @author rcooper
 */
public class ActionTypeFactory {
    HashMap registry = new HashMap();
    /** Creates a new instance of ActionTypeFactory */
    public ActionTypeFactory() {
        super();
    }
    
    public void add(Class type, ActionProvider provider){
        this.registry.put( type, provider);
    }
    
    public ActionProvider getActionProvider(Class type){
        return (ActionProvider) registry.get(type);
    }
    
}
