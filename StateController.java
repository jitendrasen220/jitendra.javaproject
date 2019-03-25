package org.dms.DMS.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dms.DMS.model.PaginationResult;
import org.dms.DMS.model.StateVo;
import org.dms.DMS.service.CityService;
import org.dms.DMS.util.AESencyrptor;
import org.dms.DMS.validator.CountryValidator;
import org.dms.DMS.validator.StateValidator;
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
public class StateController {

private static final Logger logger = Logger.getLogger(StateController.class);
	
	@Autowired
	@Qualifier(value = "stateValidator")
	private StateValidator stateValidator;

	
	@Resource(name = "cityService")
	private CityService cityService;
	
	@InitBinder
	public void myInitBinder(WebDataBinder dataBinder) {
		Object target = dataBinder.getTarget();
		if (target == null) {
			return;
		}
		if (target.getClass() == StateVo.class) {
			dataBinder.setValidator(stateValidator);
		}
	}
	
	 	@RequestMapping(value = { "/stateList" })
		public String getStateDtlsWithPagination(Model model,
				@RequestParam(value = "name", defaultValue = "") String likeName,
				@RequestParam(value = "page", defaultValue = "1") int page) {
			logger.info("Enter getStateDtlsWithPagination()..page->" + page);
			final int maxResult = 10;
			final int maxNavigationPage = 10;
			PaginationResult<StateVo> result = cityService.getStateDtlsWithPagination(page, maxResult, maxNavigationPage, likeName);
			model.addAttribute("paginationState", result);
			model.addAttribute("stateVo", new StateVo());
			logger.info("Exit getStateDtlsWithPagination()..page->" + page);
			return "locations/stateList";

		}
	 	
	 	@RequestMapping(value = { "/stateCreate" }, method = RequestMethod.GET)
		public String newState(HttpServletRequest request, Model model) {
		logger.info("Enter into newState()");

			StateVo stateVo = new StateVo();
			Map<String, Object> returnMap = new HashMap<String, Object>();
			model.addAttribute("stateVo", stateVo);
			returnMap.put("countryList", cityService.getAllCountry());
			model.mergeAttributes(returnMap);
			logger.info("Exit From newState()");
			return "locations/newState";
		}
	 	
		@RequestMapping(value = { "/stateSave" }, method = RequestMethod.POST)
		// Avoid UnexpectedRollbackException (See more explanations)
		@Transactional(propagation = Propagation.NEVER)
		public String saveState(Model model, @ModelAttribute("stateVo") @Validated StateVo vo, BindingResult result,
				final RedirectAttributes redirectAttributes) {
			logger.info("Enter saveState()..");

			Map<String, Object> returnMap = new HashMap<String, Object>();

			if (result.hasErrors()) {
				returnMap.put("createSuccessFlag", false);
				returnMap.put("countryList", cityService.getAllCountry());
				model.mergeAttributes(returnMap);
				logger.info("Exit saveState()..");
				return "locations/newState";
			}
		try {
				/*setDefaultValue(vo);*/
				Integer id = cityService.saveState(vo);
				redirectAttributes.addFlashAttribute("id", AESencyrptor.encrypt(id.toString()));
				redirectAttributes.addFlashAttribute("createSuccessFlag", true);
			} catch (Exception e) {
				e.printStackTrace();
				returnMap.put("createSuccessFlag", false);
				returnMap.put("countryList", cityService.getAllCountry());
				model.mergeAttributes(returnMap);

				logger.info("Exit saveState()..");
				return "locations/newState";
			}
			logger.info("Exit saveState()..");
			return "redirect:/stateView";
		}
		
		@RequestMapping(value = { "/stateEdit" })
		public String editState(HttpServletRequest request, Model model, @ModelAttribute("stateVo") StateVo vo, BindingResult result) {
			logger.info("Enter stateEdit()..");

			logger.info("Exit stateEdit()..");
			return loadExistingState(vo, model, result, false);
		}

		private String loadExistingState(StateVo vo, Model model, BindingResult result, boolean readonly) {
			logger.info("Entering loadExistingState() ..");

			String prmId = (String) model.asMap().get("id");
			Map<String, Object> returnMap = model.asMap();

			try {

				if (StringUtils.isNotBlank(vo.getEncId())) {
					vo.setStateId(decryptId(vo.getEncId()));
				}
				if (StringUtils.isNotBlank(prmId) && StringUtils.isBlank(vo.getEncId())) {
					vo.setStateId(decryptId(prmId));
					vo.setEncId(prmId);
				}		
			returnMap = cityService.getStateDtlsByVo(vo);

			model.addAttribute("stateVo", vo);
			returnMap.put("countryList", cityService.getAllCountry());
			returnMap.put("readonly", readonly);
			model.mergeAttributes(returnMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			returnMap.put("countryList", cityService.getAllCountry());
			e.printStackTrace();
		}

		logger.info("Exit loadExistingState()..");
		return "locations/viewAndEditState";
		}
		
		// POST: Save product
				@RequestMapping(value = { "/stateUpdate" }, method = RequestMethod.POST)
				@Transactional
				public String updateState(Model model, @ModelAttribute("stateVo") @Validated StateVo vo, BindingResult result,
				final RedirectAttributes redirectAttributes) {
				logger.info("Enter updateState()..");		
				Map<String, Object> returnMap = new HashMap<String, Object>();

				if (result.hasErrors()) {
					returnMap.put("readonly", false);
					returnMap.put("countryList", cityService.getAllCountry());
					model.mergeAttributes(returnMap);
					
					logger.info(" Early Exit updateState()..");
					return "locations/viewAndEditState";
				}
				try {
					vo.setStateId(decryptId(vo.getEncId()));
					Integer id = cityService.updateState(vo);
					redirectAttributes.addFlashAttribute("id", AESencyrptor.encrypt(id.toString()));
					redirectAttributes.addFlashAttribute("updatedSuccessfully", true);
					model.mergeAttributes(returnMap);
					
				} catch (Exception e) {
					e.printStackTrace();
					returnMap.put("readonly", false);
					returnMap.put("countryList", cityService.getAllCountry());
					model.mergeAttributes(returnMap);
					
					logger.info("Exit updateState()..");
					return "locations/viewAndEditState";
				}
				
				logger.info("Exit updateState()..");
				return "redirect:/stateView";
			}
				
				@RequestMapping(value = { "/stateView" })
				public String viewUser(HttpServletRequest request, Model model, @ModelAttribute("stateVo") StateVo vo, BindingResult result) {
					logger.info("Enter stateView()..");

					logger.info("Exit stateView()..");
					return loadExistingState(vo, model, result, true);
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
