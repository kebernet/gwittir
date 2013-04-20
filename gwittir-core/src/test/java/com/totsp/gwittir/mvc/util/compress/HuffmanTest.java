package com.totsp.gwittir.mvc.util.compress;


import com.totsp.gwittir.mvc.util.Base64;
import java.util.*;
import junit.framework.TestCase;

public class HuffmanTest extends TestCase{

    public HuffmanTest(String testName) {
        super(testName);
    }

  public void testGeneral() throws Exception{

    //The following data structure is used to
    // communicate encoding particulars from the Huffman
    // encoder to the Huffman decoder.  This is necessary
    // for the decoder to be able to decode the encoded
    // message.  Note that this data structure must be
    // empty when it is passed to the encode method.
    HashMap <Character,String>huffEncodeTable;

    //Begin the demonstration by applying Huffman encoding
    // to a text message.

    //Create and display the raw text message that will be
    // encoded.  Display 48 characters to the line.

    //Modify the comment indicators to enable one of the
    // following test messages, or insert a test message
    // of your own and then recompile the program.
/*
    //The following test message was copied directly from
    // an Internet news site.  It is probably
    // representative of typical English text.
    String rawData = "BAGHDAD, Iraq Violence increased "
    + "across Iraq after a lull following the Dec. 15 "
    + "parliamentary elections, with at least two dozen "
    + "people including a U.S. soldier killed Monday in "
    + "shootings and bombings mostly targeting the Shiite-"
    + "dominated security services. The Defense Ministry "
    + "director of operations, Brig. Gen. Abdul Aziz "
    + "Mohammed-Jassim, blamed increased violence in the "
    + "past two days on insurgents trying to deepen the "
    + "political turmoil following the elections. The "
    + "violence came as three Iraqi opposition groups "
    + "threatened another wave of protests and civil "
    + "disobedience if allegations of fraud are not "
    + "properly investigated.";
*/
/*
    String rawData = "Now is the time for all good men "
    + "to come to the aid of their country.";
*/

    //Use the following test message or some other
    // similarly short test message to illustrate the
    // construction of the HuffTree object.
    String rawData = "{\"loginurl\": \"https://www.whitehouse.gov/member/register/\",\"getSeriesById\": {\"seriesId\": \"ahR3aGl0ZWhvdXNlLW1vZGVyYXRvcnIOCxIGU2VyaWVzGPirAQw\", \"html_description\": false, \"allow_anonymous\": false, \"short_series_id\": \"55f8\", \"video_url\": \"httpa://www.youtube.com/v/hjJm_Hzc6Yg\", \"title\": \"Open for Questions Wraps Up\", \"deleted\": false, \"welcomeText\": \"The trial run of Open for Questions has wrapped up with the President answering several of the most popular questions during a special online town hall.  We will be trying to address more of the questions the President could not get to over the next week or so, and will continue looking for new ways to engage with the public and get your input.\", \"numvotes\": 3605269, \"topics\": [{\"topicId\": \"ahR3aGl0ZWhvdXNlLW1vZGVyYXRvcnINCxIFVG9waWMY1IwBDA\", \"description\": null, \"title\": \"Education\", \"deleted\": false, \"series\": \"ahR3aGl0ZWhvdXNlLW1vZGVyYXRvcnIOCxIGU2VyaWVzGPirAQw\", \"date_voting_closed\": 1238074200000.0, \"presenter\": null, \"key\": \"ahR3aGl0ZWhvdXNlLW1vZGVyYXRvcnINCxIFVG9waWMY1IwBDA\", \"short_topic_id\": \"4654\", \"is_closed\": true, \"short_series_id\": \"55f8\"}, {\"topicId\": \"ahR3aGl0ZWhvdXNlLW1vZGVyYXRvcnIMCxIFVG9waWMY-i4M\", \"description\": null, \"title\": \"Home Ownership\", \"deleted\": false, \"series\": \"ahR3aGl0ZWhvdXNlLW1vZGVyYXRvcnIOCxIGU2VyaWVzGPirAQw\", \"date_voting_closed\": 1238074200000.0, \"presenter\": null, \"key\": \"ahR3aGl0ZWhvdXNlLW1vZGVyYXRvcnIMCxIFVG9waWMY-i4M\", \"short_topic_id\": \"177a\", \"is_closed\": true, \"short_series_id\": \"55f8\"}, {\"topicId\": \"ahR3aGl0ZWhvdXNlLW1vZGVyYXRvcnINCxIFVG9waWMY-tIBDA\", \"description\": null, \"title\": \"Health Care Reform\", \"deleted\": false, \"series\": \"ahR3aGl0ZWhvdXNlLW1vZGVyYXRvcnIOCxIGU2VyaWVzGPirAQw\", \"date_voting_closed\": 1238074200000.0, \"presenter\": null, \"key\": \"ahR3aGl0ZWhvdXNlLW1vZGVyYXRvcnINCxIFVG9waWMY-tIBDA\", \"short_topic_id\": \"697a\", \"is_closed\": true, \"short_series_id\": \"55f8\"}, {\"topicId\": \"ahR3aGl0ZWhvdXNlLW1vZGVyYXRvcnIMCxIFVG9waWMYlCcM\", \"description\": null, \"title\": \"Veterans\", \"deleted\": false, \"series\": \"ahR3aGl0ZWhvdXNlLW1vZGVyYXRvcnIOCxIGU2VyaWVzGPirAQw\", \"date_voting_closed\": 1238074200000.0, \"presenter\": null, \"key\": \"ahR3aGl0ZWhvdXNlLW1vZGVyYXRvcnIMCxIFVG9waWMYlCcM\", \"short_topic_id\": \"1394\", \"is_closed\": true, \"short_series_id\": \"55f8\"}, {\"topicId\": \"ahR3aGl0ZWhvdXNlLW1vZGVyYXRvcnIMCxIFVG9waWMYin0M\", \"description\": null, \"title\": \"Small Business\", \"deleted\": false, \"series\": \"ahR3aGl0ZWhvdXNlLW1vZGVyYXRvcnIOCxIGU2VyaWVzGPirAQw\", \"date_voting_closed\": 1238074200000.0, \"presenter\": null, \"key\": \"ahR3aGl0ZWhvdXNlLW1vZGVyYXRvcnIMCxIFVG9waWMYin0M\", \"short_topic_id\": \"3e8a\", \"is_closed\": true, \"short_series_id\": \"55f8\"}, {\"topicId\": \"ahR3aGl0ZWhvdXNlLW1vZGVyYXRvcnINCxIFVG9waWMY3LMBDA\", \"description\": null, \"title\": \"Auto Industry\", \"deleted\": false, \"series\": \"ahR3aGl0ZWhvdXNlLW1vZGVyYXRvcnIOCxIGU2VyaWVzGPirAQw\", \"date_voting_closed\": 1238074200000.0, \"presenter\": null, \"key\": \"ahR3aGl0ZWhvdXNlLW1vZGVyYXRvcnINCxIFVG9waWMY3LMBDA\", \"short_topic_id\": \"59dc\", \"is_closed\": true, \"short_series_id\": \"55f8\"}, {\"topicId\": \"ahR3aGl0ZWhvdXNlLW1vZGVyYXRvcnIMCxIFVG9waWMY0GUM\", \"description\": null, \"title\": \"Retirement Security\", \"deleted\": false, \"series\": \"ahR3aGl0ZWhvdXNlLW1vZGVyYXRvcnIOCxIGU2VyaWVzGPirAQw\", \"date_voting_closed\": 1238074200000.0, \"presenter\": null, \"key\": \"ahR3aGl0ZWhvdXNlLW1vZGVyYXRvcnIMCxIFVG9waWMY0GUM\", \"short_topic_id\": \"32d0\", \"is_closed\": true, \"short_series_id\": \"55f8\"}, {\"topicId\": \"ahR3aGl0ZWhvdXNlLW1vZGVyYXRvcnIMCxIFVG9waWMYiX0M\", \"description\": null, \"title\": \"Green Jobs and Energy\", \"deleted\": false, \"series\": \"ahR3aGl0ZWhvdXNlLW1vZGVyYXRvcnIOCxIGU2VyaWVzGPirAQw\", \"date_voting_closed\": 1238074200000.0, \"presenter\": null, \"key\": \"ahR3aGl0ZWhvdXNlLW1vZGVyYXRvcnIMCxIFVG9waWMYiX0M\", \"short_topic_id\": \"3e89\", \"is_closed\": true, \"short_series_id\": \"55f8\"}, {\"topicId\": \"ahR3aGl0ZWhvdXNlLW1vZGVyYXRvcnILCxIFVG9waWMYCgw\", \"description\": null, \"title\": \"Financial Stability\", \"deleted\": false, \"series\": \"ahR3aGl0ZWhvdXNlLW1vZGVyYXRvcnIOCxIGU2VyaWVzGPirAQw\", \"date_voting_closed\": 1238074200000.0, \"presenter\": null, \"key\": \"ahR3aGl0ZWhvdXNlLW1vZGVyYXRvcnILCxIFVG9waWMYCgw\", \"short_topic_id\": \"a\", \"is_closed\": true, \"short_series_id\": \"55f8\"}, {\"topicId\": \"ahR3aGl0ZWhvdXNlLW1vZGVyYXRvcnIMCxIFVG9waWMYkycM\"";
    //Enable the following two statements to display the
    // raw data 48 characters to the line.
    System.out.println("Raw Data");
    System.out.println(rawData);

    int rawDataLen = rawData.length();

    System.out.println("nNumber raw data bytes: "
                                   + rawData.length());

    //Instantiate a Huffman encoder object
    HuffmanEncoder encoder = new HuffmanEncoder();

    //Encode the raw text message.  The encoded message
    // is received back as bytes stored in an ArrayList
    // object.  Pass the raw message to the encode
    // method.  Also pass a reference to the empty data
    // structure mentioned above to the encode method where
    // it will be populated with encoding particulars
    // needed to decode the message later
    huffEncodeTable = new HashMap<Character,String>();
    byte[] binaryEncodedData = encoder.encode(
                                  rawData,huffEncodeTable);
    

    System.out.println("Number binary encoded data bytes: "
                           + binaryEncodedData.length);
    System.out.println("Compression factor: "
      + (double)rawData.length()/binaryEncodedData.length);

    //The message has now been Huffman encoded. Display the
    // binaryEncodedData in Hexadecimal format, 48
    // characters per line.
    System.out.println(
            "nBinary Encoded Data in Base64 Format");
    String base64 = Base64.encode(binaryEncodedData);
    System.out.println(base64);
    System.out.println();
    System.out.println(rawData.length()+" vs "+base64.length() );
    //Now continue the demonstration by decoding the
    // Huffman-encoded message.

    //Instantiate a Huffman decoder object.
    HuffmanDecoder decoder = new HuffmanDecoder();

    //Pass the encoded message to the decode method of the
    // HuffmanDecoder object.  Also pass a reference
    // to the  data structure containing encoding
    // particulars to the decode method.  Also pass the
    // length of the original message so that extraneous
    // characters on the end of the decoded message can be
    // eliminated.
    String decodedData = decoder.decode(binaryEncodedData,
                                        huffEncodeTable,
                                        rawDataLen); 

    //Display the decoded results, 48 characters to the
    // line
    System.out.println("nDecoded Data");
    System.out.println(decodedData);


  }//end main
  //-----------------------------------------------------//

  //Utility method to display a String 48 characters to
  // the line.
  static void display48(String data){
    for(int cnt = 0;cnt < data.length();cnt += 48){
      if((cnt + 48) < data.length()){
        //Display 48 characters.
        System.out.println(data.substring(cnt,cnt+48));
      }else{
        //Display the final line, which may be short.
        System.out.println(data.substring(cnt));
      }//end else
    }//end for loop
  }//end display48
  //-----------------------------------------------------//

  //Utility method to display hex data 48 characters to
  // the line
  static void hexDisplay48(
                        ArrayList<Byte> binaryEncodedData){
    int charCnt = 0;
    for(Byte element : binaryEncodedData){
      System.out.print(
               Integer.toHexString((int)element & 0X00FF));
      charCnt++;
      if(charCnt%24 == 0){
        System.out.println();//new line
        charCnt = 0;
      }//end if
    }//end for-each
  }//end hexDisplay48
  //-----------------------------------------------------//
}