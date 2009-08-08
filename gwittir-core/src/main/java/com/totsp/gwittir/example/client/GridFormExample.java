package com.totsp.gwittir.example.client;

import com.totsp.gwittir.client.beans.Converter;
import com.totsp.gwittir.client.ui.BoundVerticalPanel;
import com.totsp.gwittir.client.ui.table.Field;
import com.totsp.gwittir.client.ui.table.GridForm;
import com.totsp.gwittir.client.ui.util.BoundWidgetTypeFactory;
import com.totsp.gwittir.client.validator.DoubleValidator;
import com.totsp.gwittir.client.validator.IntegerValidator;
import com.totsp.gwittir.client.validator.PopupValidationFeedback;


public class GridFormExample extends BoundVerticalPanel<Object> {
    public GridFormExample() {
        super(new BoundWidgetTypeFactory(), null);

        final Field[] mcf = new Field[10];
        mcf[0] = new Field(
                "someInteger", "An Integer", null, "This is an Integer Value", null, IntegerValidator.INSTANCE,
                new PopupValidationFeedback(PopupValidationFeedback.BOTTOM));
        mcf[1] = new Field("name", "Name", null, "A name value <br /> who cares?");
        mcf[2] = new Field("firstName", "First Name", null, "Somebody's first name.");
        mcf[3] = new Field("lastName", "Last Name", null, "Somebody's last name.");
        mcf[4] = new Field("emailAddress", "Email Address", null, "Somebody's email.");

        Converter doubleConverter = new Converter<Double, String>() {
                public String convert(Double original) {
                    return "" + original;
                }
            };

        mcf[6] = new Field(
                "price", "Price", null, "This is an decimal Value", doubleConverter, DoubleValidator.INSTANCE,
                new PopupValidationFeedback(PopupValidationFeedback.BOTTOM));
        mcf[7] = new Field("homeTown", "Home Town", null, "Somebody's place of origin.");
        mcf[8] = new Field("zipCode", "Postal Code", null, "A USPS Postal Code");
        mcf[9] = new Field("birthDate", "Birth Date", null, "Day of Birth");

        final GridForm form = new GridForm(mcf, 3);
        form.setValue(new MyClass());

        GridForm form2 = new GridForm(mcf, 3);
        form2.setValue(form.getValue());

        add(form);
        add(form2);
    }
}
