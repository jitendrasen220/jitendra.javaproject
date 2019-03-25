/**
 * 
 */
package org.dms.DMS.util;

import java.io.Serializable;
import org.apache.commons.lang3.StringUtils;
/**
 * @author scanning
 *
 */
public class UrlRights implements Serializable , Comparable<UrlRights> {
	private static final long serialVersionUID = 8674216508891720034L;


	private Integer usrId;
	private String loginId;
	private Integer processTpId;
	private String functional;
	private Integer isAllowed;
	
	
	
	
	
	@Override
	public int compareTo(UrlRights url) {
		if (StringUtils.equalsIgnoreCase(functional, url.getFunctional()) && processTpId == url.getProcessTpId())
			return 0;
		else if (!StringUtils.equalsIgnoreCase(functional, url.getFunctional()) || processTpId != url.getProcessTpId())
			return 1;
		else 
			return -1;
	}

	/**
	 * @return the usrId
	 */
	public Integer getUsrId() {
		return usrId;
	}

	/**
	 * @param usrId the usrId to set
	 */
	public void setUsrId(Integer usrId) {
		this.usrId = usrId;
	}

    /**
	 * @return the loginId
	 */
	public String getLoginId() {
		return loginId;
	}

	/**
	 * @param loginId the loginId to set
	 */
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	/**
	 * @return the processTpId
	 */
	public Integer getProcessTpId() {
		return processTpId;
	}

	/**
	 * @param processTpId the processTpId to set
	 */
	public void setProcessTpId(Integer processTpId) {
		this.processTpId = processTpId;
	}

	/**
	 * @return the functional
	 */
	public String getFunctional() {
		return functional;
	}

	/**
	 * @param functional the functional to set
	 */
	public void setFunctional(String functional) {
		this.functional = functional;
	}

     /**
	 * @return the isAllowed
	 */
	public Integer getIsAllowed() {
		return isAllowed;
	}
/**
	 * @param isAllowed the isAllowed to set
	 */
	public void setIsAllowed(Integer isAllowed) {
		this.isAllowed = isAllowed;
	}


}
