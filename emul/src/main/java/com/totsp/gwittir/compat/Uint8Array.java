/* 
 * Copyright 2009-2010 Sönke Sothmann, Steffen Schäfer and others
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

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayInteger;


/**
 * @author Steffen Schäfer
 *
 */
public class Uint8Array extends IntBasedTypedArray<Uint8Array> {

	/**
	 * protected standard constructor as specified by {@link JavaScriptObject}.
	 */
	protected Uint8Array() {
	}

	/**
	 * Creates a new instance of the {@link TypedArray} of the given length in
	 * values. All values are set to 0.
	 * 
	 * @param length the length in values of the type used by this {@link TypedArray}
	 * @return the created {@link TypedArray}.
	 */
	public static native Uint8Array create(int length) /*-{
		return new Uint8Array(length);
	}-*/;

	/**
	 * Creates a new instance of the {@link TypedArray} of the same length as
	 * the given {@link TypedArray}. The values are set to the values of the
	 * given {@link TypedArray}.
	 * 
	 * @param array the {@link TypedArray} to get the values from
	 * @return the created {@link TypedArray}.
	 */
	public static native Uint8Array create(Uint8Array array) /*-{
		return new Uint8Array(array);
	}-*/;

	/**
	 * Creates a new instance of the {@link TypedArray} of the length of the
	 * given array in values. The values are set to the values of the given
	 * array.
	 * 
	 * @param array
	 *            the array to get the values from
	 * @return the created {@link TypedArray}.
	 */
	public static Uint8Array create(int[] array) {
		if(GWT.isScript()) {
			return innerCreate(array);
		}
		return innerCreate(JsArrayUtil.wrapArray(array));
	};
	
	private static native Uint8Array innerCreate(int[] array) /*-{
		return new Uint8Array(array);
	}-*/;
	
	private static native Uint8Array innerCreate(JsArrayInteger array) /*-{
		return new Uint8Array(array);
	}-*/;
	
	/**
	 * Creates a new instance of the {@link TypedArray} using the given
	 * {@link ArrayBuffer} to read/write values from/to.
	 * 
	 * @param buffer
	 *            the underlying {@link ArrayBuffer} of the newly created
	 *            {@link TypedArray}.
	 * @return the created {@link TypedArray}.
	 */
	public static native Uint8Array create(ArrayBuffer buffer) /*-{
		return new Uint8Array(buffer);
	}-*/;

	/**
	 * Creates a new instance of the {@link TypedArray} using the given
	 * {@link ArrayBuffer} to read/write values from/to.
	 * 
	 * The {@link TypedArray} is created using the byteOffset to specify the
	 * starting point (in bytes) of the {@link TypedArray} relative to the
	 * beginning of the underlying {@link ArrayBuffer}. The byte offset must
	 * match (multiple) the value length of this {@link TypedArray}.
	 * 
	 * if the byteLength is not valid for the given {@link ArrayBuffer}, an
	 * exception is thrown
	 * 
	 * @param buffer
	 *            the underlying {@link ArrayBuffer} of the newly created
	 *            {@link TypedArray}.
	 * @param byteOffset
	 *            the offset relative to the beginning of the ArrayBuffer
	 *            (multiple of the value length of this {@link TypedArray})
	 * @return the newly created {@link TypedArray}.
	 */
	public static native Uint8Array create(ArrayBuffer buffer, int byteOffset) /*-{
		return new Uint8Array(buffer, byteOffset);
	}-*/;
	
	/**
	 * Creates a new instance of the {@link TypedArray} using the given
	 * {@link ArrayBuffer} to read/write values from/to.
	 * 
	 * The {@link TypedArray} is created using the byteOffset and length to specify the
	 * start and end (in bytes) of the {@link TypedArray} relative to the
	 * beginning of the underlying {@link ArrayBuffer}. The byte offset must
	 * match (multiple) the value length of this {@link TypedArray}.
	 * The length is in values of the type of the {@link TypedArray}
	 * 
	 * if the byteLength or length is not valid for the given {@link ArrayBuffer}, an
	 * exception is thrown
	 * 
	 * @param buffer
	 *            the underlying {@link ArrayBuffer} of the newly created
	 *            {@link TypedArray}.
	 * @param byteOffset
	 *            the offset relative to the beginning of the ArrayBuffer
	 *            (multiple of the value length of this {@link TypedArray})
	 * @param length the length of the {@link TypedArray} in vales.
	 * @return the newly created {@link TypedArray}.
	 */
	public static native Uint8Array create(ArrayBuffer buffer, int byteOffset, int length) /*-{
		return new Uint8Array(buffer, byteOffset, length);
	}-*/;

}
