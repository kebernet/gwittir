/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.mvc.beans.interfaces;

import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.user.client.ui.SourcesChangeEvents;
import com.totsp.gwittir.mvc.beans.SourcesPropertyChangeEvents;

/**
 *
 * @author kebernet
 */
public interface SetLeft {
    SetPropertyLeft bindLeft(SourcesPropertyChangeEvents o);
    SetPropertyLeft bindLeftOnChangeEvents(SourcesChangeEvents o);
    SetPropertyLeft bindLeftOnChangeHandler(HasChangeHandlers o);
}
