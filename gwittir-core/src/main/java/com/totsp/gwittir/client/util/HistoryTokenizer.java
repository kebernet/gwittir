/*
 * Copyright 2009 Robert "kebernet" Cooper
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package com.totsp.gwittir.client.util;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.History;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 *
 * @author kebernet
 */
public class HistoryTokenizer {

    private Map<String, String> tokens = new HashMap<String, String>();
    private boolean isInTransaction = false;

    HistoryTokenizer(String anchor){
        String[] values = anchor.split("&");
        for(String value : values){
            String[] nameVal = value.split("=");
            if(nameVal.length == 1 && nameVal[0].length() > 0){
                tokens.put(nameVal[0], "");
            } else {
                tokens.put(nameVal[0]
                            .replaceAll("~#038;", "&")
                            .replaceAll("~#061;", "="),
                        nameVal[1]
                            .replaceAll("~#038;", "&")
                            .replaceAll("~#061;", "=")
                        );
            }
        }
    }
    public HistoryTokenizer(){
        this(History.getToken());
    }

    public Set<Entry<String,String>> getEntries(){
        return tokens.entrySet();
    }

    public String getToken(String token){
        return tokens.get(token);
    }
    
    public void setToken(String token, String value){
        this.tokens.put(token, value);
        if(!this.isInTransaction){
            this.store();
        }
    }

    public void begin(){
        this.isInTransaction = true;
    }

    public void commit(){
        this.isInTransaction = false;
        this.store();
    }

    private void store(){
        History.newItem( this.tokenize() );
    }

    String tokenize(){
        StringBuilder sb = new StringBuilder();
        for(Entry<String, String> entry : this.tokens.entrySet() ){
            sb = sb.append(entry.getKey()
                    .replaceAll("&", "~#038;")
                    .replaceAll("=", "~#061;"));
            if( entry.getValue().length() > 0){
                sb = sb.append("=")
                        .append( entry.getValue()
                            .replaceAll("&", "~#038;")
                            .replaceAll("=", "~#061;"));
            }
            sb = sb.append("&");
        }
        GWT.log("Tokenizing history" + sb.toString(), null);
        return sb.toString();
    }



    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (this.tokens != null ? this.tokens.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final HistoryTokenizer other = (HistoryTokenizer) obj;
        if (this.tokens != other.tokens && (this.tokens == null || !this.tokens.equals(other.tokens))) {
            return false;
        }
        return true;
    }

}
