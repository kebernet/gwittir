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
 * 
 * An ArrayBufferView is used to access (read/write) the contents of an
 * ArrayBuffer.
 * 
 * @author Steffen Schäfer
 * 
 */
public abstract class ArrayBufferView extends JavaScriptObject {

	/**
	 * protected standard constructor as specified by {@link JavaScriptObject}.
	 */
	protected ArrayBufferView() {
	}
    
    /**
     * Returns the underlying {@link ArrayBuffer} of this ArrayBufferView.
     * 
     * @return the underlying {@link ArrayBuffer} of this ArrayBufferView.
     */
    public final native ArrayBuffer getBuffer() /*-{
		return this.buffer;
	}-*/;
    
    /**
     * Returns the non changeable byteOffset of the ArrayBufferView inside of the used {@link ArrayBuffer}.
     * 
     * @return the non changeable byteOffset of the ArrayBufferView inside of the used {@link ArrayBuffer}.
     */
    public final native int getByteOffset() /*-{
		return this.byteOffset;
	}-*/;
	
	/**
	 * Returns the non changeable length of the ArrayBufferView in bytes.
	 * 
	 * @return the non changeable length of the ArrayBufferView in bytes.
	 */
	public final native int getByteLength() /*-{
		return this.byteLength;
	}-*/;

}
