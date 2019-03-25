package org.dms.DMS.ajaxController;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dms.DMS.model.UserVo;
import org.dms.DMS.service.UserService;
import org.dms.DMS.util.ApplicationContextHelper;
import org.dms.DMS.util.Constants;
import org.dms.DMS.util.DMSBaseController;
import org.dms.DMS.util.Utils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Transactional
@EnableWebMvc
public class UserAjaxController  extends DMSBaseController  {
	private static final Logger logger = Logger.getLogger(UserAjaxController.class);
	
	UserService userService = null;
	
public String approveUser(String encId) {
		logger.info("Entering approveUser() ");
		
		String apprvMsg = StringUtils.EMPTY;
		try {
			userService = ApplicationContextHelper.getInstance().getManagedBean("userService", UserService.class);
			UserVo usrDtls = Utils.getAjaxUserSession();
			apprvMsg = userService.approveUser(Integer.parseInt(encId), usrDtls.getLoginId());
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION , e);
		}
		logger.info("Exiting approveUser() ");
		return apprvMsg;
	}

}
