package com.totsp.gwittir.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ChangeListenerCollection;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.HasFocus;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.SourcesClickEvents;
import com.google.gwt.user.client.ui.SourcesKeyboardEvents;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.Widget;

import com.totsp.gwittir.client.action.Action;

import java.util.Comparator;


public class TextArea extends AbstractBoundWidget<String> implements HasFocus, HasEnabled, SourcesKeyboardEvents,
    SourcesClickEvents {
    private com.google.gwt.user.client.ui.TextArea base = new com.google.gwt.user.client.ui.TextArea();
    private ChangeListenerCollection changeListeners = new ChangeListenerCollection();
    private String old;

    public TextArea() {
        this(false);
    }

    /** Creates a new instance of TextBox */
    public TextArea(final boolean updateOnKeypress) {
        final TextArea instance = this;
        old = base.getText();

        if (updateOnKeypress) {
            this.addKeyboardListener(
                new KeyboardListener() {
                    public void onKeyPress(Widget sender, char keyCode, int modifiers) {
                        changes.firePropertyChange("value", old, getValue());
                        old = (String) getValue();
                    }

                    public void onKeyDown(Widget sender, char keyCode, int modifiers) {
                    }

                    public void onKeyUp(Widget sender, char keyCode, int modifiers) {
                    }
                });
        } else {
            this.addKeyboardListener(
                new KeyboardListener() {
                    public void onKeyUp(Widget sender, char keyCode, int modifiers) {
                    }

                    public void onKeyPress(Widget sender, char keyCode, int modifiers) {
                        if (keyCode == KeyboardListener.KEY_ENTER) {
                            setFocus(false);
                            setFocus(true);
                        }
                    }

                    public void onKeyDown(Widget sender, char keyCode, int modifiers) {
                    }
                });
        }

        this.base.addChangeListener(
            new ChangeListener() {
                public void onChange(Widget sender) {
                    changes.firePropertyChange("value", old, getValue());
                    old = (String) getValue();
                    changeListeners.fireChange(instance);
                }
            });
        super.initWidget(this.base);
    }

    public void setAccessKey(char key) {
        this.base.setAccessKey(key);
    }

    public void setAction(Action action) {
        super.setAction(action);
    }

    public Action getAction() {
        Action retValue;

        retValue = super.getAction();

        return retValue;
    }

    public Comparator getComparator() {
        Comparator retValue;

        retValue = super.getComparator();

        return retValue;
    }

    public void setCursorPos(int pos) {
        this.base.setCursorPos(pos);
    }

    public int getCursorPos() {
        int retValue;

        retValue = this.base.getCursorPos();

        return retValue;
    }

    public void setEnabled(boolean enabled) {
        this.base.setEnabled(enabled);
    }

    public boolean isEnabled() {
        return this.base.isEnabled();
    }

    public void setFocus(boolean focused) {
        this.base.setFocus(focused);
    }

    public void setHeight(String height) {
        this.base.setHeight(height);
    }

    public void setKey(char key) {
        this.base.setKey(key);
    }

    public void setModel(Object model) {
        super.setModel(model);
    }

    public Object getModel() {
        Object retValue;

        retValue = super.getModel();

        return retValue;
    }

    public void setName(String name) {
        this.base.setName(name);
    }

    public String getName() {
        String retValue;

        retValue = this.base.getName();

        return retValue;
    }

    public int getOffsetHeight() {
        int retValue;

        retValue = this.base.getOffsetHeight();

        return retValue;
    }

    public int getOffsetWidth() {
        int retValue;

        retValue = this.base.getOffsetWidth();

        return retValue;
    }

    public void setPixelSize(int width, int height) {
        this.base.setPixelSize(width, height);
    }

    public void setReadOnly(boolean readOnly) {
    }

    public String getSelectedText() {
        String retValue;

        retValue = this.base.getSelectedText();

        return retValue;
    }

    public int getSelectionLength() {
        int retValue;

        retValue = this.base.getSelectionLength();

        return retValue;
    }

    public void setSelectionRange(int pos, int length) {
        this.base.setSelectionRange(pos, length);
    }

    public void setSize(String width, String height) {
        this.base.setSize(width, height);
    }

    public void setStyleName(String style) {
        this.base.setStyleName(style);
    }

    public String getStyleName() {
        String retValue;

        retValue = this.base.getStyleName();

        return retValue;
    }

    public void setTabIndex(int index) {
        this.base.setTabIndex(index);
    }

    public int getTabIndex() {
        return this.base.getTabIndex();
    }

    public void setText(String text) {
        this.base.setText(text);
    }

    public String getText() {
        return this.base.getText();
    }

    public void setTextAlignment(TextBoxBase.TextAlignConstant align) {
        this.base.setTextAlignment(align);
    }

    public void setTitle(String title) {
        this.base.setTitle(title);
    }

    public String getTitle() {
        return this.base.getTitle();
    }

    public void setValue(String value) {
        String old = this.getValue();
        this.setText(value);

        if ((this.getValue() != old) && (this.getValue() != null) && !this.getValue()
                                                                              .equals(old)) {
            this.changes.firePropertyChange("value", old, this.getValue());
        }
    }

    public String getValue() {
        try {
            return (this.base.getText()
                             .length() == 0) ? null
                                             : this.base.getText();
        } catch (RuntimeException re) {
            GWT.log("" + this.base, re);

            return null;
        }
    }

    public void setWidth(String width) {
        this.base.setWidth(width);
    }

    public void addChangeListener(ChangeListener listener) {
        this.base.addChangeListener(listener);
    }

    public void addClickListener(ClickListener listener) {
        this.base.addClickListener(listener);
    }

    public void addFocusListener(FocusListener listener) {
        this.base.addFocusListener(listener);
    }

    public void addKeyboardListener(KeyboardListener listener) {
        this.base.addKeyboardListener(listener);
    }

    public void cancelKey() {
        this.base.cancelKey();
    }

    public void removeChangeListener(ChangeListener listener) {
        this.base.removeChangeListener(listener);
    }

    public void removeClickListener(ClickListener listener) {
        this.base.removeClickListener(listener);
    }

    public void removeFocusListener(FocusListener listener) {
        this.base.removeFocusListener(listener);
    }

    public void removeKeyboardListener(KeyboardListener listener) {
        this.base.removeKeyboardListener(listener);
    }

    public void removeStyleName(String style) {
        this.base.removeStyleName(style);
    }

    public void selectAll() {
        this.base.selectAll();
    }

    public void sinkEvents(int eventBitsToAdd) {
        this.base.sinkEvents(eventBitsToAdd);
    }

    public void unsinkEvents(int eventBitsToRemove) {
        this.base.unsinkEvents(eventBitsToRemove);
    }
}
