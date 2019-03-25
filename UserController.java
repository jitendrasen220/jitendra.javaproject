package org.dms.DMS.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dms.DMS.entity.Users;
import org.dms.DMS.exceptions.DMSException;
import org.dms.DMS.model.PaginationResult;
import org.dms.DMS.model.SecurityVo;
import org.dms.DMS.model.UserVo;
import org.dms.DMS.service.CityService;
import org.dms.DMS.service.UserService;
import org.dms.DMS.util.AESencyrptor;
import org.dms.DMS.util.Constants;
import org.dms.DMS.util.DMSBaseController;
import org.dms.DMS.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Transactional
@EnableWebMvc
public class UserController extends DMSBaseController {
	protected Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Resource(name = "userService")
	private UserService userService;

	@Autowired
	@Resource(name = "cityService")
	private CityService cityService;

	@Autowired
	@Qualifier(value = "userValidator")
	private UserValidator userValidator;

	/*@RequestMapping(value = { "/userList" })
	public String getUsrDtlsWithPagination(Model model,
			@RequestParam(value = "name", defaultValue = "") String likeName,
			@RequestParam(value = "page", defaultValue = "1") int page) throws DMSException {

		logger.info("Enter getUsrDtlsWithPagination()..page->" + page);
		final int maxResult = 10;
		final int maxNavigationPage = 10;
		UserVo usrDtls = getUserSession();
		System.err.println("usrDtls..."+usrDtls);
		Users users = userService.getUsrDtlsById(usrDtls.getUserId());
		logger.info("usrDtls.getLoginId():: " + usrDtls.getLoginId() + usrDtls.toString());
		logger.info("usrDtls.getAdminPower():: " + users.getAdminPower());
		int admPower = users.getAdminPower();
		int companyId = users.getCompany().getCompanyId();
		boolean isSuperAdmin=false;
		if (users.getAdminPower() == Constants.SUPER_ADMIN_POWER) {
			PaginationResult<Users> result = userService.getUsrDtlsWithPagination(page, maxResult, maxNavigationPage,
					likeName);
			isSuperAdmin=true;
			model.addAttribute("paginationUsers", result);
			model.addAttribute("companyList", cityService.getAllCompany());
			model.addAttribute("users", new Users());
			model.addAttribute("isSuperAdmin", isSuperAdmin);
			model.addAttribute("search", 0);
			logger.info("Exit getUsrDtlsWithPagination()..page->" + page);
			return "users/usersList";
		} else {
			PaginationResult<Users> result = userService.getCompUsrDtlsWithPagination(page, maxResult,
					maxNavigationPage, likeName, admPower, companyId);
			model.addAttribute("paginationUsers", result);
			model.addAttribute("users", new Users());
			model.addAttribute("isSuperAdmin", isSuperAdmin);
			model.addAttribute("search", 0);
			logger.info("Exit getUsrDtlsWithPagination()..page->" + page);
			return "users/usersList";
		}
	}

	@RequestMapping(value = { "/usersSearch" })
	public String usersSearch(Model model, @ModelAttribute("users") Users vo,
			@RequestParam(value = "page", defaultValue = "1") int page) throws DMSException {
		logger.info("Enter usersSearch()..page->" + page);
       logger.info("vo....."+vo);
		final int maxResult = 10;
		final int maxNavigationPage = 10;
		logger.info("vo...."+vo);		
		UserVo usrDtls = getUserSession();
		logger.info("usrDtls.getLoginId():: " + usrDtls.getLoginId());		
		Users users = userService.getUsrDtlsById(usrDtls.getUserId());
		logger.info("usrDtls.getLoginId():: " + usrDtls.getLoginId() + usrDtls.toString());
		logger.info("usrDtls.getAdminPower():: " + users.getAdminPower());
		logger.info("users....."+users);
		logger.info("users.getCompany().getCompanyId()....."+users.getCompany().getCompanyId());
		int admPower = users.getAdminPower();
		boolean isSuperAdmin=false;
	
		PaginationResult<Users> result = null;
		if (StringUtils.isNotBlank(vo.getLoginId()) || StringUtils.isNotBlank(vo.getEmpId())  ||vo.getCompanyId()!=null) {
			Users usrVo = new Users();
			usrVo.setLoginId(vo.getLoginId());
			usrVo.setEmpId(vo.getEmpId());
			usrVo.setAdminPower(users.getAdminPower());
			if(users.getAdminPower()==Constants.SUPER_ADMIN_POWER) {
			usrVo.setCompanyId(vo.getCompanyId());
			}
			else {
				usrVo.setCompanyId(users.getCompany().getCompanyId());
			}
			result = userService.getUsrDtlsWithPagination(page, maxResult, maxNavigationPage, usrVo);
		} else {
			return "redirect:/userList";
		}
		if (users.getAdminPower() == Constants.SUPER_ADMIN_POWER) {
			isSuperAdmin=true;					
		}
		model.addAttribute("isSuperAdmin", isSuperAdmin);	
		model.addAttribute("companyList", cityService.getAllCompany());
		model.addAttribute("paginationUsers", result);
		model.addAttribute("userVo", vo);
		model.addAttribute("search", 1);
		logger.info("Exit usersSearch()..page->" + page);
		return "users/usersList";

	}*/

	@RequestMapping(value = { "/userList" })
    public String getUsrDtlsWithPagination(Model model,
                    @RequestParam(value = "name", defaultValue = "") String likeName,
                    @RequestParam(value = "page", defaultValue = "1") int page) throws DMSException {

            logger.info("Enter getUsrDtlsWithPagination()..page->" + page);
            final int maxResult = 10;
            final int maxNavigationPage = 10;
            UserVo usrDtls = getUserSession();
            System.err.println("usrDtls..."+usrDtls);
            Users users = userService.getUsrDtlsById(usrDtls.getUserId());
            logger.info("usrDtls.getLoginId():: " + usrDtls.getLoginId() + usrDtls.toString());
            logger.info("usrDtls.getAdminPower():: " + users.getAdminPower());
            int admPower = users.getAdminPower();
            int companyId = users.getCompany().getCompanyId();
            boolean isSuperAdmin=false;
            if (users.getAdminPower() == Constants.SUPER_ADMIN_POWER) {
                    PaginationResult<Users> result = userService.getUsrDtlsWithPagination(page, maxResult, maxNavigationPage,
                                    likeName);
                    isSuperAdmin=true;
                    model.addAttribute("paginationUsers", result);
                    model.addAttribute("companyList", cityService.getAllCompany());
                    model.addAttribute("users", new Users());
                    model.addAttribute("isSuperAdmin", isSuperAdmin);
                    model.addAttribute("search", 0);
                    logger.info("Exit getUsrDtlsWithPagination()..page->" + page);
                    return "users/usersList";
            } else {
                    PaginationResult<Users> result = userService.getCompUsrDtlsWithPagination(page, maxResult,
                                    maxNavigationPage, likeName, admPower, companyId);
                    model.addAttribute("paginationUsers", result);
                    model.addAttribute("users", new Users());
                    model.addAttribute("isSuperAdmin", isSuperAdmin);
                    model.addAttribute("search", 0);
                    logger.info("Exit getUsrDtlsWithPagination()..page->" + page);
                    return "users/usersList";
            }
    }

    @RequestMapping(value = { "/usersSearch" })
    public String usersSearch(Model model, @ModelAttribute("users") Users vo,
                    @RequestParam(value = "page", defaultValue = "1") int page) throws DMSException {
            logger.info("Enter usersSearch()..page->" + page);
            logger.info("vo....."+vo);
            final int maxResult = 10;
            final int maxNavigationPage = 10;
            logger.info("vo...."+vo);                
            UserVo usrDtls = getUserSession();
            logger.info("usrDtls.getLoginId():: " + usrDtls.getLoginId());                
            Users users = userService.getUsrDtlsById(usrDtls.getUserId());
            logger.info("usrDtls.getLoginId():: " + usrDtls.getLoginId() + usrDtls.toString());
            logger.info("usrDtls.getAdminPower():: " + users.getAdminPower());
            logger.info("users....."+users);
            logger.info("users.getCompany().getCompanyId()....."+users.getCompany().getCompanyId());
            int admPower = users.getAdminPower();
            boolean isSuperAdmin=false;
    
            PaginationResult<Users> result = null;
            if (StringUtils.isNotBlank(vo.getLoginId()) || StringUtils.isNotBlank(vo.getEmpId())  ||vo.getCompanyId()!=null) {
                    Users usrVo = new Users();
                    usrVo.setLoginId(vo.getLoginId());
                    usrVo.setEmpId(vo.getEmpId());
                    usrVo.setAdminPower(users.getAdminPower());
                    if(users.getAdminPower()==Constants.SUPER_ADMIN_POWER) {
                    usrVo.setCompanyId(vo.getCompanyId());
                    }
                    else {
                            usrVo.setCompanyId(users.getCompany().getCompanyId());
                    }
                    result = userService.getUsrDtlsWithPagination(page, maxResult, maxNavigationPage, usrVo);
            } else {
                    return "redirect:/userList";
            }
            if (users.getAdminPower() == Constants.SUPER_ADMIN_POWER) {
                    isSuperAdmin=true;                                        
            }
            model.addAttribute("isSuperAdmin", isSuperAdmin);        
            model.addAttribute("companyList", cityService.getAllCompany());
            model.addAttribute("paginationUsers", result);
            model.addAttribute("userVo", vo);
            model.addAttribute("search", 1);
            logger.info("Exit usersSearch()..page->" + page);
            return "users/usersList";

    }
    
	@RequestMapping(value = { "/userEdit" })
	public String editUser(HttpServletRequest request, Model model, @ModelAttribute("users") Users usrVo,
			BindingResult result) {
		logger.info("Enter userEdit()..ui data" + usrVo);
		logger.info("Exit userEdit()..");
		return loadExistingUser(usrVo, model, result, false);
	}

	private String loadExistingUser(Users usrVo, Model model, BindingResult result, Boolean readonly) {
		logger.info("Entering loadExistingUser() ..");

		String prmId = (String) model.asMap().get("userId");
		logger.info("prmId.." + prmId);
		logger.info("usrVo.getId().." + usrVo.getUserId() + usrVo.getFistName());

		 UserVo usrDtls = getUserSession();
		 int admPower=usrDtls.getAdminPower();
		 int companyId=usrDtls.getCompanyId();
		Map<String, Object> returnMap = model.asMap();
		if (returnMap == null) {
			returnMap = new HashMap<String, Object>();
		}
		try {
			if (StringUtils.isNotBlank(prmId))
			usrVo.setUserId(AESencyrptor.decryptId(prmId));
			returnMap = userService.getUsrDtlsByVo(usrVo);
			returnMap.put("countryList", cityService.getAllCountry());
			if (admPower == Constants.SUPER_ADMIN_POWER) {
			returnMap.put("companyList", cityService.getAllCompany());
			}
			else {
				returnMap.put("companyList", userService.getPerticularUserCompany(companyId));
			}
			
			//returnMap.put("departmentList", cityService.getAllDepartment());
			/*returnMap.put("hoRoleList", cityService.getAllRole());*/
			returnMap.put("readonly", readonly);
			model.mergeAttributes(returnMap);
			logger.info("usrVo.getId().." + usrVo.getUserId() + usrVo.getFistName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Exit loadExistingUser()..");
		return "users/viewAndEditUser";
	}

	@RequestMapping(value = { "/userUpdate" }, method = RequestMethod.POST)
	@Transactional
	public String updateUser(Model model, @ModelAttribute("users") @Validated Users users, BindingResult result,
			final RedirectAttributes redirectAttributes) {
		logger.info("Enter updateUser()..");
		userValidator.validate(users, result);
		Map<String, Object> returnMap = new HashMap<String, Object>();
		 UserVo usrDtls = getUserSession();
		 int admPower=usrDtls.getAdminPower();
		 int companyId=usrDtls.getCompanyId();
		try {
			if (result.hasErrors()) {
				returnMap.put("readonly", false);
				returnMap.put("countryList", cityService.getAllCountry());
				returnMap.put("countryList", cityService.getAllCountry());
			/*	returnMap.put("companyList", cityService.getAllCompany());
				//returnMap.put("departmentList", cityService.getAllDepartment());
				returnMap.put("hoRoleList", cityService.getAllRole());*/
				
				if (admPower == Constants.SUPER_ADMIN_POWER) {
					returnMap.put("companyList", cityService.getAllCompany());
					}
					else {
						returnMap.put("companyList", userService.getPerticularUserCompany(companyId));
					}
				model.mergeAttributes(returnMap);
				logger.info(" validation error Exit updateUser()..");
				return "users/viewAndEditUser";
			}
			Integer id = userService.updateUser(users);
			redirectAttributes.addFlashAttribute("userId", AESencyrptor.encrypt(id.toString()));
			redirectAttributes.addFlashAttribute("updatedSuccessfully", true);
			returnMap.put("users", users);
			model.mergeAttributes(returnMap);
			logger.info("Exit updateUser()..");
			return "redirect:/userView";
		} catch (DMSException e) {
			logger.error(e.getMessage());
			result.rejectValue(Constants.STRING_ERROR, "error.unknown");
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.rejectValue(Constants.STRING_ERROR, "error.unknown");
		}
		logger.info("Exit updateUser()..");
		return "users/viewAndEditUser";
	}

	@RequestMapping(value = { "/profileInfo" }, method = RequestMethod.GET)
	public String profileInfo(HttpServletRequest request, Model model, @ModelAttribute("userVo") UserVo usrVo,
			BindingResult result) {
		logger.info("Enter profileInfo()..");

		Map<String, Object> returnMap = new HashMap<String, Object>();
		UserDetails userDetails = getSessionUsrDtls();
		UserVo userV;
		try {
			userV = userService.getUsrDtls(userDetails.getUsername());
			userV.setSecurityVo(userService.getSecurityDtls(userV.getUserId()));
			model.addAttribute("userVo", userV);
			returnMap.put("readonly", true);
			model.mergeAttributes(returnMap);
		} catch (DMSException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Exit profileInfo()..");
		return "profileInfo";
	}

	@RequestMapping(value = { "/updatePass" }, method = RequestMethod.POST)
	@Transactional // Avoid UnexpectedRollbackException (See more explanations)
	public String updatePass(Model model, @ModelAttribute("userVo") @Validated UserVo vo, BindingResult result,
			final RedirectAttributes redirectAttributes) {
		logger.info("Enter updatePass()..");
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			if (result.hasErrors()) {
				returnMap.put("readonly", false);

				model.mergeAttributes(returnMap);

				logger.info("Exit updatePass()..");
				return "profileInfo";
			}

			UserVo usrDtls = getUserSession();
			logger.info("usrDtls.getLoginId():: " + usrDtls.getLoginId());
			System.out.println(usrDtls.getCompanyName() + "user Id    :" + usrDtls.getEncId());

			vo.setId(decryptId(usrDtls.getEncId()));
			Integer id = userService.updateUserPass(vo);
			if (id == 0) {
				returnMap.put("unSuccessfully", false);
				model.mergeAttributes(returnMap);
				return "profileInfo";
			}
			redirectAttributes.addFlashAttribute("id", AESencyrptor.encrypt(id.toString()));
			returnMap.put("updatedSuccessfully", true);
			model.mergeAttributes(returnMap);
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.rejectValue(Constants.STRING_ERROR, "error.unknown");
		}
		logger.info("Exit updatePass()..");
		return "profileInfo";
	}

	@RequestMapping(value = { "/userCreate" }, method = RequestMethod.GET)
	public String createUser(HttpServletRequest request, Model model, final RedirectAttributes redirectAttributes) {
		logger.info("Enter createUser()..");
		try {
			
			UserVo usrDtls = getUserSession();
			logger.info("usrDtls.getLoginId():: " + usrDtls.getLoginId());		
			Users usr = userService.getUsrDtlsById(usrDtls.getUserId());
			Users users = new Users();
			Map<String, Object> returnMap = new HashMap<String, Object>();
			model.addAttribute("users", users);
			returnMap.put("countryList", cityService.getAllCountry());			
			if(usr.getAdminPower()==Constants.SUPER_ADMIN_POWER) {				
			returnMap.put("companyList", cityService.getAllCompany());
			/*returnMap.put("departmentList", cityService.getAllDepartment());*/
			/*returnMap.put("hoRoleList", cityService.getAllRole());*/
			}
			else {
				returnMap.put("companyList", userService.getPerticularUserCompany(usr.getCompany().getCompanyId()));
				/*returnMap.put("departmentList", userService.getDeptListForComp((usr.getCompany().getCompanyId())));*/
				
				/*returnMap.put("hoRoleList", cityService.getAllRole());*/
			}

			//returnMap.put("departmentList", cityService.getAllDepartment());
			//returnMap.put("hoRoleList", cityService.getAllRole());


			model.mergeAttributes(returnMap);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		logger.info("Exit createUser()..");
		return "users/newUser";
	}

	@RequestMapping(value = { "/userSave" }, method = RequestMethod.POST)
	@Transactional(propagation = Propagation.NEVER)
	public String saveUser(Model model, @ModelAttribute("users") Users vo, BindingResult result,
			final RedirectAttributes redirectAttributes) {
		logger.info("Enter saveUser().." + vo);
		userValidator.validate(vo, result);
		Map<String, Object> returnMap = new HashMap<String, Object>();
		 UserVo usrDtls = getUserSession();
		 int admPower=usrDtls.getAdminPower();
		 int companyId=usrDtls.getCompanyId();
		try {
			if (result.hasErrors()) {
				returnMap.put("createSuccessFlag", false);
				returnMap.put("countryList", cityService.getAllCountry());
				//returnMap.put("companyList", cityService.getAllCompany());
				
				if (admPower == Constants.SUPER_ADMIN_POWER) {
					returnMap.put("companyList", cityService.getAllCompany());
					}
					else {
						returnMap.put("companyList", userService.getPerticularUserCompany(companyId));
					}

			/*	returnMap.put("departmentList", cityService.getAllDepartment());
				returnMap.put("hoRoleList", cityService.getAllRole());*/

				//returnMap.put("departmentList", cityService.getAllDepartment());
			/*	returnMap.put("hoRoleList", cityService.getAllRole());*/

				model.mergeAttributes(returnMap);
				logger.info("Exit saveUser()..");
				return "users/newUser";
			}
			setDefaultValue(vo);
			Integer id = userService.saveUser(vo);
			redirectAttributes.addFlashAttribute("userId", AESencyrptor.encrypt(id.toString()));
			redirectAttributes.addFlashAttribute("savedSuccessfully", true);
			logger.info("Exit saveUser()..");
			return "redirect:/userView";
		} catch (DMSException e) {
			logger.error(e.getMessage());
			result.rejectValue(Constants.STRING_ERROR, "error.unknown");
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.rejectValue(Constants.STRING_ERROR, "error.unknown");
		}
		logger.info("Exit saveUser()..");
		return "users/newUser";
	}

	private void setDefaultValue(Users vo) {
		UserVo usrDtls = getUserSession();
		vo.setCrtBy(usrDtls.getLoginId());
	}
	
	@RequestMapping(value = { "/userView" })
	public String viewUser(HttpServletRequest request, Model model,
			@ModelAttribute("users") Users usrVo, BindingResult result) {
		logger.info("Enter userView()..");

		logger.info("Exit userView()..");
		return loadExistingUser(usrVo, model, result, true);
	}
	
	@RequestMapping(value = { "/userSecurity" })
	public String UserSecurity(HttpServletRequest request, Model model,
			@ModelAttribute("users") Users usrVo, BindingResult result) {
		logger.info("Enter UserSecurity()..");

		Map<String, Object> returnMap = new HashMap<String, Object>();
		UserDetails userDetails = getSessionUsrDtls();
		UserVo userV;
		try {
			userV = userService.getUsrDtls(userDetails.getUsername());
			model.addAttribute("userVo", userV);
			returnMap.put("readonly", true);
			model.mergeAttributes(returnMap);
		} catch (DMSException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Exit UserSecurity()..");
		return "securityPage";
	}
	
	@RequestMapping(value = { "/updateSecurity" }, method = RequestMethod.POST)
	@Transactional // Avoid UnexpectedRollbackException (See more explanations)
	public String updateSecurity(Model model, @ModelAttribute("userVo") @Validated UserVo vo, BindingResult result,
			final RedirectAttributes redirectAttributes) {
		logger.info("Enter updateSecurity()..");
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			if (result.hasErrors()) {
				returnMap.put("readonly", false);

				model.mergeAttributes(returnMap);

				logger.info("Exit updateSecurity()..");
				return "profileInfo";
			}

			UserVo usrDtls = getUserSession();
			logger.info("usrDtls.getLoginId():: " + usrDtls.getLoginId());
			System.out.println(usrDtls.getCompanyName() + "user Id    :" + usrDtls.getEncId());

			vo.setId(decryptId(usrDtls.getEncId()));
			Integer id = userService.updateUserSecurity(vo);
			if (id == 0) {
				returnMap.put("unSuccessfully1", false);
				model.mergeAttributes(returnMap);
				return "profileInfo";
			}
			redirectAttributes.addFlashAttribute("id", AESencyrptor.encrypt(id.toString()));
			returnMap.put("updatedSuccessfully1", true);
			model.mergeAttributes(returnMap);
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.rejectValue(Constants.STRING_ERROR, "error.unknown");
		}
		logger.info("Exit updateSecurity()..");
		return "profileInfo";
	}
	
	@RequestMapping(value = { "/secPage" }, method = RequestMethod.GET)
	public String secPage(HttpServletRequest request, Model model, @ModelAttribute("userVo") UserVo usrVo,
			BindingResult result) {
		logger.info("Enter secPage()..");

		Map<String, Object> returnMap = new HashMap<String, Object>();
		UserDetails userDetails = getSessionUsrDtls();
		UserVo userV;
		try {
			userV = userService.getUsrDtls(userDetails.getUsername());
			//userV.setSecurityVo(userService.getSecurityDtls(userV.getUserId()));
			model.addAttribute("userVo", userV);
			model.addAttribute("Successfully", false);
			returnMap.put("readonly", true);
			model.mergeAttributes(returnMap);
		} catch (DMSException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Exit secPage()..");
		return "secPage";
	}
	
	@RequestMapping(value = { "/updateNewPass" }, method = RequestMethod.POST)
	@Transactional // Avoid UnexpectedRollbackException (See more explanations)
	public String updateNewPass(Model model, @ModelAttribute("userVo") @Validated UserVo vo, BindingResult result,
			final RedirectAttributes redirectAttributes) {
		logger.info("Enter updateNewPass()..");
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			if (result.hasErrors()) {
				returnMap.put("readonly", false);

				model.mergeAttributes(returnMap);

				logger.info("Exit updateNewPass()..");
				return "secPage";
			}

			UserVo usrDtls = getUserSession();
			logger.info("usrDtls.getLoginId():: " + usrDtls.getLoginId());
			System.out.println(usrDtls.getCompanyName() + "user Id    :" + usrDtls.getEncId());

			vo.setId(decryptId(usrDtls.getEncId()));
			Integer id = userService.updateUserPass(vo);
			if (id == 0) {
				returnMap.put("passSuccessfully", false);
				model.mergeAttributes(returnMap);
				return "secPage";
			}
			redirectAttributes.addFlashAttribute("id", AESencyrptor.encrypt(id.toString()));
			returnMap.put("passSuccessfully", true);
			returnMap.put("secSuccessfully", false);
			model.mergeAttributes(returnMap);
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.rejectValue(Constants.STRING_ERROR, "error.unknown");
		}
		logger.info("Exit updateNewPass()..");
		return "secPage";
	}
	
	@RequestMapping(value = { "/updateNewSecurity" }, method = RequestMethod.POST)
	@Transactional // Avoid UnexpectedRollbackException (See more explanations)
	public String updateNewSecurity(Model model, @ModelAttribute("userVo") @Validated UserVo vo, BindingResult result,
			final RedirectAttributes redirectAttributes) {
		logger.info("Enter updateNewSecurity()..");
		Map<String, Object> returnMap = new HashMap<String, Object>();
		UserVo usrDtls = getUserSession();
		try {
			if (result.hasErrors()) {
				returnMap.put("readonly", false);

				model.mergeAttributes(returnMap);

				logger.info("Exit updateNewSecurity()..");
				return "secPage";
			}

			
			logger.info("usrDtls.getLoginId():: " + usrDtls.getLoginId());
			System.out.println(usrDtls.getCompanyName() + "user Id    :" + usrDtls.getEncId());

			vo.setId(decryptId(usrDtls.getEncId()));
			Integer id = userService.createUserSecurity(vo);
			if (id == 0) {
				returnMap.put("passSuccessfully", "not");
				returnMap.put("secSuccessfully", false);
				model.mergeAttributes(returnMap);
				return "secPage";
			}
			redirectAttributes.addFlashAttribute("id", AESencyrptor.encrypt(id.toString()));
			returnMap.put("passSuccessfully", "not");
			returnMap.put("secSuccessfully", true);
			model.mergeAttributes(returnMap);
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.rejectValue(Constants.STRING_ERROR, "error.unknown");
		}
		logger.info("Exit updateNewSecurity()..");
		
		if(usrDtls.getAdminPower()==0 || usrDtls.getAdminPower()== null) {
			return "redirect:/documentList";
		} else {
			return "redirect:/userList";
		}
		
		
	}
	/*
	@RequestMapping(value = { "/forgetPass" }, method = RequestMethod.GET)
	@Transactional // Avoid UnexpectedRollbackException (See more explanations)
	public String forgetPass(Model model, @ModelAttribute("userVo") @Validated UserVo vo, BindingResult result,
			final RedirectAttributes redirectAttributes) {
		logger.info("Enter forgetPass()..");
		Map<String, Object> returnMap = new HashMap<String, Object>();
		UserVo usrDtls = getUserSession();
		try {
			if (result.hasErrors()) {
				returnMap.put("readonly", false);

				model.mergeAttributes(returnMap);

				logger.info("Exit forgetPass()..");
				return "secPage";
			}

			

			vo.setId(decryptId(usrDtls.getEncId()));
			Integer id = userService.createUserSecurity(vo);
			if (id == 0) {
				returnMap.put("passSuccessfully", "not");
				returnMap.put("secSuccessfully", false);
				model.mergeAttributes(returnMap);
				return "secPage";
			}
			redirectAttributes.addFlashAttribute("id", AESencyrptor.encrypt(id.toString()));
			returnMap.put("passSuccessfully", "not");
			returnMap.put("secSuccessfully", true);
			model.mergeAttributes(returnMap);
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.rejectValue(Constants.STRING_ERROR, "error.unknown");
		}
		logger.info("Exit forgetPass()..");
		
		if(usrDtls.getAdminPower()==0 || usrDtls.getAdminPower()== null) {
			return "redirect:/documentList";
		} else {
			return "redirect:/userList";
		}
		return "forgetPass";
		
	}*/
	
	/*@RequestMapping(value = "/forgetPass", method = RequestMethod.GET)
	public ModelAndView displayForgotPasswordPage() {
		return new ModelAndView("forgetPass");
    }*/
	
}