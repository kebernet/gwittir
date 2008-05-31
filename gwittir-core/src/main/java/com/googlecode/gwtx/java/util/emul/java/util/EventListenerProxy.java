/*
 * Copyright 2006 Sandy McArthur, Jr.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package java.util;

/**
 * An abstract wrapper class for an EventListener class which associates a set of additional
 * parameters with the listener.
 *
 * @see <a href="http://java.sun.com/j2se/1.5.0/docs/api/java/util/EventListenerProxy.html">Sun's Javadocs</a>
 */
public abstract class EventListenerProxy implements EventListener {
    private final EventListener listener;

    public EventListenerProxy(final EventListener listener) {
        this.listener = listener;
    }

    public EventListener getListener() {
        return listener;
    }
}
