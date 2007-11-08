/*
 * Calendar.java
 *
 * Created on October 31, 2007, 7:18 PM
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

package com.totsp.gwittir.client.ui.calendar;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TableListener;
import com.google.gwt.user.client.ui.Widget;
import com.totsp.gwittir.client.ui.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author cooper
 */
public class Calendar extends AbstractBoundWidget implements SourcesCalendarEvents, SourcesCalendarDrawEvents {
    private static final CalendarConstants STRINGS =
            (CalendarConstants) GWT.create(CalendarConstants.class);
    public static final String[] DAYS_OF_WEEK = {
        STRINGS.sunday(), STRINGS.monday(), STRINGS.tuesday(), STRINGS.wednesday(),
        STRINGS.thursday(), STRINGS.friday(), STRINGS.saturday() };
    public static final String[] MONTHS_OF_YEAR = {
        STRINGS.january(), STRINGS.february(), STRINGS.march(), STRINGS.april(),
        STRINGS.may(), STRINGS.june(), STRINGS.july(), STRINGS.august(), STRINGS.september(),
        STRINGS.october(), STRINGS.november(), STRINGS.december()
    };
    public static final String[] DAYS_OF_WEEK_SHORT = {
        STRINGS.sunday_short(), STRINGS.monday_short(), STRINGS.tuesday_short(), STRINGS.wednesday_short(),
        STRINGS.thursday_short(), STRINGS.friday_short(), STRINGS.saturday_short()
    };
    public static final String[] MONTHS_OF_YEAR_SHORT = {
        STRINGS.january_short(), STRINGS.february_short(), STRINGS.march_short(), STRINGS.april_short(),
        STRINGS.may_short(), STRINGS.june_short(), STRINGS.july_short(), STRINGS.august_short(),
        STRINGS.september_short(), STRINGS.october_short(), STRINGS.november_short(), STRINGS.december_short()
    };
    public static final long ONE_DAY = 1000L * 60L * 60L * 24L;
    public static final long TWENTY_EIGHT_DAYS = 30 * ONE_DAY;
    private Date renderDate = new Date( new Date().getYear(), new Date().getMonth(), 1 );
    private Date[][] currentDates = new Date[5][7];
    private Date value = this.renderDate;
    private Grid grid = new Grid(6,7);
    private List eventListeners = new ArrayList();
    private List drawEventListeners = new ArrayList();
    
    /** Creates a new instance of Calendar */
    public Calendar() {
        for( int i=0; i < 7; i++){
            grid.setWidget( 0, i, new Label(Calendar.DAYS_OF_WEEK_SHORT[i]) );
            grid.getCellFormatter().setStyleName(0,i, "day");
        }
        super.initWidget( grid );
        this.render();
        final Calendar instance = this;
        this.grid.addTableListener( new TableListener(){
            public void onCellClicked(SourcesTableEvents sender, int row, int cell) {
                for( Iterator it = eventListeners.iterator(); it.hasNext();  ){
                    if( ((CalendarListener) it.next()).onDateClicked( instance, currentDates[row-1][cell] )){
                        if( currentDates[row-1][cell].getMonth() == getRenderDate().getMonth() ){
                            setValue(  currentDates[row-1][cell] );
                            break;
                        } 
                    }
                }
            }
            
        });
        this.setStyleName("gwittir-Calendar");
    }
    
    public Object getValue() {
        return value;
    }
    
    public void setValue(Object value) {
        this.value = (Date) value;
        this.setRenderDate(new Date( this.value.getYear(), this.value.getMonth(), 1));
        this.render();
    }
    
    public void render(){
        this.renderDate = new Date(getRenderDate().getYear(), getRenderDate().getMonth(), getRenderDate().getDate() );
        int month = getRenderDate().getMonth();
        Date tempDate = new Date( getRenderDate().getYear(), getRenderDate().getMonth(), 1 );
        while( tempDate.getDay() != 0 ){
            tempDate = new Date( tempDate.getTime() - Calendar.ONE_DAY );
        }
        for( int row = 0; row < 5; row++ ){
            for( int col = 0; col < 7; col++ ){
                this.currentDates[row][col] = tempDate;
                if( tempDate.getMonth() != this.getRenderDate().getMonth() ){
                    this.grid.getCellFormatter().setStyleName(row + 1, col, "empty");
                    this.grid.clearCell( row +1, col);
                } else {
                    this.grid.setWidget( row +1, col, new Label( Integer.toString(tempDate.getDate())));
                    String styleName = "date";
                    for( Iterator it = this.drawEventListeners.iterator(); it.hasNext(); ){
                        String altStyle = ((CalendarDrawListener) it.next() ).onCalendarDrawEvent( new CalendarDrawEvent( this, tempDate ) );
                        if( altStyle != null ){
                            this.grid.getCellFormatter().addStyleName( row + 1, col, altStyle );
                        }
                    }
                    this.grid.setStyleName("date");
                }
                tempDate = new Date( tempDate.getTime() + Calendar.ONE_DAY );
            }
        }
    }

    public void addCalendarListener(CalendarListener l) {
        this.eventListeners.add( l );
    }

    public void removeCalendarListener(CalendarListener l) {
        this.eventListeners.remove( l );
    }

    public CalendarListener[] getCalendarListeners() {
        return (CalendarListener[]) this.eventListeners.toArray( new CalendarListener[ this.eventListeners.size() ]);
    }

    public void addCalendarDrawListener(CalendarDrawListener cdl) {
        this.drawEventListeners.add( cdl );
    }

    public void removeCalendarDrawListener(CalendarDrawListener cdl) {
        this.drawEventListeners.remove( cdl );
    }

    public CalendarDrawListener[] getCalendarDrawListeners() {
        return (CalendarDrawListener[]) this.drawEventListeners.toArray( new CalendarDrawListener[ this.drawEventListeners.size() ]);
    }

    public Date getRenderDate() {
        return renderDate;
    }

    public void setRenderDate(Date renderDate) {
        Date old = this.renderDate;
        this.renderDate = renderDate;
        if( !this.renderDate.equals( old ) ){
            this.render();
            this.changes.firePropertyChange("renderDate", old, renderDate );
        }
    }
    
    public boolean setDateWidget( Date date, Widget widget ){
        for( int row =0; row <  this.currentDates.length; row++ ){
            for( int col = 0; col <  this.currentDates[row].length; col++ ){
                if( this.currentDates.equals( date ) ){
                    this.grid.setWidget( row + 1, col, widget );
                    return true;
                }
            }
        }
        return false;
    }
    
    
}
