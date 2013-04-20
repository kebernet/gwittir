/*
 * PopupDatePicker.java
 *
 * Created on November 9, 2007, 3:23 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.totsp.gwittir.mvc.ui.calendar;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HasFocus;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SourcesClickEvents;
import com.google.gwt.user.client.ui.Widget;
import com.totsp.gwittir.mvc.beans.Binding;
import com.totsp.gwittir.mvc.beans.Converter;
import com.totsp.gwittir.mvc.ui.AbstractBoundWidget;
import com.totsp.gwittir.mvc.ui.Label;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;

/**
 * Renders a Date value to a Label with a Image src of
 * "[module root]/calendar-icon.gif", which when clicked, will pop up a DatePicker
 * for selection.
 * @author rcooper
 */
public class PopupDatePicker extends AbstractBoundWidget<Date>
        implements SourcesCalendarDrawEvents, SourcesCalendarEvents, DateRenderers, HasFocus, SourcesClickEvents {
    DatePicker base = new DatePicker();
    Label label = new Label();
    Image icon = new Image(GWT.getModuleBaseURL()+"calendar-icon.gif");
    FocusPanel fp = new FocusPanel();
    HorizontalPanel hp = new HorizontalPanel();
    PopupPanel pp = new PopupPanel(true);
    private ConverterWrapper converter = new ConverterWrapper();
    private boolean hasFirstSet = false;
    /** Creates a new instance of PopupDatePicker */
    public PopupDatePicker() {
        
        Binding b = new Binding( label, "value", null, base, "value", converter);
        b.setLeft();
        b.bind();
        pp.setWidget(base);
        this.hp.add( this.label );
        this.hp.add( this.icon );
        fp.setWidget( hp );
        this.initWidget( fp );
        this.setStyleName("gwittir-PopupDatePicker");
        icon.addClickListener( new ClickListener(){
            public void onClick(Widget sender) {
                fp.setFocus(true);
                if( pp.isAttached() ){
                    pp.hide();
                } else {
                    int width = Window.getClientWidth() + Window.getScrollLeft();
                    pp.setPopupPosition( getAbsoluteLeft(),
                            getAbsoluteTop() + getOffsetHeight() );
                    base.addCalendarListener(new CalendarListener(){
                        public boolean onDateClicked(Calendar calendar, Date date) {
                            if(date.getMonth() != base.getRenderDate().getMonth() ||
                                    date.getYear() != base.getRenderDate().getYear() ){
                                return true;
                            }
                            pp.hide();
                            calendar.removeCalendarListener(this);
                            return true;
                        }
                        
                    });
                    pp.show();
                    if( pp.getPopupLeft() + base.getOffsetWidth()  > width  ){
                        pp.setPopupPosition( pp.getPopupLeft() + 
                                (width - pp.getPopupLeft()- base.getOffsetWidth() ), pp.getPopupTop() );
                    }
                }
            }
        });
        this.base.addPropertyChangeListener("value", new PropertyChangeListener(){
            public void propertyChange(PropertyChangeEvent evt) {
            	if(!hasFirstSet){
            		hasFirstSet=true;
            	} else {
            		changes.firePropertyChange("value", evt.getOldValue(), evt.getNewValue() );
            	}
            }
            
        });
    }
    
    
    /**
     * Current Date value.
     * @return Current Date value.
     */
    public Date getValue() {
        return this.base.getValue();
    }
    
    /**
     * Current Date value.
     * @param value Current Date value.
     */
    public void setValue(Date value) {
        this.base.setValue(value);
    }

    public void setDateRenderer(Converter<Date,String> renderer){
        this.converter.setImpl(renderer);
    }

    /**
     *
     * @param cdl
     */
    public void addCalendarDrawListener(CalendarDrawListener cdl) {
        this.base.addCalendarDrawListener( cdl );
    }
    
    /**
     *
     * @param cdl
     */
    public void removeCalendarDrawListener(CalendarDrawListener cdl) {
        this.base.removeCalendarDrawListener( cdl );
    }
    
    /**
     *
     * @return
     */
    public CalendarDrawListener[] getCalendarDrawListeners() {
        return this.base.getCalendarDrawListeners();
    }
    
    /**
     *
     * @param l
     */
    public void addCalendarListener(CalendarListener l) {
        this.base.addCalendarListener( l );
    }
    
    /**
     *
     * @param l
     */
    public void removeCalendarListener(CalendarListener l) {
        this.base.removeCalendarListener( l );
    }
    
    /**
     *
     * @return
     */
    public CalendarListener[] getCalendarListeners() {
        return this.base.getCalendarListeners();
    }
    
    public void addFocusListener(FocusListener listener) {
        fp.addFocusListener( listener );
    }
    
    public void removeFocusListener(FocusListener listener) {
        fp.removeFocusListener( listener );
    }

    public void addClickListener(ClickListener listener) {
        this.fp.addClickListener( listener );
        this.icon.addClickListener( listener );
    }

    public void removeClickListener(ClickListener listener) {
        this.fp.removeClickListener( listener );
        this.icon.removeClickListener( listener );
    }

    public int getTabIndex() {
        return this.fp.getTabIndex();
    }

    public void setAccessKey(char key) {
        this.fp.setAccessKey( key );
    }

    public void setFocus(boolean focused) {
        this.fp.setFocus( focused );
    }

    public void setTabIndex(int index) {
        this.fp.setTabIndex( index );
    }

    public void addKeyboardListener(KeyboardListener listener) {
        this.fp.addKeyboardListener(listener );
    }

    public void removeKeyboardListener(KeyboardListener listener) {
        this.fp.removeKeyboardListener( listener );
    }

    private static class ConverterWrapper implements Converter<Date,String> {

        private Converter<Date,String> impl = DateRenderers.SHORT_DATE_RENDERER;

        public void setImpl(Converter<Date,String> newImpl){
            if(impl == null ){
                this.impl = DateRenderers.SHORT_DATE_RENDERER;
            } else {
                this.impl = newImpl;
            }
        }

        public String convert(Date original) {
            return impl.convert(original);
        }

    }

}
