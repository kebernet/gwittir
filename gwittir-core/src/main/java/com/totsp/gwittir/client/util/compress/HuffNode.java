/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.client.util.compress;

/**
 *
 * @author kebernet
 */
class HuffNode extends HuffTree{

    private HuffTree left;
    private HuffTree right;

    //HuffNode constructor
    public HuffNode(
               int frequency,HuffTree left,HuffTree right){
      this.frequency = frequency;
      this.left = left;
      this.right = right;
    }//end HuffNode constructor

    public HuffTree getLeft(){
      return left;
    }//end getLeft

    public HuffTree getRight(){
      return right;
    }//end getRight

  }