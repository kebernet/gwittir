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
 * A DataView is an ArrayBufferView with no concrete type to read and write
 * values. The DataView provides read and write methods for all types supported
 * by the {@link TypedArray}s.
 * 
 * @author Steffen Schäfer
 * 
 */
public class DataView extends ArrayBufferView {

	/**
	 * protected standard constructor as specified by {@link JavaScriptObject}.
	 */
	protected DataView() {
	}
	
	/**
	 * Constructs a new DataView instance using the given {@link ArrayBuffer}.
	 * 
	 * @param buffer the underlying {@link ArrayBuffer}.
	 * @return the newly created DataView
	 */
	public static native DataView create(ArrayBuffer buffer) /*-{
		return new DataView(buffer);
	}-*/;
	
	/**
	 * Constructs a new DataView instance using the given {@link ArrayBuffer} and byteOffset.
	 * 
	 * @param buffer the underlying {@link ArrayBuffer}.
	 * @param byteOffset the byteOffset of the DataView relative to the start of the underlying {@link ArrayBuffer}.
	 * @return the created DataView
	 */
	public static native DataView create(ArrayBuffer buffer, int byteOffset) /*-{
		return new DataView(buffer, byteOffset);
	}-*/;
	
	/**
	 * Constructs a new DataView instance using the given {@link ArrayBuffer}, byteOffset and length.
	 * 
	 * @param buffer the underlying {@link ArrayBuffer}.
	 * @param byteOffset the byteOffset of the DataView relative to the start of the underlying {@link ArrayBuffer}.
	 * @param length the length of the DataView in bytes.
	 * @return the created DataView
	 */
	public static native DataView create(ArrayBuffer buffer, int byteOffset, int length) /*-{
		return new DataView(buffer, byteOffset, length);
	}-*/;
	
	/**
	 * Reads a Int8 value at the given byteOffset.
	 * 
	 * @param byteOffset the byte offset from the start of the DataView.
	 * @return the Int8 value at the given byteOffset
	 */
	public final native int getInt8(int byteOffset) /*-{
		return this.getInt8(byteOffset);
	}-*/;
	
	/**
	 * Reads a Uint8 value at the given byteOffset.
	 * 
	 * @param byteOffset the byte offset from the start of the DataView.
	 * @return the Uint8 value at the given byteOffset
	 */
	public final native int getUint8(int byteOffset) /*-{
		return this.getUint8(byteOffset);
	}-*/;
	
	/**
	 * Reads a Int16 value at the given byteOffset.
	 * 
	 * @param byteOffset the byte offset from the start of the DataView.
	 * @return the Int16 value at the given byteOffset
	 */
	public final native int getInt16(int byteOffset) /*-{
		return this.getInt16(byteOffset);
	}-*/;
	
	/**
	 * Reads a Int16 value at the given byteOffset.
	 * 
	 * @param byteOffset the byte offset from the start of the DataView.
	 * @param littleEndian flag, if the value should be read as little endian.
	 * @return the Int16 value at the given byteOffset
	 */
	public final native int getInt16(int byteOffset, boolean littleEndian) /*-{
		return this.getInt16(byteOffset, littleEndian);
	}-*/;

	/**
	 * Reads a Uint16 value at the given byteOffset.
	 * 
	 * @param byteOffset the byte offset from the start of the DataView.
	 * @return the Uint16 value at the given byteOffset
	 */
	public final native int getUint16(int byteOffset) /*-{
		return this.getUint16(byteOffset);
	}-*/;
	
	/**
	 * Reads a Uint16 value at the given byteOffset.
	 * 
	 * @param byteOffset the byte offset from the start of the DataView.
	 * @param littleEndian flag, if the value should be read as little endian.
	 * @return the Uint16 value at the given byteOffset
	 */
	public final native int getUint16(int byteOffset, boolean littleEndian) /*-{
		return this.getUint16(byteOffset, littleEndian);
	}-*/;
	
	/**
	 * Reads a Int32 value at the given byteOffset.
	 * 
	 * @param byteOffset the byte offset from the start of the DataView.
	 * @return the Int32 value at the given byteOffset
	 */
	public final native int getInt32(int byteOffset) /*-{
		return this.getInt32(byteOffset);
	}-*/;
	
	/**
	 * Reads a Int32 value at the given byteOffset.
	 * 
	 * @param byteOffset the byte offset from the start of the DataView.
	 * @param littleEndian flag, if the value should be read as little endian.
	 * @return the Int32 value at the given byteOffset
	 */
	public final native int getInt32(int byteOffset, boolean littleEndian) /*-{
		return this.getInt32(byteOffset, littleEndian);
	}-*/;
	
	/**
	 * Reads a Uint32 value at the given byteOffset.
	 * 
	 * @param byteOffset the byte offset from the start of the DataView.
	 * @return the Uint32 value at the given byteOffset
	 */
	public final native int getUint32(int byteOffset) /*-{
		return this.getUint32(byteOffset);
	}-*/;
	
	/**
	 * Reads a Uint32 value at the given byteOffset.
	 * 
	 * @param byteOffset the byte offset from the start of the DataView.
	 * @param littleEndian flag, if the value should be read as little endian.
	 * @return the Uint32 value at the given byteOffset
	 */
	public final native int getUint32(int byteOffset, boolean littleEndian) /*-{
		return this.getUint32(byteOffset, littleEndian);
	}-*/;
	
	/**
	 * Reads a Float32 value at the given byteOffset.
	 * 
	 * @param byteOffset the byte offset from the start of the DataView.
	 * @return the Float32 value at the given byteOffset
	 */
	public final native float getFloat32(int byteOffset) /*-{
		return this.getFloat32(byteOffset);
	}-*/;
	
	/**
	 * Reads a Float32 value at the given byteOffset.
	 * 
	 * @param byteOffset the byte offset from the start of the DataView.
	 * @param littleEndian flag, if the value should be read as little endian.
	 * @return the Float32 value at the given byteOffset
	 */
	public final native float getFloat32(int byteOffset, boolean littleEndian) /*-{
		return this.getFloat32(byteOffset, littleEndian);
	}-*/;
	
	/**
	 * Reads a Float64 value at the given byteOffset.
	 * 
	 * @param byteOffset the byte offset from the start of the DataView.
	 * @return the Float64 value at the given byteOffset
	 */
	public final native double getFloat64(int byteOffset) /*-{
		return this.getFloat64(byteOffset);
	}-*/;
	
	/**
	 * Reads a Float64 value at the given byteOffset.
	 * 
	 * @param byteOffset the byte offset from the start of the DataView.
	 * @param littleEndian flag, if the value should be read as little endian.
	 * @return the Float64 value at the given byteOffset
	 */
	public final native double getFloat64(int byteOffset, boolean littleEndian) /*-{
		return this.getFloat64(byteOffset, littleEndian);
	}-*/;
	
	/**
	 * Writes a Int8 value at the given byteOffset.
	 * 
	 * @param byteOffset the byte offset from the start of the DataView.
	 * @param value the value to set
	 */
	public final native void setInt8(int byteOffset, int value) /*-{
		this.setInt8(byteOffset, value);
	}-*/;
	
	/**
	 * Writes a Uint8 value at the given byteOffset.
	 * 
	 * @param byteOffset the byte offset from the start of the DataView.
	 * @param value the value to set
	 */
	public final native void setUint8(int byteOffset, int value) /*-{
		this.setUint8(byteOffset, value);
	}-*/;
	
	/**
	 * Writes a Int16 value at the given byteOffset.
	 * 
	 * @param byteOffset the byte offset from the start of the DataView.
	 * @param value the value to set
	 */
	public final native void setInt16(int byteOffset, int value) /*-{
		this.setInt16(byteOffset, value);
	}-*/;
	
	/**
	 * Writes a Int16 value at the given byteOffset.
	 * 
	 * @param byteOffset the byte offset from the start of the DataView.
	 * @param littleEndian flag, if the value should be written as little endian.
	 * @param value the value to set
	 */
	public final native void setInt16(int byteOffset, int value, boolean littleEndian) /*-{
		this.setInt16(byteOffset, value, littleEndian);
	}-*/;
	
	/**
	 * Writes a Uint16 value at the given byteOffset.
	 * 
	 * @param byteOffset the byte offset from the start of the DataView.
	 * @param value the value to set
	 */
	public final native void setUint16(int byteOffset, int value) /*-{
		this.setUint16(byteOffset, value);
	}-*/;
	
	/**
	 * Writes a Uint16 value at the given byteOffset.
	 * 
	 * @param byteOffset the byte offset from the start of the DataView.
	 * @param littleEndian flag, if the value should be written as little endian.
	 * @param value the value to set
	 */
	public final native void setUint16(int byteOffset, int value, boolean littleEndian) /*-{
		this.setUint16(byteOffset, value, littleEndian);
	}-*/;

	/**
	 * Writes a Int32 value at the given byteOffset.
	 * 
	 * @param byteOffset the byte offset from the start of the DataView.
	 * @param value the value to set
	 */
	public final native void setInt32(int byteOffset, int value) /*-{
		this.setInt32(byteOffset, value);
	}-*/;
	
	/**
	 * Writes a Int32 value at the given byteOffset.
	 * 
	 * @param byteOffset the byte offset from the start of the DataView.
	 * @param littleEndian flag, if the value should be written as little endian.
	 * @param value the value to set
	 */
	public final native void setInt32(int byteOffset, int value, boolean littleEndian) /*-{
		this.setInt32(byteOffset, value, littleEndian);
	}-*/;

	/**
	 * Writes a Uint32 value at the given byteOffset.
	 * 
	 * @param byteOffset the byte offset from the start of the DataView.
	 * @param value the value to set
	 */
	public final native void setUint32(int byteOffset, int value) /*-{
		this.setUint32(byteOffset, value);
	}-*/;

	/**
	 * Writes a Uint32 value at the given byteOffset.
	 * 
	 * @param byteOffset the byte offset from the start of the DataView.
	 * @param littleEndian flag, if the value should be written as little endian.
	 * @param value the value to set
	 */
	public final native void setUint32(int byteOffset, int value, boolean littleEndian) /*-{
		this.setUint32(byteOffset, value, littleEndian);
	}-*/;
	
	/**
	 * Writes a Float32 value at the given byteOffset.
	 * 
	 * @param byteOffset the byte offset from the start of the DataView.
	 * @param value the value to set
	 */
	public final native void setFloat32(int byteOffset, float value) /*-{
		this.setFloat32(byteOffset, value);
	}-*/;
	
	/**
	 * Writes a Float32 value at the given byteOffset.
	 * 
	 * @param byteOffset the byte offset from the start of the DataView.
	 * @param littleEndian flag, if the value should be written as little endian.
	 * @param value the value to set
	 */
	public final native void setFloat32(int byteOffset, float value, boolean littleEndian) /*-{
		this.setFloat32(byteOffset, value, littleEndian);
	}-*/;
	
	/**
	 * Writes a Float64 value at the given byteOffset.
	 * 
	 * @param byteOffset the byte offset from the start of the DataView.
	 * @param value the value to set
	 */
	public final native void setFloat64(int byteOffset, double value) /*-{
		this.setFloat64(byteOffset, value);
	}-*/;
	
	/**
	 * Writes a Float64 value at the given byteOffset.
	 * 
	 * @param byteOffset the byte offset from the start of the DataView.
	 * @param littleEndian flag, if the value should be written as little endian.
	 * @param value the value to set
	 */
	public final native void setFloat64(int byteOffset, double value, boolean littleEndian) /*-{
		this.setFloat64(byteOffset, value, littleEndian);
	}-*/;

}
