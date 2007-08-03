/*
 * MutationStrategy.java
 *
 * Created on August 3, 2007, 2:41 PM
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
package com.totsp.gwittir.client.fx;

import com.totsp.gwittir.client.util.UnitsParser;
import com.totsp.gwittir.client.util.UnitsParser.UnitValue;


/**
 *
 * @author cooper
 */
public interface MutationStrategy {
    public static final MutationStrategy INTEGER_SINOIDAL = new MutationStrategy() {
        public Object mutateValue(Object from, Object to,
                double percentComplete) {
            double f = ((Integer) from).doubleValue();
            double t = ((Integer) to).doubleValue();
            double offset = ((
                    -Math.cos((double) percentComplete * Math.PI) / 2d
                    ) + 0.5d);
            
            return new Integer((int) Math.round((offset * (t - f)) + f));
        }
    };
    
    public static final MutationStrategy INTEGER_LINEAR = new MutationStrategy() {
        public Object mutateValue(Object from, Object to,
                double percentComplete) {
            double f = ((Integer) from).doubleValue();
            double t = ((Integer) to).doubleValue();
            
            return new Integer((int) Math.round((
                    percentComplete * (t - f)
                    ) + f));
        }
    };
    
    public static final MutationStrategy INTEGER_CUBIC = new MutationStrategy() {
        public Object mutateValue(Object from, Object to,
                double percentComplete) {
            double f = ((Integer) from).doubleValue();
            double t = ((Integer) to).doubleValue();
            double offset = Math.pow(percentComplete, 3);
            
            return new Integer((int) Math.round((offset * (t - f)) + f));
        }
    };
    
    public static final MutationStrategy UNITS_SINOIDAL = new MutationStrategy() {
        public Object mutateValue(Object from, Object to,
                double percentComplete) {
            UnitValue tv = UnitsParser.parse((String) to);
            double f = (double) UnitsParser.parse((String) from).value;
            double t = (double) tv.value;
            double offset = ((
                    -Math.cos((double) percentComplete * Math.PI) / 2d
                    ) + 0.5d);
            
            return Math.round((offset * (t - f)) + f) + tv.units;
        }
    };
    
    public static final MutationStrategy UNITS_LINEAR = new MutationStrategy() {
        public Object mutateValue(Object from, Object to,
                double percentComplete) {
            UnitValue tv = UnitsParser.parse((String) to);
            double f = (double) UnitsParser.parse((String) from).value;
            double t = (double) tv.value;
            
            return Math.round((percentComplete * (t - f)) + f) + tv.units;
        }
    };
    
    public static final MutationStrategy UNITS_CUBIC = new MutationStrategy() {
        public Object mutateValue(Object from, Object to,
                double percentComplete) {
            UnitValue tv = UnitsParser.parse((String) to);
            double f = (double) UnitsParser.parse((String) from).value;
            double t = (double) tv.value;
            double offset = Math.pow(percentComplete, 3);
            
            return Math.round((offset * (t - f)) + f) + tv.units;
        }
    };
    
    public static final MutationStrategy DOUBLE_SINOIDAL = new MutationStrategy() {
        public Object mutateValue(Object from, Object to,
                double percentComplete) {
            double f = ((Double) from).doubleValue();
            double t = ((Double) to).doubleValue();
            double offset = ((
                    -Math.cos((double) percentComplete * Math.PI) / 2d
                    ) + 0.5d);
            
            return new Double((offset * (t - f)) + f);
        }
    };
    
    public static final MutationStrategy DOUBLE_LINEAR = new MutationStrategy() {
        public Object mutateValue(Object from, Object to,
                double percentComplete) {
            double f = ((Double) from).doubleValue();
            double t = ((Double) to).doubleValue();
            
            return new Double((
                    percentComplete * (t - f)
                    ) + f);
        }
    };
    
    public static final MutationStrategy DOUBLE_CUBIC = new MutationStrategy() {
        public Object mutateValue(Object from, Object to,
                double percentComplete) {
            double f = ((Double) from).doubleValue();
            double t = ((Double) to).doubleValue();
            double offset = Math.pow(percentComplete, 3);
            
            return new Double((offset * (t - f)) + f);
        }
    };
    
    Object mutateValue(Object from, Object to, double percentComplete);
}
