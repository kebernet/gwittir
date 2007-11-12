/*
 * FlickrSearch.java
 *
 * Created on November 9, 2007, 9:28 PM
 *
 */

package com.totsp.gwittir.client.jsni.flickr;

import com.totsp.gwittir.client.beans.AbstractModelBean;
import com.totsp.gwittir.client.jsni.JSONCallback;
import com.totsp.gwittir.client.jsni.JSONServiceInvoker;
import com.totsp.gwittir.client.jsni.JavaScriptObjectDecorator;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

/**
 * A bean that performs JSON-P calls the Flickr Feeds API. Any changes to the 
 * parameters (tags, user), will update the search result fields (title, phots). 
 * 
 * @author rcooper
 */
public class FlickrSearch extends AbstractModelBean {
    
    private String[] tags;
    private String user;
    private String title;
    private List photos = new ArrayList();
    
    /** Creates a new instance of FlickrSearch */
    public FlickrSearch() {
        this.addPropertyChangeListener( new PropertyChangeListener(){
            public void propertyChange(PropertyChangeEvent evt) {
                if( evt.getPropertyName().equals("photos") || evt.getPropertyName().equals("title")){
                    return;
                }
                update();
            }
        });
        update();
    }
    
    /**
     * Returns the current set of search tags.
     * @return the current set of search tags.
     */
    public String[] getTags() {
        return tags;
    }
    
    /**
     * Sets the search tags.
     * @param tags String[] containing tags to search for.
     */
    public void setTags(String[] tags) {
        String[] old = this.tags;
        this.tags = tags;
        this.changeSupport.firePropertyChange("tags", old, tags );
    }
    
    /**
     * Returns the current user search ID
     * @return Flickr ID of the user to search
     */
    public String getUser() {
        return user;
    }
    
    /**
     * Sets Flickr ID of the user to search
     * @param user Flickr ID of the user to search
     */
    public void setUser(String user) {
        this.user = user;
    }
    
    /**
     * Returns the title of the current set of search results.
     * @return title of the current set of search results.
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * Sets the title of the current set of search results.
     * @param title the title of the current set of search results.
     */
    public void setTitle(String title) {
        String old = this.title;
        this.title = title;
        this.changeSupport.firePropertyChange("title", old, title );
    }
    
    /**
     * Returns a List of FlickrPhoto objects that are the results of the last search 
     * returned.
     * @return a List of FlickrPhoto objects that are the results of the last search 
     * returned.
     */
    public List getPhotos() {
        return photos;
    }
    
    /**
     * Sets the list of result photos.
     * @param photos the list of result photos.
     */
    public void setPhotos(List photos) {
        List old = this.photos;
        this.photos = photos;
        this.changeSupport.firePropertyChange("photos", old, photos );
    }
    
    private void update(){
        String url = "http://api.flickr.com/services/feeds/photos_public.gne?lang=en-us&format=json&";
        if( tags != null ){
            url+="tags=";
            for( int i=0; i < tags.length; i++ ){
                url+=tags[i]+",";
            }
            url+="&";
        }
        if( this.user != null ){
            url +="id="+this.user+"&";
        }
        JSONServiceInvoker.invoke(url, "jsonFlickrFeed", new JSONCallback(){
            public void onJSONResult(JavaScriptObjectDecorator decorator) {
                setTitle( decorator.getStringProperty("title") );
                JavaScriptObjectDecorator items = decorator.getJavaScriptObjectProperty("items");
                List photos = new ArrayList();
                for( int i=0; i < items.getIntProperty("length"); i++ ){
                    JavaScriptObjectDecorator item = items.getJavaScriptObjectProperty(i+"");
                    FlickrPhoto p = new FlickrPhoto();
                    p.setTitle(item.getStringProperty("title") );
                    p.setMedium( item.getJavaScriptObjectProperty("media").getStringProperty("m"));
                    String author = item.getStringProperty("author");
                    author = author.substring( author.indexOf("(") + 1, author.lastIndexOf(")") );
                    p.setAuthor(author);
                    photos.add( p );
                }
                setPhotos(photos);
            }
            
        });
    }
    
}
