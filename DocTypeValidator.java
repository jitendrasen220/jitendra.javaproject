package org.dms.DMS.validator;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.dms.DMS.dao.DocTypeDao;
import org.dms.DMS.model.DocTypeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component(value = "docTypeValidator")
public class DocTypeValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return clazz == DocTypeVo.class;
	}
	
	@Autowired
	private DocTypeDao docTypeDao;
	
	@Resource(name = "commonValidator")
	private CommonValidator commonValidator;
	
	@Override
	public void validate(Object target, Errors errors) {
		
		DocTypeVo docTypeVo = (DocTypeVo) target;
		
		if (docTypeVo.getDeptId() == null) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "deptId", "docType.deptId.req");
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "companyId", "docType.company.req");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "docTypeCode", "docType.docTypecode.req");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "docTypeDesc", "docTypeDesc.docTypeDesc.req");
		
		if (StringUtils.isBlank(docTypeVo.getDocTypeName())) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "docTypeName", "docType.docTypeName.req");
		} else {
			if (!(commonValidator.alpha(docTypeVo.getDocTypeName()))) {
				errors.rejectValue("docTypeName", "docType.docTypeName.req");
		      }else {
					Boolean isRoleExist = docTypeDao.checkIsDocTypeExist(docTypeVo.getDocTypeName(), docTypeVo.getDocTypeId(), docTypeVo.getDeptId(), docTypeVo.getCompanyId());
		    	  if (isRoleExist) {
						errors.rejectValue("docTypeName", "docType.docTypeName.exist");
					}
		      }
		}
	}
}
