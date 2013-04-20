/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.mvc.beans.adapters;

import com.totsp.gwittir.mvc.beans.SourcesPropertyChangeEvents;


/**
 *
 * @author rcooper
 */
public interface AdapterInstrumentor {

    public SourcesPropertyChangeEvents instrument(Object o) throws AdapterException;

}
