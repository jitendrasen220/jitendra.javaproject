package org.dms.DMS.config;

import java.util.Collections;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {
	/*private static final Logger logger = Logger.getLogger(SessionListener.class);*/

	private static Map<String, HttpSession> sessionMX = new HashMap<String, HttpSession>();

	public SessionListener() {
		sessionMX = Collections.synchronizedMap(sessionMX);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http.HttpSessionEvent)
	 */
	@Override
	public void sessionCreated(HttpSessionEvent HttpSessionEvent) {
		HttpSession session = HttpSessionEvent.getSession();   
		sessionMX.put(session.getId(), session);
	}

	/* This Method would be called at the time session destroyed
	 * (non-Javadoc)
	 * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)
	 */
	@Override
	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
		/*logger.info("Entering sessionDestroyed()..");
		UserDao userDao = null;*/
		/*try {*/
			HttpSession httpSession = httpSessionEvent.getSession();

			/*UserVo usrVo = (UserVo) httpSession.getAttribute(Constants.USER_SESSION_ATTRIBUTE);
			userDao = ApplicationContextHelper.getInstance().getManagedBean("userDao", UserDao.class);
			if (usrVo != null) {
				Date logoutDt = new Date();
				Date curLoginTs = DateUtil.parseDateAndTimeInHrMin(usrVo.getCurrLoginTs());
				String loginId = usrVo.getLoginId();
				String usrTp = usrVo.getUsrTp();
				Integer compId = usrVo.getCompId();
				userDao.updateLastLoginDtls(loginId, curLoginTs, logoutDt);
				userDao.updateUserLoginTrack(loginId, httpSession.getId(), usrTp, compId);
			}*/

			sessionMX.remove(httpSession.getId());
		/*} catch (DMSException e) {
			e.printStackTrace();
		}*/

		/*logger.info("Exiting sessionDestroyed()..");*/
	}

	/**
	 * @param sessionId
	 * @return boolean
	 */
	public static boolean isActive(String sessionId) {
		return sessionMX.containsKey(sessionId);
	}

	/**
	 * @param sessionId
	 * @return boolean
	 */
	public static boolean force2Invalidate(String sessionId) {

		boolean flag = false;
		if (isActive(sessionId)) {
			sessionMX.get(sessionId).invalidate();
			flag = true;
		}

		return flag;
	}

}