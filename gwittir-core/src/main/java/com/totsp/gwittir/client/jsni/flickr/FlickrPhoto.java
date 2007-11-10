/*
 * FlickrPhoto.java
 *
 * Created on November 9, 2007, 9:29 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.totsp.gwittir.client.jsni.flickr;

import com.totsp.gwittir.client.beans.AbstractModelBean;

/**
 *
 * @author rcooper
 */
public class FlickrPhoto extends AbstractModelBean {
    
    private String title;
    private String thumbnail;
    private String medium;
    private String normal;
    private String author;
   
    
    /** Creates a new instance of FlickrPhoto */
    public FlickrPhoto() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        String old = this.title;
        this.title = title;
        this.changeSupport.firePropertyChange("title", old, title);
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        String old = this.thumbnail;
        this.thumbnail = thumbnail;
        this.changeSupport.firePropertyChange("thumbnail", old, thumbnail );
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        String old = this.medium;
        this.medium = medium;
        this.changeSupport.firePropertyChange("medium", old, medium );
        if( medium != null ){
            this.setThumbnail( medium.substring(0, medium.indexOf("_m") ) +"_t.jpg" );
            this.setNormal( medium.substring(0, medium.indexOf("_m") )+".jpg" );
        }
    }

    public String getNormal() {
        return normal;
    }

    public void setNormal(String normal) {
        String old = this.normal;
        this.normal = normal;
        this.changeSupport.firePropertyChange("normal", old, normal );
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        String old = this.author;
        this.author = author;
        this.changeSupport.firePropertyChange("author", old, this.author );
    }
    
}
