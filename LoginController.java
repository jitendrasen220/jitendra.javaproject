package org.dms.DMS.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dms.DMS.config.SessionListener;
import org.dms.DMS.model.UserVo;
import org.dms.DMS.service.UserService;
import org.dms.DMS.util.DMSBaseController;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Controller
// Enable Hibernate Transaction.
@Transactional
// Need to use RedirectAttributes
@EnableWebMvc
public class LoginController extends DMSBaseController {

	protected Log logger = LogFactory.getLog(this.getClass());

	@Resource(name = "userService")
	private UserService userService;

	// GET: Show Login Page
	@RequestMapping(value = { "/login" })
	public String login(Model model) {

		return "login";
	}

	@RequestMapping(value = { "/accountInfo" }, method = RequestMethod.GET)
	public String accountInfo(Model model) {
		logger.info("Enter accountInfo()..");

		/*
		 * UserDetails userDetails = getSessionUsrDtls(); Users usr =
		 * userService.getUsrDtls(userDetails.getUsername()); setUserSession(usr);
		 */
		/*
		 * System.out.println(""+usr.getLoginId());
		 * System.out.println(userDetails.getUsername());
		 * System.out.println(userDetails.isEnabled());
		 */
		/* logger.info(userDetails.getUsername()+" -- "+usr.getLoginId()); */

		// model.addAttribute("userDetails", userDetails);

		logger.info("Exit accountInfo()..");
		return "dashBoard";
	}

	@RequestMapping("/403")
	public String accessDenied() {
		return "/403";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		SessionListener.force2Invalidate(session.getId());
		// session.invalidate();
		return "login";
	}

	@RequestMapping("/")
	public String home() {
		return "index";
	}

	@RequestMapping("/dashBoardLoad")
	public String dashBoardLoad() {
		return "shoppingCart";
	}

}
