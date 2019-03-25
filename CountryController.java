package org.dms.DMS.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dms.DMS.model.CountryVo;
import org.dms.DMS.model.PaginationResult;
import org.dms.DMS.service.CityService;
import org.dms.DMS.util.AESencyrptor;
import org.dms.DMS.util.DMSBaseController;
import org.dms.DMS.validator.CountryValidator;
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
//Enable Hibernate Transaction.
@Transactional
//Need to use RedirectAttributes
@EnableWebMvc
public class CountryController  extends DMSBaseController{

private static final Logger logger = Logger.getLogger(CountryController.class);
	
	@Autowired
	@Qualifier(value = "countryValidator")
	private CountryValidator countryValidator;
	
	@Resource(name = "cityService")
	private CityService cityService;
	
	@InitBinder
	public void myInitBinder(WebDataBinder dataBinder) {
		Object target = dataBinder.getTarget();
		if (target == null) {
			return;
		}
		if (target.getClass() == CountryVo.class) {
			dataBinder.setValidator(countryValidator);
		}
	}
	
	 	@RequestMapping(value = { "/countryList" })
		public String getCountryDtlsWithPagination(Model model,
			@RequestParam(value = "name", defaultValue = "") String likeName,
			@RequestParam(value = "page", defaultValue = "1") int page) {
			logger.info("Enter getCountryDtlsWithPagination()..page->" + page);
			final int maxResult = 10;
			final int maxNavigationPage = 10;
			PaginationResult<CountryVo> result = cityService.getCountryDtlsWithPagination(page, maxResult, maxNavigationPage, likeName);
			model.addAttribute("paginationCountry", result);
			model.addAttribute("countryVo", new CountryVo());
			logger.info("Exit getCountryDtlsWithPagination()..page->" + page);
			return "locations/countryList";

		}	
	 	/**
		 * @param request
		 * @param model
		 * @return
		 */
	 	
	 	
	 	@RequestMapping(value = { "/countryCreate" }, method = RequestMethod.GET)
		public String newCountry(HttpServletRequest request, Model model) {
		logger.info("Enter into newCountry()");

			CountryVo cntryVo = new CountryVo();
			Map<String, Object> returnMap = new HashMap<String, Object>();
			model.addAttribute("countryVo", cntryVo);
			model.mergeAttributes(returnMap);
			logger.info("Exit From newCountry()");
			return "locations/newCountry";
		}
	 	
		@RequestMapping(value = { "/countrySave" }, method = RequestMethod.POST)
		// Avoid UnexpectedRollbackException (See more explanations)
		@Transactional(propagation = Propagation.NEVER)
		public String saveCountry(Model model, @ModelAttribute("countryVo") @Validated CountryVo vo, BindingResult result,
				final RedirectAttributes redirectAttributes) {
			logger.info("Enter saveCountry()..");

			Map<String, Object> returnMap = new HashMap<String, Object>();

			if (result.hasErrors()) {
				returnMap.put("createSuccessFlag", false);

				model.mergeAttributes(returnMap);

				logger.info("Exit saveCountry()..");
				return "locations/newCountry";
			}
			try {
				/*setDefaultValue(vo);*/
				Integer id = cityService.saveCountry(vo);
				redirectAttributes.addFlashAttribute("id", AESencyrptor.encrypt(id.toString()));
				redirectAttributes.addFlashAttribute("createSuccessFlag", true);
			} catch (Exception e) {
				e.printStackTrace();
				returnMap.put("createSuccessFlag", false);
				model.mergeAttributes(returnMap);

				logger.info("Exit saveCountry()..");
				return "locations/newCountry";
			}

			logger.info("Exit saveCountry()..");
			return "redirect:/countryView";
		}
		
		@RequestMapping(value = { "/countryView" })
		public String viewUser(HttpServletRequest request, Model model, @ModelAttribute("countryVo") CountryVo vo, BindingResult result) {
			logger.info("Enter countryView()..");

			logger.info("Exit countryView()..");
			return loadExistingCountry(vo, model, result, true);
		}
		
		
		@RequestMapping(value = { "/countryEdit" })
		public String editCountry(HttpServletRequest request, Model model, @ModelAttribute("countryVo") CountryVo vo, BindingResult result) {
			logger.info("Enter countryEdit()..");

			logger.info("Exit countryEdit()..");
			return loadExistingCountry(vo, model, result, false);
		}
		
		

		private String loadExistingCountry(CountryVo vo, Model model, BindingResult result, boolean readonly) {
			logger.info("Entering loadExistingCountry() ..");

			String prmId = (String) model.asMap().get("id");
			Map<String, Object> returnMap = model.asMap();

			try {

				if (StringUtils.isNotBlank(vo.getEncId())) {
					vo.setCountryId(decryptId(vo.getEncId()));
				}
				if (StringUtils.isNotBlank(prmId) && StringUtils.isBlank(vo.getEncId())) {
					vo.setCountryId(decryptId(prmId));
					vo.setEncId(prmId);
				}		
			returnMap = cityService.getCountryDtlsByVo(vo);

			model.addAttribute("countryVo", vo);
			returnMap.put("readonly", readonly);
			model.mergeAttributes(returnMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.info("Exit loadExistingCountry()..");
		return "locations/viewAndEditCountry";
	}
		// POST: Save product
		@RequestMapping(value = { "/countryUpdate" }, method = RequestMethod.POST)
		// Avoid UnexpectedRollbackException (See more explanations)
		@Transactional
		public String updateCountry(Model model, @ModelAttribute("countryVo") @Validated CountryVo vo, BindingResult result,
				final RedirectAttributes redirectAttributes) {
			logger.info("Enter updateCountry()..");		
			Map<String, Object> returnMap = new HashMap<String, Object>();

			if (result.hasErrors()) {
				returnMap.put("readonly", false);
				model.mergeAttributes(returnMap);
				
				logger.info(" Early Exit updateCountry()..");
				return "locations/viewAndEditCountry";
			}
			try {
				//setDefaultValue(vo);
				vo.setCountryId(decryptId(vo.getEncId()));
				Integer id = cityService.updateCountry(vo);
				redirectAttributes.addFlashAttribute("id", AESencyrptor.encrypt(id.toString()));
				redirectAttributes.addFlashAttribute("updatedSuccessfully", true);
				//returnMap.put("userVo", vo);
				model.mergeAttributes(returnMap);
				
			} catch (Exception e) {
				e.printStackTrace();
				returnMap.put("readonly", false);
				model.mergeAttributes(returnMap);
				
				logger.info("Exit updateCountry()..");
				return "locations/viewAndEditCountry";
			}
			
			logger.info("Exit updateCountry()..");
			return "redirect:/countryView";
		}	

		

}
