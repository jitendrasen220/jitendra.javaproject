package org.dms.DMS.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dms.DMS.exceptions.DMSException;
import org.dms.DMS.model.DeptVo;
import org.dms.DMS.model.DocIndexVo;
import org.dms.DMS.model.PaginationResult;
import org.dms.DMS.model.RoleVo;
import org.dms.DMS.model.UserVo;
import org.dms.DMS.service.CityService;
import org.dms.DMS.service.DeptService;
import org.dms.DMS.service.DocIndexService;
import org.dms.DMS.service.UserService;
import org.dms.DMS.util.AESencyrptor;
import org.dms.DMS.util.Constants;
import org.dms.DMS.util.DMSBaseController;
import org.dms.DMS.validator.DocIndexValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Transactional
@EnableWebMvc
public class DocIndexController extends DMSBaseController {

	private static final Logger logger = Logger.getLogger(DocIndexController.class);

	@Autowired


	@Qualifier(value = "docIndexValidator")
	private DocIndexValidator docIndexValidator;
	
	@Resource(name = "deptService")
	private DeptService deptService;
	
	@Resource(name = "docIndexService")
	private DocIndexService docIndexService;
	
	@Autowired
	@Resource(name = "cityService")
	private CityService cityService;
	
	@Autowired
	@Resource(name = "userService")
	private UserService userService;
	
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
	
	@RequestMapping(value = { "/docIndexList" })
	public String getDocIndexDtlsWithPagination(Model model,
		@RequestParam(value = "name", defaultValue = "") String likeName,
		@RequestParam(value = "page", defaultValue = "1") int page) {
		logger.info("Enter getDocIndexDtlsWithPagination()..page->" + page);
		final int maxResult = 10;
		final int maxNavigationPage = 10;
		
        UserVo usrDtls = getUserSession();		
		int admPower = usrDtls.getAdminPower();
		int companyId = usrDtls.getCompanyId();		
		
		PaginationResult<DocIndexVo> result = docIndexService.getDocIndexDtlsWithPagination(page, maxResult, maxNavigationPage, likeName,admPower,companyId);
		model.addAttribute("paginationDocIndex", result);
		model.addAttribute("docIndexVo", new DocIndexVo());
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("deptList", deptService.getAllDept());
		model.addAttribute("power", usrDtls.getAdminPower());
		
		if (usrDtls.getAdminPower() == 2) {
			model.addAttribute("companyList", cityService.getAllCompany());
		} else if (usrDtls.getAdminPower() == 1) {
			List<DeptVo> deptList = cityService.getDeptIdBycompanyId(usrDtls.getCompanyId());
			model.addAttribute("departmentList", deptList);
		} else {
			/*List<DocTypeVo> docTypeList = documentService.getDocTypeByDept(usrVo.getDepartmentVo().getDepartmentId());
			model.addAttribute("docTypeList", docTypeList);*/
			List<DeptVo> deptList = cityService.getDeptIdBycompanyId(usrDtls.getCompanyId());
			model.addAttribute("departmentList", deptList);
		}model.mergeAttributes(returnMap);
		logger.info("Exit getDocIndexDtlsWithPagination()..page->" + page);
		return "docIndex/docIndexList";
	}	
	
	@RequestMapping(value = { "/docIndexCreate" }, method = RequestMethod.GET)
	public String newDocIndex(HttpServletRequest request, Model model) throws DMSException {
	logger.info("Enter into newDocIndex()");
		DocIndexVo docIndexVo = new DocIndexVo();
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		model.addAttribute("docIndexVo", docIndexVo);
		returnMap.put("docIndxTypList", docIndexService.getAlldocIndxTypList());
		//returnMap.put("deptList", deptService.getAllDept());
		returnMap.put("indxMstList", docIndexService.getAllIndxMstList());
		UserVo usrDtls = getUserSession();
		if(usrDtls.getAdminPower()==Constants.SUPER_ADMIN_POWER) {				
			returnMap.put("companyList", cityService.getAllCompany());
		} else {
			returnMap.put("companyList", userService.getPerticularUserCompany(usrDtls.getCompanyId()));
		}
		//returnMap.put("companyList", cityService.getAllCompany());
		model.mergeAttributes(returnMap);
		logger.info("Exit From newDocIndex()");
		return "docIndex/docIndexCreate";
	}
	
	@RequestMapping(value = { "/docIndexSave" }, method = RequestMethod.POST)
	// Avoid UnexpectedRollbackException (See more explanations)
	@Transactional(propagation = Propagation.NEVER)
	public String saveDocIndex(Model model, @ModelAttribute("docIndexVo") @Validated DocIndexVo vo, BindingResult result,
			final RedirectAttributes redirectAttributes) throws DMSException {
		logger.info("Enter saveDocIndex()..");

		Map<String, Object> returnMap = new HashMap<String, Object>();

		if (result.hasErrors()) {
			returnMap.put("createSuccessFlag", false);
			returnMap.put("docIndxTypList", docIndexService.getAlldocIndxTypList());
			//returnMap.put("deptList", deptService.getAllDept());
			returnMap.put("indxMstList", docIndexService.getAllIndxMstList());
			UserVo usrDtls = getUserSession();
			if(usrDtls.getAdminPower()==Constants.SUPER_ADMIN_POWER) {				
				returnMap.put("companyList", cityService.getAllCompany());
			} else {
				returnMap.put("companyList", userService.getPerticularUserCompany(usrDtls.getCompanyId()));
			}
			//returnMap.put("companyList", cityService.getAllCompany());
			model.mergeAttributes(returnMap);

			logger.info("Exit saveDocIndex()..");
			return "docIndex/docIndexCreate";
		}
		try {
			/*setDefaultValue(vo);*/
			Integer id = docIndexService.saveDocIndex(vo);
			redirectAttributes.addFlashAttribute("id", AESencyrptor.encrypt(id.toString()));
			redirectAttributes.addFlashAttribute("createSuccessFlag", true);
		} catch (Exception e) {
			e.printStackTrace();
			returnMap.put("createSuccessFlag", false);
			UserVo usrDtls = getUserSession();
			if(usrDtls.getAdminPower()==Constants.SUPER_ADMIN_POWER) {				
				returnMap.put("companyList", cityService.getAllCompany());
			} else {
				returnMap.put("companyList", userService.getPerticularUserCompany(usrDtls.getCompanyId()));
			}
			//returnMap.put("companyList", cityService.getAllCompany());
			model.mergeAttributes(returnMap);

			logger.info("Exit saveDocIndex()..");
			return "docIndex/docIndexCreate";
		}

		logger.info("Exit saveDocIndex()..");
		return "redirect:/docIndexView";
	}
	@RequestMapping(value = { "/docIndexView" })
	public String viewDocIndex(HttpServletRequest request, Model model, @ModelAttribute("docIndexVo") DocIndexVo vo, BindingResult result) {
		logger.info("Enter viewDocIndex()..");

		logger.info("Exit viewDocIndex()..");
		return loadExistingDocIndex(vo, model, result, true);
	}
	
	private String loadExistingDocIndex(DocIndexVo vo, Model model, BindingResult result, boolean readonly) {
		logger.info("Entering loadExistingDocIndex() ..");

		String prmId = (String) model.asMap().get("id");
		Map<String, Object> returnMap = model.asMap();

		try {

			if (StringUtils.isNotBlank(vo.getDocIndxEncId())) {
				vo.setDocIndxId(decryptId(vo.getDocIndxEncId()));
			}
			if (StringUtils.isNotBlank(prmId) && StringUtils.isBlank(vo.getDocIndxEncId())) {
				vo.setDocIndxId(decryptId(prmId));
				vo.setDocIndxEncId(prmId);
			}		
		returnMap = docIndexService.getDocIndexDtlsByVo(vo);

		model.addAttribute("docIndexVo", vo);
		returnMap.put("docIndxTypList", docIndexService.getAlldocIndxTypList());
		//returnMap.put("deptList", deptService.getAllDept());
		returnMap.put("indxMstList", docIndexService.getAllIndxMstList());
		UserVo usrDtls = getUserSession();
		if(usrDtls.getAdminPower()==Constants.SUPER_ADMIN_POWER) {				
			returnMap.put("companyList", cityService.getAllCompany());
		} else {
			returnMap.put("companyList", userService.getPerticularUserCompany(usrDtls.getCompanyId()));
		}
		//returnMap.put("companyList", cityService.getAllCompany());
		returnMap.put("readonly", readonly);
		model.mergeAttributes(returnMap);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	logger.info("Exit loadExistingDocIndex()..");
	return "docIndex/viewAndEditDocIndex";	
	}

	@RequestMapping(value = { "/drpDwnSave" }, method = RequestMethod.POST)
	// Avoid UnexpectedRollbackException (See more explanations)
	@Transactional(propagation = Propagation.NEVER)
	public String saveDrpDwn(Model model, @ModelAttribute("docIndexVo")  DocIndexVo vo, BindingResult result,
			final RedirectAttributes redirectAttributes) throws DMSException {
		logger.info("Enter saveDrpDwn()..");

		Map<String, Object> returnMap = new HashMap<String, Object>();

		if (result.hasErrors()) {
			returnMap.put("createSuccessFlag", false);
			returnMap.put("docIndxTypList", docIndexService.getAlldocIndxTypList());
			//returnMap.put("deptList", deptService.getAllDept());
			returnMap.put("indxMstList", docIndexService.getAllIndxMstList());
			UserVo usrDtls = getUserSession();
			if(usrDtls.getAdminPower()==Constants.SUPER_ADMIN_POWER) {				
				returnMap.put("companyList", cityService.getAllCompany());
			} else {
				returnMap.put("companyList", userService.getPerticularUserCompany(usrDtls.getCompanyId()));
			}
			//returnMap.put("companyList", cityService.getAllCompany());
			model.mergeAttributes(returnMap);
			logger.info("Exit saveDocIndex()..");
			return "docIndex/docIndexCreate";
		}
		try {
			/*setDefaultValue(vo);*/
			Integer id = docIndexService.saveDrpDwn(vo);
			redirectAttributes.addFlashAttribute("id", AESencyrptor.encrypt(id.toString()));
			redirectAttributes.addFlashAttribute("createSuccessFlag", true);
		} catch (Exception e) {
			e.printStackTrace();
			returnMap.put("createSuccessFlag", false);
			returnMap.put("docIndxTypList", docIndexService.getAlldocIndxTypList());
			//returnMap.put("deptList", deptService.getAllDept());
			returnMap.put("indxMstList", docIndexService.getAllIndxMstList());
			UserVo usrDtls = getUserSession();
			if(usrDtls.getAdminPower()==Constants.SUPER_ADMIN_POWER) {				
				returnMap.put("companyList", cityService.getAllCompany());
			} else {
				returnMap.put("companyList", userService.getPerticularUserCompany(usrDtls.getCompanyId()));
			}
			model.mergeAttributes(returnMap);
			logger.info("Exit saveDrpDwn()..");
			return "docIndex/docIndexCreate";
		}

		logger.info("Exit saveDocIndex()..");
		return "redirect:/docIndexCreate";
	}
	
	@RequestMapping(value = { "/docIndexEdit" })
	public String editDocIndex(HttpServletRequest request, Model model, @ModelAttribute("docIndexVo") DocIndexVo vo, BindingResult result) {
		logger.info("Enter editDocIndex()..");

		logger.info("Exit editDocIndex()..");
		return loadExistingDocIndex(vo, model, result, false);
	}
	
	
	@RequestMapping(value = { "/docIndexUpdate" }, method = RequestMethod.POST)
	@Transactional
	public String updateDocIndex(Model model, @ModelAttribute("docIndexVo") @Validated DocIndexVo vo, BindingResult result,
	final RedirectAttributes redirectAttributes) throws DMSException {
	logger.info("Enter updateDocIndex()..");		
	Map<String, Object> returnMap = new HashMap<String, Object>();

	if (result.hasErrors()) {
		returnMap.put("readonly", false);
		returnMap.put("docIndxTypList", docIndexService.getAlldocIndxTypList());
		//returnMap.put("deptList", deptService.getAllDept());
		returnMap.put("indxMstList", docIndexService.getAllIndxMstList());
		UserVo usrDtls = getUserSession();
		if(usrDtls.getAdminPower()==Constants.SUPER_ADMIN_POWER) {				
			returnMap.put("companyList", cityService.getAllCompany());
		} else {
			returnMap.put("companyList", userService.getPerticularUserCompany(usrDtls.getCompanyId()));
		}
		//returnMap.put("companyList", cityService.getAllCompany());
		model.mergeAttributes(returnMap);
		
		logger.info(" Early Exit updateDocIndex()..");
		return "docIndex/viewAndEditDocIndex";
	}
	try {
		vo.setDocIndxId(decryptId(vo.getDocIndxEncId()));
		Integer id = docIndexService.updateDocIndex(vo);
		redirectAttributes.addFlashAttribute("id", AESencyrptor.encrypt(id.toString()));
		redirectAttributes.addFlashAttribute("updatedSuccessfully", true);
		model.mergeAttributes(returnMap);
		
	} catch (Exception e) {
		e.printStackTrace();
		returnMap.put("readonly", false);
		returnMap.put("docIndxTypList", docIndexService.getAlldocIndxTypList());
		//returnMap.put("deptList", deptService.getAllDept());
		returnMap.put("indxMstList", docIndexService.getAllIndxMstList());
		UserVo usrDtls = getUserSession();
		if(usrDtls.getAdminPower()==Constants.SUPER_ADMIN_POWER) {				
			returnMap.put("companyList", cityService.getAllCompany());
		} else {
			returnMap.put("companyList", userService.getPerticularUserCompany(usrDtls.getCompanyId()));
		}
		//returnMap.put("companyList", cityService.getAllCompany());
		model.mergeAttributes(returnMap);
		
		logger.info("Exit updateDocIndex()..");
		return "locations/viewAndEditDocIndex";
	}
	
	logger.info("Exit updateDocIndex()..");
	return "redirect:/docIndexView";
}
	
	@RequestMapping(value = { "/docIndexSearch" })
	public String docIndexSearch(Model model, @ModelAttribute("docIndexVo")DocIndexVo vo,
			@RequestParam(value = "page", defaultValue = "1") int page) {
		logger.info("Enter docIndexSearch()..page->" + page);

		final int maxResult = 10;
		final int maxNavigationPage = 10;

		PaginationResult<DocIndexVo> result = null;
			DocIndexVo docIndexVo = new DocIndexVo();
			docIndexVo.setDeptId(vo.getDeptId());
			docIndexVo.setDocTypeId(vo.getDocTypeId());
			result = docIndexService.getDocIndexDtlsWithPagination(page, maxResult, maxNavigationPage, docIndexVo);
		model.addAttribute("paginationDocIndex", result);
		model.addAttribute("docIndexVo", vo);
		model.addAttribute("search", 1);
		Map<String, Object> returnMap = new HashMap<String, Object>();
		//returnMap.put("deptList", deptService.getAllDept());
		
		UserVo usrVo = getUserSession();
		model.addAttribute("power", usrVo.getAdminPower());
		
		if (usrVo.getAdminPower() == 2) {
			model.addAttribute("companyList", cityService.getAllCompany());
		} else if (usrVo.getAdminPower() == 1) {
			List<DeptVo> deptList = cityService.getDeptIdBycompanyId(usrVo.getCompanyId());
			model.addAttribute("departmentList", deptList);
		} else {
			/*List<DocTypeVo> docTypeList = documentService.getDocTypeByDept(usrVo.getDepartmentVo().getDepartmentId());
			model.addAttribute("docTypeList", docTypeList);*/
			List<DeptVo> deptList = cityService.getDeptIdBycompanyId(usrVo.getCompanyId());
			model.addAttribute("departmentList", deptList);
		}
		model.mergeAttributes(returnMap);
		logger.info("Exit docIndexSearch()..page->" + page);
		return "docIndex/docIndexList";

	}
	
	@RequestMapping(value = { "/docIndexLoad" })
	public String docIndexLoad(Model model, @ModelAttribute("docIndexVo")DocIndexVo vo,
			@RequestParam(value = "page", defaultValue = "1") int page) {
		logger.info("Enter docIndexLoad()..page->" + page);

		final int maxResult = 5;
		final int maxNavigationPage = 5;

		PaginationResult<DocIndexVo> result = null;
			DocIndexVo docIndexVo = new DocIndexVo();
			docIndexVo.setDeptId(vo.getDeptId());
			docIndexVo.setDocTypeId(vo.getDocTypeId());
			result = docIndexService.getDocIndexDtlsWithPagination(page, maxResult, maxNavigationPage, docIndexVo);
		model.addAttribute("paginationDocIndex", result);
		model.addAttribute("docIndexVo", vo);
		model.addAttribute("search", 1);
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("deptList", deptService.getAllDept());
		returnMap.put("docIndxTypList", docIndexService.getAlldocIndxTypList());
		returnMap.put("indxMstList", docIndexService.getAllIndxMstList());
		returnMap.put("companyList", cityService.getAllCompany());

		model.mergeAttributes(returnMap);
		logger.info("Exit docIndexLoad()..page->" + page);
		return "docIndex/docIndexCreate";

	}
}
