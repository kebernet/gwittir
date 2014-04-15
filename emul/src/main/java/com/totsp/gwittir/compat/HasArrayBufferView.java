package com.totsp.gwittir.compat;

/**
 * This class allows us to get direct access to the typed array used by the
 * nio buffer emulation in GWT. All nio buffer types implement this
 * interfaces in GWT mode. This can be used only in HTML-platform
 * specific code.
 */
public interface HasArrayBufferView {
    /**
     * Returns the underlying typed array buffer view.
     */
    public ArrayBufferView getTypedArray ();

    /**
     * Returns the element size in bytes (e.g. 4 for a FloatBuffer and
     * 1 for a ByteBuffer).
     */
    int getElementSize ();

    /**
     * Returns the open GL element type constant corresponding to the buffer
     * contents.
     */
    int getElementType();
}