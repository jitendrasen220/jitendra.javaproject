package org.dms.DMS.validator;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.dms.DMS.dao.CityDao;
import org.dms.DMS.model.CityVo;
import org.dms.DMS.util.AESencyrptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component(value = "cityValidator")
public class CityValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return clazz == CityVo.class;
	}
	@Autowired
	private CityDao cityDao;
	
	@Resource(name = "commonValidator")
	private CommonValidator commonValidator;

	@Override
	public void validate(Object target, Errors errors) {
		CityVo cityVo =  (CityVo) target;

		try {
			
		System.out.println("Inside Cityvo");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "countryId", "city.countryId.req");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "stateId", "city.stateId.req");
		
		if (StringUtils.isNotBlank(cityVo.getEncId())) {
			try {
				cityVo.setCityId(AESencyrptor.decryptId(cityVo.getEncId()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (StringUtils.isBlank(cityVo.getName())) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "city.name.req");
		} else {
			if (!(commonValidator.alpha(cityVo.getName()))) {
				errors.rejectValue("name", "city.name.inValid");
		      }else {
					Boolean isCityExist = cityDao.checkIsCityExist(cityVo.getName(), cityVo.getCityId());
		    	  if (isCityExist) {
						errors.rejectValue("name", "city.name.exist");
					}
		      }
		}
	}catch (Exception e) {
		e.printStackTrace();
	}
	}
}
