/*
 * ReflectedImageGroup.java
 *
 * Created on November 7, 2007, 8:43 PM
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

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.totsp.gwittir.client.ui.ToStringRenderer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 *
 * @author cooper
 */
public class ReflectedFisheyeImageGroup extends Composite {
    
    Collection value;
    double maxScalar = 1.5D;
    Image[] images;
    int baseWidth;
    int baseHeight;
    double reflectHeight;
    double opacity;
    HorizontalPanel imagesPanel = new HorizontalPanel();
   //FocusPanel fp = new FocusPanel();
    
    /** Creates a new instance of ReflectedImageGroup */
    public ReflectedFisheyeImageGroup(final int baseWidth,
            final int baseHeight, final double reflectHeight, final double opacity) {
        this.baseWidth = baseWidth;
        this.baseHeight = baseHeight;
        this.reflectHeight = reflectHeight;
        this.opacity = opacity;
        this.imagesPanel.setStyleName("gwittir-ImagesPanel");
        this.imagesPanel.setVerticalAlignment( HasVerticalAlignment.ALIGN_MIDDLE );
        super.initWidget( imagesPanel );
        //fp.setWidget( imagesPanel );
        imagesPanel.setWidth( "100%");
        imagesPanel.setHorizontalAlignment( HasHorizontalAlignment.ALIGN_CENTER );
        
        /*fp.addMouseListener( new MouseListenerAdapter(){
            public void onMouseMove(Widget sender, int x, int y) {
                LOG.log( Level.SPAM, x +" / "+ fp.getOffsetWidth(), null );
                int activeImage = Math.round( (float) images.length * ( (float)x/(float) fp.getOffsetWidth() ) );
                LOG.log( Level.SPAM, "activeImage "+activeImage, null);
                for( int i=0; i < images.length; i++ ){
                    
                    int distance = Math.abs( i - activeImage );
                    GWT.log(  "distance "+distance, null);
                    double imageModifier = maxScalar * ((10D - distance)/ 10D);
                    if( imageModifier < 1D ){
                        imageModifier = 1D;
                    }
                    GWT.log(  i+" imageModifier " + imageModifier,null);
                    if( imageModifier > maxScalar ){
                        imageModifier = maxScalar;
                    }
                    int width = (int) Math.round( imageModifier * baseWidth );
                    int height = (int) Math.round( imageModifier * baseHeight );
                    images[(int) i].setPixelSize( width, height );
                }
                
            }
            
        });*/
    }
    
    
    public Object getValue() {
        return this.value;
    }
    
    public void setValue(Object value) {
        this.value = (Collection) value;
        //this.changes.firePropertyChange( "value", null, value );
        this.render( this.value == null ? new ArrayList() : this.value );
    }
    
    protected void render(Collection value){
        this.images = new Image[ value.size() ];
        this.imagesPanel.clear();
        Iterator it = value.iterator();
        for( int i=0; it.hasNext(); i++ ){
            this.images[i] = new Image( (String) it.next().toString() );
            images[i].setPixelSize( this.baseWidth, this.baseHeight );
            /*new ReflectedImage(
                    this.getRenderer().render( it.next() ).toString(),
                    this.baseWidth, this.baseHeight,
                    this.reflectHeight, this.opacity );*/
            this.imagesPanel.add( this.images[i] );
        }
        
    }
    
    
}
