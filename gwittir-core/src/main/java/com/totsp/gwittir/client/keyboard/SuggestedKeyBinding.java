/*
 * SuggestedKeyBinding.java
 *
 * Created on October 22, 2007, 4:44 PM
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


/**
 *
 * @author rcooper
 */
public class SuggestedKeyBinding extends KeyBinding {
    /** Creates a new instance of SuggestedKeyBinding */
    public SuggestedKeyBinding(char key, boolean control, boolean alt, boolean shift) {
        super(key, control, alt, shift);
    }

    public boolean equals(Object o) {
        if (o instanceof SuggestedKeyBinding && (o != null)) {
            SuggestedKeyBinding b = (SuggestedKeyBinding) o;

            if ((b.alt == this.alt) && (b.control == this.control) && (b.shift == this.shift) && (b.key == this.key)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public int hashCode() {
        return super.hashCode() + 4096;
    }
}
