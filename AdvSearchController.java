package org.dms.DMS.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.dms.DMS.model.DocIndexVo;
import org.dms.DMS.service.DeptService;
import org.dms.DMS.util.DMSBaseController;
import org.dms.DMS.validator.DocIndexValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Controller
@Transactional
@EnableWebMvc
public class AdvSearchController extends DMSBaseController {
	
	private static final Logger logger = Logger.getLogger(AdvSearchController.class);

	@Autowired

	@Resource(name = "deptService")
	private DeptService deptService;
	
	@Qualifier(value = "docIndexValidator")
	private DocIndexValidator docIndexValidator;
	
	@InitBinder
	public void myInitBinder(WebDataBinder dataBinder) {
		Object target = dataBinder.getTarget();
		if (target == null) {
			return;
		}
		if (target.getClass() == DocIndexVo.class) {
			dataBinder.setValidator(docIndexValidator);
		}
	}
	
	@RequestMapping(value = { "/advSearch" })
	public String getAdvSearchWithPagination(Model model,
		@RequestParam(value = "name", defaultValue = "") String likeName,
		@RequestParam(value = "page", defaultValue = "1") int page) {
		logger.info("Enter getAdvSearchWithPagination()..page->" + page);
		final int maxResult = 10;
		final int maxNavigationPage = 10;
		model.addAttribute("docIndexVo", new DocIndexVo());
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("deptList", deptService.getAllDept());
		model.mergeAttributes(returnMap);
		logger.info("Exit getAdvSearchWithPagination()..page->" + page);
		return "advSearch/advSearch";
	}	
}
