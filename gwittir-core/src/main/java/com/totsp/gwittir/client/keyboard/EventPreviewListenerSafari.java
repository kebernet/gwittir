/*
 * EventPreviewListenerSafari.java
 *
 * Created on October 22, 2007, 1:38 PM
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

package com.totsp.gwittir.client.keyboard;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.totsp.gwittir.client.log.Level;
/**
 *
 * @author cooper
 */
public class EventPreviewListenerSafari extends EventPreviewListener {
    
    /** Creates a new instance of EventPreviewListenerSafari */
    public EventPreviewListenerSafari() {
    }
    
    public boolean onEventPreview(Event event) {
        if( DOM.eventGetType(event) != Event.ONKEYPRESS ){
            return true;
        }
        KeyboardController.LOG.log( Level.SPAM, "(Safari) Got preview event EventType: "+ DOM.eventGetType( event )+ " "+Event.ONKEYPRESS, null );
        KeyboardController.LOG.log( Level.SPAM, "(Safari) KeyCode: "+ DOM.eventGetKeyCode(event), null );
        int keyCode = DOM.eventGetKeyCode( event );
        if( keyCode > 122 ){
            keyCode -= 108;
        } else if( keyCode <= 31 ){
            keyCode += 96 - (DOM.eventGetShiftKey( event ) ? 32 : 0 );
        }
        return KeyboardController.INSTANCE.handleEvent( (char) keyCode,
                DOM.eventGetCtrlKey(event), DOM.eventGetAltKey(event) );
    }
    
}
