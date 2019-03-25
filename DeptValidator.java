package org.dms.DMS.validator;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.dms.DMS.dao.DeptDao;
import org.dms.DMS.model.DeptVo;
import org.dms.DMS.util.AESencyrptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component(value = "deptValidator")
public class DeptValidator implements Validator {
	
	@Override
	public  boolean supports(Class<?> clazz) {
		return	clazz == DeptVo.class;
	}
	@Autowired
	private DeptDao deptDao;
	
	@Resource(name = "commonValidator")
	private CommonValidator commonValidator;

	@Override
	public void validate(Object target, Errors errors) {
		DeptVo deptVo =  (DeptVo) target;

		try {
			
		System.out.println("Inside DeptVo");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "companyId", "dept.company.req");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "code", "dept.code.req");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "dept.description.req");

		if (StringUtils.isNotBlank(deptVo.getEncId())) {																					
			try {
				deptVo.setDepartmentId(AESencyrptor.decryptId(deptVo.getEncId()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (StringUtils.isBlank(deptVo.getName())) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "dept.name.req");
		} else {
			if (!(commonValidator.alpha(deptVo.getName()))) {
				errors.rejectValue("name", "dept.name.inValid");
		      }else {
					Boolean isDeptExist = deptDao.checkIsDepartmentExist(deptVo.getName(), deptVo.getDepartmentId(), deptVo.getCompanyId());
		    	  if (isDeptExist) {
						errors.rejectValue("name", "dept.name.exist");
					}
		      }
		}
	}catch (Exception e) {
		e.printStackTrace();
	}
		
	}

}
