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
import org.dms.DMS.model.PaginationResult;
import org.dms.DMS.model.RoleVo;
import org.dms.DMS.model.UserVo;
import org.dms.DMS.service.CityService;
import org.dms.DMS.service.DeptService;
import org.dms.DMS.service.RoleService;
import org.dms.DMS.service.UserService;
import org.dms.DMS.util.AESencyrptor;
import org.dms.DMS.util.Constants;
import org.dms.DMS.util.DMSBaseController;
import org.dms.DMS.validator.RoleValidator;
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
public class RoleController extends DMSBaseController {

	private static final Logger logger = Logger.getLogger(RoleController.class);

	@Autowired

	@Qualifier(value = "roleValidator")
	private RoleValidator roleValidator;

	@Resource(name = "roleService")
	private RoleService roleService;

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
		if (target.getClass() == RoleVo.class) {
			dataBinder.setValidator(roleValidator);
		}
	}

	@RequestMapping(value = { "/roleList" })
	public String getRoleDtlsWithPagination(Model model,
			@RequestParam(value = "name", defaultValue = "") String likeName,
			@RequestParam(value = "page", defaultValue = "1") int page) {
		logger.info("Enter getRoleDtlsWithPagination()..page->" + page);
		final int maxResult = 10;
		final int maxNavigationPage = 10;
		UserVo usrDtls = getUserSession();
		/* if(usrDtls.getAdminPower()==Constants.SUPER_ADMIN_POWER) { */
		int admPower = usrDtls.getAdminPower();
		int companyId = usrDtls.getCompanyId();

		PaginationResult<RoleVo> result = roleService.getRoleDtlsWithPagination(page, maxResult, maxNavigationPage,
				likeName, admPower, companyId);

		model.addAttribute("paginationRole", result);
		model.addAttribute("roleVo", new RoleVo());
		model.addAttribute("power", usrDtls.getAdminPower());
		if (usrDtls.getAdminPower() == Constants.SUPER_ADMIN_POWER) {
			model.addAttribute("companyList", cityService.getAllCompany());
		} else if (usrDtls.getAdminPower() == Constants.ADMIN_POWER) {
			List<DeptVo> deptList = cityService.getDeptIdBycompanyId(usrDtls.getCompanyId());
			model.addAttribute("departmentList", deptList);
		} else {
			/*List<DocTypeVo> docTypeList = documentService.getDocTypeByDept(usrVo.getDepartmentVo().getDepartmentId());
			model.addAttribute("docTypeList", docTypeList);*/
			List<DeptVo> deptList = cityService.getDeptIdBycompanyId(usrDtls.getCompanyId());
			model.addAttribute("departmentList", deptList);
		}
		logger.info("Exit getRoleDtlsWithPagination()..page->" + page);
		return "role/roleList";
	}

	@RequestMapping(value = { "/roleCreate" }, method = RequestMethod.GET)
	public String newRole(HttpServletRequest request, Model model) throws DMSException {
		logger.info("Enter into newRole()");
		RoleVo roleVo = new RoleVo();
		Map<String, Object> returnMap = new HashMap<String, Object>();
		model.addAttribute("roleVo", roleVo);
		// returnMap.put("deptList", deptService.getAllDept());
		UserVo usrDtls = getUserSession();
		if(usrDtls.getAdminPower()==Constants.SUPER_ADMIN_POWER) {				
			returnMap.put("companyList", cityService.getAllCompany());
		} else {
			returnMap.put("companyList", userService.getPerticularUserCompany(usrDtls.getCompanyId()));
		}
		//returnMap.put("companyList", cityService.getAllCompany());
		model.mergeAttributes(returnMap);
		logger.info("Exit From newRole()");
		return "role/roleCreate";
	}

	@RequestMapping(value = { "/roleSave" }, method = RequestMethod.POST)
	@Transactional(propagation = Propagation.NEVER)
	public String saveRole(Model model, @ModelAttribute("roleVo") @Validated RoleVo vo, BindingResult result,
			final RedirectAttributes redirectAttributes) throws DMSException {
		logger.info("Enter saveRole()..");

		Map<String, Object> returnMap = new HashMap<String, Object>();

		if (result.hasErrors()) {
			returnMap.put("createSuccessFlag", false);
			// returnMap.put("deptList", deptService.getAllDept());
			UserVo usrDtls = getUserSession();
			if(usrDtls.getAdminPower()==Constants.SUPER_ADMIN_POWER) {				
				returnMap.put("companyList", cityService.getAllCompany());
			} else {
				returnMap.put("companyList", userService.getPerticularUserCompany(usrDtls.getCompanyId()));
			}
			//returnMap.put("companyList", cityService.getAllCompany());
			model.mergeAttributes(returnMap);

			logger.info("Exit saveRole()..");
			return "role/roleCreate";
		}
		try {
			/* setDefaultValue(vo); */
			Integer id = roleService.saveRole(vo);
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

			logger.info("Exit saveRole()..");
			return "role/roleCreate";
		}

		logger.info("Exit saveRole()..");
		return "redirect:/roleView";
	}

	@RequestMapping(value = { "/roleView" })
	public String viewRole(HttpServletRequest request, Model model, @ModelAttribute("roleVo") RoleVo vo,
			BindingResult result) {
		logger.info("Enter viewRole()..");

		logger.info("Exit viewRole()..");
		return loadExistingRole(vo, model, result, true);
	}

	@RequestMapping(value = { "/roleEdit" })
	public String editRole(HttpServletRequest request, Model model, @ModelAttribute("roleVo") RoleVo vo,
			BindingResult result) {
		logger.info("Enter roleEdit()..");

		logger.info("Exit roleEdit()..");
		return loadExistingRole(vo, model, result, false);
	}

	private String loadExistingRole(RoleVo vo, Model model, BindingResult result, boolean readonly) {
		logger.info("Entering loadExistingRole() ..");

		String prmId = (String) model.asMap().get("id");
		Map<String, Object> returnMap = model.asMap();

		try {

			if (StringUtils.isNotBlank(vo.getEncId())) {
				vo.setRoleId(decryptId(vo.getEncId()));
			}
			if (StringUtils.isNotBlank(prmId) && StringUtils.isBlank(vo.getEncId())) {
				vo.setRoleId(decryptId(prmId));
				vo.setEncId(prmId);
			}
			returnMap = roleService.getRoleDtlsByVo(vo);

			model.addAttribute("deptVo", vo);
			// returnMap.put("deptList", deptService.getAllDept());
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

		logger.info("Exit loadExistingRole()..");
		return "role/viewAndEditRole";
	}

	@RequestMapping(value = { "/roleUpdate" }, method = RequestMethod.POST)
	@Transactional
	public String updateRole(Model model, @ModelAttribute("roleVo") @Validated RoleVo vo, BindingResult result,
			final RedirectAttributes redirectAttributes) throws DMSException {
		logger.info("Enter updateRole()..");
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

			logger.info(" Early Exit updateRole()..");
			return "role/viewAndEditRole";
		}
		try {
			vo.setRoleId(decryptId(vo.getEncId()));
			Integer id = roleService.updateRole(vo);
			redirectAttributes.addFlashAttribute("id", AESencyrptor.encrypt(id.toString()));
			redirectAttributes.addFlashAttribute("updatedSuccessfully", true);
			model.mergeAttributes(returnMap);

		} catch (Exception e) {
			e.printStackTrace();
			returnMap.put("readonly", false);
			// returnMap.put("deptList", deptService.getAllDept());
			UserVo usrDtls = getUserSession();
			if(usrDtls.getAdminPower()==Constants.SUPER_ADMIN_POWER) {				
				returnMap.put("companyList", cityService.getAllCompany());
			} else {
				returnMap.put("companyList", userService.getPerticularUserCompany(usrDtls.getCompanyId()));
			}
			//returnMap.put("companyList", cityService.getAllCompany());
			model.mergeAttributes(returnMap);

			logger.info("Exit updateRole()..");
			return "locations/viewAndEditRole";
		}

		logger.info("Exit updateRole()..");
		return "redirect:/roleView";
	}

	@RequestMapping(value = { "/roleSearch" })
	public String roleSearch(Model model, @ModelAttribute("roleVo") RoleVo vo,
			@RequestParam(value = "page", defaultValue = "1") int page) {
		logger.info("Enter roleSearch()..page->" + page);
		final int maxResult = 10;
		final int maxNavigationPage = 10;
		UserVo usrDtls = getUserSession();

		PaginationResult<RoleVo> result = null;
			int admPower = usrDtls.getAdminPower();			
			result = roleService.getRoleDtlsWithPagination(page, maxResult, maxNavigationPage, vo,admPower);

		model.addAttribute("paginationRole", result);
		model.addAttribute("roleVo", vo);
		model.addAttribute("search", 1);
		model.addAttribute("power", usrDtls.getAdminPower());
		
		if (usrDtls.getAdminPower() == Constants.SUPER_ADMIN_POWER) {
			model.addAttribute("companyList", cityService.getAllCompany());
		} else if (usrDtls.getAdminPower() == Constants.ADMIN_POWER) {
			List<DeptVo> deptList = cityService.getDeptIdBycompanyId(usrDtls.getCompanyId());
			model.addAttribute("departmentList", deptList);
		} else {
			/*List<DocTypeVo> docTypeList = documentService.getDocTypeByDept(usrVo.getDepartmentVo().getDepartmentId());
			model.addAttribute("docTypeList", docTypeList);*/
			List<DeptVo> deptList = cityService.getDeptIdBycompanyId(usrDtls.getCompanyId());
			model.addAttribute("departmentList", deptList);
		}
		logger.info("Exit roleSearch()..page->" + page);
		return "role/roleList";

	}

}
