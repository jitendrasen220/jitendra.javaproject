package org.dms.DMS.model;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

public class AttachmentVo implements Serializable{
	
	private static final long serialVersionUID = -8133329113485810055L;
	private Integer attchId;
	private String encAttchId;
	private Integer rowIndx;
	private String attchCd;
	private String attchNm;
	private String attchValue;
	private String attchPath;
	private Integer isChckd;
	private MultipartFile atchmnt;
	private String scanAttchNm;
	/**
	 * @return the attchId
	 */
	public Integer getAttchId() {
		return attchId;
	}
	/**
	 * @param attchId the attchId to set
	 */
	public void setAttchId(Integer attchId) {
		this.attchId = attchId;
	}
	/**
	 * @return the encAttchId
	 */
	public String getEncAttchId() {
		return encAttchId;
	}
	/**
	 * @param encAttchId the encAttchId to set
	 */
	public void setEncAttchId(String encAttchId) {
		this.encAttchId = encAttchId;
	}
	/**
	 * @return the rowIndx
	 */
	public Integer getRowIndx() {
		return rowIndx;
	}
	/**
	 * @param rowIndx the rowIndx to set
	 */
	public void setRowIndx(Integer rowIndx) {
		this.rowIndx = rowIndx;
	}
	/**
	 * @return the attchCd
	 */
	public String getAttchCd() {
		return attchCd;
	}
	/**
	 * @param attchCd the attchCd to set
	 */
	public void setAttchCd(String attchCd) {
		this.attchCd = attchCd;
	}
	/**
	 * @return the attchNm
	 */
	public String getAttchNm() {
		return attchNm;
	}
	/**
	 * @param attchNm the attchNm to set
	 */
	public void setAttchNm(String attchNm) {
		this.attchNm = attchNm;
	}
	/**
	 * @return the attchValue
	 */
	public String getAttchValue() {
		return attchValue;
	}
	/**
	 * @param attchValue the attchValue to set
	 */
	public void setAttchValue(String attchValue) {
		this.attchValue = attchValue;
	}
	/**
	 * @return the attchPath
	 */
	public String getAttchPath() {
		return attchPath;
	}
	/**
	 * @param attchPath the attchPath to set
	 */
	public void setAttchPath(String attchPath) {
		this.attchPath = attchPath;
	}
	/**
	 * @return the isChckd
	 */
	public Integer getIsChckd() {
		return isChckd;
	}
	/**
	 * @param isChckd the isChckd to set
	 */
	public void setIsChckd(Integer isChckd) {
		this.isChckd = isChckd;
	}
	/**
	 * @return the atchmnt
	 */
	public MultipartFile getAtchmnt() {
		return atchmnt;
	}
	/**
	 * @param atchmnt the atchmnt to set
	 */
	public void setAtchmnt(MultipartFile atchmnt) {
		this.atchmnt = atchmnt;
	}
	/**
	 * @return the scanAttchNm
	 */
	public String getScanAttchNm() {
		return scanAttchNm;
	}
	/**
	 * @param scanAttchNm the scanAttchNm to set
	 */
	public void setScanAttchNm(String scanAttchNm) {
		this.scanAttchNm = scanAttchNm;
	}
		

}
