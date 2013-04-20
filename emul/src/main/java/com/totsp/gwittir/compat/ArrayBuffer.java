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
 * The ArrayBuffer is buffer to the data of {@link TypedArray}s.
 * 
 * @author Steffen Schäfer
 *
 */
public class ArrayBuffer extends JavaScriptObject {
	
	/**
	 * protected standard constructor as specified by {@link JavaScriptObject}.
	 */
	protected ArrayBuffer() {
	}

	/**
	 * Constructs a new ArrayBuffer instance. The newly created ArrayBuffer has
	 * the given length in bytes. The ArrayBuffer is initialized with 0 values.
	 * 
	 * @param length
	 *            the byte length of the newly created ArrayBuffer
	 * @return the created ArrayBuffer
	 */
	public static native ArrayBuffer create(int length) /*-{
		return new ArrayBuffer(length);
	}-*/;
	
	/**
	 * Returns the non changeable length of the ArrayBuffer in bytes.
	 * 
	 * @return the non changeable length of the ArrayBuffer in bytes.
	 */
	public final native int getByteLength() /*-{
		return this.byteLength;
	}-*/;

}
