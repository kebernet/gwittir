package com.totsp.gwittir;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.google.gwt.junit.tools.GWTTestSuite;
import com.totsp.gwittir.client.beans.GwtTestBinding;
import com.totsp.gwittir.client.beans.GwtTestIntrospection;
import com.totsp.gwittir.client.ui.calendar.GwtTestTimeZoneList;
import com.totsp.gwittir.client.util.GwtTestEqualsHashCodeBean;
import com.totsp.gwittir.client.util.GwtTestToStringBean;

public class GwittirTestSuite extends TestSuite {

	public static Test suite() {
        TestSuite gwtTestSuite = new GWTTestSuite();
        gwtTestSuite.addTestSuite(GwtTestBinding.class);
        gwtTestSuite.addTestSuite(GwtTestIntrospection.class);
        gwtTestSuite.addTestSuite(GwtTestTimeZoneList.class);
        gwtTestSuite.addTestSuite(GwtTestEqualsHashCodeBean.class);
        gwtTestSuite.addTestSuite(GwtTestToStringBean.class);
        return gwtTestSuite;
	}
	
}

