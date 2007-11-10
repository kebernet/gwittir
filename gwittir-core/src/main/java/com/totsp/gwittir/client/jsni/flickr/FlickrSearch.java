/*
 * FlickrSearch.java
 *
 * Created on November 9, 2007, 9:28 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
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
                if( evt.getPropertyName().equals("photos") ){
                    return;
                }
                update();
            }
        });
        update();
    }
    
    public String[] getTags() {
        return tags;
    }
    
    public void setTags(String[] tags) {
        String[] old = this.tags;
        this.tags = tags;
        this.changeSupport.firePropertyChange("tags", old, tags );
    }
    
    public String getUser() {
        return user;
    }
    
    public void setUser(String user) {
        this.user = user;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public List getPhotos() {
        return photos;
    }
    
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
