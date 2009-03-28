/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.client.util.html5db;

import com.google.gwt.core.client.JavaScriptObject;

/**
 *
 * @author kebernet
 */
public class Database extends JavaScriptObject {


    protected Database(){
    }

    public final native void run(TransactionTask task)/*-{
        this.transaction(function(tx)
        {
            task.@com.totsp.gwittir.client.util.html5db.TransactionTask::run(Lcom/totsp/gwittir/client/util/html5db/Transaction;)(tx);

        });
        return;
    }-*/;

}
