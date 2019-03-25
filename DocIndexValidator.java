package org.dms.DMS.validator;

import org.dms.DMS.dao.DocIndexDao;
import org.dms.DMS.model.DocIndexVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component(value = "docIndexValidator")
public class DocIndexValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return	clazz == DocIndexVo.class;
	}
	
	@Autowired
	private DocIndexDao docIndexDao;

	@Override
	public void validate(Object target, Errors errors) {
		DocIndexVo docIndexVo =  (DocIndexVo) target;
		
		try {

			System.out.println("Inside DeptVo");
			
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "companyId", "docIndexVo.company.req");
			if (docIndexVo.getDeptId() == null) {
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "deptId", "docIndexVo.deptId.req");
			}
			if (docIndexVo.getDocTypeId() == null) {
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "docTypeId", "docIndexVo.docTypeId.req");
			}
			
			if (docIndexVo.getDocIndexTypeId() == null) {
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "docIndexTypeId", "docIndexVo.docIndexTypeId.req");
			}
			if (docIndexVo.getDocIndexTypeId() == 5) {
				if (docIndexVo.getIndexListMstId() == null) {
					ValidationUtils.rejectIfEmptyOrWhitespace(errors, "indexListMstId", "docIndexVo.indexListMstId.req");
				}

			}
//			else if(docIndexVo.getDocIndexTypeId() == 6) {
//			if (docIndexVo.getReminder() == null) {
//				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "reminder", "docIndexVo.reminder.req");
//			}
//		}

			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "docIndxNm", "docIndexVo.docIndxNm.req");
			
			}catch (Exception e) {
			e.printStackTrace();
		}
		

	}

}
