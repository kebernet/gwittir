/*
 * PropertyAnimator.java
 *
 * Created on August 3, 2007, 2:39 PM
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

import com.google.gwt.user.client.Timer;
import com.totsp.gwittir.client.beans.Introspector;
import com.totsp.gwittir.client.beans.Method;
import com.totsp.gwittir.client.beans.Property;


/**
 *
 * @author <a href="mailto:cooper@screaming-penguin.com">Robert "kebernet" Cooper</a>
 */
public class PropertyAnimator {
    private AnimationFinishedCallback callback;
    private Object target;
    private MutationStrategy strategy;
    private Object finalValue;
    private Object initialValue;
    private Property property;
    private int duration;
    private int stepTime;

    private Timer timer;
    //TODO examine step time real results and increase animation time to match.
    /** Creates a new instance of PropertyAnimator */
    public PropertyAnimator(Object target, String propertyName,
        Object initialValue, Object finalValue, MutationStrategy strategy,
        int duration, int stepTime, AnimationFinishedCallback callback) {
        this.target = target;
        this.property = Introspector.INSTANCE.getDescriptor(target)
                                             .getProperty(propertyName);

        if((property == null)
                || (
                    (property.getAccessorMethod() == null)
                    && (initialValue == null)
                ) || (property.getMutatorMethod() == null)) {
            throw new RuntimeException("Invalid property.");
        }

        try {
            this.initialValue = (initialValue == null)
                ? property.getAccessorMethod().invoke(target, null) : initialValue;
        } catch(Exception e) {
            throw new RuntimeException(e);
        }

        this.finalValue = finalValue;
        this.strategy = strategy;
        this.callback = callback;
        this.duration = duration;
        this.stepTime = stepTime;
    }

    public PropertyAnimator(Object target, String propertyName,
        Object finalValue, MutationStrategy strategy) {
        this(target, propertyName, null, finalValue, strategy, 1000, 50, null);
    }

    public PropertyAnimator(Object target, String propertyName,
        Object finalValue, MutationStrategy strategy, int duration) {
        this(target, propertyName, null, finalValue, strategy, duration, 50,
            null);
    }

    public PropertyAnimator(Object target, String propertyName,
        Object initialValue, Object finalValue, MutationStrategy strategy,
        int duration) {
        this(target, propertyName, initialValue, finalValue, strategy,
            duration, 50, null);
    }

    public PropertyAnimator(Object target, String propertyName,
        Object finalValue, MutationStrategy strategy, int duration,
        AnimationFinishedCallback callback) {
        this(target, propertyName, null, finalValue, strategy, duration, 50,
            callback);
    }

    public PropertyAnimator(Object target, String propertyName,
        Object initialValue, Object finalValue, MutationStrategy strategy,
        int duration, AnimationFinishedCallback callback) {
        this(target, propertyName, initialValue, finalValue, strategy,
            duration, 50, callback);
    }

    void invoke(Method method, Object value) throws Exception {
        if(this.property.getType() == null) {
            method.invoke(this.target, (Object[]) value);
        } else {
            Object[] args = { value };
            method.invoke(this.target, args);
        }
    }

    public void start() {
        final long startTime = System.currentTimeMillis();
        final PropertyAnimator instance = this;

        try {
            invoke(this.property.getMutatorMethod(), this.initialValue);
        } catch(Exception e) {
            if(this.callback != null) {
                this.callback.onFailure(this, e);

                return;
            }
        }

         timer= new Timer() {
                private long lastStep = System.currentTimeMillis();
                public void run() {
                    try {
                        double percentComplete = (double) (
                                System.currentTimeMillis() - startTime
                            ) / (double) duration;
                        Object newValue = strategy.mutateValue(initialValue,
                                finalValue, percentComplete);

                        if(percentComplete < 1) {
                            invoke(property.getMutatorMethod(), newValue);
                        } else {
                            invoke(property.getMutatorMethod(), finalValue);

                            if(callback != null) {
                                callback.onFinish(instance);
                            }
                            
                            timer.cancel();
                            return;
                        }
                    } catch(Exception e) {
                        if(callback != null) {
                            callback.onFailure(instance, e);
                            timer.cancel();

                            return;
                        }
                    }
                    if( lastStep - System.currentTimeMillis() > stepTime * 2 ){
                        stepTime = (int) lastStep - (int) System.currentTimeMillis();
                    }
                    timer.schedule(stepTime);
                }
               
            };
            this.timer.schedule( this.stepTime );
        
    }
    
    public void cancel(){
        timer.cancel();
        if( this.callback != null ){
            callback.onFailure(this, null);
        }
    }
}
