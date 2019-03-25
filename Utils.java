package org.dms.DMS.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.dms.DMS.entity.Users;
import org.dms.DMS.model.DocumentRightsVo;
import org.dms.DMS.model.UserVo;


public class Utils {

// Project Related
	
	
	public static UserVo getAjaxUserSession() {
		WebContext wctx = WebContextFactory.get();
	    HttpServletRequest request = wctx.getHttpServletRequest();
		return (UserVo) request.getSession().getAttribute(Constants.USER_SESSION_ATTRIBUTE);
	}
	

	public static HttpServletRequest getRequest() {
		return (HttpServletRequest) ThreadLocalUtility.getFromThreadLocal(Constants.HTTP_REQUEST);
	}

	/**
	 * @return associated response object
	 */
	public static HttpServletResponse getResponse() {
		return (HttpServletResponse) ThreadLocalUtility.getFromThreadLocal(Constants.HTTP_RESPONSE);
	}
	
	/**
	 * @return associated session object
	 */
	public static HttpSession getSession() {
		HttpSession session = ((HttpServletRequest) ThreadLocalUtility.getFromThreadLocal(Constants.HTTP_REQUEST)).getSession();
		return session;
	}
	
	/**
	 * @return UserVo
	 */
	public static UserVo getUserSession() {
		return (UserVo) getSession().getAttribute(Constants.USER_SESSION_ATTRIBUTE);
	}
	
/*	public static UserVo getAjaxUserSession() {
		WebContext wctx = WebContextFactory.get();
	    HttpServletRequest request = wctx.getHttpServletRequest();
		return (UserVo) request.getSession().getAttribute(Constants.USER_SESSION_ATTRIBUTE);
	}*/
	
	/**
	 * @param UserVo
	 *            vo
	 */
	public static void setUserSession(UserVo usrVo) {
		/*getSession().setAttribute(Constants.USER_SESSION_ATTRIBUTE, usrDtl);*/
		getSession().setAttribute(Constants.USER_SESSION_ATTRIBUTE, usrVo);
	}
	
	public static void setUserUrlRights( Map<String, Map<String, List<DocumentRightsVo>>> urlRights) {
		getSession().setAttribute(Constants.URL_RIGHTS, urlRights);
	}
	
	public static  Map<String, Map<String, List<DocumentRightsVo>>> getUserUrlRights() {
		return ( Map<String, Map<String, List<DocumentRightsVo>>>) getSession().getAttribute(Constants.URL_RIGHTS);
	}
	
	public static void generateNewSession() {
		HttpSession httpSession = getRequest().getSession(false);

		if (httpSession != null) {
			httpSession.invalidate();
		}

		httpSession = getRequest().getSession(true); // create the session
	}
	
	/*public static Boolean urlValidate(int proTpId, String function) {
		boolean isAllowed = false;
		Map<String, Map<String, List<DocumentRightsVo>>> usrRightList = getUserUrlRights();
		if (usrRightList != null && !usrRightList.isEmpty()) {
			for (String mstNm : usrRightList.keySet()) {
				for (String subNm : usrRightList.get(mstNm).keySet()) {
					for (DocumentRightsVo vo : usrRightList.get(mstNm).get(subNm)) {
						if (vo.getProcessTpId() == proTpId) {
							if (StringUtils.equalsIgnoreCase(Constants.ProcessValidate.APPROVE, function) && vo.getApprove() == 1) {
								isAllowed = true;
								break;
							} else if (StringUtils.equalsIgnoreCase(Constants.ProcessValidate.REJECT, function) && vo.getReject() == 1) {
								isAllowed = true;
								break;
							} else if (StringUtils.equalsIgnoreCase(Constants.ProcessValidate.PREVIEW, function) && vo.getPreview() == 1) {
								isAllowed = true;
								break;
							} else if (StringUtils.equalsIgnoreCase(Constants.ProcessValidate.DOWNLOAD, function) && vo.getDownload() == 1) {
								isAllowed = true;
								break;
							} else if (StringUtils.equalsIgnoreCase(Constants.ProcessValidate.PRINT, function) && vo.getPrint() == 1) {
								isAllowed = true;
								break;
							} else if (StringUtils.equalsIgnoreCase(Constants.ProcessValidate.MAIL, function) && vo.getMail() == 1) {
								isAllowed = true;
								break;
							} else if (StringUtils.equalsIgnoreCase(Constants.ProcessValidate.FAX, function) && vo.getFax() == 1) {
								isAllowed = true;
								break;
							} else if (StringUtils.equalsIgnoreCase(Constants.ProcessValidate.UPLOAD, function) && vo.getUpload() == 1) {
								isAllowed = true;
								break;
							} else if (StringUtils.equalsIgnoreCase(Constants.ProcessValidate.DEL, function) && vo.getDel() == 1) {
								isAllowed = true;
								break;
							} else if (StringUtils.equalsIgnoreCase(Constants.ProcessValidate.CREATOR, function) && vo.getCreator() == 1) {
								isAllowed = true;
								break;
							} else if (StringUtils.equalsIgnoreCase(Constants.ProcessValidate.VERIFIER, function) && vo.getVerifier() == 1) {
								isAllowed = true;
								break;
							}
						}
					}
				}
			}
		}
		return isAllowed;
	}
	
	public static List<DocumentRightsVo> getAllUsrProcessType() {
		List<DocumentRightsVo> voList = new ArrayList<DocumentRightsVo>();
		Map<String, Map<String, List<DocumentRightsVo>>> usrRightList = getUserUrlRights();
		if (usrRightList != null && !usrRightList.isEmpty()) {
			for (String mstNm : usrRightList.keySet()) {
				for (String subNm : usrRightList.get(mstNm).keySet()) {
					for (DocumentRightsVo vo : usrRightList.get(mstNm).get(subNm)) {
							if (vo.getApprove() == 1  || vo.getReject() == 1 || vo.getPreview() == 1 || vo.getDownload() == 1 || vo.getPrint() == 1 || vo.getMail() == 1 ||
									vo.getFax() == 1 || vo.getUpload() == 1 || vo.getDel() == 1 || vo.getCreator() == 1 || vo.getVerifier() == 1) {
								voList.add(vo);
							}
					}
				}
			}
		}
		return voList;
	}*/


	

}
