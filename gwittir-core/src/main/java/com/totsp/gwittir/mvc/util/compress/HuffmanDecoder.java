/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.totsp.gwittir.mvc.util.compress;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


/**
 *
 * @author kebernet
 */
public class HuffmanDecoder {
    ArrayList<Byte> binaryEncodedData;
    HashMap<Byte, String> decodingBitMap = new HashMap<Byte, String>();
    HashMap<String, Character> huffDecodeTable = new HashMap<String, Character>();
    //The following structure contains particulars as to how
    // the original message was encoded, and must be received
    // as an incoming parameter to the decode method along
    // with the encoded message and the length of the
    // original message.
    HashMap<Character, String> huffEncodeTable;
    String decodedData = "";
    String stringDecodedData;

    //Used to eliminate the extraneous characters on the end.
    int rawDataLen;

    //This method populates a lookup table that relates eight
    // bits represented as a String to eight actual bits for
    // all possible combinations of eight bits.  This is
    // essentially a reverse lookup table relative to the
    // encodingBitMap table that is used to encode the
    // message.  The only difference between the two is a
    // reversal of the key and the value in the HashMap
    // that contains the table.
    void buildDecodingBitMap() {
        for (int cnt = 0; cnt <= 255; cnt++) {
            StringBuffer workingBuf = new StringBuffer();

            if ((cnt & 128) > 0) {
                workingBuf.append("1");
            } else {
                workingBuf.append("0");
            }

            if ((cnt & 64) > 0) {
                workingBuf.append("1");
            } else {
                workingBuf.append("0");
            }

            if ((cnt & 32) > 0) {
                workingBuf.append("1");
            } else {
                workingBuf.append("0");
            }

            if ((cnt & 16) > 0) {
                workingBuf.append("1");
            } else {
                workingBuf.append("0");
            }

            if ((cnt & 8) > 0) {
                workingBuf.append("1");
            } else {
                workingBuf.append("0");
            }

            if ((cnt & 4) > 0) {
                workingBuf.append("1");
            } else {
                workingBuf.append("0");
            }

            if ((cnt & 2) > 0) {
                workingBuf.append("1");
            } else {
                workingBuf.append("0");
            }

            if ((cnt & 1) > 0) {
                workingBuf.append("1");
            } else {
                workingBuf.append("0");
            }

            decodingBitMap.put((byte) (cnt), workingBuf.toString());
        } //end for loop
    } //end buildDecodingBitMap()
      //-----------------------------------------------------//

    //This method creates a Huffman decoding table by
    // swapping the keys and the values from the Huffman
    // encoding table received as an incoming parameter by
    // the decode method.
    void buildHuffDecodingTable() {
        Iterator<Character> enumerator = huffEncodeTable.keySet().iterator();

        while (enumerator.hasNext()) {
            Character nextKey = enumerator.next();
            String nextString = huffEncodeTable.get(nextKey);
            huffDecodeTable.put(nextString, nextKey);
        } //end while
    } //end buildHuffDecodingTable()
      //-----------------------------------------------------//

    //-----------------------------------------------------//

    //This method receives a Huffman-encoded message along
    // with a data structure containing particulars as to how
    // the original message was encoded and the length of the
    // original message.  It decodes the original message and
    // returns the decoded version as a String object.
    public String decode(byte[] encodedData,
        HashMap<Character, String> huffEncodeTable, int rawDataLen) {
        //Save the incoming parameters.
        ArrayList<Byte> encodedDataList = new ArrayList<Byte>(encodedData.length);
        for(byte b: encodedData){
            encodedDataList.add(b);
        }
        this.binaryEncodedData = encodedDataList;
        this.huffEncodeTable = huffEncodeTable;
        this.rawDataLen = rawDataLen;

        //Create a decoding bit map, which is essentially the
        // reverse of the encoding bit map that was used to
        // encode the original message.
        buildDecodingBitMap();

        //Decode the encoded message from a binary
        // representation to a String of 1 and 0 characters
        // that represent the actual bits in the encoded
        // message.  Also, for illustration purposes only,
        // this method may optionally display the String.
        decodeToBitsAsString();

        //Create a Huffman decoding table by swapping the keys
        // and values from the Huffman encoding table received
        // as an incoming parameter by the decode method.
        buildHuffDecodingTable();

        //Decode the String containing only 1 and 0 characters
        // that represent the bits in the encoded message. This
        // produces a replica of the original message that was
        // subjected to Huffman encoding.  Write the resulting
        // decoded message into a String object referred to by
        // decoded data.
        decodeStringBitsToCharacters();

        //Return the decoded message.  Eliminate the extraneous
        // characters from the end of the message on the basis
        // of the known length of the original message.
        return decodedData.substring(0, rawDataLen);
    } //end decode method
      //-----------------------------------------------------//

    //The method begins with an empty StringBuffer object
    // referred to by the variable named workBuf and another
    // empty StringBuffer object referred to by the variable
    // named output.  The StringBuffer object referred to by
    // output is used to construct the decoded message.  The
    // StringBuffer object referred to by workBuf is used as
    // a temporary holding area for substring data.
    //The method reads the String containing only 1 and 0
    // characters that represent the bits in the encoded
    // message (stringDecodedData).  The characters are read
    // from this string one character at a time.  As each new
    // character is read, it is appended to the StringBuffer
    // object referred to by workBuf.
    //As each new character is appended to the StringBuffer
    // object, a test is performed to determine if the
    // current contents of the StringBuffer object match one
    // of the keys in a lookup table that relates strings
    // representing Huffman bitcodes to characters in the
    // original message.
    //When a match is found, the value  associated with that
    // key is extracted and appended to the StringBuffer
    // object referred to by output.  Thus, the output text
    // is built up one character at a time.
    //Having processed the matching key, A new empty
    // StringBuffer object is instantiated, referred to by
    // workBuf, and the process of reading, appending, and
    // testing for a match is repeated until all of the
    // characters in the string that represents the bits in
    // the encoded message have been processed.  At that
    // point, the StringBuffer object referred to by output
    // contains the entire decoded message.  It is converted
    // to type String and written into the object referred to
    // by decodedData.  Then the method returns with the task
    // of decoding the encoded message having been completed.
    void decodeStringBitsToCharacters() {
        StringBuffer output = new StringBuffer();
        StringBuffer workBuf = new StringBuffer();

        for (int cnt = 0; cnt < stringDecodedData.length(); cnt++) {
            workBuf.append(stringDecodedData.charAt(cnt));

            if (huffDecodeTable.containsKey(workBuf.toString())) {
                output.append(huffDecodeTable.get(workBuf.toString()));
                workBuf = new StringBuffer();
            } //end if
        } //end for loop

        decodedData = output.toString();
    }

    //This method decodes the encoded message from a binary
    // representation to a String of 1 and 0 characters that
    // represent the actual bits in the encoded message.
    // Also, for illustration purposes only, this method
    // may optionally display that String.
    void decodeToBitsAsString() {
        StringBuffer workingBuf = new StringBuffer();

        for (Byte element : binaryEncodedData) {
            byte wholeByte = element;
            workingBuf.append(decodingBitMap.get(wholeByte));
        } //end for-each

        //Convert from StringBuffer to String
        stringDecodedData = workingBuf.toString();

        //For illustration purposes only, enable the following
        // two statements to display the String containing 1
        // and 0 characters that represent the bits in the
        // encoded message.
    } //end decodeToBitsAsString
      //-----------------------------------------------------//
}
