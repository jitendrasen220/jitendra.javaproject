package org.dms.DMS.util;

import java.text.DecimalFormat;


/**
 * @author E & A
 *
 */
public class EnglishNumberToWords {

	private static final String[] tensNames = {"", " Ten", " Twenty", " Thirty", " Forty",
	    " Fifty", " Sixty", " Seventy", " Eighty", " Ninety" };

	  private static final String[] uptoTeenNames = {"", " One", " Two", " Three", " Four", " Five",
	    " Six", " Seven", " Eight", " Nine", " Ten", " Eleven", " Twelve", " Thirteen", " Fourteen",
	    " Fifteen", " Sixteen", " Seventeen", " Eighteen", " Nineteen" };

	  /**
	   * This method converts English number less then one thousand to words
	   * @param number
	   * @return
	   */
	  private static String convertLessThanOneThousand(int number) {
		    String numInWords;

		    if (number % 100 < 20){
		      numInWords = uptoTeenNames[number % 100];
		      number /= 100;
		    } else {
		      numInWords = uptoTeenNames[number % 10];
		      number /= 10;

		      numInWords = tensNames[number % 10] + numInWords;
		      number /= 10;
		    }
		    if (number == 0) return numInWords;
			return uptoTeenNames[number] + " Hundred" + numInWords;
		  }
	  
	  /**
	   * This method converts English number to words
	   * range is 0 to 99,99,99,999
	   * @param number
	   * @return
	   */
	  public static String convertNumberToWords(long number) {
		  // 0 to 99 99 99 999
		  if (number == 0) { return "zero"; }

		    String snumber = Long.toString(number);

		    // pad with "0"
		    String mask = "000000000";
		    DecimalFormat df = new DecimalFormat(mask);
		    snumber = df.format(number);
		    
		    // XXnnnnnnn 
		    int tenCrore = Integer.parseInt(snumber.substring(0,2));
		    // nnXXnnnnn
		    int tenLacs  = Integer.parseInt(snumber.substring(2,4)); 
		    // nnnnXXnnn
		    int tenThousands = Integer.parseInt(snumber.substring(4,6)); 
		    // nnnnnnXXX
		    int thousands = Integer.parseInt(snumber.substring(6,9));    

		    // Convert Crore part
		    String convertCrore;
		    switch (tenCrore) {
		    case 0:
		    	convertCrore = "";
		    	break;
		    case 1 :
		    	convertCrore = convertLessThanOneThousand(tenCrore) + " Crore ";
		    	break;
		    default :
		    	convertCrore = convertLessThanOneThousand(tenCrore) + " Crore ";
		    }
		    String result =  convertCrore;
		    
		    // Convert Lacs part
		    String convertLacs;
		    switch (tenLacs) {
		    case 0:
		    	convertLacs = "";
		    	break;
		    case 1 :
		    	convertLacs = convertLessThanOneThousand(tenLacs) + " Lacs ";
		    	break;
		    default :
		    	convertLacs = convertLessThanOneThousand(tenLacs) + " Lacs ";
		    }
		    result =  result + convertLacs;
		    
		    // Convert Thousands part
		    String convertTenThousands;
		    switch (tenThousands) {
		    case 0:
		    	convertTenThousands = "";
		    	break;
		    case 1 :
		    	convertTenThousands = convertLessThanOneThousand(tenThousands) + " Thousand ";
		    	break;
		    default :
		    	convertTenThousands = convertLessThanOneThousand(tenThousands) + " Thousand ";
		    }
		    result =  result + convertTenThousands;

		    // Convert Thousands part
		    String convertThousand;
		    convertThousand = convertLessThanOneThousand(thousands);
		    result =  result + convertThousand + " Only";

		    // remove extra spaces!
		    return result.replaceAll("^\\s+", "").replaceAll("\\b\\s{2,}\\b", " ");

	  }


}
