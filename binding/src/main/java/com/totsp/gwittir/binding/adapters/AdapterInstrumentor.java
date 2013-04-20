/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.binding.adapters;


import com.totsp.gwittir.binding.SourcesPropertyChangeEvents;

/**
 *
 * @author rcooper
 */
public interface AdapterInstrumentor {

    public SourcesPropertyChangeEvents instrument(Object o) throws AdapterException;

}
