package com.webapp.ims.utils;
import java.text.DecimalFormat;

public class INRNumberFormat
{

    protected Double value;
    private static final String tensNames[] = {
        "", " Ten", " Twenty", " Thirty", " Forty", " Fifty", " Sixty", " Seventy", " Eighty", " Ninety"
    };
    private static final String numNames[] = {
        "", " One", " Two", " Three", " Four", " Five", " Six", " Seven", " Eight", " Nine", 
        " Ten", " Eleven", " Twelve", " Thirteen", " Fourteen", " Fifteen", " Sixteen", " Seventeen", " Eighteen", " Nineteen"
    };

    public INRNumberFormat()
    {
    }

    public static String getValue(Double value)
    {
        if(value == null)
        {
            value = new Double("0.0");
        }
        DecimalFormat df = new DecimalFormat("##0.00");
        StringBuffer sb = new StringBuffer();
        StringBuffer sbf = new StringBuffer();
        boolean sign = false;
        if(value.doubleValue() < 0.0D)
        {
            value = Double.valueOf(value.doubleValue() * -1D);
            sign = true;
        }
        int l = df.format(value).length();
        String s = df.format(value);
        int i = l - 1;
        for(int q = 0; i >= 0; q++)
        {
            sb.append(s.charAt(i));
            if(q >= 5 && (q + 1) % 2 == 0 && q != l - 1)
            {
                sb.append(',');
            }
            i--;
        }

        l = sb.length();
        if(sign)
        {
            sbf.append("-");
        }
        for(i = l - 1; i >= 0; i--)
        {
            sbf.append(sb.charAt(i));
        }

        return sbf.toString();
        
    }

    ///////////////////********
    
    public static String getValueStar(Double value)
    {
        if(value == null)
        {
            value = new Double("0.0");
        }
        DecimalFormat df = new DecimalFormat("##0.00");
        StringBuffer sb = new StringBuffer();
        StringBuffer sbf = new StringBuffer();
        boolean sign = false;
        if(value.doubleValue() < 0.0D)
        {
            value = Double.valueOf(value.doubleValue() * -1D);
            sign = true;
        }
        int l = df.format(value).length();
        String s = df.format(value);
        int i = l - 1;
        for(int q = 0; i >= 0; q++)
        {
            sb.append(s.charAt(i));
            if(q >= 5 && (q + 1) % 2 == 0 && q != l - 1)
            {
                sb.append(',');
            }
            i--;
        }

        l = sb.length();
        if(sign)
        {
            sbf.append("-");
        }
        for(i = l - 1; i >= 0; i--)
        {
            sbf.append(sb.charAt(i));
        }

       //return sbf.toString();
	    return paddingString(sbf.toString(), 21, "*", true);
    }
    //////////////////////////
    
    
    public static String getCountValue(Double value)
    {
        if(value == null)
        {
            value = new Double("");
        }
        DecimalFormat df = new DecimalFormat("##0");
        StringBuffer sb = new StringBuffer();
        StringBuffer sbf = new StringBuffer();

        int l = df.format(value).length();
        
        String s = df.format(value);
        int i = l - 1;
        for(int q = 0; i >= 0; q++)
        {
            sb.append(s.charAt(i));

            if(q >= 2 && (q + 2) % 2 == 0 && q != l - 1)
            {
                sb.append(',');
            }
            i--;
        }

        l = sb.length();

        for(i = l - 1; i >= 0; i--)
        {
            sbf.append(sb.charAt(i));
        }
        return sbf.toString();
        
    }
    
    
///////////////////***************
    
	public static String getValueFormattedS(Object value)
	{
		DecimalFormat df = new DecimalFormat("000");
		return df.format(value);
	}

    public static String getValueS(Object value)
    {
        DecimalFormat df = new DecimalFormat("0");
        return df.format(value);
    }

    private static String convertLessThanOneThousand(int number)
    {
        String soFar;
        if(number % 100 < 20)
        {
            soFar = numNames[number % 100];
            number /= 100;
        } else
        {
            soFar = numNames[number % 10];
            number /= 10;
            soFar = (new StringBuilder(String.valueOf(tensNames[number % 10]))).append(soFar).toString();
            number /= 10;
        }
        if(number == 0)
        {
            return soFar;
        } else
        {
            return (new StringBuilder(String.valueOf(numNames[number]))).append(" Hundred").append(soFar).toString();
        }
    }

    private static String convert(long number)
    {
        if(number == 0L)
        {
            return "Zero";
        }
        if(number < 0L)
        {
            number *= -1L;
        }
        String snumber = Long.toString(number);
        String mask = "0000000000";
        DecimalFormat df = new DecimalFormat(mask);
        snumber = df.format(number);
        int crores = Integer.parseInt(snumber.substring(0, 3));
        int lakhs = Integer.parseInt(snumber.substring(3, 5));
        int hundredThousands = Integer.parseInt(snumber.substring(5, 7));
        int thousands = Integer.parseInt(snumber.substring(7, 10));
        String tradcrores;
        switch(crores)
        {
        case 0: // '\0'
            tradcrores = "";
            break;

        case 1: // '\001'
            tradcrores = (new StringBuilder(String.valueOf(convertLessThanOneThousand(crores)))).append(" Crores ").toString();
            break;

        default:
            tradcrores = (new StringBuilder(String.valueOf(convertLessThanOneThousand(crores)))).append(" Crores ").toString();
            break;
        }
        String result = tradcrores;
        String tradlakhs;
        switch(lakhs)
        {
        case 0: // '\0'
            tradlakhs = "";
            break;

        case 1: // '\001'
            tradlakhs = (new StringBuilder(String.valueOf(convertLessThanOneThousand(lakhs)))).append(" Lakhs ").toString();
            break;

        default:
            tradlakhs = (new StringBuilder(String.valueOf(convertLessThanOneThousand(lakhs)))).append(" Lakhs ").toString();
            break;
        }
        result = (new StringBuilder(String.valueOf(result))).append(tradlakhs).toString();
        String tradHundredThousands;
        switch(hundredThousands)
        {
        case 0: // '\0'
            tradHundredThousands = "";
            break;

        case 1: // '\001'
            tradHundredThousands = "One Thousand ";
            break;

        default:
            tradHundredThousands = (new StringBuilder(String.valueOf(convertLessThanOneThousand(hundredThousands)))).append(" Thousand ").toString();
            break;
        }
        result = (new StringBuilder(String.valueOf(result))).append(tradHundredThousands).toString();
        String tradThousand = convertLessThanOneThousand(thousands);
        result = (new StringBuilder(String.valueOf(result))).append(tradThousand).toString();
        return result.replaceAll("^\\s+", "").replaceAll("\\b\\s{2,}\\b", " ");
    }

    public static String convertToWords(String amount)
    {
        DecimalFormat df = new DecimalFormat("0.00");
        DecimalFormat df1 = new DecimalFormat("0");
        Double amountd = new Double(df.format(Double.parseDouble(amount)));
        long amountl = amountd.longValue();
        Double amountAsPaise = new Double(amountd.doubleValue() - (double)amountl);
        Double paiseAs = new Double(df1.format(amountAsPaise.doubleValue() * 100D));
        long paise = paiseAs.longValue();
        return (new StringBuilder(String.valueOf(convert(amountl)))).append(" and ").append(convert(paise)).append(" Paise").toString();
    }
    
    public static String paddingString( String s, int n, String c , boolean paddingLeft  )
	  {
	    StringBuffer str = new StringBuffer(s);
	    int strLength  = str.length();
	    if ( n > 0 && n > strLength ) {
	      for ( int i = 0; i <= n ; i ++ ) {
	            if ( paddingLeft ) {
	              if ( i < n - strLength ) str.insert( 0, c );
	            }
	            else {
	              if ( i > strLength ) str.append( c );
	            }
	      }
	    }
	    return str.toString();
	  }

}
