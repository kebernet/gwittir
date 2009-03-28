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

package com.totsp.gwittir.client.util.domstorage;

/**
 * Interface matching the DOMStorage interface:
 * interface Storage {
 * readonly attribute unsigned long length;
 * [IndexGetter] DOMString key(in unsigned long index);
 * [NameGetter] DOMString getItem(in DOMString key);
 * [NameSetter] void setItem(in DOMString key, in DOMString data);
 * [XXX] void removeItem(in DOMString key);
 * void clear();
 * };
 * @author kebernet
 */
public interface Storage {

    String get(String key);
    int length();
    void clear();
    String key(int index);
    void remove(String key);
}
