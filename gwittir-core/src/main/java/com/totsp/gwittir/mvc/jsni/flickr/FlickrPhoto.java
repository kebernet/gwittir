/*
 * FlickrPhoto.java
 *
 * Created on November 9, 2007, 9:29 PM
 */

package com.totsp.gwittir.util.jsni.flickr;

import com.totsp.gwittir.mvc.beans.AbstractModelBean;

/**
 * A data bean that represents a photo result from Flickr
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

    /**
     * Returns the title of the photo
     * @return Title of the Photo.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title.
     * @param title title of the photo.
     */
    public void setTitle(String title) {
        String old = this.title;
        this.title = title;
        this.changeSupport.firePropertyChange("title", old, title);
    }

    /**
     * Returns the URL to the thumbnail image.
     * @return URL to the thumbnail image.
     */
    public String getThumbnail() {
        return thumbnail;
    }

    /**
     * Sets the thumbnail URL.
     * @param thumbnail URL to the thumbnail image.
     */
    public void setThumbnail(String thumbnail) {
        String old = this.thumbnail;
        this.thumbnail = thumbnail;
        this.changeSupport.firePropertyChange("thumbnail", old, thumbnail );
    }

    /**
     * Returns the URL to the medium size image.
     * @return Returns the URL to the medium size image.
     */
    public String getMedium() {
        return medium;
    }

    /**
     * Sets Returns the URL to the medium size image.
     * 
     * Also this will set the values of the other image urls. Since the flickr
     * feed only returns the "m" photo, we do string manipulation here to determine 
     * the others.
     * @param medium URL to the medium size image
     */
    public void setMedium(String medium) {
        String old = this.medium;
        this.medium = medium;
        this.changeSupport.firePropertyChange("medium", old, medium );
        if( medium != null ){
            this.setThumbnail( medium.substring(0, medium.indexOf("_m") ) +"_t.jpg" );
            this.setNormal( medium.substring(0, medium.indexOf("_m") )+".jpg" );
        }
    }

    /**
     * Returns the URL to the "normal" size image
     * @return URL to the "normal" size image
     */
    public String getNormal() {
        return normal;
    }

    /**
     * Sets URL to the "normal" size image
     * @param normal URL to the "normal" size image
     */
    public void setNormal(String normal) {
        String old = this.normal;
        this.normal = normal;
        this.changeSupport.firePropertyChange("normal", old, normal );
    }

    /**
     * Returns the name of the Author.
     * @return  name of the Author.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets name of the Author.
     * @param author name of the Author.
     */
    public void setAuthor(String author) {
        String old = this.author;
        this.author = author;
        this.changeSupport.firePropertyChange("author", old, this.author );
    }
    
}
