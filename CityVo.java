package org.dms.DMS.model;

import java.io.Serializable;

import org.dms.DMS.util.AESencyrptor;

public class CityVo implements Serializable {

	private static final long serialVersionUID = -2576670215015463100L;
	
	private Integer cityId;
	private Integer stateId;
	private String name;
	private String stateName;
	protected String encId;		
	private String countryName;
	private Integer countryId;
	private String code;
	private String alfaCode;

	/**
	 * @return the cityId
	 * @throws 	 
	 */
	
	public CityVo(Integer cityId, String name, String stateName, String CountryName ) throws Exception {
		this.encId  = AESencyrptor.encrypt(cityId.toString());
		this.cityId = cityId;
		this.name = name;
		this.setStateName(stateName); 
		this.setCountryName(CountryName);
	}
	
	public CityVo(Integer cityId, String name, String stateName, String CountryName, String code, String alfaCode) throws Exception {
		this.encId  = AESencyrptor.encrypt(cityId.toString());
		this.cityId = cityId;
		this.name = name;
		this.setStateName(stateName); 
		this.setCountryName(CountryName);
		this.setCode(code);
		this.setAlfaCode(alfaCode);
	}
	
	public Integer getCityId() {
		return cityId;
	}
	/**
	 * @param cityId the cityId to set
	 */
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	/**
	 * @return the stateId
	 */
	public Integer getStateId() {
		return stateId;
	}
	/**
	 * @param stateId the stateId to set
	 */
	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEncId() {
		return encId;
	}

	/**
	 * @param encId the encId to set
	 */
	public void setEncId(String encId) {
		this.encId = encId;
	}
	public CityVo(Integer cityId, Integer stateId, String name) {
		super();
		this.cityId = cityId;
		this.stateId = stateId;
		this.name = name;
	}
	public CityVo() {
		// TODO Auto-generated constructor stub
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public Integer getCountryId() {
		return countryId;
	}
	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getAlfaCode() {
		return alfaCode;
	}
	public void setAlfaCode(String alfaCode) {
		this.alfaCode = alfaCode;
	}

}