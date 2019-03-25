package org.dms.DMS.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dms.DMS.entity.Users;
import org.dms.DMS.exceptions.DMSException;
import org.dms.DMS.model.UserVo;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Controller
//Enable Hibernate Transaction.
@Transactional
//Need to use RedirectAttributes
@EnableWebMvc
public class PasswordController extends DMSBaseController {

  protected Log logger = LogFactory.getLog(this.getClass());
	
  @Autowired
  private UserService userService;

  @RequestMapping(value = { "/forgetPass" })
	public ModelAndView forgetPass(Model model, @ModelAttribute("userVo") @Validated UserVo vo, BindingResult result) {
	  	Map<String, Object> returnMap = new HashMap<String, Object>();
	  	returnMap.put("security", 0);
		model.mergeAttributes(returnMap);
		return new ModelAndView("forgetPass");
  }
  
  @RequestMapping(value = { "/forgetUsingId" })
	public String forgetUsingId(HttpServletRequest request, Model model, @ModelAttribute("userVo") UserVo usrVo, BindingResult result) {
		logger.info("Enter forgetUsingId()..");

		Map<String, Object> returnMap = new HashMap<String, Object>();
		//UserDetails userDetails = getSessionUsrDtls();
		UserVo userV;
		try {
			userV = userService.getUsrDtls(usrVo.getLoginId());
			userV.setSecurityVo(userService.getSecurityDtls(userV.getUserId()));
			model.addAttribute("userVo", userV);
			returnMap.put("security", 1);
			returnMap.put("readonly", true);
			model.mergeAttributes(returnMap);
		} catch (DMSException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Exit forgetUsingId()..");
		return "forgetPass";
	}
  
  @RequestMapping(value = { "/updateForgetPass" })
	public String updateForgetPass(HttpServletRequest request, Model model, @ModelAttribute("userVo") UserVo usrVo, BindingResult result) {
		logger.info("Enter updateForgetPass()..");
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			if (result.hasErrors()) {
				returnMap.put("readonly", false);
				returnMap.put("security", 2);
				model.mergeAttributes(returnMap);

				logger.info("Exit updateForgetPass()..");
				return "forgetPass";
			}

			//UserVo usrDtls = getUserSession();

			//vo.setId(decryptId(usrDtls.getEncId()));
			Integer id = userService.updateForgetPass(usrVo);
			if (id == 0) {
				returnMap.put("security", 2);
				model.mergeAttributes(returnMap);
				return "forgetPass";
			}
			//redirectAttributes.addFlashAttribute("id", AESencyrptor.encrypt(id.toString()));
			returnMap.put("security", 3);
			model.mergeAttributes(returnMap);
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.rejectValue(Constants.STRING_ERROR, "error.unknown");
		}
		logger.info("Exit updateForgetPass()..");
		return "forgetPass";
	}
  
  
  /*@RequestMapping(value = { "/resetPass" }, method = RequestMethod.POST)
  @Transactional // Avoid UnexpectedRollbackException (See more explanations)
  public String resetPass(Model model, @ModelAttribute("userVo") @Validated UserVo vo, BindingResult result,
			final RedirectAttributes redirectAttributes) {
	logger.info("Enter resetPass()..");
	Map<String, Object> returnMap = new HashMap<String, Object>();
	try {
		if (result.hasErrors()) {
			returnMap.put("readonly", false);

			model.mergeAttributes(returnMap);

			logger.info("Exit resetPass()..");
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
		logger.info("Exit resetPass()..");
		return "secPage";
  }*/
  
  
  // Display forgotPassword page
  /*@RequestMapping(value = "/forgot", method = RequestMethod.GET)
  public ModelAndView displayForgotPasswordPage() {
    return new ModelAndView("forgotPass");
  }*/
    
  // Process form submission from forgotPassword page
 /* @RequestMapping(value = "/forgot", method = RequestMethod.POST)
  public ModelAndView processForgotPasswordForm(ModelAndView modelAndView, @RequestParam("email") String userEmail, HttpServletRequest request) {

    // Lookup user in database by e-mail
    Optional<User> optional = userService.findUserByEmail(userEmail);

    if (!optional.isPresent()) {
      modelAndView.addObject("errorMessage", "We didn't find an account for that e-mail address.");
    } else {	
      
      // Generate random 36-character string token for reset password 
      User user = optional.get();
      user.setResetToken(UUID.randomUUID().toString());

      // Save token to database
      userService.saveUser(user);

      String appUrl = request.getScheme() + "://" + request.getServerName();
			
      // Email message
      SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
      passwordResetEmail.setFrom("support@demo.com");
      passwordResetEmail.setTo(user.getEmail());
      passwordResetEmail.setSubject("Password Reset Request");
      passwordResetEmail.setText("To reset your password, click the link below:\n" + appUrl
        + "/reset?token=" + user.getResetToken());
			
      emailService.sendEmail(passwordResetEmail);

      // Add success message to view
      modelAndView.addObject("successMessage", "A password reset link has been sent to " + userEmail);
    }
  
    modelAndView.setViewName("forgotPassword");
    return modelAndView;
  }

  // Display form to reset password
  @RequestMapping(value = "/reset", method = RequestMethod.GET)
  public ModelAndView displayResetPasswordPage(ModelAndView modelAndView, @RequestParam("token") String token) {
		
    Optional<User> user = userService.findUserByResetToken(token);

    if (user.isPresent()) { // Token found in DB
      modelAndView.addObject("resetToken", token);
    } else { // Token not found in DB
      modelAndView.addObject("errorMessage", "Oops!  This is an invalid password reset link.");
    }

    modelAndView.setViewName("resetPassword");
    return modelAndView;
  }

  // Process reset password form
  @RequestMapping(value = "/reset", method = RequestMethod.POST)
  public ModelAndView setNewPassword(ModelAndView modelAndView, @RequestParam Map<String, String> requestParams, RedirectAttributes redir) {

    // Find the user associated with the reset token
    Optional<User> user = userService.findUserByResetToken(requestParams.get("token"));

    if (user.isPresent()) {
      User resetUser = user.get(); 
            
      // Set new password	          
      resetUser.setPassword(bCryptPasswordEncoder.encode(requestParams.get("password")));

      // Set the reset token to null so it cannot be used again
      resetUser.setResetToken(null);

      // Save user
      userService.saveUser(resetUser);

      // In order to set a model attribute on a redirect, we must use RedirectAttributes
      redir.addFlashAttribute("successMessage", "You have successfully reset your password.  You may now login.");

      modelAndView.setViewName("redirect:login");
      return modelAndView;
    } else {
      modelAndView.addObject("errorMessage", "Oops!  This is an invalid password reset link.");
      modelAndView.setViewName("resetPassword");	
    }
		
    return modelAndView;
  }
   
  // Going to reset page without a token redirects to login page
  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ModelAndView handleMissingParams(MissingServletRequestParameterException ex) {
    return new ModelAndView("redirect:login");
  }*/
}