package org.dms.DMS.ajaxController;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dms.DMS.entity.IndexListMst;
import org.dms.DMS.model.DocTypeVo;
import org.dms.DMS.service.DocIndexService;
import org.dms.DMS.service.DocTypeService;
import org.dms.DMS.util.ApplicationContextHelper;
import org.dms.DMS.util.Constants;
import org.dms.DMS.util.DMSBaseController;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Transactional
@EnableWebMvc
public class DocIndexAjaxController extends DMSBaseController  {
	private static final Logger logger = Logger.getLogger(DocIndexAjaxController.class);
	
	DocTypeService docTypeService = null;
	DocIndexService docIndexService = null;

	public List<DocTypeVo> getDocTypByDeptId(Integer deptId) {
		logger.info("Entering getDocTypByDeptId() ");
		
		List<DocTypeVo> docTypList =  new ArrayList<>();
		try {
			docTypeService = ApplicationContextHelper.getInstance().getManagedBean("docTypeService", DocTypeService.class);
			docTypList = docTypeService.getDocTypByDeptId(deptId);
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION , e);
		}
		logger.info("Exit getDocTypByDeptId() ");
		return docTypList;
	}
	
	public Integer saveDrpDwnData(String indxMstNm, String indxSubVal) {
		logger.info("Entering saveDrpdown() ");
		Integer mstId = 0;

		try {
			docIndexService = ApplicationContextHelper.getInstance().getManagedBean("docIndexService", DocIndexService.class);
			
			mstId = docIndexService.saveDrpDwn(indxMstNm, indxSubVal);
 		} catch (Exception e) {
			logger.error(Constants.EXCEPTION , e);
		}
		logger.info("Exit saveDrpdown() ");
		return mstId;
	}
	

}
