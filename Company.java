package org.dms.DMS.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.dms.DMS.model.CityVo;
import org.dms.DMS.model.CountryVo;
import org.dms.DMS.model.StateVo;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "company")
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "company_id")
	private Integer companyId;

	@Column(name = "company_name")
	private String companyName;

	@Column(name = "company_type")
	private String companyType;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id")
	private Address address;

	@Column(name = "DOMAIN")
	private String domain;

	@Column(name = "HEAD_OFFICE")
	private Integer hdOfice;

	@Column(name = "COMP_CODE")
	protected String companyCode;

	@Column(name = "URL")
	private String url;

	@Column(name = "EMAIL")
	private String email;

	@Transient
	private Integer addressId;

		
	@Transient
	private Integer cityId;

	@Transient
	private Integer stateId;

	@Transient
	private Integer countryId;

	public Company() {
		super();
	}

	public Company(Integer companyId) {
		super();
		this.companyId = companyId;
	}

	public Company(Integer companyId, String companyName, String companyType, Address address, String domain,
			Integer hdOfice, String companyCode, String url, Integer addressId) {
		super();
		this.companyId = companyId;
		this.companyName = companyName;
		this.companyType = companyType;
		this.address = address;
		this.domain = domain;
		this.hdOfice = hdOfice;
		this.companyCode = companyCode;
		this.url = url;
		this.addressId = addressId;
	}

	/**
	 * @return the companyId
	 */
	public Integer getCompanyId() {
		return companyId;
	}

	/**
	 * @param companyId
	 *            the companyId to set
	 */
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * @param companyName
	 *            the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * @return the companyType
	 */
	public String getCompanyType() {
		return companyType;
	}

	/**
	 * @param companyType
	 *            the companyType to set
	 */
	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	/**
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

	/**
	 * @return the domain
	 */
	public String getDomain() {
		return domain;
	}

	/**
	 * @param domain
	 *            the domain to set
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}

	/**
	 * @return the hdOfice
	 */
	public Integer getHdOfice() {
		return hdOfice;
	}

	/**
	 * @param hdOfice
	 *            the hdOfice to set
	 */
	public void setHdOfice(Integer hdOfice) {
		this.hdOfice = hdOfice;
	}

	/**
	 * @return the companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode
	 *            the companyCode to set
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the addressId
	 */
	public Integer getAddressId() {
		return addressId;
	}

	/**
	 * @param addressId
	 *            the addressId to set
	 */
	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}
     /**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	public Company(Integer companyId, String companyName, String domain, String companyCode, String url) {
		super();
		this.companyId = companyId;
		this.companyName = companyName;
		this.domain = domain;
		this.companyCode = companyCode;
		this.url = url;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	public Integer getCountryId() {
		return countryId;
	}

	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}

	public Company(Integer companyId, String companyName) {
		super();
		this.companyId = companyId;
		this.companyName = companyName;
	}

}
