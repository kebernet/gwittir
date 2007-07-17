/*
 * AbstractHistoryManager.java
 *
 * Created on June 27, 2007, 11:44 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.totsp.gwittir.client.flow;

import com.google.gwt.user.client.History;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;


/**
 *
 * @author rcooper
 */
public abstract class AbstractHistoryManager implements HistoryManager {
    /** Creates a new instance of AbstractHistoryManager */
    public AbstractHistoryManager() {
    }

    public String generateHistoryToken(HashMap values) {
        StringBuffer sb = new StringBuffer();

        for(Iterator it = values.entrySet().iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();

            if(entry.getValue() instanceof List) {
                for(Iterator vit = ((List) entry.getValue()).iterator(); vit.hasNext();) {
                    sb.append(entry.getKey());
                    sb.append("=");
                    sb.append(vit.next().toString().replaceAll(";", "@~"));

                    if(vit.hasNext()) {
                        sb.append(";");
                    }
                }
            } else {
                sb.append(entry.getKey());
                sb.append("=");
                sb.append(entry.getValue());
            }

            if(it.hasNext()) {
                sb.append(";");
            }
        }

        return sb.toString();
    }

    public String getParameter(String key) {
        HashMap values = parseHistoryToken(History.getToken());
        Object value = values.get(key);

        if(value == null) {
            return null;
        } else if(value instanceof List) {
            return ((List) value).get(0).toString();
        } else {
            return value.toString();
        }
    }

    public HashMap parseHistoryToken(String historyToken) {
        StringTokenizer tok = new StringTokenizer(historyToken, ";");
        HashMap map = new HashMap();

        while(tok.hasMoreTokens()) {
            String token = tok.nextToken();
            String key = token.substring(0, token.indexOf("="));
            String value = token.substring(token.indexOf("=") + 1);
            value = value.replaceAll("@~", ";");

            if(map.containsKey(key)) {
                Object current = map.get(key);

                if(current instanceof List ) {
                    ((List) current).add(value);
                } else {
                    ArrayList newVals = new ArrayList();
                    newVals.add(current);
                    newVals.add(value);
                    current = newVals;
                }

                map.put(key, current);
            } else {
                map.put(key, value);
            }
        }

        return map;
    }

    public void setParameter(String key, String value) {
        HashMap values = parseHistoryToken(History.getToken());
        values.put(key, value);
        History.newItem(generateHistoryToken(values));
    }
}
