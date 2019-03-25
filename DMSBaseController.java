package org.dms.DMS.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.dms.DMS.model.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;

public class DMSBaseController {

	public static final String UNAUTHORIZED_VIEW = "unauthorized.view";

	@Autowired
	private SessionRegistry sessionRegistry;

	protected SessionInformation getSessionInfo() {

		// SessionInformation
		// sessionRegistry.getSessionInformation(request.getSession().getId());
		return sessionRegistry.getSessionInformation(Utils.getRequest().getSession().getId());

	}

	protected UserDetails getSessionUsrDtls() {
		return (UserDetails) getSessionInfo().getPrincipal();
	}

	/**
	 * @return associated request object
	 */
	protected HttpServletRequest getRequest() {
		return Utils.getRequest();
	}

	/**
	 * @return associated response object
	 */
	protected HttpServletResponse getResponse() {
		return Utils.getResponse();
	}

	/**
	 * @return associated session object
	 */
	/*
	 * protected HttpSession getSession() { return Utils.getSession(); }
	 */

	/**
	 * @return UserVo
	 */
	protected UserVo getUserSession() {
		return Utils.getUserSession();
	}
	
	protected UserVo getAjaxUserSession() {
		return Utils.getAjaxUserSession();
	}

	/**
	 * @param Users
	 *            vo
	 */
	protected void setUserSession(UserVo usrVo) {
		Utils.setUserSession(usrVo);
	}


	/**
	 * @return Users
	 */
	protected String getLoggedInUser() {
		return Utils.getUserSession().getLoginId();
	}

	/**
	 * Create New Session
	 */
	protected void generateNewSession() {
		Utils.generateNewSession();
	}

	protected Integer decryptId(String encId) throws Exception {

		Integer Id = null;
		if (StringUtils.isNotBlank(encId)) {
			String decId = encId;
			 decId = AESencyrptor.decrypt(encId);
			 Id = Integer.parseInt(decId);
		}

		return Id;
	}

	/*protected Boolean urlValidate(int proTpId, String function) {
		return Utils.urlValidate(proTpId, function);
	}

	protected List<DocumentRightsVo> getAllUsrProcessType() {
		return Utils.getAllUsrProcessType();
	}*/
}
