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
 * This BoundWidget renders a Calendar on the screen, and 
 * supports a value property of Date.
 * @author cooper
 */
public class Calendar extends AbstractBoundWidget implements Renderers,
    SourcesCalendarEvents, SourcesCalendarDrawEvents {
    /**
     * Milliseconds in a day.
     */
    public static final long ONE_DAY = 1000L * 60L * 60L * 24L;
    /**
     * Milliseconds in 28 days
     */
    public static final long TWENTY_EIGHT_DAYS = 30 * ONE_DAY;
    private Date renderDate = new Date(new Date().getYear(), new Date().getMonth(), 1);
    private Date value;
    private Grid grid = new Grid(7, 7);
    private List drawEventListeners = new ArrayList();
    private List eventListeners = new ArrayList();
    private Date[][] currentDates = new Date[6][7];

    /** Creates a new instance of Calendar */
    public Calendar() {
        this.value = this.renderDate;
        for (int i = 0; i < 7; i++) {
            grid.setWidget(0, i, new Label(Calendar.DAYS_OF_WEEK_SHORT[i]));
            grid.getCellFormatter().setStyleName(0, i, "day");
        }

        grid.setCellSpacing(0);
        grid.setCellPadding(0);
        super.initWidget(grid);
        final Calendar instance = this;
        this.grid.addTableListener(
            new TableListener() {
                public void onCellClicked(SourcesTableEvents sender, int row, int cell) {
                    boolean cancelled = false;

                    for (
                        Iterator it = new ArrayList(eventListeners).iterator();
                            it.hasNext();) {
                        if (
                            !((CalendarListener) it.next()).onDateClicked(
                                    instance, currentDates[row - 1][cell])) {
                            cancelled = true;

                            break;
                        }
                    }

                    if (
                        !cancelled &&
                            (
                                currentDates[row - 1][cell].getMonth() == getRenderDate()
                                                                                  .getMonth()
                            )) {
                        setValue(currentDates[row - 1][cell]);
                    }
                }
            });
        this.setStyleName("gwittir-Calendar");
    }

    /**
     * 
     * @return 
     */
    public CalendarDrawListener[] getCalendarDrawListeners() {
        return (CalendarDrawListener[]) this.drawEventListeners.toArray(
            new CalendarDrawListener[this.drawEventListeners.size()]);
    }

    /**
     * 
     * @return 
     */
    public CalendarListener[] getCalendarListeners() {
        return (CalendarListener[]) this.eventListeners.toArray(
            new CalendarListener[this.eventListeners.size()]);
    }

    /**
     * Replaces the Label containing the date in the Calendar's table with the widget 
     * provided.
     * @param date The date to overwrite
     * @param widget The widget to place in the table.
     * @return boolean indicating if the date is currently drawn, and the operation succeeded.
     */
    public boolean setDateWidget(Date date, Widget widget) {
        for (int row = 0; row < this.currentDates.length; row++) {
            for (int col = 0; col < this.currentDates[row].length; col++) {
                if (this.currentDates.equals(date)) {
                    this.grid.setWidget(row + 1, col, widget);

                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Sets a date value that represents the month and year the calendar
     * will be rendering.
     * @param renderDate Date to render around.
     */
    public void setRenderDate(Date renderDate) {
        Date old = this.renderDate;
        this.renderDate = renderDate;

        if (!this.renderDate.equals(old)) {
            if( this.isAttached() ){ 
                this.render();
            }
            this.changes.firePropertyChange("renderDate", old, renderDate);
        }
    }

    /**
     * Returns a date value that represents the month and year the calendar
     * will be rendering.
     * @return Date to rendered around.
     */
    public Date getRenderDate() {
        return renderDate;
    }

    /**
     * Sets the date value of the calendar. Will reset the render date to 
     * this value as well.
     * @param value Date balue of the Calendar
     */
    public void setValue(Object value) {
        Date old = this.value;
        this.value = (Date) value;
        this.setRenderDate(new Date(this.value.getYear(), this.value.getMonth(), 1));
        if(this.isAttached() ){
            this.render();
        }
        this.changes.firePropertyChange("value", old, this.value);
    }

    /**
     * Returns the value of the last date clicked.
     * @return Returns the value of the last date clicked.
     */
    public Object getValue() {
        return this.value;
    }

    /**
     * 
     * @param cdl 
     */
    public void addCalendarDrawListener(CalendarDrawListener cdl) {
        this.drawEventListeners.add(cdl);
    }

    /**
     * 
     * @param l 
     */
    public void addCalendarListener(CalendarListener l) {
        this.eventListeners.add(l);
    }

    /**
     * 
     * @param cdl 
     */
    public void removeCalendarDrawListener(CalendarDrawListener cdl) {
        this.drawEventListeners.remove(cdl);
    }

    /**
     * 
     * @param l 
     */
    public void removeCalendarListener(CalendarListener l) {
        this.eventListeners.remove(l);
    }

    /**
     * Renders/Rerenders the table based on the renderDate.
     */
    public void render() {
        this.renderDate = new Date(
                getRenderDate().getYear(), getRenderDate().getMonth(),
                getRenderDate().getDate());

        int month = getRenderDate().getMonth();
        Date tempDate = new Date(
                getRenderDate().getYear(), getRenderDate().getMonth(), 1);

        while (tempDate.getDay() != 0) {
            tempDate = new Date(tempDate.getTime() - Calendar.ONE_DAY);
        }

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                this.currentDates[row][col] = tempDate;

                if (tempDate.getMonth() != this.getRenderDate().getMonth()) {
                    this.grid.getCellFormatter().setStyleName(row + 1, col, "empty");
                    this.grid.clearCell(row + 1, col);
                } else {
                    this.grid.setWidget(
                        row + 1, col, new Label(Integer.toString(tempDate.getDate())));
                    this.grid.getCellFormatter().setStyleName(row + 1, col, "date");

                    for (Iterator it = this.drawEventListeners.iterator(); it.hasNext();) {
                        String altStyle = ((CalendarDrawListener) it.next()).onCalendarDrawEvent(
                                new CalendarDrawEvent(this, tempDate));

                        if (altStyle != null) {
                            this.grid.getCellFormatter().addStyleName(
                                row + 1, col, altStyle);
                        }
                    }
                }

                tempDate = new Date(tempDate.getYear(), tempDate.getMonth(), tempDate.getDate() +1);
            }
        }
    }

    protected void onAttach() {
        super.onAttach();
        this.render();
    }
}
