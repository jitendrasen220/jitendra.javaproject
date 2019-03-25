
package org.dms.DMS.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dms.DMS.entity.Company;
import org.dms.DMS.exceptions.DMSException;
import org.dms.DMS.model.PaginationResult;
import org.dms.DMS.service.CityService;
import org.dms.DMS.service.CompanyService;
import org.dms.DMS.util.AESencyrptor;
import org.dms.DMS.util.Constants;
import org.dms.DMS.util.DMSBaseController;
import org.dms.DMS.validator.CompanyValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Transactional
@EnableWebMvc
public class CompanyController extends DMSBaseController {

	protected Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Resource(name = "companyService")
	private CompanyService companyService;

	@Autowired
	@Resource(name = "cityService")
	private CityService cityService;

	@Autowired
	@Qualifier(value = "companyValidator")
	private CompanyValidator companyValidator;

	/*@Autowired
	private UserUtil util;*/
	
	@RequestMapping(value = { "companyUpdate" }, method = RequestMethod.POST)
	@Transactional
	public String updateCompany(Model model, @ModelAttribute("company") @Validated Company company, BindingResult result,
			final RedirectAttributes redirectAttributes) throws DMSException {
		logger.info("Enter updateCompany()..");
		companyValidator.validate(company, result);
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			
			if (result.hasErrors()) {
				returnMap.put("readonly", false);
				returnMap.put("countryList", cityService.getAllCountry());				
				model.mergeAttributes(returnMap);
				logger.info("Exit updateUser()..");
				return "company/viewAndEditCompany";
			}

			Integer id = companyService.updateCompany(company);
			redirectAttributes.addFlashAttribute("companyId", AESencyrptor.encrypt(id.toString()));
			redirectAttributes.addFlashAttribute("updatedSuccessfully", true);
			returnMap.put("company", company);
			model.mergeAttributes(returnMap);

			logger.info("Exit updateUser()..");
			return "redirect:/companyView";
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.rejectValue(Constants.STRING_ERROR, "error.unknown");
		}
		logger.info("Exit updateUser()..");
		return "company/companyList";
	}

	@RequestMapping(value = { "/companyEdit" })
	public String editCompany(HttpServletRequest request, Model model, @ModelAttribute("company") Company company,
			BindingResult result) {
		logger.info("Enter userEdit()..ui data" + company);
		logger.info("Exit companyEdit()..");
		return loadExistingCompany(company, model, result, false);
	}

	@RequestMapping(value = { "/companyView" })
	public String viewCompany(HttpServletRequest request, Model model, @ModelAttribute("company") Company company,
			BindingResult result) {
		logger.info("Exit companyView()..");
		return loadExistingCompany(company, model, result, true);

	}

	private String loadExistingCompany(Company company, Model model, BindingResult result, Boolean readonly) {
		logger.info("Entering loadExistingUser() ..");
		String prmId = (String) model.asMap().get("companyId");
		logger.info("prmId.." + prmId);
		Map<String, Object> returnMap = model.asMap();
		if (returnMap == null) {
			returnMap = new HashMap<String, Object>();
		}
		try {

			if (StringUtils.isNotBlank(prmId))
				company.setCompanyId(AESencyrptor.decryptId(prmId));

			returnMap = companyService.getCompanyDtlsByVo(company);
			returnMap.put("countryList", cityService.getAllCountry());
			returnMap.put("readonly", readonly);
			model.mergeAttributes(returnMap);
			logger.info("usrVo.getId().." + company.getCompanyId() + company.getCompanyCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Exit loadExistingUser()..");
		return "company/viewAndEditCompany";
	}

	@RequestMapping(value = { "/companySave" }, method = RequestMethod.POST)
	@Transactional(propagation = Propagation.NEVER)
	public String saveUser(Model model, @ModelAttribute("company") Company vo, BindingResult result,
			final RedirectAttributes redirectAttributes) {
		logger.info("Enter saveUser()..");
		companyValidator.validate(vo, result);
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			if (result.hasErrors()) {
				returnMap.put("savedSuccessfully", false);
				returnMap.put("createSuccessFlag", false);
				returnMap.put("countryList", cityService.getAllCountry());
				model.mergeAttributes(returnMap);
				logger.info("Exit saveUser()..");
				return "company/newCompany";
			}
			Integer id = companyService.saveCompany(vo);			
			redirectAttributes.addFlashAttribute("companyId", AESencyrptor.encrypt(id.toString()));
			redirectAttributes.addFlashAttribute("savedSuccessfully", true);
			//returnMap.put("savedSuccessfully", false);
			logger.info("Exit saveUser()..");			
			return "redirect:/companyView";

		} catch (DMSException e) {
			logger.error(e.getMessage());
			result.rejectValue(Constants.STRING_ERROR, "error.unknown");
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.rejectValue(Constants.STRING_ERROR, "error.unknown");
		}
		logger.info("Exit saveUser()..");
		return "company/newCompany";
	}

	@RequestMapping(value = { "/companyCreate" }, method = RequestMethod.GET)
	public String createCompany(HttpServletRequest request, Model model, @ModelAttribute("company") Company company) {
		logger.info("Enter createCompany()..");
		try {
			Map<String, Object> returnMap = new HashMap<String, Object>();
			returnMap.put("countryList", cityService.getAllCountry());
			model.mergeAttributes(returnMap);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		logger.info("Exit createUser()..");
		return "company/newCompany";
	}

	@RequestMapping(value = { "/companyList" })
	public String getCompanyDtlPagination(Model model, @ModelAttribute("company") Company vo,
			@RequestParam(value = "page", defaultValue = "1") int page) {
		
		final int maxResult = 10;
		final int maxNavigationPage = 10;
		PaginationResult<Company> result = companyService.getCompanyCompanyWithPagination(page, maxResult, maxNavigationPage, vo);

		model.addAttribute("paginationUsers", result);
		return "company/companyList";
	}
	
	@RequestMapping(value = { "/companyLoad" })
	public String companyLoad(Model model, @ModelAttribute("company") Company vo,
			@RequestParam(value = "page", defaultValue = "1") int page) {

		vo = new Company();
		final int maxResult = 10;
		final int maxNavigationPage = 10;
		PaginationResult<Company> result = companyService.getCompanyCompanyWithPagination(page, maxResult, maxNavigationPage, vo);

		model.addAttribute("paginationUsers", result);
		return "company/companyList";
	}

}
