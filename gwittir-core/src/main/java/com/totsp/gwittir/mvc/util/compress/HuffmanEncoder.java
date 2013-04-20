/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.totsp.gwittir.mvc.util.compress;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeSet;


/**
 *
 * @author kebernet
 */
public class HuffmanEncoder {
    ArrayList<Byte> binaryEncodedData = new ArrayList<Byte>();
    HashMap<String, Byte> encodingBitMap = new HashMap<String, Byte>();
    HashMap<Character, Integer> frequencyData = new HashMap<Character, Integer>();
    HashMap<Character, String> huffEncodeTable;
    String rawData;
    String stringEncodedData;
    StringBuffer code = new StringBuffer();
    TreeSet<HuffTree> theTree = new TreeSet<HuffTree>();

    //This method populates a lookup table that relates eight
    // bits represented as a String to eight actual bits for
    // all possible combinations of eight bits.
    void buildEncodingBitMap() {
        for (int cnt = 0; cnt <= 255; cnt++) {
            StringBuffer workingBuf = new StringBuffer();

            if ((cnt & 128) > 0) {
                workingBuf.append("1");
            } else {
                workingBuf.append("0");
            }

            ;

            if ((cnt & 64) > 0) {
                workingBuf.append("1");
            } else {
                workingBuf.append("0");
            }

            ;

            if ((cnt & 32) > 0) {
                workingBuf.append("1");
            } else {
                workingBuf.append("0");
            }

            ;

            if ((cnt & 16) > 0) {
                workingBuf.append("1");
            } else {
                workingBuf.append("0");
            }

            ;

            if ((cnt & 8) > 0) {
                workingBuf.append("1");
            } else {
                workingBuf.append("0");
            }

            ;

            if ((cnt & 4) > 0) {
                workingBuf.append("1");
            } else {
                workingBuf.append("0");
            }

            ;

            if ((cnt & 2) > 0) {
                workingBuf.append("1");
            } else {
                workingBuf.append("0");
            }

            ;

            if ((cnt & 1) > 0) {
                workingBuf.append("1");
            } else {
                workingBuf.append("0");
            }

            ;
            encodingBitMap.put(workingBuf.toString(), (byte) (cnt));
        } //end for loop
    } //end buildEncodingBitMap
      //-----------------------------------------------------//

    //This method uses the Huffman tree in a recursive manner
    // to create a bitcode for each character in the message.
    // The bitcodes are different lengths with the shorter
    // bitcodes corresponding to the characters with a high
    // usage frequency value and the longer bitcodes
    // corresponding to the characters with the lower
    // frequency values.
    //Note that this method receives a reference to the
    // Huffman tree that was earlier contained as the only
    // object in the TreeSet object.  (Because this method is
    // recursive, it cannot conveniently work with the
    // TreeSet object.

    //This method creates a Huffman encoding table as a
    // HashMap object that relates the variable length
    // bitcodes to the characters in the original message.
    // The bitcodes are constructed as objects of type
    // StringBuffer consisting of sequences of the characters
    // 1 and 0.
    //Each bitcode describes the traversal path from the root
    // of the Huffman tree to a leaf on that tree.  Each time
    // the path turns to the left, a 0 character is appended
    // onto the StringBuffer object and becomes part of the
    // resulting bitcode.  Each time the path turns to the
    // right, a 1 character is appended onto the
    // StringBuffer object.  When a leaf is reached, the
    // character stored in that leaf is retrieved and put
    // into the HashMap object as a key.  A String
    // representation of the StringBuffer object is used as
    // the value for that key in the HashMap.
    //At completion,the HashMap object contains a series of
    // keys consisting of the original characters in the
    // message and a series of values as String objects
    // (consisting only of 1 and 0 characters) representing
    // the bitcodes that will eventually be used to encode
    // the original message.
    //Note that theHashMap object that is populated by this
    // method is the data structure that is required later
    // to decode the encoded message.
    void createBitCodes(HuffTree tree) {
        if (tree instanceof HuffNode) {
            // This is a node, not a leaf.  Process it as a node.

            //Cast to type HuffNode.
            HuffNode node = (HuffNode) tree;

            // Get and save the left and right branches
            HuffTree left = node.getLeft();
            HuffTree right = node.getRight();

            //Append a 0 onto the StringBuffer object.  Then make
            // a recursive call to this method passing a
            // reference to the left child as a parameter.  This
            // recursive call will work its way all the way down
            // to a leaf before returning.  Then it will be time
            // to process the right path.
            code.append("0");
            createBitCodes(left);

            //Return to here from recursive call on left child.

            //Delete the 0 from the end of the StringBuffer
            // object to restore the contents of that object to
            // the same state that it had before appending the 0
            // and making the recursive call on the left branch.
            //Now we will make a right turn.  Append a 1 to the
            // StringBuffer object and make a recursive call to
            // this method passing a reference to the right child
            // as a parameter.  Once again, this recursive call
            // will work its way all the  way down to a leaf
            // before returning.
            code.deleteCharAt(code.length() - 1); //Delete the 0.
            code.append("1");
            createBitCodes(right);

            //Return to here from recursive call on right child.

            //Delete the character most recently appended to the
            // StringBuffer object and return from this recursive
            // call to the method.  The character is deleted
            // because control is being transferred back one
            // level in the recursive process and the
            // StringBuffer object must be restored to the same
            // state that it had when this recursive call was
            // made.
            code.deleteCharAt(code.length() - 1);
        } else {
            //This is a leaf.  Process it as such.
            //Cast the object to type HuffLeaf.
            HuffLeaf leaf = (HuffLeaf) tree;

            //Put an entry into the HashMap.  The HashMap
            // key consists of the character value stored in the
            // leaf. The value in the HashMap consists of the
            // contents of the StringBuffer object representing
            // the path from the root of the tree to the leaf.
            // This is the bitcode and is stored in the HashMap
            // as a String consisting of only 1 and 0 characters.
            huffEncodeTable.put((char) (leaf.getValue()), code.toString());
        } //end else
    } //end createBitCodes
      //-----------------------------------------------------//

    //This method creates a frequency chart that identifies
    // each of the individual characters in the original
    // message and the number of times that each character
    // appeared in the message.  The results are stored in
    // a HashMap with the characters being the keys and the
    // usage frequency of each character being the
    // corresponding HashMap value for that key.
    void createFreqData() {
        for (int cnt = 0; cnt < rawData.length(); cnt++) {
            char key = rawData.charAt(cnt);

            if (frequencyData.containsKey(key)) {
                int value = frequencyData.get(key);
                value += 1;
                frequencyData.put(key, value);
            } else {
                frequencyData.put(key, 1);
            } //end else
        } //end for loop
    } //end createFreqData
      //-----------------------------------------------------//

    //This inner class is used to construct a leaf object in
    // the Huffman tree.
    //End HuffLeaf class
    //=====================================================//

    //Assemble the HuffLeaf objects into a HuffTree object.
    // A HuffTree object is a special form of a binary tree
    // consisting of properly linked HuffNode objects and
    // HuffLeaf objects.
    //When the method terminates, the HuffTree object
    // remains as the only object stored in the TreeSet
    // object that previously contained all of the HuffLeaf
    // objects.  This is because all of the HuffLeaf
    // objects have been removed from the TreeSet object
    // and combined with HuffNode objects to form the
    // Huffman tree (as represented by the single HuffTree
    // object).
    //The method contains two sections of code that can be
    // enabled to display:
    // 1. The contents of the original TreeSet object.
    // 2. The contents of the TreeSet object for each
    //    iteration during which HuffLeaf objects are being
    //    combined with HuffNode objects to form the final
    //    HuffTree object.
    // This display is very useful for understanding how the
    // Huffman tree is constructed.  However, this code
    // should be enabled only for small trees because it
    // generates a very large amount of output.

    //The HuffTree object is constructed by:
    // 1. Extracting pairs of HuffLeaf or HuffNode objects
    //    from the TreeSet object in ascending order based
    //    on their frequency value.
    // 2. Using the pair of extracted objects to construct
    //    a new HuffNode object where the two extracted
    //    objects become children of the new HuffNode
    //    object, and where the frequency value stored in
    //    the new HuffNode object is the sum of the
    //    frequency values in the two child objects.
    // 3. Removing the two original HuffLeaf or HuffNode
    //    objects from the TreeSet and adding the new
    //    HuffNode object to the TreeSet object.  The
    //    position of the new HuffNode object in the Treeset
    //    object is determined by its frequency value
    //    relative to the other HuffNode or HuffLeaf objects
    //    in the collection. The new HuffNode object will
    //    eventually become a child of another new HuffNode
    //    object unless it ends up as the root of the
    //    HuffTree object.
    // 4. Continuing this process until the TreeSet object
    //    contains a single object of type HuffTree.
    void createHuffTree() {
        //Enable the following statements to see the original
        // contents of the TreeSet object. Do this only for
        // small trees because it generates lots of output.
        /*
            System.out.println("nnDisplay Original TreeSet");
            Iterator <HuffTree> originalIter = theTree.iterator();
            while(originalIter.hasNext()){
              System.out.println(
                              "nHuffNode, HuffLeaf, or HuffTree");
              displayHuffTree(originalIter.next(),0);
            }//end while loop
            //End code to display the TreeSet
        */

        //Iterate on the size of the TreeSet object until all
        // of the elements have been combined into a single
        // element of type HuffTree
        while (theTree.size() > 1) {
            //Get, save, and remove the first two elements.
            HuffTree left = theTree.first();
            theTree.remove(left);

            HuffTree right = theTree.first();
            theTree.remove(right);

            //Combine the two saved elements into a new element
            // of type HuffNode and add it to the TreeSet
            // object.
            HuffNode tempNode = new HuffNode(left.getFrequency() +
                    right.getFrequency(), left, right);
            theTree.add(tempNode);

            //Enable the following statements to see the HuffTree
            // being created from HuffNode and HuffLeaf objects.
            // Do this only for small trees because it will
            // generate a lot of output.
            /*
                  System.out.println("nnDisplay Working TreeSet");
                  Iterator <HuffTree> workingIter = theTree.iterator();
                  while(workingIter.hasNext()){
                    System.out.println(
                                  "nHuffNode, HuffLeaf, or HuffTree");
                    displayHuffTree(workingIter.next(),0);
                  }//end while loop
                  //End code to display the TreeSet
            */
        } //end while
    } //end createHuffTree
      //-----------------------------------------------------//

    //This method creates a HuffLeaf object for each char
    // identified in the frequency chart.  The HuffLeaf
    // objects are stored in a TreeSet object.  Each HuffLeaf
    // object encapsulates the character as well as the
    // number of times that the character appeared in the
    // original message.
    void createLeaves() {
        Iterator<Character> enumerator = frequencyData.keySet().iterator();

        while (enumerator.hasNext()) {
            Character nextKey = enumerator.next();
            theTree.add(new HuffLeaf(nextKey, frequencyData.get(nextKey)));
        } //end while
    } //end createLeaves
      //-----------------------------------------------------//

    //This method displays a table showing the relationship
    // between the characters in the original message and the
    // bitcodes that will ultimately replace each of those
    // characters to produce the Huffman-encoded message.
    void displayBitCodes() {
        Iterator<Character> enumerator = huffEncodeTable.keySet().iterator();

        while (enumerator.hasNext()) {
            Character nextKey = enumerator.next();
            System.out.println(nextKey + " " + huffEncodeTable.get(nextKey));
        }
    }

    //This method displays the contents of the frequency
    // chart created by the method named createFreqData.
    void displayFreqData() {
        System.out.println("nFrequency Data");

        Iterator<Character> enumerator = frequencyData.keySet().iterator();

        while (enumerator.hasNext()) {
            Character nextKey = enumerator.next();
            System.out.println(nextKey + " " + frequencyData.get(nextKey));
        } //end while
    } //end displayFreqData
      //-----------------------------------------------------//

    //Recursive method to display a HufTree object.  The
    // first call to this method should pass a value of 0
    // for recurLevel.
    void displayHuffTree(HuffTree tree, int recurLevel) {
        recurLevel++;

        if (tree instanceof HuffNode) {
            // This is a node, not a leaf.  Process it as a node.

            //Cast to type HuffNode.
            HuffNode node = (HuffNode) tree;

            // Get and save the left and right branches
            HuffTree left = node.getLeft();
            HuffTree right = node.getRight();

            //Display information that traces out the recursive
            // traversal of the tree in order to display the
            // contents of the leaves.
            System.out.print("  Left to " + recurLevel + " ");
            //Make a recursive call.
            displayHuffTree(left, recurLevel);

            System.out.print("  Right to " + recurLevel + " ");
            //Make a recursive call.
            displayHuffTree(right, recurLevel);
        } else {
            //This is a leaf.  Process it as such.
            //Cast the object to type HuffLeaf.
            HuffLeaf leaf = (HuffLeaf) tree;
            System.out.println("  Leaf:" + (char) leaf.getValue());
        } //end else

        System.out.print("  Back ");
    } //end displayHuffTree

    //This method displays a message string as a series of
    // characters each having a value of 1 or 0.
    void displayRawDataAsBits() {
        for (int cnt = 0, charCnt = 0; cnt < rawData.length();
                cnt++, charCnt++) {
            char theCharacter = rawData.charAt(cnt);
            String binaryString = Integer.toBinaryString(theCharacter);

            //Append leading zeros as necessary to show eight
            // bits per character.
            while (binaryString.length() < 8) {
                binaryString = "0" + binaryString;
            } //end while loop

            if ((charCnt % 6) == 0) {
                //Display 48 bits per line.
                charCnt = 0;
                System.out.println(); //new line
            } //end if

            System.out.print(binaryString);
        } //end for loop

        System.out.println();
    } //end displayRawDataAsBits
      //-----------------------------------------------------//

    //-----------------------------------------------------//

    //This method encodes an incoming String message using
    // the Huffman encoding methodology.  The method also
    // receives a reference to an empty data structure.
    // This data structures is populated with encoding
    // particulars required later by the decode method
    // to decode and transform the encoded message back
    // into the original String message.  Note that in
    // order to keep this method simple, pad characters may
    // be appended onto the end of the original
    // message when it is encoded.  This is done to cause the
    // number of bits in the encoded message to be a multiple
    // of eight, thus causing the length of the encoded
    // message to be an integral number of bytes.  Additional
    // code would be required to avoid this at this point.
    // However, it is easy to eliminate the extraneous
    // characters during decoding if the length of the
    // original message is known.
    public byte[] encode(String rawData,
        HashMap<Character, String> huffEncodeTable) {
        //Save the incoming parameters.
        this.rawData = rawData;
        this.huffEncodeTable = huffEncodeTable;

        //For illustration purposes only, enable the following
        // two statements to display the original message as a
        // stream of bits.  This can be visually compared with
        // a similar display for the encoded  message later to
        // illustrate the amount of compression provided by
        // the encoding process.
        /*
            System.out.println("nRaw Data as Bits");
            displayRawDataAsBits();
        */

        //Create a frequency chart that identifies each of the
        // individual characters in the original message and
        // the number of times (frequency) that each character
        // appeared in the message.
        createFreqData();

        //For illustration purposes only, enable the following
        // statement to display the contents of the frequency
        // chart created above.
        /*
            displayFreqData();
        */

        //Create a HuffLeaf object for each character
        // identified in the frequency chart.  Store the
        // HuffLeaf objects in a TreeSet object.  Each HuffLeaf
        // object encapsulates the character as well as the
        // number of times that the character appeared in the
        // original message (the frequency).
        createLeaves();

        //Assemble the HuffLeaf objects into  a Huffman tree
        // (a HuffTree object). A Huffman tree is a special
        // form of a binary tree  consisting of properly linked
        // HuffNode objects and HuffLeaf objects.
        //When the following method returns, the HuffTree
        // object remains as the only object stored in the
        // TreeSet object that previously contained all of the
        // HuffLeaf objects.  This is because all of the
        // HuffLeaf objects have been combined with HuffNode
        // objects to form the tree.
        createHuffTree();

        //Use the Huffman tree in a recursive manner to create
        // a bit code for each character in the message.  The
        // bit codes are different lengths with the shorter
        // codes corresponding to the characters with a high
        // frequency value and the longer codes corresponding
        // to the characters with the lower frequency values.
        //Note that the method call extracts the reference to
        // the Huffman tree from the TreeSet object and passes
        // that reference to the method.  This is necessary
        // because the method is recursive and cannot
        // conveniently work with the TreeSet object.
        //This method populates the data structure that is
        // required later to decode the encoded message.
        createBitCodes(theTree.first());

        //For purposes of illustration only, enable the
        // following two statements to display a table showing
        // the relationship between the characters in the
        // original message and the bitcodes that will replace
        // those characters to produce the Huffman-encoded
        // message.
        /*
            System.out.println();
            displayBitCodes();
        */

        //Encode the message into a String representation
        // of the bits that will make up the final encoded
        // message.  Also,the following method may optionally
        // display the String showing the bit values that will
        // appear in the final Huffman-encoded message.  This
        // is useful for comparing back against the bits in
        // the original message for purposes of evaluating the
        // amount of compression provided by encoding the
        // message.
        encodeToString();

        //Populate a lookup table that relates eight bits
        // represented as a String to every possible combinaion
        // of eight actual bits.
        buildEncodingBitMap();

        //Encode the String representation of the bits that
        // make up the encoded message to the actual bits
        // that make up the encoded message.
        //Note that this method doesn't handle the end of the
        // message very gracefully for those cases where the
        // number of required bits is not a multiple of 8.  It
        // simply adds enough "0" characters to the end to
        // cause the length to be a multiple of 8.  This may
        // result in extraneous characters at the end of the
        // decoded message later.
        //For illustration purposes only, this method may also
        // display the extended version of the String
        // representation of the bits for comparison with the
        // non-extended version.
        encodeStringToBits();

        //Return the encoded message.
        byte[] val = new byte[binaryEncodedData.size()];
        for(int i=0; i<val.length; i++){
            val[i] = binaryEncodedData.get(i);
        }
        return val;
    } //end encode method
      //-----------------------------------------------------//

    //The purpose of this method is to create actual bit data
    // that matches the 1 and 0 characters in the
    // stringEncodedData that represents bits with the 1 and
    // 0 characters.
    //Note that this method doesn't handle the end of the
    // data very gracefully for those cases where the number
    // of required bits is not a multiple of 8.  It simply
    // adds enough "0" characters to the end to cause the
    // length to be a multiple of 8.  This may result in
    // extraneous characters at the end of the decoded
    // message later. However, it isn't difficult to remove
    // the extraneous characters at decode time as long as
    // the length of the original message is known.
    //For illustration purposes, this method may optionally
    // display the extended version of the stringEncodedData
    // for comparison with the non-extended version.
    //Note that the binary Huffman-encoded data produced by
    // this method is stored in a data structure of type
    // ArrayList <Byte>.
    void encodeStringToBits() {
        //Extend the length of the stringEncodedData to cause
        // it to be a multiple of 8.
        int remainder = stringEncodedData.length() % 8;

        for (int cnt = 0; cnt < (8 - remainder); cnt++) {
            stringEncodedData += "0";
        } //end for loop

        //For illustration purposes only, enable the following
        // two statements to display the extended
        // stringEncodedData in the same format as the
        // original stringEncodedData.

        //Extract the String representations of the required
        // eight bits.  Generate eight actual matching bits by
        // looking the bit combination up in a table.
        for (int cnt = 0; cnt < stringEncodedData.length(); cnt += 8) {
            String strBits = stringEncodedData.substring(cnt, cnt + 8);
            byte realBits = encodingBitMap.get(strBits);
            binaryEncodedData.add(realBits);
        }
    }

    //This method encodes the message into a String
    // representation of the bits that will make up the final
    // encoded message.  The String consists of only 1 and 0
    // characters where each character represents the state
    // of one of the bits in the Huffman-encoded message.
    //Also for illustration purposes, this method optionally
    // displays the String showing the bit values that will
    // appear in the Huffman-encoded message.
    void encodeToString() {
        StringBuffer tempEncoding = new StringBuffer();

        for (int cnt = 0; cnt < rawData.length(); cnt++) {
            tempEncoding.append(huffEncodeTable.get(rawData.charAt(cnt)));
        }

        stringEncodedData = tempEncoding.toString();

        //For illustration purposes, enable the following two
        // statements to display the String showing the bit
        // values that will appear in the Huffman-encoded
        // message.  Display 48 bits to the line except for
        // the last line, which may be shorter, and which may
        // not be a multiple of 8 bits.
        /*
            System.out.println("nString Encoded Data");
            display48(stringEncodedData);
        */
    } //end encodeToString
      //-----------------------------------------------------//
}
