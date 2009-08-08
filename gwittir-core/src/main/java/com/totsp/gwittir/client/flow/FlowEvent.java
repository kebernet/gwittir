/*
 * FlowEvent.java
 *
 * Created on August 18, 2007, 3:36 PM
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
package com.totsp.gwittir.client.flow;

import com.google.gwt.user.client.ui.Widget;

import com.totsp.gwittir.client.ui.BoundWidget;


/** An even triggered whenever the FlowController changes activities.
 *
 * @author <a href="mailto:cooper@screaming-penguin.com">Robert "kebernet" Cooper</a>
 */
public class FlowEvent {
    private BoundWidget<?> fromWidget;
    private BoundWidget<?> toWidget;
    private FlowContext context;
    private Object fromModel;
    private Object toModel;
    private String fromName;
    private String toName;
    private Widget managedWidget;

    /** Creates a new instance of FlowEvent */
    public FlowEvent(
        FlowContext context, Widget managedWidget, BoundWidget<?> fromWidget, Object fromModel, String fromName,
        BoundWidget<?> toWidget, Object toModel, String toName) {
        this.context = context;
        this.fromWidget = fromWidget;
        this.fromModel = fromModel;
        this.fromName = fromName;
        this.toWidget = toWidget;
        this.toModel = toModel;
        this.toName = toName;
        this.managedWidget = managedWidget;
    }

    /**
     * Returns the FlowContext managing this event.
     * @return the FlowContext managing this event.
     */
    public FlowContext getContext() {
        return context;
    }

    /** The model that was used for the previous state.
     *
     * @return The model that was used for the previous state.
     */
    public Object getFromModel() {
        return fromModel;
    }

    /** The activity name form the previous state.
     *
     * @return The activity name form the previous state.
     */
    public String getFromName() {
        return fromName;
    }

    /** The BoundWidget from the previous state
     *
     * @return The BoundWidget from the previous state
     */
    public BoundWidget<?> getFromWidget() {
        return fromWidget;
    }

    public Widget getManagedWidget() {
        return this.managedWidget;
    }

    /** The model for the next state.
     *
     * @return The model for the new state.
     */
    public Object getToModel() {
        return toModel;
    }

    /** The activity name for the new state.
     *
     * @return The activity name for the new state.
     */
    public String getToName() {
        return toName;
    }

    /** The BoundWidget for the new State
     *
     * @return The BoundWidget for the new State
     */
    public BoundWidget<?> getToWidget() {
        return toWidget;
    }
}
