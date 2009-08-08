package com.totsp.gwittir.example.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Widget;

import com.totsp.gwittir.client.keyboard.KeyBinding;
import com.totsp.gwittir.client.keyboard.SuggestedKeyBinding;
import com.totsp.gwittir.client.keyboard.Task;
import com.totsp.gwittir.client.ui.BoundVerticalPanel;
import com.totsp.gwittir.client.ui.Button;
import com.totsp.gwittir.client.ui.table.BoundTable;
import com.totsp.gwittir.client.ui.table.Field;
import com.totsp.gwittir.client.ui.util.BoundWidgetTypeFactory;
import com.totsp.gwittir.client.ui.util.ChangeMarkedTypeFactory;
import com.totsp.gwittir.client.validator.IntegerValidator;
import com.totsp.gwittir.client.validator.PopupValidationFeedback;

import java.util.ArrayList;
import java.util.List;


public class TableExample extends BoundVerticalPanel<Object> {
    public TableExample() {
        super(new BoundWidgetTypeFactory(), null);

        Field[] cols = new Field[6];
        cols[0] = new Field(
                "someInteger", "An Integer", null, "This is an Integer Value", null, IntegerValidator.INSTANCE,
                new PopupValidationFeedback(PopupValidationFeedback.BOTTOM));
        cols[1] = new Field("name", "Name", null, "A name value <br /> who cares?");
        cols[2] = new Field("firstName", "First Name", null, "Somebody's first name.");
        cols[3] = new Field("lastName", "Last Name", null, "Somebody's last name.");
        cols[4] = new Field("emailAddress", "Email Address", null, "Somebody's email.");
        cols[5] = new Field("birthDate", "Birth Date", null, "Day of Birth");

        ChangeMarkedTypeFactory factory = new ChangeMarkedTypeFactory();

        final BoundTable t = new BoundTable(
                BoundTable.HEADER_MASK + BoundTable.SORT_MASK + BoundTable.ROW_HANDLE_MASK +
                BoundTable.NO_SELECT_COL_MASK + BoundTable.NO_SELECT_CELL_MASK + BoundTable.MULTIROWSELECT_MASK +
                BoundTable.MULTI_REQUIRES_SHIFT, factory, cols);
        ArrayList<MyClass> list = new ArrayList<MyClass>();
        list.add(new MyClass());
        list.add(new MyClass());
        list.add(new MyClass());
        list.add(new MyClass());
        list.add(new MyClass());
        list.add(new MyClass());
        list.add(new MyClass());
        list.add(new MyClass());
        list.add(new MyClass());
        list.add(new MyClass());
        list.add(new MyClass());
        list.add(new MyClass());
        list.add(new MyClass());
        list.add(new MyClass());
        list.add(new MyClass());
        list.add(new MyClass());
        list.add(new MyClass());
        list.add(new MyClass());
        list.add(new MyClass());
        list.add(new MyClass());
        list.add(new MyClass());
        list.add(new MyClass());
        list.add(new MyClass());
        list.add(new MyClass());
        list.add(new MyClass());
        list.add(new MyClass());
        list.add(new MyClass());

        try {
            t.addKeyBinding(
                new SuggestedKeyBinding('N', true, false, false),
                new Task() {
                    public void run() {
                        MyClass newClass = new MyClass();
                        t.add(newClass);

                        List select = new ArrayList();
                        select.add(newClass);
                        t.setSelected(select);
                    }
                });
            t.addKeyBinding(
                new SuggestedKeyBinding(KeyBinding.DELETE, true, false, false),
                new Task() {
                    public void run() {
                        List selected = t.getSelected();
                        List value = (List) t.getValue();
                        value.removeAll(selected);
                        t.setValue(value);
                    }
                });
        } catch (Exception e) {
            e.printStackTrace();
        }

        t.setValue(list);

        add(t);

        Button hide = new Button(
                "Hide",
                new ClickListener() {
                    public void onClick(Widget sender) {
                        t.setVisible(!t.isVisible());
                        GWT.log(t.isAttached() + "", null);
                    }
                });
        add(hide);
        factory.setMarking(true);
    }
}
