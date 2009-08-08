/*
 * EventPreviewListener.java
 *
 * Created on October 22, 2007, 1:04 PM
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
import com.google.gwt.user.client.EventPreview;

import com.totsp.gwittir.client.log.Level;


/**
 *
 * @author cooper
 */
class EventPreviewListener implements EventPreview {
    /** Creates a new instance of EventPreviewListener */
    public EventPreviewListener() {
    }

    public boolean onEventPreview(Event event) {
        if(DOM.eventGetType(event) != Event.ONKEYDOWN) {
            return true;
        }

        KeyboardController.LOG.log(
            Level.SPAM,
            "Got preview event EventType: " + DOM.eventGetType(event) + " "
            + Event.ONKEYDOWN, null);
        KeyboardController.LOG.log(
            Level.SPAM, "KeyCode: " + DOM.eventGetKeyCode(event), null);

        boolean bubble = KeyboardController.INSTANCE.handleEvent(
                (char) DOM.eventGetKeyCode(event), DOM.eventGetCtrlKey(event),
                DOM.eventGetAltKey(event), DOM.eventGetShiftKey(event));

        if(!bubble) {
            DOM.eventPreventDefault(event);
        }

        return bubble;
    }
}
