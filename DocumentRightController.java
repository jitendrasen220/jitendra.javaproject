package org.dms.DMS.controller;

import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dms.DMS.entity.Users;
import org.dms.DMS.exceptions.DMSException;
import org.dms.DMS.model.DocumentRightsMstVo;
import org.dms.DMS.model.PaginationResult;
import org.dms.DMS.model.UserVo;
import org.dms.DMS.service.CityService;
import org.dms.DMS.service.DocumentRightsService;
import org.dms.DMS.service.UserService;
import org.dms.DMS.util.AESencyrptor;
import org.dms.DMS.util.Constants;
import org.dms.DMS.util.DMSBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Transactional
@EnableWebMvc
public class DocumentRightController extends DMSBaseController {
	protected Log logger = LogFactory.getLog(this.getClass());
	@Resource(name = "userService")
	private UserService userService;
	
	@Autowired
	@Resource(name = "cityService")
	private CityService cityService;

	@Resource(name = "documentRightsService")
	private DocumentRightsService documentRightsService;
	

	@RequestMapping(value = { "/documentRightsLoad" })
	public String processRightsLoad(Model model, @ModelAttribute("documentRightsMstVo") DocumentRightsMstVo rightsVo,
			@RequestParam(value = "page", defaultValue = "1") int page) {
		logger.info("Enter getUsrDtlsWithPagination()..page->" + page);
		UserVo usrDtls = getUserSession();
		boolean isSuperAdmin=false;
		
		logger.info("usrDtls.getLoginId():: " + usrDtls.getLoginId());
		PaginationResult<UserVo> result = null;
		if (usrDtls.getAdminPower() == Constants.SUPER_ADMIN_POWER) {
			model.addAttribute("companyList", cityService.getAllCompany());
			isSuperAdmin=true;
		} 
		model.addAttribute("paginationUsers", result);
		model.addAttribute("documentRightsMstVo", new DocumentRightsMstVo());
		model.addAttribute("isSuperAdmin", isSuperAdmin);
		logger.info("Exit getUsrDtlsWithPagination()..page->" + page);
		return "documentRights/documentRightsSearch";
	}
	
	
    @RequestMapping(value = { "/documentRightsSearch" })
	public String processRightsSearch(Model model, @ModelAttribute("documentRightsMstVo") DocumentRightsMstVo rightsVo,
			@RequestParam(value = "page", defaultValue = "1") int page) {
		logger.info("Enter getUsrDtlsWithPagination()..page->" + page);
		UserVo userV;
		final int maxResult = 10;
		final int maxNavigationPage = 10;
		UserVo usrDtls = getUserSession();
		logger.info("usrDtls.getLoginId():: " + usrDtls.getCompanyName());
		int admPower=usrDtls.getAdminPower();
		  boolean isSuperAdmin=false;
		PaginationResult<UserVo> result = null;		
	if (StringUtils.isNotBlank(rightsVo.getLoginId()) || StringUtils.isNotBlank(rightsVo.getEmpId()) ||rightsVo.getCompanyId()!=null) {
			UserVo usrVo = new UserVo();
			try {
			usrVo.setLoginId(rightsVo.getLoginId());
			usrVo.setEmpId(rightsVo.getEmpId());
			/*usrVo.setCompanyId(rightsVo.getCompanyId());*/
			usrVo.setAdminPower(admPower);
			   if(admPower==Constants.SUPER_ADMIN_POWER) {
                   usrVo.setCompanyId(rightsVo.getCompanyId());
                }
                else {
                           usrVo.setCompanyId(usrDtls.getCompanyId());
                   }			
			/*userV = userService.getUsrDtls(usrDtls.getLoginId());			
			usrVo.setAdminPower(userV.getAdminPower());
			usrVo.setCompanyName(userV.getCompanyName());
			usrVo.setCompanyId(userV.getCompanyId());
			Integer seasrchComp = rightsVo.getCompanyId();*/
			
			result = userService.getUsrDtlsWithPaginationRight(page, maxResult, maxNavigationPage, usrVo);
			}catch (Exception e) {
				// TODO: handle exception
			}
	}
			else {
                return "redirect:/documentRightsLoad";
        }
	 if (admPower == Constants.SUPER_ADMIN_POWER) {
         isSuperAdmin=true;                                        
 }
        model.addAttribute("isSuperAdmin", isSuperAdmin);        
        model.addAttribute("companyList", cityService.getAllCompany());	
		model.addAttribute("paginationUsers", result);
		model.addAttribute("search", 1);
		model.addAttribute("documentRightsMstVo", rightsVo);
		logger.info("Exit getUsrDtlsWithPagination()..page->" + page);
		return "documentRights/documentRightsSearch";
	}

/*	@RequestMapping(value = { "/documentRightsSearch" })
	public String processRightsSearch(Model model, @ModelAttribute("documentRightsMstVo") DocumentRightsMstVo rightsVo,
			@RequestParam(value = "page", defaultValue = "1") int page) {
		logger.info("Enter getUsrDtlsWithPagination()..page->" + page);
		UserVo userV;
		final int maxResult = 10;
		final int maxNavigationPage = 10;
		UserVo usrDtls = getUserSession();
		logger.info("usrDtls.getLoginId():: " + usrDtls.getCompanyName());
		int admPower=usrDtls.getAdminPower();
		PaginationResult<UserVo> result = null;
		
//		if (StringUtils.isNotBlank(rightsVo.getLoginId()) || StringUtils.isNotBlank(rightsVo.getEmpId())) {
			UserVo usrVo = new UserVo();
			try {
			usrVo.setLoginId(rightsVo.getLoginId());
			usrVo.setEmpId(rightsVo.getEmpId());
			
			userV = userService.getUsrDtls(usrDtls.getLoginId());
			
			usrVo.setAdminPower(userV.getAdminPower());
			usrVo.setCompanyName(userV.getCompanyName());
			usrVo.setCompanyId(userV.getCompanyId());
			Integer seasrchComp = rightsVo.getCompanyId();
			
			result = userService.getUsrDtlsWithPaginationRight(page, maxResult, maxNavigationPage, usrVo);
			
			}catch (Exception e) {
				e.getMessage();
			}
			
//	}
		model.addAttribute("paginationUsers", result);
		model.addAttribute("documentRightsMstVo", rightsVo);
		logger.info("Exit getUsrDtlsWithPagination()..page->" + page);
		return "documentRights/documentRightsSearch";
	}*/

	@RequestMapping(value = { "/documentRightsEdit" })
	public String processRightsEdit(Model model,
			@ModelAttribute("documentRightsMstVo") DocumentRightsMstVo rightsVo, BindingResult result) {
		logger.info("Enter documentRightsEdit()..");
		logger.info("Exit coumentRightsEdit()..");
		return loadExistingprocessRights(rightsVo, model, result, false);
	}

	@RequestMapping(value = { "/documentRightsView" })
	public String processRightsView(HttpServletRequest request, Model model,
			@ModelAttribute("documentRightsMstVo") DocumentRightsMstVo rightsVo, BindingResult result) {
		logger.info("Enter documentRightsView()..");
		logger.info("Exit documentRightsView()..");
		return loadExistingprocessRights(rightsVo, model, result, true);
	}

	private String loadExistingprocessRights(DocumentRightsMstVo rightsMstVo, Model model, BindingResult result,
			boolean readonly) {
		logger.info("Entering loadExistingprocessRights() ..");
		String prmId = (String) model.asMap().get("id");
		logger.info("prmId.." + prmId);
		logger.info("usrVo.getId().." + rightsMstVo.getEncId());
		Map<String, Object> returnMap = model.asMap();
		UserVo usrDtls = getUserSession();
		UserVo userV;
		
	
		
		
		System.out.println("usrDtls)  :"+usrDtls.getLoginId());
		if (returnMap == null) {
			returnMap = new HashMap<String, Object>();
		}
		try {
			if (StringUtils.isNotBlank(rightsMstVo.getEncId())) {
				rightsMstVo.setUsrId(decryptId(rightsMstVo.getEncId()));
			}
			if (StringUtils.isNotBlank(prmId) && StringUtils.isBlank(rightsMstVo.getEncId())) {
				rightsMstVo.setUsrId(decryptId(prmId));
				rightsMstVo.setEncId(prmId);
			}
			if (rightsMstVo.getUsrId() == null || rightsMstVo.getUsrId() <= 0) {
				logger.info("Exiting loadExistingprocessRights() ..");
				return "redirect:/documentRightsLoad";
			}
			userV = userService.getUsrDtls(usrDtls.getLoginId());
			System.out.println("userv   "+userV);
			Users userRig = userService.getUsrDtlsById(rightsMstVo.getUsrId());
			
			returnMap = documentRightsService.getPrcssRightsByVo(rightsMstVo,userRig.getCompany().getCompanyId());
			returnMap.put("readonly", readonly);
			model.mergeAttributes(returnMap);
		} catch (DMSException e) {
			logger.error(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		logger.info("Exiting loadExistingprocessRights() ..");
		return "documentRights/viewAndEditDocumentRights";
	}

	@RequestMapping(value = { "/documentRightsUpdate" }, method = RequestMethod.POST)
	@Transactional // Avoid UnexpectedRollbackException (See more explanations)
	public String updateProcessRights(Model model,@ModelAttribute("documentRightsMstVo") @Validated DocumentRightsMstVo vo, BindingResult result,final RedirectAttributes redirectAttributes) {
		
		logger.info("Enter updatedocumentRights()..");
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		if (result.hasErrors()) {
			returnMap.put("readonly", false);
			model.mergeAttributes(returnMap);
			logger.info("Exit updatedocumentRights()..");
			return "documentRights/viewAndEditDocumentRights";
		}
		try {
			
			vo.setUsrId(decryptId(vo.getEncId()));
			Integer id = documentRightsService.updateProcessRights(vo);
			redirectAttributes.addFlashAttribute("id", AESencyrptor.encrypt(id.toString()));
			redirectAttributes.addFlashAttribute("updatedSuccessfully", true);
			model.mergeAttributes(returnMap);
		
		} catch (DMSException e) {
			logger.error(e.getMessage());
			returnMap.put("readonly", false);
			model.mergeAttributes(returnMap);
			logger.info("Exit   returnMap updateProcessRights()..");
			return "documentRights/viewAndEditDocumentRights";
		
		} catch (Exception e) {
			logger.error(e.getMessage());
			returnMap.put("readonly", false);
			model.mergeAttributes(returnMap);
			logger.info("Exit viewAndEditProcessRights()..");
			return "documentRights/viewAndEditDocumentRights";
		
		}
		logger.info("Exit processRightsView..redirect");
		return "redirect:/documentRightsView";
	
	}
}
