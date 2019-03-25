package org.dms.DMS.validator;

import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.dms.DMS.dao.CityDao;
import org.dms.DMS.model.StateVo;
import org.dms.DMS.util.AESencyrptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component(value = "stateValidator")
public class StateValidator implements Validator  {

	@Override
	public  boolean supports(Class<?> clazz) {
		return	clazz == StateVo.class;
	}
	@Autowired
	private CityDao cityDao;
	
	@Resource(name = "commonValidator")
	private CommonValidator commonValidator;

	@Override
	public void validate(Object target, Errors errors) {
		
		StateVo stateVo = (StateVo) target;
		
		System.out.println("Inside City Validation");
		try {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "countryId", "state.countryId.req");
		//	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "state.name.req");
			
			if (StringUtils.isNotBlank(stateVo.getEncId())) {
				stateVo.setStateId(AESencyrptor.decryptId(stateVo.getEncId()));
			}
			 
			if (StringUtils.isBlank(stateVo.getName())) {
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "state.name.req");
			} else {
				if (!(commonValidator.alpha(stateVo.getName()))) {
					errors.rejectValue("name", "state.name.inValid");
			      }else {
						Boolean isStateExist = cityDao.checkIsStateExist(stateVo.getName(), stateVo.getStateId());
			    	  if (isStateExist) {
							errors.rejectValue("name", "state.name.exist");
						}
			      }
			}
			  
		}
		
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	

}
