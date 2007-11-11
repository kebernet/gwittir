/*
 * ReflectedImage.java
 *
 * Created on August 15, 2007, 1:39 PM
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
package com.totsp.gwittir.client.fx.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.LoadListener;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.SourcesMouseEvents;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import com.totsp.gwittir.client.fx.rebind.Reflection;
import com.totsp.gwittir.client.ui.AbstractBoundWidget;


/**
 *
 * @author <a href="mailto:cooper@screaming-penguin.com">Robert "kebernet" Cooper</a>
 */
public class ReflectedImage extends AbstractBoundWidget implements SourcesMouseEvents {
    private Image base;
    private Object value;
    private Reflection reflect = (Reflection) GWT.create(Reflection.class);
    private VerticalPanel v = new VerticalPanel();
    private double opacity;
    private double reflectHeight;
    private int baseHeight;
    private int baseWidth;

    /** Creates a new instance of ReflectedImage */
    public ReflectedImage(String url, final int baseWidth,
        final int baseHeight, final double reflectHeight, final double opacity) {
        this.value = url;
        this.base = new Image(url);
        this.base.addLoadListener(new LoadListener() {
                public void onLoad(Widget sender) {
                    reflect.paint(base, baseWidth, baseHeight, reflectHeight,
                        opacity);
                    v.add(reflect);
                }

                public void onError(Widget sender) {
                }
            });
        this.base.setPixelSize(baseWidth, baseHeight);
        this.baseWidth = baseWidth;
        this.baseHeight = baseHeight;
        this.reflectHeight = reflectHeight;
        this.opacity = opacity;
        v.add(base);
        v.setHorizontalAlignment( HasHorizontalAlignment.ALIGN_CENTER);
        super.initWidget(v);
    }
    
    public ReflectedImage( final int baseWidth,
        final int baseHeight, final double reflectHeight, final double opacity) {
        this.base = new Image();
        this.base.addLoadListener(new LoadListener() {
                public void onLoad(Widget sender) {
                    reflect.paint(base, baseWidth, baseHeight, reflectHeight,
                        opacity);
                    v.add(reflect);
                }

                public void onError(Widget sender) {
                }
            });
        this.base.setPixelSize(baseWidth, baseHeight);
        this.baseWidth = baseWidth;
        this.baseHeight = baseHeight;
        this.reflectHeight = reflectHeight;
        this.opacity = opacity;
        v.add(base);
        v.setHorizontalAlignment( HasHorizontalAlignment.ALIGN_CENTER);
        super.initWidget(v);
    }

    public void setPixelSize(int width, int height){
        this.setWidth( width );
        this.setHeight( height );
    }
    
    public int getHeight() {
        return this.base.getHeight();
    }

    public String getUrl() {
        return this.base.getUrl();
    }

    public Object getValue() {
        return this.value;
    }

    public int getWidth() {
        return this.base.getWidth();
    }

    public void setHeight(int height) {
        int old = this.base.getHeight();
        this.base.setHeight(height + "px");
        this.changes.firePropertyChange("height", old, height);
        this.reflect.paint(this.base, this.getWidth(), height,
            this.reflectHeight, this.opacity);
    }

    public void setUrl(String url) {
        this.base.setUrl(url);
        String old = this.base.getUrl();
        this.changes.firePropertyChange("url", old, url);
    }

    public void setValue(Object newValue) {
        Object old = this.value;
        this.value = newValue;
        this.setUrl((String) this.getRenderer().render(newValue));
        this.reflect.paint(this.base, this.getWidth(), this.getHeight(),
            this.reflectHeight, this.opacity);
        this.changes.firePropertyChange("value", old, this.value);
    }

    public void setWidth(int width) {
        int old = this.base.getWidth();
        this.base.setWidth(width + "px");
        this.changes.firePropertyChange("width", old, width);
        this.reflect.paint(this.base, width, this.getHeight(),
            this.reflectHeight, this.opacity);
    }

    public void addMouseListener(MouseListener listener) {
        this.base.addMouseListener( listener );
    }

    public void removeMouseListener(MouseListener listener) {
        this.base.addMouseListener( listener );
    }
}
