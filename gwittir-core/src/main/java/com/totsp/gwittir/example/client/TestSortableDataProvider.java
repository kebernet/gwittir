/*
 * TestSortableDataProvider.java
 *
 * Created on July 28, 2007, 6:07 PM
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

package com.totsp.gwittir.example.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.totsp.gwittir.client.ui.table.HasChunks;
import com.totsp.gwittir.client.ui.table.SortableDataProvider;
import com.totsp.gwittir.client.util.ListSorter;
import java.util.ArrayList;

/**
 *
 * @author cooper
 */
public class TestSortableDataProvider implements SortableDataProvider {
    
    ArrayList data = new ArrayList();
    int chunkSize = 10;
    int numberOfChunks;
    
    /** Creates a new instance of TestSortableDataProvider */
    public TestSortableDataProvider() {
        
        data.add(new Foo("Bacon", 1));
        data.add(new Foo("Egss", 2));
        data.add(new Foo("Hashbrowns", 3));
        data.add(new Foo("Toash", 4));
        data.add(new Foo("Sausage", 1));
        data.add(new Foo("Cereal", 2));
        data.add(new Foo("Melon", 3));
        data.add(new Foo("Biscuit", 4));
        data.add(new Foo("Waffle", 1));
        data.add(new Foo("Pancake", 2));
        data.add(new Foo("Ham", 3));
        data.add(new Foo("Bagel", 4));
        
        
        data.add(new Foo("Hamburger", 1));
        data.add(new Foo("Fries", 2));
        data.add(new Foo("Chicken Sandwich", 3));
        data.add(new Foo("Crab Cakes", 4));
        data.add(new Foo("Hot Dog", 1));
        data.add(new Foo("House Salad", 2));
        data.add(new Foo("Caesar Salad", 3));
        data.add(new Foo("Muffin", 4));
        data.add(new Foo("Fruit Plate", 1));
        data.add(new Foo("Macaroni and Cheese", 2));
        data.add(new Foo("Spinach", 3));
        data.add(new Foo("Okra", 4));
        
        data.add(new Foo("Squash", 1));
        data.add(new Foo("Carrots", 2));
        data.add(new Foo("Tater Tots", 3));
        data.add(new Foo("Mushrooms", 4));
        data.add(new Foo("Broccoli", 1));
        data.add(new Foo("Quiche", 2));
        data.add(new Foo("Fish and Chips", 3));
        data.add(new Foo("Cheese Burger", 4));
        data.add(new Foo("FO", 1));
        data.add(new Foo("Slawdog", 2));
        data.add(new Foo("Chili Cheese Dog", 3));
        data.add(new Foo("Chili Cheese Burger", 4));
        
        numberOfChunks = data.size() / chunkSize + ( data.size() % chunkSize > 0 ? 1 : 0);
        
    }
    
    public void getChunk(final HasChunks table, final int chunkNumber) {
         GWT.log( "getChunk fired "+ chunkNumber, null );
        final ArrayList thisChunk = new ArrayList();
        for( int i = chunkSize * chunkNumber;  i < data.size() && i < chunkSize * (chunkNumber+1); i++ ){
            thisChunk.add( data.get(i));
        }
        Timer t = new Timer(){
            public void run() {
                 GWT.log( "Calling setChunk", null );
                table.setChunk( thisChunk );
            }
            
        };
        t.schedule(1);
    }
    
    public void sortOnProperty(HasChunks table, String propertyName, boolean ascending) {
        try{
            ListSorter.sortOnProperty( data, propertyName, ascending);
        } catch(Exception e){
            GWT.log( "SORT ERROR!!!!!!", e);
        }
        GWT.log( "Sorted. ", null); 
        init( table );
    }
    
    public void init(final HasChunks table) {
        Timer t = new Timer(){
            public void run(){
                ArrayList firstChunk = new ArrayList();
                for( int i=0; i < chunkSize; i++){
                    firstChunk.add( data.get( i ) );
                }
                table.init( firstChunk, numberOfChunks );
            }
        };
        t.schedule( 1 );
        
    }
    
    public String[] getSortableProperties() {
        String[] props = { "stringProperty", "intProperty" };
        return props;
    }
    
}
