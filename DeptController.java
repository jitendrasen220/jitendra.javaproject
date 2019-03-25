package org.dms.DMS.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dms.DMS.entity.Users;
import org.dms.DMS.exceptions.DMSException;
import org.dms.DMS.model.DeptVo;
import org.dms.DMS.model.PaginationResult;
import org.dms.DMS.model.UserVo;
import org.dms.DMS.service.CityService;
import org.dms.DMS.service.DeptService;
import org.dms.DMS.service.UserService;
import org.dms.DMS.util.AESencyrptor;
import org.dms.DMS.util.Constants;
import org.dms.DMS.util.DMSBaseController;
import org.dms.DMS.validator.DeptValidator;
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
public class DeptController extends DMSBaseController {
	
	private static final Logger logger = Logger.getLogger(DeptController.class);

	@Autowired
	
	@Qualifier(value = "deptValidator")
	private DeptValidator deptValidator;
	
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
		if (target.getClass() == DeptVo.class) {
			dataBinder.setValidator(deptValidator);
		}
	}
		
	@RequestMapping(value = { "/deptList" })
	public String getDeptDtlsWithPagination(Model model,
		@RequestParam(value = "name", defaultValue = "") String likeName,
		@RequestParam(value = "page", defaultValue = "1") int page) {
		logger.info("Enter getDeptDtlsWithPagination()..page->" + page);
		 boolean isSuperAdmin=false;
		final int maxResult = 10;
		final int maxNavigationPage = 10;
		UserVo usrDtls = getUserSession();
		int admPower = usrDtls.getAdminPower();
		int companyId = usrDtls.getCompanyId();
		PaginationResult<DeptVo> result = deptService.getDeptDtlsWithPagination(page, maxResult, maxNavigationPage, likeName, admPower, companyId);
		
		 if (usrDtls.getAdminPower() == Constants.SUPER_ADMIN_POWER) {
			 isSuperAdmin=true;
			 model.addAttribute("companyList", cityService.getAllCompany());
		 }
	    model.addAttribute("isSuperAdmin", isSuperAdmin);		
		model.addAttribute("paginationDept", result); 
		model.addAttribute("deptVo", new DeptVo());
		logger.info("Exit getCountryDtlsWithPagination()..page->" + page);
		return "department/deptList";
	}	
	
 	@RequestMapping(value = { "/deptCreate" }, method = RequestMethod.GET)
	public String newDept(HttpServletRequest request, Model model) throws DMSException {
	logger.info("Enter into newDept()");
		DeptVo deptVo = new DeptVo();
		Map<String, Object> returnMap = new HashMap<String, Object>();
		model.addAttribute("deptVo", deptVo);
		UserVo usrDtls = getUserSession();
		if(usrDtls.getAdminPower()==Constants.SUPER_ADMIN_POWER) {				
			returnMap.put("companyList", cityService.getAllCompany());
		} else {
			returnMap.put("companyList", userService.getPerticularUserCompany(usrDtls.getCompanyId()));
		}
		model.mergeAttributes(returnMap);
		logger.info("Exit From newDept()");
		return "department/deptCreate";
	}
 	
	@RequestMapping(value = { "/deptSave" }, method = RequestMethod.POST)
	// Avoid UnexpectedRollbackException (See more explanations)
	@Transactional(propagation = Propagation.NEVER)
	public String saveDept(Model model, @ModelAttribute("deptVo") @Validated DeptVo vo, BindingResult result,
			final RedirectAttributes redirectAttributes) throws DMSException {
		logger.info("Enter saveDept()..");

		Map<String, Object> returnMap = new HashMap<String, Object>();

		if (result.hasErrors()) {
			returnMap.put("createSuccessFlag", false);
			UserVo usrDtls = getUserSession();
			if(usrDtls.getAdminPower()==Constants.SUPER_ADMIN_POWER) {				
				returnMap.put("companyList", cityService.getAllCompany());
			} else {
				returnMap.put("companyList", userService.getPerticularUserCompany(usrDtls.getCompanyId()));
			}
			//returnMap.put("companyList", cityService.getAllCompany());
			model.mergeAttributes(returnMap);

			logger.info("Exit saveDept()..");
			return "department/deptCreate";
		}
		try {
			/*setDefaultValue(vo);*/
			Integer id = deptService.saveDept(vo);
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

			logger.info("Exit saveDept()..");
			return "department/deptCreate";
		}

		logger.info("Exit saveDept()..");
		return "redirect:/deptView";
	}
	
	@RequestMapping(value = { "/deptView" })
	public String viewDept(HttpServletRequest request, Model model, @ModelAttribute("deptVo") DeptVo vo, BindingResult result) {
		logger.info("Enter viewDept()..");

		logger.info("Exit viewDept()..");
		return loadExistingDept(vo, model, result, true);
	}
	
	@RequestMapping(value = { "/deptEdit" })
	public String editDept(HttpServletRequest request, Model model, @ModelAttribute("deptVo") DeptVo vo, BindingResult result) {
		logger.info("Enter editDept()..");

		logger.info("Exit editDept()..");
		return loadExistingDept(vo, model, result, false);
	}

private String loadExistingDept(DeptVo vo, Model model, BindingResult result, boolean readonly) {
		logger.info("Entering loadExistingDept() ..");

		String prmId = (String) model.asMap().get("id");
		Map<String, Object> returnMap = model.asMap();

		try {

			if (StringUtils.isNotBlank(vo.getEncId())) {
				vo.setDepartmentId(decryptId(vo.getEncId()));
			}
			if (StringUtils.isNotBlank(prmId) && StringUtils.isBlank(vo.getEncId())) {
				vo.setDepartmentId(decryptId(prmId));
				vo.setEncId(prmId);
			}		
		returnMap = deptService.getDeptDtlsByVo(vo);
		model.addAttribute("deptVo", vo);
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

	logger.info("Exit loadExistingDept()..");
	return "department/viewAndEditDept";
	}

// POST: Save product
	@RequestMapping(value = { "/deptUpdate" }, method = RequestMethod.POST)
	// Avoid UnexpectedRollbackException (See more explanations)
	@Transactional
	public String updateDept(Model model, @ModelAttribute("deptVo") @Validated DeptVo vo, BindingResult result,
			final RedirectAttributes redirectAttributes) throws DMSException {
		logger.info("Enter updateDept()..");		
		Map<String, Object> returnMap = new HashMap<String, Object>();
	
		if (result.hasErrors()) {
			returnMap.put("readonly", false);
			UserVo usrDtls = getUserSession();
			if(usrDtls.getAdminPower()==Constants.SUPER_ADMIN_POWER) {				
				returnMap.put("companyList", cityService.getAllCompany());
			} else {
				returnMap.put("companyList", userService.getPerticularUserCompany(usrDtls.getCompanyId()));
			}
			//returnMap.put("companyList", cityService.getAllCompany());
			model.mergeAttributes(returnMap);
			
			logger.info(" Early Exit updateDept()..");
			return "department/viewAndEditDept";
		}
		try {
			//setDefaultValue(vo);
			vo.setDepartmentId(decryptId(vo.getEncId()));
			Integer id = deptService.updateDept(vo);
			redirectAttributes.addFlashAttribute("id", AESencyrptor.encrypt(id.toString()));
			redirectAttributes.addFlashAttribute("updatedSuccessfully", true);
			//returnMap.put("userVo", vo);
			model.mergeAttributes(returnMap);
			
		} catch (Exception e) {
			e.printStackTrace();
			returnMap.put("readonly", false);
			UserVo usrDtls = getUserSession();
			if(usrDtls.getAdminPower()==Constants.SUPER_ADMIN_POWER) {				
				returnMap.put("companyList", cityService.getAllCompany());
			} else {
				returnMap.put("companyList", userService.getPerticularUserCompany(usrDtls.getCompanyId()));
			}
			//returnMap.put("companyList", cityService.getAllCompany());
			model.mergeAttributes(returnMap);
			
			logger.info("Exit updateDept()..");
			return "department/viewAndEditDept";
		}
		
		logger.info("Exit updateDept()..");
		return "redirect:/deptView";
	}	
	

	

	@RequestMapping(value = { "/deptSearch" })
	public String deptSearch(Model model, @ModelAttribute("deptVo") DeptVo vo,
			@RequestParam(value = "page", defaultValue = "1") int page) {
		logger.info("Enter deptSearch()..page->" + page);
		final int maxResult = 10;
		final int maxNavigationPage = 10;
		UserVo usrDtls = getUserSession();
		boolean isSuperAdmin = false;
		
		PaginationResult<DeptVo> result = null;
		if (StringUtils.isNotBlank(vo.getName()) || vo.getCompanyId()!=null) {
			DeptVo deptVo = new DeptVo();
			deptVo.setName(vo.getName());			
			int admPower = usrDtls.getAdminPower();	
            if(usrDtls.getAdminPower()==Constants.SUPER_ADMIN_POWER) {
            	deptVo.setCompanyId(vo.getCompanyId());
            	isSuperAdmin = true;
            }
            else {
            	deptVo.setCompanyId(usrDtls.getCompanyId());
            }		
			
			result = deptService.getDeptDtlsWithPagination(page, maxResult, maxNavigationPage, deptVo, admPower);
		}
		if (usrDtls.getAdminPower() == Constants.SUPER_ADMIN_POWER) {
			 isSuperAdmin=true;
			 model.addAttribute("companyList", cityService.getAllCompany());
		 }
		model.addAttribute("paginationDept", result);
		model.addAttribute("deptVo", vo);
		model.addAttribute("isSuperAdmin", isSuperAdmin);
		model.addAttribute("search", 1);
		logger.info("Exit deptSearch()..page->" + page);
		return "department/deptList";

	}

}
