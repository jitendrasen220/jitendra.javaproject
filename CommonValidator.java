package org.dms.DMS.validator;

import java.util.regex.Pattern;

import org.dms.DMS.util.Constants;
import org.springframework.stereotype.Component;

@Component(value = "commonValidator")
public class CommonValidator {

	public boolean alpha(String alpha) {
		Pattern format = Pattern.compile(Constants.Validator.Alpha,Pattern.CASE_INSENSITIVE);
		if (!(format.matcher(alpha).matches())) {
	    	  return false;
	      }
		return true;
	}
	
	public boolean alphaNum (String alphaNum) {
		Pattern format = Pattern.compile(Constants.Validator.AlphaNum,Pattern.CASE_INSENSITIVE);
		if (!(format.matcher(alphaNum).matches())) {
	    	  return false;
	      }
		return true;
	}
	
	public boolean code(String code) {
		Pattern format = Pattern.compile(Constants.Validator.Code,Pattern.CASE_INSENSITIVE);
		if (!(format.matcher(code).matches())) {
	    	  return false;
	      }
		return true;
	}

	public boolean panNum (String panNum) {
		Pattern format = Pattern.compile(Constants.Validator.PanNum,Pattern.CASE_INSENSITIVE);
		if (!(format.matcher(panNum).matches())) {
	    	  return false;
	      }
		return true;
	}
	
	public boolean mobNum(String mobNum) {
		Pattern format = Pattern.compile(Constants.Validator.MobNum,Pattern.CASE_INSENSITIVE);
		if (!(format.matcher(mobNum).matches())) {
	    	  return false;
	      }
		return true;
	}
	
	public boolean telNum (String telNum) {
		Pattern format = Pattern.compile(Constants.Validator.TelNum,Pattern.CASE_INSENSITIVE);
		if (!(format.matcher(telNum).matches())) {
	    	  return false;
	      }
		return true;
	}
	
	public boolean email(String email) {
		Pattern format = Pattern.compile(Constants.Validator.Email,Pattern.CASE_INSENSITIVE);
		if (!(format.matcher(email).matches())) {
	    	  return false;
	      }
		return true;
	}
	
	public boolean numberFloat(String numberFloat) {
		Pattern format = Pattern.compile(Constants.Validator.NumberFloat,Pattern.CASE_INSENSITIVE);
 		if (!(format.matcher(numberFloat).matches())) {
	    	  return false;
	      }
		return true;
	}
	
	public boolean rate(String rate) {
		Pattern format = Pattern.compile(Constants.Validator.Rate,Pattern.CASE_INSENSITIVE);
 		if (!(format.matcher(rate).matches())) {
	    	  return false;
	      }
		return true;
	}
	
	public boolean countryCode (String countryCode) {
		Pattern format = Pattern.compile(Constants.Validator.CountryCode,Pattern.CASE_INSENSITIVE);
		if (!(format.matcher(countryCode).matches())) {
	    	  return false;
	      }
		return true;
	}
	
	public boolean numberFormat (String numberFormat) {
		Pattern format = Pattern.compile(Constants.Validator.NumberFormat,Pattern.CASE_INSENSITIVE);
		if (!(format.matcher(numberFormat).matches())) {
	    	  return false;
	      }
		return true;
	}
}
