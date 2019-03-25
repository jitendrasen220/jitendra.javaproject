package org.dms.DMS.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dms.DMS.model.CityVo;
import org.dms.DMS.model.PaginationResult;
import org.dms.DMS.service.CityService;
import org.dms.DMS.util.AESencyrptor;
import org.dms.DMS.validator.CityValidator;
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
public class CityController {
private static final Logger logger = Logger.getLogger(CityController.class);
	

	
	@Resource(name = "cityService")
	private CityService cityService;
	
	@Resource(name = "cityValidator")
	private CityValidator cityValidator;
	
	@InitBinder
	public void myInitBinder(WebDataBinder dataBinder) {
		Object target = dataBinder.getTarget();
		if (target == null) {
			return;
		}
		if (target.getClass() == CityVo.class) {
			dataBinder.setValidator(cityValidator);
		}
	}
	
	 	@RequestMapping(value = { "/cityList" })
		public String getCityDtlsWithPagination(Model model,
				@RequestParam(value = "name", defaultValue = "") String likeName,
				@RequestParam(value = "page", defaultValue = "1") int page) {
			logger.info("Enter getCityDtlsWithPagination()..page->" + page);
			final int maxResult = 10;
			final int maxNavigationPage = 10;
			PaginationResult<CityVo> result = cityService.getCityDtlsWithPagination(page, maxResult, maxNavigationPage, likeName);
			model.addAttribute("paginationCity", result);
			model.addAttribute("cityVo", new CityVo());
			logger.info("Exit getCityDtlsWithPagination()..page->" + page);
			return "locations/cityList";

		}
	 	
	 	@RequestMapping(value = { "/cityCreate" }, method = RequestMethod.GET)
		public String newCity(HttpServletRequest request, Model model) {
		logger.info("Enter into newCity()");

			CityVo cityVo = new CityVo();
			Map<String, Object> returnMap = new HashMap<String, Object>();
			model.addAttribute("cityVo", cityVo);
			returnMap.put("countryList", cityService.getAllCountry());
			model.mergeAttributes(returnMap);
			logger.info("Exit From newCity()");
			return "locations/newCity";
		}
	 	
		@RequestMapping(value = { "/citySave" }, method = RequestMethod.POST)
		// Avoid UnexpectedRollbackException (See more explanations)
		@Transactional(propagation = Propagation.NEVER)
		public String saveCity(Model model, @ModelAttribute("cityVo") @Validated CityVo vo, BindingResult result,
				final RedirectAttributes redirectAttributes) {
			logger.info("Enter saveCity()..");

			Map<String, Object> returnMap = new HashMap<String, Object>();

			if (result.hasErrors()) {
				returnMap.put("createSuccessFlag", false);
				returnMap.put("countryList", cityService.getAllCountry());
				model.mergeAttributes(returnMap);
				logger.info("Exit saveCity()..");
				return "locations/newCity";
			}
		try {
				/*setDefaultValue(vo);*/
				Integer id = cityService.saveCity(vo);
				redirectAttributes.addFlashAttribute("id", AESencyrptor.encrypt(id.toString()));
				redirectAttributes.addFlashAttribute("createSuccessFlag", true);
			} catch (Exception e) {
				e.printStackTrace();
				returnMap.put("createSuccessFlag", false);
				returnMap.put("countryList", cityService.getAllCountry());
				model.mergeAttributes(returnMap);

				logger.info("Exit saveCity()..");
				return "locations/newState";
			}
			logger.info("Exit saveCity()..");
			return "redirect:/cityView";
		}
		@RequestMapping(value = { "/cityEdit" })
		public String editCity(HttpServletRequest request, Model model, @ModelAttribute("cityVo") CityVo vo, BindingResult result) {
			logger.info("Enter cityEdit()..");
			return loadExistingCity(vo, model, result, false);

		}
		
		
	 	
	 	private String loadExistingCity(CityVo vo, Model model, BindingResult result, boolean readonly) {
	 		logger.info("Entering loadExistingCity() ..");

			String prmId = (String) model.asMap().get("id");
			Map<String, Object> returnMap = model.asMap();

			try {

				if (StringUtils.isNotBlank(vo.getEncId())) {
					vo.setCityId(decryptId(vo.getEncId()));
				}
				if (StringUtils.isNotBlank(prmId) && StringUtils.isBlank(vo.getEncId())) {
					vo.setCityId(decryptId(prmId));
					vo.setEncId(prmId);
				}		
			returnMap = cityService.getCityDtlsByVo(vo);

			model.addAttribute("cityVo", vo);
			returnMap.put("countryList", cityService.getAllCountry());
			returnMap.put("readonly", readonly);
			model.mergeAttributes(returnMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.info("Exit loadExistingCity()..");
		return "locations/viewAndEditCity";
		}
	 	
		// POST: Save product
		@RequestMapping(value = { "/cityUpdate" }, method = RequestMethod.POST)
		@Transactional
		public String updateCity(Model model, @ModelAttribute("cityVo") @Validated CityVo vo, BindingResult result,
		final RedirectAttributes redirectAttributes) {
		logger.info("Enter updateCity()..");		
		Map<String, Object> returnMap = new HashMap<String, Object>();

		if (result.hasErrors()) {
			returnMap.put("readonly", false);
			returnMap.put("countryList", cityService.getAllCountry());
			model.mergeAttributes(returnMap);
			
			logger.info(" Early Exit updateCity()..");
			return "locations/viewAndEditCity";
		}
		try {
			vo.setCityId(decryptId(vo.getEncId()));
			Integer id = cityService.updateCity(vo);
			redirectAttributes.addFlashAttribute("id", AESencyrptor.encrypt(id.toString()));
			redirectAttributes.addFlashAttribute("updatedSuccessfully", true);
			model.mergeAttributes(returnMap);
			
		} catch (Exception e) {
			e.printStackTrace();
			returnMap.put("readonly", false);
			returnMap.put("countryList", cityService.getAllCountry());
			model.mergeAttributes(returnMap);
			
			logger.info("Exit updateCity()..");
			return "locations/viewAndEditCity";
		}
		
		logger.info("Exit updateCity()..");
		return "redirect:/cityView";
	}
		@RequestMapping(value = { "/cityView" })
		public String viewUser(HttpServletRequest request, Model model, @ModelAttribute("cityVo") CityVo vo, BindingResult result) {
			logger.info("Enter cityView()..");

			logger.info("Exit cityView()..");
			return loadExistingCity(vo, model, result, true);
		}


		@RequestMapping(value = { "/advLocList" })
		public String getAdvLocDtlsWithPagination(Model model,
				@RequestParam(value = "name", defaultValue = "") String likeName,
				@RequestParam(value = "page", defaultValue = "1") int page) {
			logger.info("Enter getCityDtlsWithPagination()..page->" + page);
			final int maxResult = 10;
			final int maxNavigationPage = 10;
			PaginationResult<CityVo> result = cityService.getCityDtlsWithPagination(page, maxResult, maxNavigationPage, likeName);
			model.addAttribute("paginationAdvLoc", result);
			model.addAttribute("cityVo", new CityVo());
			logger.info("Exit getCityDtlsWithPagination()..page->" + page);
			return "locations/advLocList";

		}
		protected Integer decryptId(String encId) throws Exception {

			Integer Id = null;
			if (StringUtils.isNotBlank(encId)) {
				String decId = AESencyrptor.decrypt(encId);
				Id = Integer.parseInt(decId);
			}

			return Id;
		}
}
