package org.dms.DMS.validator;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.dms.DMS.dao.UserDAO;
import org.dms.DMS.entity.AccessOptions;
import org.dms.DMS.entity.Address;
import org.dms.DMS.entity.Users;
import org.dms.DMS.model.UserVo;
import org.dms.DMS.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component(value = "userValidator")
public class UserValidator implements Validator {

	@Autowired
	private UserDAO userDao;

	@Autowired
	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "commonValidator")
	private CommonValidator commonValidator;

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz == UserVo.class;
	}

	@SuppressWarnings("unlikely-arg-type")
	@Override
	public void validate(Object target, Errors errors) {

		Users usrVo = (Users) target;
		Address addVo = usrVo.getAddress();
		AccessOptions optVo = usrVo.getAccessOptions();

		if (StringUtils.isBlank(usrVo.getEmail())) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "userInfoVo.email.req");
		} else {
			if (!(commonValidator.email(usrVo.getEmail()))) {
				errors.rejectValue("email", "userInfoVo.email.inValid");
			}
		}
	
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "loginId", "user.loginId.req");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fistName", "userInfoVo.firstName.req");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "userInfoVo.lastName.req");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "empId", "user.empId.req");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address.add1", "userInfoVo.address.req");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "roleId", "user.role.req");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "companyId", "userInfoVo.company.req");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "deptId", "userInfoVo.department.req");
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address.add1", "userInfoVo.address.req");	
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address.cntryId", "userInfoVo.country.req");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address.stateId", "userInfoVo.state.req");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address.cityId", "userInfoVo.city.req");
		
		/*ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address.mobile", "userInfoVo.mobile.req");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address.telephone", "userInfoVo.telephone.req");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address.fax", "userInfoVo.fax.req");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address.mobileCd", "userInfoVo.mobileCd.req");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address.telephoneCd", "userInfoVo.telephoneCd.req");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address.faxCd", "userInfoVo.faxCd.req");*/

		if (StringUtils.isBlank(addVo.getMobileCd()) ) {
			errors.rejectValue("address.mobileCd", "userInfoVo.mobileCd.req");
		} else if (StringUtils.isBlank(addVo.getMobile()) ) {
			errors.rejectValue("address.mobile", "userInfoVo.mobile.req");
		} else if (!(commonValidator.numberFormat(addVo.getMobile()))) {
			errors.rejectValue("address.mobile", "userInfoVo.mobile.inValid");
	    }
		
		if (StringUtils.isNotBlank(addVo.getTelephone()) && !(commonValidator.numberFormat(addVo.getTelephone()))) {
			errors.rejectValue("address.telephone", "userInfoVo.telephone.inValid");
	    }
		
		if (StringUtils.isNotBlank(addVo.getFax()) && !(commonValidator.numberFormat(addVo.getFax()))) {
			errors.rejectValue("address.fax", "userInfoVo.fax.inValid");
		}
		
		if (StringUtils.isBlank(usrVo.getEncId()))
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "user.password.req");

		if (StringUtils.isNotBlank(usrVo.getLoginId())) {
			if (!(commonValidator.email(usrVo.getLoginId()))) {
				errors.rejectValue("loginId", "userInfoVo.loginId.inValid");
			} else {
				Boolean isLoginExist = userDao.checkIsLoginIDExist(usrVo);
				if (isLoginExist) {
					errors.rejectValue("loginId", "user.loginId.exist");
				}
			}
		}
		

		if (StringUtils.isNotBlank(usrVo.getEmpId())) {
			Boolean isLoginExist = userDao.checkIsEmpIdExist(usrVo);
			if (isLoginExist) {
				errors.rejectValue("empId", "user.empId.exist");
			}
		}
		/*if (StringUtils.isNotBlank(usrVo.getEmail())) {
			System.out.println("Insie checkIsCom Email exist");
			Boolean isLoginExist = userDao.checkIsEmailIdExist(usrVo);
			if (isLoginExist) {			
				errors.rejectValue("email", "user.email.exist");
			}
		}
		*/
		
		/*if (StringUtils.isNotBlank(usrVo.getOfficeType()) && StringUtils.equalsIgnoreCase(usrVo.getOfficeType(), "ADMIN")) {
			
		} else */if ( (optVo.getIsUpload() == null || optVo.getIsUpload() == 0)
				&& (optVo.getIsSearch() == null || optVo.getIsSearch() == 0) 
				&& (optVo.getIsReports() == null || optVo.getIsReports() == 0) 
				&& (optVo.getIsReminders() == null || optVo.getIsReminders() == 0)
				&& (optVo.getIsDocuments() == null || optVo.getIsDocuments() == 0) ) {
			errors.rejectValue("accessOptions.id", "user.acess.optn.req");
		}
		
/*	 if ( ( (optVo.getIsUpload() == 0 ) )){
			errors.rejectValue("accessOptions.id", "user.acess.optn.req");
		}*/

	}
}
