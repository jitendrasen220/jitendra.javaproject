package org.dms.DMS.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class AccessOptionsVo implements Serializable {

	private static final long serialVersionUID = -5857464360337439489L;

	private Integer accessOptnId;
	private Integer isDashboard;
	private Integer isDocuments;
	private Integer isBpm;
	private Integer isUpload;
	private Integer isSearch;
	private Integer isScanner;
	private Integer isReminders;
	private Integer isReports;
	private Integer isUsrAdmin;

	/**
	 * @return the accessOptnId
	 */
	public Integer getAccessOptnId() {
		return accessOptnId;
	}

	/**
	 * @param accessOptnId
	 *            the accessOptnId to set
	 */
	public void setAccessOptnId(Integer accessOptnId) {
		this.accessOptnId = accessOptnId;
	}

	/**
	 * @return the isDashboard
	 */
	public Integer getIsDashboard() {
		return isDashboard;
	}

	/**
	 * @param isDashboard
	 *            the isDashboard to set
	 */
	public void setIsDashboard(Integer isDashboard) {
		this.isDashboard = isDashboard;
	}

	/**
	 * @return the isDocuments
	 */
	public Integer getIsDocuments() {
		return isDocuments;
	}

	/**
	 * @param isDocuments
	 *            the isDocuments to set
	 */
	public void setIsDocuments(Integer isDocuments) {
		this.isDocuments = isDocuments;
	}

	/**
	 * @return the isBpm
	 */
	public Integer getIsBpm() {
		return isBpm;
	}

	/**
	 * @param isBpm
	 *            the isBpm to set
	 */
	public void setIsBpm(Integer isBpm) {
		this.isBpm = isBpm;
	}

	/**
	 * @return the isUpload
	 */
	public Integer getIsUpload() {
		return isUpload;
	}

	/**
	 * @param isUpload
	 *            the isUpload to set
	 */
	public void setIsUpload(Integer isUpload) {
		this.isUpload = isUpload;
	}

	/**
	 * @return the isSearch
	 */
	public Integer getIsSearch() {
		return isSearch;
	}

	/**
	 * @param isSearch
	 *            the isSearch to set
	 */
	public void setIsSearch(Integer isSearch) {
		this.isSearch = isSearch;
	}

	/**
	 * @return the isScanner
	 */
	public Integer getIsScanner() {
		return isScanner;
	}

	/**
	 * @param isScanner
	 *            the isScanner to set
	 */
	public void setIsScanner(Integer isScanner) {
		this.isScanner = isScanner;
	}

	/**
	 * @return the isReminders
	 */
	public Integer getIsReminders() {
		return isReminders;
	}

	/**
	 * @param isReminders
	 *            the isReminders to set
	 */
	public void setIsReminders(Integer isReminders) {
		this.isReminders = isReminders;
	}

	/**
	 * @return the isReports
	 */
	public Integer getIsReports() {
		return isReports;
	}

	/**
	 * @param isReports
	 *            the isReports to set
	 */
	public void setIsReports(Integer isReports) {
		this.isReports = isReports;
	}

	/**
	 * @return the isUsrAdmin
	 */
	public Integer getIsUsrAdmin() {
		return isUsrAdmin;
	}

	/**
	 * @param isUsrAdmin
	 *            the isUsrAdmin to set
	 */
	public void setIsUsrAdmin(Integer isUsrAdmin) {
		this.isUsrAdmin = isUsrAdmin;
	}

}
