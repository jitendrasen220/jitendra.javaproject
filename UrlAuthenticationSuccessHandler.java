package org.dms.DMS.util;

import java.io.IOException;
import java.util.Collection;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dms.DMS.entity.Security;
import org.dms.DMS.entity.Users;
import org.dms.DMS.exceptions.DMSException;
import org.dms.DMS.model.SecurityVo;
import org.dms.DMS.model.UserVo;
import org.dms.DMS.service.ProcessService;
import org.dms.DMS.service.UploadDocumentService;
import org.dms.DMS.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class UrlAuthenticationSuccessHandler extends DMSBaseController implements AuthenticationSuccessHandler   {

	protected Log logger = LogFactory.getLog(this.getClass());
	
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	@Resource(name = "userService")
	private UserService userService;
	
	@Resource(name = "processService")
	private ProcessService processService;
	
	@Resource(name = "uploadDocumentService")
	private UploadDocumentService uploadDocumentService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
	throws IOException {
		logger.info("Enter onAuthenticationSuccess()..");
		handle(request, response, authentication);
		clearAuthenticationAttributes(request);
		logger.info("Enter onAuthenticationSuccess()..");
	}
	
	protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
	
		
		
		if (response.isCommitted()) {
			logger.debug(
			"Response has already been committed. Unable to redirect to targetUrl");
			return;
		}
		ThreadLocalUtility.addToThreadLocal(Constants.HTTP_REQUEST, request);
		ThreadLocalUtility.addToThreadLocal(Constants.HTTP_RESPONSE, response);
		
		UserDetails userDetails = getSessionUsrDtls();
		UserVo usr;
		try {
			usr = userService.getUsrDtlsForSession(userDetails.getUsername());
			setUserSession(usr);

			
			//Set<UsersUrlRights> urlRights = processService.getProcessRightsForUrl(usr.getId());
//			Utils.setUserUrlRights(uploadDocumentService.uploadDocumentByUsrId(usr.getUserId(), false));
			//ThreadLocalUtility.addToThreadLocal(Constants.URL_RIGHTS, urlRights);
			
			Utils.setUserUrlRights(uploadDocumentService.uploadDocumentByUsrId(usr.getUserId(), false,usr.getCompanyId(),usr.getAdminPower(),usr.getDepatmentId(),usr.getUserId()));
			String targetUrl = determineTargetUrl(authentication);
			redirectStrategy.sendRedirect(request, response, targetUrl);
			
		} catch (DMSException e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	
	
	protected String determineTargetUrl(Authentication authentication) {
		logger.info("Enter determineTargetUrl()..");
		boolean isUser = false;
		boolean isAdmin = false;
		boolean security = true;
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			if (grantedAuthority.getAuthority().equals("ROLE_USER") || grantedAuthority.getAuthority().equals("ROLE_HD_OFFICE") ||grantedAuthority.getAuthority().equals("ROLE_USER") || grantedAuthority.getAuthority().equals("ROLE_HD_OFFICE") ||grantedAuthority.getAuthority().equals("ROLE_SR.MANAGER")) {
			
			/*if (grantedAuthority.getAuthority().equals("Admin") || grantedAuthority.getAuthority().equals("Sr.manager")|| grantedAuthority.getAuthority().equals("Jr.manager")) {*/
				isUser = true;
				break;
			} else if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
				isAdmin = true;
				break;
			}
			else {
				isUser = true;
				break;
			}
		}
		UserVo userDetails = getUserSession();
		try {
			SecurityVo us = userService.getSecQues(userDetails.getUserId());
			if (us != null) {
				security = false;
			}
		} catch (DMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		logger.info("Exit determineTargetUrl()..");
		if (security) {
			return "/secPage";
		} else if (isUser) {
			return "/documentList";
		} else if (isAdmin) {
			return "/userList";
		} else {
			throw new IllegalStateException();
		}
		
	}
	
	protected void clearAuthenticationAttributes(HttpServletRequest request) {
		logger.info("Enter clearAuthenticationAttributes()..");
		HttpSession session = request.getSession(false);
		if (session == null) {
			return;
		}
		logger.info("Exit clearAuthenticationAttributes()..");
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}
	
	public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
		this.redirectStrategy = redirectStrategy;
	}
	protected RedirectStrategy getRedirectStrategy() {
		return redirectStrategy;
	}
}
