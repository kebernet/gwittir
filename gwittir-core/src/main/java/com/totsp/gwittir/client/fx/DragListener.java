/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.client.fx;

/**
 *
 * @author kebernet
 */
public interface DragListener {

    

    /** Allows for eventing and vetoing of drag events
     *
     *
     */
    DragPoint onDrag(DragPoint p);


    public class DragPoint {
        private int x;
        private int y;

        public DragPoint(int x, int y){
            this.x = x;
            this.y = y;
        }

        /**
         * @return the x
         */
        public int getX() {
            return x;
        }

        /**
         * @return the y
         */
        public int getY() {
            return y;
        }
    }

}
