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

import com.totsp.gwittir.client.ui.BoundWidget;


/**
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

    /** Creates a new instance of FlowEvent */
    public FlowEvent(FlowContext context, BoundWidget<?> fromWidget,
        Object fromModel, String fromName, BoundWidget<?> toWidget,
        Object toModel, String toName) {
        this.context = context;
        this.fromWidget = fromWidget;
        this.fromModel = fromModel;
        this.fromName = fromName;
        this.toWidget = toWidget;
        this.toModel = toModel;
        this.toName = toName;
    }

    public FlowContext getContext() {
        return context;
    }

    public Object getFromModel() {
        return fromModel;
    }

    public String getFromName() {
        return fromName;
    }

    public BoundWidget<?> getFromWidget() {
        return fromWidget;
    }

    public Object getToModel() {
        return toModel;
    }

    public String getToName() {
        return toName;
    }

    public BoundWidget<?> getToWidget() {
        return toWidget;
    }
}
