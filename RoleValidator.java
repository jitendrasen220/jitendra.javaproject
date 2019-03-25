package org.dms.DMS.validator;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.dms.DMS.dao.RoleDao;
import org.dms.DMS.model.RoleVo;
import org.dms.DMS.util.AESencyrptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component(value = "roleValidator")

public class RoleValidator implements Validator {

	
	@Override
	public  boolean supports(Class<?> clazz) {
		return	clazz == RoleVo.class;
	}
	@Autowired
	private RoleDao roleDao;
	
	@Resource(name = "commonValidator")
	private CommonValidator commonValidator;

	@Override
	public void validate(Object target, Errors errors) {
		RoleVo roleVo =  (RoleVo) target;

		try {
			
		System.out.println("Inside RoleVo");
		
		if (roleVo.getDeptId() == null) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "deptId", "role.deptId.req");
		}
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "companyId", "role.company.req");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "roleDesc", "role.roleDesc.req");


		if (StringUtils.isBlank(roleVo.getRoleNm())) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "roleNm", "role.roleNm.req");
		} else {
			if (!(commonValidator.code(roleVo.getRoleNm()))) {
				errors.rejectValue("roleNm", "role.roleNm.req");
		      }else {
					Boolean isRoleExist = roleDao.checkIsRoleExist(roleVo.getRoleNm(), roleVo.getRoleId(), roleVo.getDeptId(), roleVo.getCompanyId());
		    	  if (isRoleExist) {
						errors.rejectValue("roleNm", "role.roleNm.exist");
					}
		      }
		}
		
	}catch (Exception e) {
		e.printStackTrace();
	}
		
	}
}
