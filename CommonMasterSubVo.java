package org.dms.DMS.model;

import java.io.Serializable;
import java.util.Date;

public class CommonMasterSubVo implements Serializable {

	private static final long serialVersionUID = -5045777387378564328L;

	private Integer commonMstSubId;
	private Integer commonMstId;
	private String code;
	private String value;
	private String crUser;
	private Date crDate;
	private String mdUser;
	private Date mdDate;

	public CommonMasterSubVo() {
		super();
	}

	/**
	 * @return the commonMstSubId
	 */
	public Integer getCommonMstSubId() {
		return commonMstSubId;
	}

	public CommonMasterSubVo(Integer commonMstId, String code, String value) {
		super();
		this.commonMstId = commonMstId;
		this.code = code;
		this.value = value;
	}

	/**
	 * @param commonMstSubId
	 *            the commonMstSubId to set
	 */
	public void setCommonMstSubId(Integer commonMstSubId) {
		this.commonMstSubId = commonMstSubId;
	}

	/**
	 * @return the commonMstId
	 */
	public Integer getCommonMstId() {
		return commonMstId;
	}

	/**
	 * @param commonMstId
	 *            the commonMstId to set
	 */
	public void setCommonMstId(Integer commonMstId) {
		this.commonMstId = commonMstId;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value.isEmpty() ? null : value;
	}

	/**
	 * @return the crUser
	 */
	public String getCrUser() {
		return crUser;
	}

	/**
	 * @param crUser
	 *            the crUser to set
	 */
	public void setCrUser(String crUser) {
		this.crUser = crUser;
	}

	/**
	 * @return the crDate
	 */
	public Date getCrDate() {
		return crDate;
	}

	/**
	 * @param crDate
	 *            the crDate to set
	 */
	public void setCrDate(Date crDate) {
		this.crDate = crDate;
	}

	/**
	 * @return the mdUser
	 */
	public String getMdUser() {
		return mdUser;
	}

	/**
	 * @param mdUser
	 *            the mdUser to set
	 */
	public void setMdUser(String mdUser) {
		this.mdUser = mdUser;
	}

	/**
	 * @return the mdDate
	 */
	public Date getMdDate() {
		return mdDate;
	}

	/**
	 * @param mdDate
	 *            the mdDate to set
	 */
	public void setMdDate(Date mdDate) {
		this.mdDate = mdDate;
	}

}