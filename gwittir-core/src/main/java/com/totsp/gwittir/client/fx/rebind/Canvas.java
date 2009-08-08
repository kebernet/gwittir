/*
 * Canvas.java
 *
 * Created on August 15, 2007, 12:05 PM
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
package com.totsp.gwittir.client.fx.rebind;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;


/**
 *
 * @author <a href="mailto:cooper@screaming-penguin.com">Robert "kebernet" Cooper</a>
 */
public class Canvas extends Widget {
    private Element canvas = DOM.createElement("canvas");

    /** Creates a new instance of Canvas */
    public Canvas() {
        super.setElement(canvas);
    }

    public Context getContext() {
        return new Context(this.getContext(this.canvas));
    }

    private static native JavaScriptObject getContext(Element canvas);

    public static interface FillStyle {
        public JavaScriptObject getNative();
    }

    public static class Context {
        private JavaScriptObject context;

        private Context(JavaScriptObject context) {
            this.context = context;
        }

        public void setFillStyle(FillStyle style) {
            this.setFillStyle(this.context, style.getNative());
        }

        public void setGlobalCompositeOperation(String value) {
            this.setGlobalcCompositeOperation(this.context, value);
        }

        public String getGlobalCompositeOperation() {
            return this.getGlobalCompositeOperation(this.context);
        }

        public void clearRect(int x, int y, int width, int height) {
            this.clearRect(this.context, x, y, width, height);
        }

        public LinearGradient createLinearGradient(int startX, int startY, int endX, int endY) {
            return new LinearGradient(this.createLinearGradient(this.context, startX, startY, endX, endY));
        }

        public void drawImage(Image image, float x, float y, float width, float height) {
            this.drawImage(this.context, image.getElement(), x, y, width, height);
        }

        public void fill() {
            this.fill(this.context);
        }

        public void fillRect(int x, int y, int width, int height) {
            this.fillRect(this.context, x, y, width, height);
        }

        public void restore() {
            this.restore(this.context);
        }

        public void rotate(float radians) {
            this.rotate(this.context, radians);
        }

        public void save() {
            this.save(this.context);
        }

        public void scale(float x, float y) {
            this.scale(this.context, x, y);
        }

        public void translate(float x, float y) {
            this.translate(this.context, x, y);
        }

        private static native void setFillStyle(JavaScriptObject context, JavaScriptObject fillStyle);

        private static native String getGlobalCompositeOperation(JavaScriptObject context);

        private static native void setGlobalcCompositeOperation(JavaScriptObject context, String value);

        private static native void clearRect(JavaScriptObject context, int x, int y, int width, int height);

        private static native JavaScriptObject createLinearGradient(
            JavaScriptObject context, int startX, int startY, int endX, int endY);

        private static native void drawImage(
            JavaScriptObject context, Element image, float x, float y, float width, float height);

        private static native void fill(JavaScriptObject context);

        private static native void fillRect(JavaScriptObject context, int x, int y, int width, int height);

        private static native void restore(JavaScriptObject context);

        private static native void rotate(JavaScriptObject context, float radians);

        private native void save(JavaScriptObject context) /*-{ context.save(); }-*/;

        private static native void scale(JavaScriptObject context, float x, float y);

        private static native void translate(JavaScriptObject context, float x, float y);
    }

    public static class LinearGradient implements FillStyle {
        private JavaScriptObject gradient;

        private LinearGradient(JavaScriptObject gradient) {
            this.gradient = gradient;
        }

        public JavaScriptObject getNative() {
            return this.gradient;
        }

        public void addColorStop(float position, String color) {
            this.addColorStop(this.gradient, position, color);
        }

        private static native void addColorStop(JavaScriptObject gradient, float position, String color);
    }
}
