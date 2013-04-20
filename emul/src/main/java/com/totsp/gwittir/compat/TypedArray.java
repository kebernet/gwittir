/*   
 * Copyright 2009-2011 Sönke Sothmann, Steffen Schäfer and others
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.totsp.gwittir.compat;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * A TypedArray is an ArrayBufferView that reads and writes value of one
 * specific type to/from an {@link ArrayBuffer}.
 * 
 * @author Steffen Schäfer
 * 
 * @param <T>
 *            the concrete subtype of the TypedArray itself. Used for methods
 *            using the type of the TypedArray as Parameter or return value.
 */
public abstract class TypedArray<T extends TypedArray<T>> extends ArrayBufferView {

	/**
	 * protected standard constructor as specified by {@link JavaScriptObject}.
	 */
	protected TypedArray() {
	}
	
	/**
	 * Returns the number of values of the array type contained in the array.
	 * 
	 * @return the number of values of the array type contained in the array.
	 */
	public final native int getLength() /*-{
		return this.length;
	}-*/;
	
	/**
	 * Set multiple values, of the given array to this array.
	 * 
	 * @param array the array to get the values from
	 */
	public final native void set(T array)/*-{
		return this.set(array);
	}-*/;

	/**
	 * Set multiple values, of the given array to this array starting at the
	 * given offset.
	 * 
	 * @param array
	 *            the array to get the values from
	 * @param offset
	 *            the offset to start setting the values
	 */
	public final native void set(T array, int offset)/*-{
		return this.set(array, offset);
	}-*/;

	/**
	 * Returns a new {@link TypedArray} with the same underlying
	 * {@link ArrayBuffer}.
	 * 
	 * @param begin
	 *            the beginning offset of the new {@link TypedArray} from the
	 *            start of this {@link TypedArray}. If the value is negative,
	 *            it's the offset from the end of this {@link TypedArray}.
	 * @return the new Array
	 */
	public final native T subarray(int begin)/*-{
		return this.subarray(begin);
	}-*/;
	
	/**
	 * Returns a new {@link TypedArray} with the same underlying
	 * {@link ArrayBuffer}.
	 * 
	 * @param begin
	 *            the beginning offset of the new {@link TypedArray} from the
	 *            start of this {@link TypedArray}. If the value is negative,
	 *            it's the offset from the end of this {@link TypedArray}.
	 * @param end
	 *            the end offset (exclusive). If the value is negative, it's the
	 *            offset from the end of this {@link TypedArray}.
	 * @return the new Array
	 */
	public final native T subarray(int begin, int end)/*-{
		return this.subarray(begin, length);
	}-*/;

}
