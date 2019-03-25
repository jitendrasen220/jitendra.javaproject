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
import org.dms.DMS.model.DocTypeVo;
import org.dms.DMS.model.PaginationResult;
import org.dms.DMS.model.RoleVo;
import org.dms.DMS.model.UserVo;
import org.dms.DMS.service.CityService;
import org.dms.DMS.service.DeptService;
import org.dms.DMS.service.DocTypeService;
import org.dms.DMS.service.UserService;
import org.dms.DMS.util.AESencyrptor;
import org.dms.DMS.util.Constants;
import org.dms.DMS.util.DMSBaseController;
import org.dms.DMS.validator.DocTypeValidator;
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
public class DocTypeController extends DMSBaseController {
	private static final Logger logger = Logger.getLogger(DocTypeController.class);

	
	@Autowired
	@Qualifier(value = "docTypeValidator")
	private DocTypeValidator docTypeValidator;
	
	@Resource(name = "docTypeService")
	private DocTypeService docTypeService;

	@Resource(name = "deptService")
	private DeptService deptService;
	
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
		if (target.getClass() == DocTypeVo.class) {
			dataBinder.setValidator(docTypeValidator);
		}
	}
	
	@RequestMapping(value = {"/docTypeList"})
	public String getDocTypeDtlsWithPagination(Model model, 
			@RequestParam(value = "name", defaultValue = "") String likeName,
			@RequestParam(value = "page", defaultValue = "1") int page) {
		logger.info("Enter getDocTypeDtlsWithPagination()..page->" + page);
		final int maxResult = 10;
		final int maxNavigationPage = 10;
		UserVo usrDtls = getUserSession();
			
		int admPower = usrDtls.getAdminPower();
		int companyId = usrDtls.getCompanyId();		
		
		PaginationResult<DocTypeVo> result = docTypeService.getDocTypeDtlsWithPagination(page, maxResult, maxNavigationPage, likeName,admPower, companyId);
		model.addAttribute("search", 0);
		model.addAttribute("paginationDocType", result);
		model.addAttribute("docTypeVo", new DocTypeVo());
		model.addAttribute("power", usrDtls.getAdminPower());
		
		if (usrDtls.getAdminPower() == Constants.SUPER_ADMIN_POWER) {
			model.addAttribute("companyList", cityService.getAllCompany());
		}  else if (usrDtls.getAdminPower() == Constants.ADMIN_POWER) {
			List<DeptVo> deptList = cityService.getDeptIdBycompanyId(usrDtls.getCompanyId());
			model.addAttribute("departmentList", deptList);
		} else {			
			List<DeptVo> deptList = cityService.getDeptIdBycompanyId(usrDtls.getCompanyId());
			model.addAttribute("departmentList", deptList);
		}
		logger.info("Exit getDocTypeDtlsWithPagination()..page->" + page);
		return "docType/docTypeList";
	}
	
	@RequestMapping(value = { "/docTypeSearch" })
	public String docTypeSearch(Model model, @ModelAttribute("docTypeVo") DocTypeVo vo,
			@RequestParam(value = "page", defaultValue = "1") int page) {
		logger.info("Enter docTypeSearch()..page->" + page);
		final int maxResult = 10;
		final int maxNavigationPage = 10;
		UserVo usrDtls = getUserSession();

		PaginationResult<DocTypeVo> result = null;
			int admPower = usrDtls.getAdminPower();			
			result = docTypeService.getDocTypeDtlsWithPagination(page, maxResult, maxNavigationPage, vo,admPower);

		model.addAttribute("paginationDocType", result);
		model.addAttribute("docTypeVo", vo);
		model.addAttribute("search", 1);
		model.addAttribute("power", usrDtls.getAdminPower());
		
		if (usrDtls.getAdminPower() == Constants.SUPER_ADMIN_POWER) {
			model.addAttribute("companyList", cityService.getAllCompany());
		} else if (usrDtls.getAdminPower() == Constants.ADMIN_POWER) {
			List<DeptVo> deptList = cityService.getDeptIdBycompanyId(usrDtls.getCompanyId());
			model.addAttribute("departmentList", deptList);
		} else {
			
			List<DeptVo> deptList = cityService.getDeptIdBycompanyId(usrDtls.getCompanyId());
			model.addAttribute("departmentList", deptList);
		}
		logger.info("Exit roleSearch()..page->" + page);
		return "docType/docTypeList";

	}

	@RequestMapping(value = { "/docTypeCreate" }, method = RequestMethod.GET)
	public String newDocType(HttpServletRequest request, Model model) throws DMSException {
	logger.info("Enter into newDocType()");
	DocTypeVo docTypeVo = new DocTypeVo();
		Map<String, Object> returnMap = new HashMap<String, Object>();
		model.addAttribute("docTypeVo", docTypeVo);
		//returnMap.put("deptList", deptService.getAllDept());
		UserVo usrDtls = getUserSession();
		if(usrDtls.getAdminPower()==Constants.SUPER_ADMIN_POWER) {				
			returnMap.put("companyList", cityService.getAllCompany());
		} else {
			returnMap.put("companyList", userService.getPerticularUserCompany(usrDtls.getCompanyId()));
		}
		//returnMap.put("companyList", cityService.getAllCompany());
		model.mergeAttributes(returnMap);
		logger.info("Exit From newDocType()");
		return "docType/docTypeCreate";
	}
	
	@RequestMapping(value = { "/docTypeSave" }, method = RequestMethod.POST)
	@Transactional(propagation = Propagation.NEVER)
	public String saveDocType(Model model, @ModelAttribute("docTypeVo") @Validated DocTypeVo vo, BindingResult result,
			final RedirectAttributes redirectAttributes) throws DMSException {
		logger.info("Enter saveDocType()..");
		Map<String, Object> returnMap = new HashMap<String, Object>();
		if (result.hasErrors()) {
			returnMap.put("createSuccessFlag", false);
			////returnMap.put("deptList", deptService.getAllDept());
			UserVo usrDtls = getUserSession();
			if(usrDtls.getAdminPower()==Constants.SUPER_ADMIN_POWER) {				
				returnMap.put("companyList", cityService.getAllCompany());
			} else {
				returnMap.put("companyList", userService.getPerticularUserCompany(usrDtls.getCompanyId()));
			}
			//returnMap.put("companyList", cityService.getAllCompany());
			model.mergeAttributes(returnMap);
			logger.info("Exit saveDocType()..");
			return "docType/docTypeCreate";
		}
		try {
			Integer id = docTypeService.saveDocType(vo);
			redirectAttributes.addFlashAttribute("id", AESencyrptor.encrypt(id.toString()));
			redirectAttributes.addFlashAttribute("createSuccessFlag", true);
		} catch (Exception e) {
			e.printStackTrace();
			returnMap.put("createSuccessFlag", false);
			//returnMap.put("deptList", deptService.getAllDept());
			UserVo usrDtls = getUserSession();
			if(usrDtls.getAdminPower()==Constants.SUPER_ADMIN_POWER) {				
				returnMap.put("companyList", cityService.getAllCompany());
			} else {
				returnMap.put("companyList", userService.getPerticularUserCompany(usrDtls.getCompanyId()));
			}
			//returnMap.put("companyList", cityService.getAllCompany());
			model.mergeAttributes(returnMap);
			logger.info("Exit saveDocType()..");
			return "docType/docTypeCreate";
		}

		logger.info("Exit saveDocType()..");  
		return "redirect:/docTypeView";
	}
	
	@RequestMapping(value = { "/docTypeEdit" })
	public String editDocType(HttpServletRequest request, Model model, @ModelAttribute("docTypeVo") DocTypeVo vo, BindingResult result) {
		logger.info("Enter editDocType()..");

		logger.info("Exit editDocType()..");
		return loadExistingDocType(vo, model, result, false);
	}
	
	@RequestMapping(value = { "/docTypeView" })
	public String viewDocType(HttpServletRequest request, Model model, @ModelAttribute("docTypeVo") DocTypeVo vo, BindingResult result) {
		logger.info("Enter viewDocType()..");
		
		return loadExistingDocType(vo, model, result, true);
	}
	
	private String loadExistingDocType(DocTypeVo vo, Model model, BindingResult result, boolean readonly) {
		logger.info("Entering loadExistingDocType() ..");

		String prmId = (String) model.asMap().get("id");
		Map<String, Object> returnMap = model.asMap();

		try {

			if (StringUtils.isNotBlank(vo.getEncDocTypeId())) {
				vo.setDocTypeId(decryptId(vo.getEncDocTypeId()));
			}
			if (StringUtils.isNotBlank(prmId) && StringUtils.isBlank(vo.getEncDocTypeId())) {
				vo.setDocTypeId(decryptId(prmId));
				vo.setEncDocTypeId(prmId);
			}		
		returnMap = docTypeService.getDocTypeDtlsByVo(vo);
		model.addAttribute("docTypeVo", vo);
		//returnMap.put("deptList", deptService.getAllDept());
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

	logger.info("Exit loadExistingDocType()..");
	return "docType/viewAndEditDocType";
	}
	
	@RequestMapping(value = { "/docTypeUpdate" }, method = RequestMethod.POST)
	// Avoid UnexpectedRollbackException (See more explanations)
	@Transactional
	public String updateDocTypeVo(Model model, @ModelAttribute("docTypeVo") @Validated DocTypeVo vo, BindingResult result,
		final RedirectAttributes redirectAttributes) throws DMSException {
		logger.info("Enter updateDocTypeVo()..");		
		Map<String, Object> returnMap = new HashMap<String, Object>();

		if (result.hasErrors()) {
			returnMap.put("readonly", false);
			//returnMap.put("deptList", deptService.getAllDept());
			returnMap.put("companyList", cityService.getAllCompany());
			model.mergeAttributes(returnMap);
			
			logger.info(" Early Exit updateDocTypeVo()..");
			return "docType/viewAndEditDocType";
		}
		try {
			//setDefaultValue(vo);
			vo.setDocTypeId(decryptId(vo.getEncDocTypeId()));
			Integer id = docTypeService.updateDocType(vo);
			redirectAttributes.addFlashAttribute("id", AESencyrptor.encrypt(id.toString()));
			redirectAttributes.addFlashAttribute("updatedSuccessfully", true);
			model.mergeAttributes(returnMap);
			
		} catch (Exception e) {
			e.printStackTrace();
			returnMap.put("readonly", false);
			//returnMap.put("deptList", deptService.getAllDept());
			UserVo usrDtls = getUserSession();
			if(usrDtls.getAdminPower()==Constants.SUPER_ADMIN_POWER) {				
				returnMap.put("companyList", cityService.getAllCompany());
			} else {
				returnMap.put("companyList", userService.getPerticularUserCompany(usrDtls.getCompanyId()));
			}
			//returnMap.put("companyList", cityService.getAllCompany());
			model.mergeAttributes(returnMap);
			
			logger.info("Exit updateDocTypeVo()..");
			return "docType/viewAndEditDocType";
		}
		
		logger.info("Exit updateDocTypeVo()..");
		return "redirect:/docTypeView";
	}
}
