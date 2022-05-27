package com.cdgtaxi.ibs.util;

public class FieldFormatterUtil {

/** Creates new StringFormatter */
     public FieldFormatterUtil() 
     {
     }

     public static String formatAsString (String raw, int reqdLength, String reqdAlignment,
          String trimOff)
     {
          String str = "";
          int reqdPadCount, i;
          if (raw.length() < reqdLength)     // Raw string is shorter than required length
          {
               str = raw;
               reqdPadCount = reqdLength - raw.length();
               if ((reqdAlignment.equalsIgnoreCase("R")) || (reqdAlignment.equalsIgnoreCase("RIGHT")))
               {
                    for (i=0; i<reqdPadCount; i++)
                         str = " " + str;
               }
               else      // Default alignment is left
               {
                    for (i=0; i<reqdPadCount; i++)
                         str = str + " ";
               }
          }
          else      // Raw string is equal to or longer than required length
          {
               if ((trimOff.equalsIgnoreCase("L")) || (trimOff.equalsIgnoreCase("LEFT")))
                    str = raw.substring(raw.length()-reqdLength);
               else      // Default is to trim off the right end
                    str = raw.substring(0,reqdLength);
          }
          return str;
     }
     
     public static String formatAsNumber (String raw, int reqdLengthBeforeDecimal, 
          int reqdLengthAfterDecimal, boolean padFrontWithZeros,
          boolean padBackWithZeros, String reqdAlignment)
     {
          String str = "";
          String temp = "";
          int totalReqdLength, reqdPadCount, i;
          if (reqdLengthAfterDecimal>0)
               totalReqdLength = reqdLengthBeforeDecimal + reqdLengthAfterDecimal + 1;
          else
               totalReqdLength = reqdLengthBeforeDecimal;
          int dotpos = raw.indexOf(".");
          if (dotpos<0)       // No decimal point in raw string;
                              // whole raw string will be treated as portion before decimal pt
          {
               if (raw.length() < reqdLengthBeforeDecimal)  // Raw string is shorter than
                                                            // required length before decimal pt
               {
                    str = raw;
                    if (padFrontWithZeros)
                    {
                         reqdPadCount = reqdLengthBeforeDecimal - raw.length();
                         for (i=0; i<reqdPadCount; i++)
                              str = "0" + str;
                    }
                    else if (padBackWithZeros)
                    {
                        reqdPadCount = reqdLengthBeforeDecimal - raw.length();
                        for (i=0; i<reqdPadCount; i++)
                             str = str + "0";
                    }
               }
               else      // Raw string is equal to or longer than required length before decimal pt
               {
                    str = raw.substring(0, reqdLengthBeforeDecimal);
               }
          // Append decimal point and portion after decimal point if required
               if ((reqdLengthAfterDecimal > 0) && padBackWithZeros)
               {
                    str = str + ".";
                    for (i=0; i<reqdLengthAfterDecimal; i++)
                         str = str + "0";
               }
          }
          else      // Decimal point exists in raw string
          {
               if (dotpos < reqdLengthBeforeDecimal)   // Front portion of raw string is shorter than required
               {
                    str = raw.substring(0,dotpos+1);
                    if (padFrontWithZeros)
                    {
                         reqdPadCount = reqdLengthBeforeDecimal - dotpos;
                         for (i=0; i<reqdPadCount; i++)
                              str = "0" + str;
                    }
               }
               else      // Front portion of raw string is longer than required
               {
                    str = raw.substring(dotpos-reqdLengthBeforeDecimal,dotpos+1);
               }
               if (raw.length()-dotpos-1 < reqdLengthAfterDecimal)    // Back portion of raw string
                                                                      // is shorter than required
               {
                    temp = raw.substring(dotpos+1);
                    if ((reqdLengthAfterDecimal > 0) && padBackWithZeros)
                    {
                         reqdPadCount = reqdLengthAfterDecimal - temp.length();
                         for (i=0; i<reqdPadCount; i++)
                              temp = temp + "0";
                    }
                    str = str.concat(temp);
               }
               else      // Back portion of raw string is longer than required
               {
                    temp = raw.substring(dotpos+1,dotpos+reqdLengthAfterDecimal+1);
                    str = str.concat(temp);
               }
          }
     // Perform alignment if string length is still less than total required length
          if (str.length() < totalReqdLength)
          {
               reqdPadCount = totalReqdLength - str.length();
               if ((reqdAlignment.equalsIgnoreCase("R")) || (reqdAlignment.equalsIgnoreCase("RIGHT")))
               {
                    for (i=0; i<reqdPadCount; i++)
                         str = " " + str;
               }
               else      // Default alignment is left
               {
                    for (i=0; i<reqdPadCount; i++)
                         str = str + " ";
               }
          }
          return str;
     }
     
     /*
      * Remove Non-AlphaNumeric Characters in String
      */
     public static String removeNonAlphaNumeric(String sInput)
     {
        int length = sInput.length();
        String sOutput="";

        for(int i=0;i<length;i++)
        {            
            if(Character.isLetterOrDigit(sInput.charAt(i)))
               sOutput = sOutput + String.valueOf(sInput.charAt(i));            
        }

        return sOutput;
     }
}
