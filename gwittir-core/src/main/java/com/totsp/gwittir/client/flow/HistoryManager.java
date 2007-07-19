/*
 * HistoryManager.java
 *
 * Created on June 27, 2007, 11:43 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.totsp.gwittir.client.flow;


import com.totsp.gwittir.client.ui.BoundWidget;


/**
 *
 * @author rcooper
 */
public interface HistoryManager {
    public void apply(String historyToken);

    public String getParameter(String key);

    public void setParameter(String key, String value);

    public void transition(String name, BoundWidget old, BoundWidget current);
}
