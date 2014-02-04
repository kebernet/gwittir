/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.totsp.gwittir.util.compress;

import com.totsp.gwittir.util.Base64;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
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

    void createBitCodes(HuffTree tree) {
        if (tree instanceof HuffNode) {
            // This is a node, not a leaf.  Process it as a node.

            //Cast to type HuffNode.
            HuffNode node = (HuffNode) tree;

            // Get and save the left and right branches
            HuffTree left = node.getLeft();
            HuffTree right = node.getRight();


            code.append("0");
            createBitCodes(left);

            code.deleteCharAt(code.length() - 1); //Delete the 0.
            code.append("1");
            createBitCodes(right);

            code.deleteCharAt(code.length() - 1);
        } else {
            HuffLeaf leaf = (HuffLeaf) tree;
            huffEncodeTable.put((char) (leaf.getValue()), code.toString());
        } //end else
    } //end createBitCodes

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
    } //
    void createHuffTree() {

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

        } //end while
    } //end createHuffTree

    void createLeaves() {
        Iterator<Character> enumerator = frequencyData.keySet().iterator();

        while (enumerator.hasNext()) {
            Character nextKey = enumerator.next();
            theTree.add(new HuffLeaf(nextKey, frequencyData.get(nextKey)));
        } //end while
    } //end createLeaves

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
    }
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
    }
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
    }
    public byte[] encode(String rawData,
        HashMap<Character, String> huffEncodeTable) {
        //Save the incoming parameters.
        this.rawData = rawData;
        this.huffEncodeTable = huffEncodeTable;

        createFreqData();

        createLeaves();

        createHuffTree();

        createBitCodes(theTree.first());

        encodeToString();

        buildEncodingBitMap();

        encodeStringToBits();

        //Return the encoded message.
        byte[] val = new byte[binaryEncodedData.size()];
        for(int i=0; i<val.length; i++){
            val[i] = binaryEncodedData.get(i);
        }
        return val;
    }
    void encodeStringToBits() {
        //Extend the length of the stringEncodedData to cause
        // it to be a multiple of 8.
        int remainder = stringEncodedData.length() % 8;

        for (int cnt = 0; cnt < (8 - remainder); cnt++) {
            stringEncodedData += "0";
        }
        for (int cnt = 0; cnt < stringEncodedData.length(); cnt += 8) {
            String strBits = stringEncodedData.substring(cnt, cnt + 8);
            byte realBits = encodingBitMap.get(strBits);
            binaryEncodedData.add(realBits);
        }
    }

    public String encodeToStringRep(String data){
        HashMap<Character, String> table = new HashMap<Character, String>();
        byte[] payload = this.encode(data, table);
        StringBuilder sb = new StringBuilder();
        sb.append(data.length())
          .append("|");
        for(Map.Entry<Character, String> entry : table.entrySet()){
            sb.append(entry.getKey())
              .append(entry.getValue().length())
              .append(Integer.toHexString(Integer.parseInt(entry.getValue(), 2)));
            sb.append("&");
        }
        sb.append("|")
          .append(Base64.encode(payload));
        return sb.toString().replace("\r\n", "");
    }

    void encodeToString() {
        StringBuffer tempEncoding = new StringBuffer();

        for (int cnt = 0; cnt < rawData.length(); cnt++) {
            tempEncoding.append(huffEncodeTable.get(rawData.charAt(cnt)));
        }

        stringEncodedData = tempEncoding.toString();
    }
}
