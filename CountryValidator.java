package org.dms.DMS.validator;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.dms.DMS.dao.CityDao;
import org.dms.DMS.model.CountryVo;
import org.dms.DMS.util.AESencyrptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component(value = "countryValidator")
public class CountryValidator implements Validator  {

	@Override
	public  boolean supports(Class<?> clazz) {
		return	clazz == CountryVo.class;
	}
	@Autowired
	private CityDao cityDao;

	@Resource(name = "commonValidator")
	private CommonValidator commonValidator;

	
	@Override
	public void validate(Object target, Errors errors) {
		
		CountryVo countryVo = (CountryVo) target;

		System.out.println("Inside City Validation");
		try {
			
			if (StringUtils.isNotBlank(countryVo.getEncId())) {
				countryVo.setCountryId(AESencyrptor.decryptId(countryVo.getEncId()));
			}
			
			if (StringUtils.isBlank(countryVo.getName())) {
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "country.name.req");
			} else {
				if (!(commonValidator.alpha(countryVo.getName()))) {
					errors.rejectValue("name", "country.name.inValid");
			      }else {
						Boolean isCountryExist = cityDao.checkIsCountryExist(countryVo.getName(), countryVo.getCountryId());
			    	  if (isCountryExist) {
							errors.rejectValue("name", "country.name.exist");
						}
			      }
			}
			if (StringUtils.isBlank(countryVo.getAlfaCode())) {
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "alfaCode", "country.alfaCode.req");
			} else {
				if (!(commonValidator.alpha(countryVo.getAlfaCode()))) {
					errors.rejectValue("alfaCode", "country.alfaCode.inValid");
			      }
			}
			
			if (StringUtils.isBlank(countryVo.getCode())) {
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "code", "country.code.req");
			} else {
				if (!(commonValidator.countryCode(countryVo.getCode()))) {
					errors.rejectValue("code", "country.code.inValid");
			      }
			}
			

			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}


/*	private Integer decryptId(String encId) throws Exception {
		Integer Id = null;
		if (StringUtils.isNotBlank(encId)) {
			String decId = AESencyrptor.decrypt(encId);
			Id = Integer.parseInt(decId);
		}

		return Id;
	}
*/



}
