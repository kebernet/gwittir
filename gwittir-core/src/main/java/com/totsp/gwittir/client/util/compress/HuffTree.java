/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.totsp.gwittir.client.util.compress;


/**
 *
 * @author kebernet
 */
abstract class HuffTree implements Comparable {
    int frequency;

    public int getFrequency() {
        return frequency;
    } //end getFrequency

    //This method compares this object to an object whose
    // reference is received as an incoming parameter.
    // The method guarantees that sorting processes that
    // depend on this method, such as TreeSet objects, will
    // sort the objects into a definitive order.

    // If the frequency values of the two objects are
    // different, the sort is based on the frequency values.
    // If the frequency values are equal, the objects are
    // sorted based on their relative hashCode values.
    // Thus, if the same two objects with the same frequency
    // value are compared two or more times during the
    // execution of the program, those two objects will
    // always be sorted into the same order.  There is no
    // chance of an ambiguous tie as to which object
    // should be first except for the case where an object
    // is compared to itself using two references to the
    // same object.
    public int compareTo(Object obj) {
        HuffTree theTree = (HuffTree) obj;

        if (frequency == theTree.frequency) {
            //The objects are in a tie based on the frequency
            // value.  Return a tiebreaker value based on the
            // relative hashCode values of the two objects.
            return (hashCode() - theTree.hashCode());
        } else {
            //Return negative or positive as this frequency is
            // less than or greater than the frequency value of
            // the object referred to by the parameter.
            return frequency - theTree.frequency;
        } //end else
    } //end compareTo
}
