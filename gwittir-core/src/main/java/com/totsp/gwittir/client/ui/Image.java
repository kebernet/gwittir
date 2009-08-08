/*
 * Image.java
 *
 * Created on November 11, 2007, 2:46 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.totsp.gwittir.client.ui;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.LoadListener;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.MouseWheelListener;
import com.google.gwt.user.client.ui.SourcesClickEvents;
import com.google.gwt.user.client.ui.SourcesLoadEvents;
import com.google.gwt.user.client.ui.SourcesMouseEvents;
import com.google.gwt.user.client.ui.SourcesMouseWheelEvents;


/**
 *
 * @author rcooper
 */
public class Image extends AbstractBoundWidget<String> implements SourcesClickEvents, SourcesLoadEvents,
    SourcesMouseEvents, SourcesMouseWheelEvents {
    private com.google.gwt.user.client.ui.Image base = new com.google.gwt.user.client.ui.Image();

    /** Creates a new instance of Image */
    public Image() {
        super.initWidget(base);
        this.setStyleName("gwittir-Image");
    }

    public Image(String value) {
        this();
        this.setValue(value);
    }

    public int getOriginLeft() {
        return this.base.getOriginLeft();
    }

    public int getOriginTop() {
        return this.base.getOriginTop();
    }

    public void setPixelHeight(int height) {
        int old = this.base.getHeight();
        this.base.setPixelSize(this.base.getWidth(), height);
        this.changes.firePropertyChange("pixelHeight", old, height);
    }

    public int getPixelHeight() {
        return this.base.getHeight();
    }

    public void setPixelSize(int width, int height) {
        this.base.setPixelSize(width, height);
    }

    public void setPixelWidth(int width) {
        int old = this.base.getWidth();
        this.base.setPixelSize(width, this.base.getHeight());
        this.changes.firePropertyChange("pixelWidth", old, width);
    }

    public int getPixelWidth() {
        return this.base.getWidth();
    }

    public void setValue(String value) {
        Object old = base.getUrl();
        base.setUrl(value);
        this.changes.firePropertyChange("value", old, value);
    }

    public String getValue() {
        return base.getUrl();
    }

    public void setVisibleRect(int left, int top, int width, int height) {
        this.base.setVisibleRect(left, top, width, height);
    }

    public void addClickListener(ClickListener listener) {
        this.base.addClickListener(listener);
    }

    public void addLoadListener(LoadListener listener) {
        this.base.addLoadListener(listener);
    }

    public void addMouseListener(MouseListener listener) {
        this.base.addMouseListener(listener);
    }

    public void addMouseWheelListener(MouseWheelListener listener) {
        this.base.addMouseWheelListener(listener);
    }

    public void removeClickListener(ClickListener listener) {
        this.base.removeClickListener(listener);
    }

    public void removeLoadListener(LoadListener listener) {
        this.base.removeLoadListener(listener);
    }

    public void removeMouseListener(MouseListener listener) {
        this.base.removeMouseListener(listener);
    }

    public void removeMouseWheelListener(MouseWheelListener listener) {
        this.base.removeMouseWheelListener(listener);
    }
}
