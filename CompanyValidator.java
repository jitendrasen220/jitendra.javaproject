package org.dms.DMS.validator;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.dms.DMS.dao.CompanyDao;
import org.dms.DMS.entity.Address;
import org.dms.DMS.entity.Company;
import org.dms.DMS.model.CompanyVo;
import org.dms.DMS.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component(value = "companyValidator")
public class CompanyValidator implements Validator {

	@Autowired
	private CompanyDao companyDao;

	@Autowired
	@Resource(name = "companyService")
	private CompanyService companyService;

	@Resource(name = "commonValidator")
	private CommonValidator commonValidator;

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz == CompanyVo.class;
	}

	@SuppressWarnings("unlikely-arg-type")
	@Override
	public void validate(Object target, Errors errors) {

		Company compVo = (Company) target;
		Address infoVo = compVo.getAddress();

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "companyCode", "company.code.req");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "companyName", "company.name.req");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "domain", "company.domain.req");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "company.email.req");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "url", "company.url.req");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address.add1", "company.address.req");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address.cntryId", "userInfoVo.country.req");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address.stateId", "userInfoVo.state.req");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address.cityId", "userInfoVo.city.req");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address.telephone", "userInfoVo.telephone.req");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address.fax", "userInfoVo.fax.req");

		if (StringUtils.isNotBlank(compVo.getCompanyCode())) {
			System.out.println("Insie checkIsCom code exist");
			Boolean isLoginExist = companyDao.checkIsCompanyCodeExist(compVo);
			if (isLoginExist) {			
				errors.rejectValue("companyCode", "company.code.exist");
			}
		}

		if (StringUtils.isNotBlank(compVo.getCompanyName())) {
			System.out.println("Insie checkIsCom name exist");
			Boolean isLoginExist = companyDao.checkIsCompNameExist(compVo);
			if (isLoginExist) {				
				errors.rejectValue("companyName", "company.name.exist");
			}
		}

		if (StringUtils.isNotBlank(compVo.getUrl())) {
			System.out.println("Insie checkIsCom name exist");
			Boolean isLoginExist = companyDao.checkIsUrlExist(compVo);
			if (isLoginExist) {				
				errors.rejectValue("url", "company.url.exist");
			}
		}

		if (StringUtils.isNotBlank(compVo.getDomain())) {
			System.out.println("Insie checkIsCom code exist");
			Boolean isLoginExist = companyDao.getCompanywithDomain(compVo);
			if (isLoginExist) {				
				errors.rejectValue("domain", "company.domain.exist");
			}
		}
		
		if (StringUtils.isNotBlank(compVo.getEmail())) {
			System.out.println("Insie checkIsCom Email exist");
			Boolean isLoginExist = companyDao.checkIsCompEmailExist(compVo);
			if (isLoginExist) {			
				errors.rejectValue("email", "company.email.exist");
			}
		}

	}
}
