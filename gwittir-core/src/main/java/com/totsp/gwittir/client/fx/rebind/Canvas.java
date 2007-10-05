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

import com.google.gwt.core.client.GWT;
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
    
    private Element canvas = DOM.createElement( "canvas" ); 
    
    /** Creates a new instance of Canvas */
    public Canvas() {
        super.setElement( canvas );
    }
    
    private static native JavaScriptObject getContext( Element canvas )
    /*-{  return canvas.getContext("2d"); }-*/;
    
    public Context getContext(){
        return new Context( this.getContext( this.canvas ) );
    }
    
    public static class Context {
        
        private JavaScriptObject context;
        
        private Context( JavaScriptObject context ){
            this.context = context;
        }
        
        private native void save(JavaScriptObject context)/*-{ context.save(); }-*/;
        
        public void save(){
            this.save(this.context);
        }
        
        private static native void translate(JavaScriptObject context, float x, float y)
        /*-{ context.translate( x, y ); }-*/;
                                                                     
        public void translate( float x, float y){
            this.translate( this.context, x, y);
        }
        
        private static native void rotate( JavaScriptObject context, float radians )
        /*-{ context.rotate( radians ); }-*/;
        
        public void rotate( float radians ){
            this.rotate( this.context, radians );
        }
        
        private static native void scale( JavaScriptObject context, float x, float y )
        /*-{ context.scale( x, y ); }-*/;
        
        public void scale( float x, float y){
            this.scale( this.context, x, y );
        }
        
        private static native void restore( JavaScriptObject context )
        /*-{ context.restore() }-*/;
        
        public void restore(){
            this.restore( this.context );
        }
        
        private static native void fill( JavaScriptObject context )
        /*-{ context.fill() }-*/;
        
        public void fill(){
            this.fill( this.context); 
        }
        
        
        private static native void drawImage( JavaScriptObject context, Element image, float x, float y, float width, float height )
        /*-{  context.drawImage( image, x, y, width, height ); }-*/;
        
        public void drawImage( Image image, float x, float y, float width, float height ){
            this.drawImage( this.context, image.getElement(), x, y, width, height);
        }
        
        private static native String getGlobalCompositeOperation(JavaScriptObject context)
        /*-{ return context.globalCompositeOperation == undefined ? null : context.globalCompositeOperation; }-*/; 
        
        public String getGlobalCompositeOperation(){
            return this.getGlobalCompositeOperation(this.context);
        }
        
        private static native void setGlobalcCompositeOperation(JavaScriptObject context, String value)
        /*-{ context.globalCompositeOperation = value; }-*/;
        
        public void setGlobalCompositeOperation(String value){
            this.setGlobalcCompositeOperation(this.context, value);
        }
        
        private static native JavaScriptObject createLinearGradient( JavaScriptObject context, int startX, int startY, int endX, int endY )
        /*-{ return context.createLinearGradient( startX, startY, endX, endY ); }-*/;
        
        public LinearGradient createLinearGradient( int startX, int startY, int endX, int endY ){
            return new LinearGradient( this.createLinearGradient( this.context, startX, startY, endX, endY ) );
        }
        
        private static native void setFillStyle( JavaScriptObject context, JavaScriptObject fillStyle )
        /*-{ context.fillStyle = fillStyle; }-*/;
        
        public void setFillStyle( FillStyle style ){
            this.setFillStyle( this.context, style.getNative() );
        }
        
        private static native void clearRect( JavaScriptObject context, int x, int y, int width, int height)
        /*-{ context.clearRect( x, y, width, height ); }-*/;
        
        public void clearRect( int x, int y, int width, int height ){
            this.clearRect( this.context, x, y, width, height );
        }
        
        private static native void fillRect( JavaScriptObject context, int x, int y, int width, int height )
        /*-{ context.fillRect( x, y, width, height ); }-*/;
        
        public void fillRect( int x, int y, int width, int height ){
            this.fillRect( this.context, x, y, width, height );
        }
    }
    
    public static interface FillStyle {
        public JavaScriptObject getNative();
    }
    
    public static class LinearGradient implements FillStyle {
        private JavaScriptObject gradient;
        
        private LinearGradient(JavaScriptObject gradient){
            this.gradient = gradient;
        }
        
        private static native void addColorStop( JavaScriptObject gradient, float position, String color )
        /*-{ gradient.addColorStop( position, color ); }-*/;
        
        public void addColorStop( float position, String color ){
            this.addColorStop(this.gradient, position, color);
        }
        
        public JavaScriptObject getNative(){
            return this.gradient;
        }
        
    }
    
}
