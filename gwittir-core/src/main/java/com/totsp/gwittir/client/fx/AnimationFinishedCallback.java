/*
 * AnimationFinishedCallback.java
 *
 * Created on August 3, 2007, 2:44 PM
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


/**
 * This is a callback interface that can be implemented to receive
 * notifications when a PropertyAnimator completes its run.
 * @author <a href="mailto:cooper@screaming-penguin.com">Robert "kebernet" Cooper</a>
 */
public interface AnimationFinishedCallback {
    /**
     * This method is called when an exception occurred during the animation.
     * @param animator The animator that failed.
     * @param e The exception that was thrown.
     */
    public void onFailure(PropertyAnimator animator, Exception e);

    /**
     * This method is called when a property animator finishes its run.
     * @param animator The animator that finished.
     */
    public void onFinish(PropertyAnimator animator);
}
