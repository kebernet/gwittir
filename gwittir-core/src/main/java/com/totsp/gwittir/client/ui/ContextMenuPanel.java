/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.totsp.gwittir.client.ui;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.totsp.gwittir.client.action.Action;

/**
 * 
 * @author kebernet
 */
public class ContextMenuPanel<T> extends SimplePanel implements BoundWidget<T> {
	private BoundWidget<T> internal;
	private List<Widget> menuItems = new ArrayList<Widget>();
	protected PopupPanel popup = new PopupPanel(true, true);
	private PropertyChangeSupport changes = new PropertyChangeSupport(this);
	private VerticalPanel panel = new VerticalPanel();
	private boolean rightDown = false;

	public ContextMenuPanel(BoundWidget<T> internal) {
		super();
		this.internal = internal;
		setWidget((Widget) internal);
		this.sinkEvents(Event.ONCLICK | Event.ONCONTEXTMENU | Event.MOUSEEVENTS
				| Event.ONMOUSEDOWN | Event.ONMOUSEUP);
		this.panel.setStyleName("gwittir-ContextMenu");
		this.popup.setWidget(panel);
                this.getElement().setAttribute("oncontextmenu", "return false;");
	}

	public void addChangeListener(ChangeListener arg0) {
	}

	public void addMenuItemWidget(Widget w) {
		this.menuItems.add(w);

		if (w instanceof MenuItem) {
			((MenuItem) w).setMenu(this);
		}

		if (w instanceof SubMenu) {
			((SubMenu) w).setMenu(this);
		}

		this.panel.add(w);
	}

	public void addPropertyChangeListener(PropertyChangeListener l) {
		this.changes.addPropertyChangeListener(l);
	}

	public void addPropertyChangeListener(String propertyName,
			PropertyChangeListener l) {
		this.changes.addPropertyChangeListener(propertyName, l);
	}

	public Action getAction() {
		return this.internal.getAction();
	}

	public Comparator getComparator() {
		return null;
	}

	public BoundWidget<T> getInternal() {
		return this.internal;
	}

	public List<Widget> getMenuItemWidgets() {
		return this.menuItems;
	}

	public Object getModel() {
		return internal.getModel();
	}

	public PropertyChangeListener[] getPropertyChangeListeners() {
		return this.changes.getPropertyChangeListeners();
	}

	public T getValue() {
		return internal.getValue();
	}

	public void hide() {
		this.popup.hide();
	}

	private int x;
	private int y;
	private Timer t = new Timer() {

		@Override
		public void run() {
			popup.show();
			popup.setPopupPosition(x, y);
		}

	};

	@Override
	public void onBrowserEvent(Event event) {
		switch (DOM.eventGetType(event)) {
		case Event.ONCONTEXTMENU:
			GWT.log("ONCONTEXTMENU", null);
			DOM.eventPreventDefault(event);
			this.popup.show();
			this.popup.setPopupPosition(x, y);
			break;

		case Event.ONMOUSEDOWN:
			GWT.log("ONMOUSEDOWN", null);
			if ((DOM.eventGetButton(event) == Event.BUTTON_RIGHT)
					|| DOM.eventGetCtrlKey(event)) {
				rightDown = true;
				DOM.eventPreventDefault(event);
				x = DOM.eventGetClientX(event);
				y = DOM.eventGetClientY(event);
				if ((DOM.eventGetButton(event) == Event.BUTTON_RIGHT)) {
					t.schedule(5);
				}
                                DOM.eventCancelBubble(event, true);
			} else {
				rightDown = false;
			}

			break;

		case Event.ONMOUSEUP:
			if (rightDown) {
				this.popup.show();
				this.popup.setPopupPosition(x, y);
				DOM.eventCancelBubble(event, true);
			} else {
				rightDown = false;
			}

			break;
		case Event.ONCLICK:
			if (DOM.eventGetButton(event) == Event.BUTTON_RIGHT) {
				GWT.log("RIGHT CLICK", null);
				DOM.eventPreventDefault(event);
			}
		default:
			break;
		}
	}

	public void removeChangeListener(ChangeListener arg0) {
	}

	public boolean removeMenuItemWidget(Widget w) {
		this.menuItems.remove(w);

		return this.panel.remove(w);
	}

	public void removePropertyChangeListener(PropertyChangeListener l) {
		this.changes.addPropertyChangeListener(l);
	}

	public void removePropertyChangeListener(String propertyName,
			PropertyChangeListener l) {
		this.changes.addPropertyChangeListener(propertyName, l);
	}

	public void setAction(Action action) {
		this.internal.setAction(action);
	}

	public void setComparator(Comparator comparator) {
	}

	public void setInternal(BoundWidget<T> internal) {
		this.remove((Widget) this.internal);
		super.setWidget((Widget) internal);
		this.changes.firePropertyChange("internal", this.internal,
				this.internal = internal);
	}

	public void setModel(Object model) {
		internal.setModel(model);
	}

	public void setValue(T value) {
		internal.setValue(value);
	}

	public static class MenuItem extends Button {
		public MenuItem(String text) {
			super(text);
			this.setStyleName("gwittir-ContextMenuItem");
			this.sinkEvents(Event.ONCONTEXTMENU | Event.MOUSEEVENTS);

		}

		public MenuItem(String text, ClickListener listener) {
			super(text, listener);
		}

		public void onBrowserEvent(Event event) {
			switch (DOM.eventGetType(event)) {
			case Event.ONCONTEXTMENU:
				GWT.log("ONCONTEXTMENU-BUTTON", null);
				DOM.eventPreventDefault(event);

				break;
			default:
				super.onBrowserEvent(event);
				break;
			}
		}

		void setMenu(final ContextMenuPanel panel) {
			this.addClickListener(new ClickListener() {
				public void onClick(Widget w) {
					panel.hide();
				}
			});
		}
	}
	
	public void onDetach(){
		super.onDetach();
		DeferredCommand.addCommand( new Command(){

			public void execute() {
				hide();
			}
			
		});
		
	}

	public static class SubMenu extends ContextMenuPanel {
		ContextMenuPanel panel;

		public SubMenu(String text) {
			super(new Button(text));
			((Widget) this.getInternal())
					.setStyleName("gwittir-ContextSubMenu");
		}

		public void hide() {
			this.popup.hide();
			this.panel.hide();
		}

		@Override
		public void onBrowserEvent(Event event) {
			switch (DOM.eventGetType(event)) {
			case Event.ONMOUSEDOWN:

				Widget w = (Widget) this.getInternal();
				int x = w.getAbsoluteLeft() + w.getOffsetWidth();
				int y = w.getAbsoluteTop();
				this.popup.show();
				this.popup.setPopupPosition(x, y);
				DOM.eventCancelBubble(event, true);

				break;

			default:
				break;
			}
			
		}

		void setMenu(final ContextMenuPanel panel) {
			this.panel = panel;
			((Button) this.getInternal()).addClickListener(new ClickListener() {
				public void onClick(Widget w) {
					hide();
				}
			});
		}
	}
}
