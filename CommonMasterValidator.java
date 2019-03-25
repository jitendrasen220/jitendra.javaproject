package org.dms.DMS.validator;
/*package org.dms.canaraDMS.validator;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.dms.canaraDMS.dao.CommonMasterDao;
import org.dms.canaraDMS.model.CommonMasterSubVo;
import org.dms.canaraDMS.model.CommonMasterVo;
import org.dms.canaraDMS.model.CurrencyMasterVo;
import org.dms.canaraDMS.service.CommonMasterService;
import org.dms.canaraDMS.util.AESencyrptor;
import org.dms.canaraDMS.util.CommonUtil;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component(value = "commonMasterValidator")
public class CommonMasterValidator implements Validator {

	@Resource(name = "commonMasterService")
	private CommonMasterService commonMasterService;
	
	@Resource(name = "commonValidator")
	private CommonValidator commonValidator;
	
	@Resource(name = "commonMasterDao")
	private CommonMasterDao commonMasterDao;

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz == CommonMasterVo.class;
	}

	public void validate(Object target, Errors errors) {
		CommonMasterVo commonMasterVo = (CommonMasterVo) target;
		try {
			if (!commonMasterService.isCmmnMstEditable(commonMasterVo)) {
				errors.rejectValue("error", "commonMaster.error.req");
			} else {

				if (StringUtils.isNotBlank(commonMasterVo.getEncComMstId())) {
					commonMasterVo.setCommonMstId(AESencyrptor.decryptId(commonMasterVo.getEncComMstId()));
				}

				
				 * ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mstType",
				 * "commonMaster.mstType.req");
				 
				if (!CommonUtil.isObjectNotEmpty(commonMasterVo.getMstValue())) {
					ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mstValue", "commonMaster.mstValue.req");
				} else {
					if (!(commonValidator.alpha(commonMasterVo.getMstValue()))) {
						errors.rejectValue("mstValue", "commonMaster.mstValue.valid");
				      } else {
				    	  Boolean isMasterExist = commonMasterDao.isMasterExist(commonMasterVo.getMstValue(), commonMasterVo.getCommonMstId());
				    	  if(isMasterExist) {
				    		  errors.rejectValue("mstValue", "commonMaster.error.Master.exist");
				    	  }
				      }
					
				}
					

				int i = 0;
				List<CommonMasterSubVo> newSubCommonMasterList = new ArrayList<>();

				for (CommonMasterSubVo subComVo : commonMasterVo.getSubCommonMasterList()) {
					if (CommonUtil.isObjectNotEmpty(subComVo)) {
						if (StringUtils.isBlank(subComVo.getCode())) {
							errors.rejectValue("subCommonMasterList[" + i + "].code", "commonMaster.code.req");
						} else {
							if (!(commonValidator.code(subComVo.getCode()))) {
								errors.rejectValue("subCommonMasterList[" + i + "].code", "commonMaster.code.valid");
						      } else {
						    	  for (CommonMasterSubVo newVo : newSubCommonMasterList) {
						    		  if (newVo.getCode()!=null) {
											
											String str1 = subComVo.getCode();
											String str2 = newVo.getCode();
											
											boolean isEqual = str1.equals(str2);
											if (isEqual) {
												errors.rejectValue("subCommonMasterList[" + i + "].code", "commonMaster.error.code.exist");
												newVo.setIsRowEmpty(false);
												subComVo.setIsRowEmpty(false);
												break;
											}
						    		  }
						    	  }
						      }
						}
						if (StringUtils.isBlank(subComVo.getValue())) {
							errors.rejectValue("subCommonMasterList[" + i + "].value", "commonMaster.value.req");
						} else {
							if (!(commonValidator.alpha(subComVo.getValue()))) {
								errors.rejectValue("subCommonMasterList[" + i + "].value", "commonMaster.value.valid");
						      }
						}
						i++;
						newSubCommonMasterList.add(subComVo);
					}
				}
				commonMasterVo.setSubCommonMasterList(newSubCommonMasterList);

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
*/