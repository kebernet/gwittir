/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.totsp.gwittir.client.ui.media;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Widget;

import java.util.Date;
import java.util.LinkedList;


/**
 * http://www.w3.org/TR/html5/video.html#media-elements
 *
 * @author robert.cooper
 */
public abstract class Media extends Widget {
    private final Element element;

    public Media() {
        this.setElement(element = createElement());
    }

    protected abstract Element createElement();

    public void setControls(boolean controls) {
        setControls(element, controls);
    }

    public boolean isControls() {
        return isControls(element);
    }

    public void setCurrentTime(double currentTime) {
        setCurrentTime(element, currentTime);
    }

    public double getCurrentTime() {
        return getCurrentTime(element);
    }

    public double getDefaultPlaybackRate() {
        return getDefaultPlaybackRate(element);
    }

    public boolean isEnded() {
        return isEnded(element);
    }

    public void setLoop(boolean loop) {
        setLoop(element, loop);
    }

    public boolean getLoop() {
        return getLoop(element);
    }

    public void setMuted(boolean muted) {
        setMuted(element, muted);
    }

    public boolean isMuted() {
        return isMuted(element);
    }

    public boolean isPaused() {
        return isPaused(element);
    }

    public TimeRanges getPlayed() {
        return getPlayed(element);
    }

    public TimeRanges getSeekable() {
        return getSeekable(element);
    }

    public void setSource(String url) {
        this.element.setAttribute("src", url);
    }

    public String getSource() {
        return this.element.getAttribute("src");
    }

    public void setVolume(int volume) {
        setVolume(element, volume);
    }

    public int getVolume() {
        return getVolume(element);
    }

    public void load() {
        load(element);
    }

    public void play() {
        play(element);
    }

    public Date startOffsetTime() {
        throw new UnsupportedOperationException("TODO: Implement this");
    }

    protected static native void setControls(Element e, boolean controls) /*-{
    e.controls = controls;
    }-*/;

    protected static native boolean isControls(Element e) /*-{
    return e.controls;
    }-*/;

    protected static native void setCurrentTime(Element e, double currentTime) /*-{
    e.currentTime = currentTime;
    }-*/;

    protected static native double getCurrentTime(Element e) /*-{
    return e.currentTime;
    }-*/;

    protected static native double getDefaultPlaybackRate(Element e) /*-{
    return e.defaultPlaybackRate;
    }-*/;

    protected static native boolean isEnded(Element e) /*-{
    return e.ended;
    }-*/;

    protected static native void setLoop(Element e, boolean loop) /*-{
    e.loop = loop;
    }-*/;

    protected static native boolean getLoop(Element e) /*-{
    return e.loop;
    }-*/;

    protected static native void setMuted(Element e, boolean muted) /*-{
    e.muted = muted;;
    }-*/;

    protected static native boolean isMuted(Element e) /*-{
    return e.muted;
    }-*/;

    protected static native boolean isPaused(Element e) /*-{
    return e.paused;
    }-*/;

    protected static native TimeRanges getPlayed(Element e) /*-{
    return e.played
    }-*/;

    protected static native TimeRanges getSeekable(Element e) /*-{
    return e.seekable
    }-*/;

    protected static native void setVolume(Element e, int currentTime) /*-{
    e.volume = volume;
    }-*/;

    protected static native int getVolume(Element e) /*-{
    return e.volume;
    }-*/;

    protected static native void load(Element e) /*-{
    e.load();
    }-*/;

    protected static native void play(Element e) /*-{
    e.play();
    }-*/;
}
