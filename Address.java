package org.dms.DMS.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "address")
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "address_id")
	private Integer addId;

	@Column(name = "ADDRESS1")
	private String add1;

	@Column(name = "ADDRESS2")
	private String add2;

	@Column(name = "ADDRESS3")
	private String add3;

	@Column(name = "pincode")
	private String pinCode;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "city_id")
	private City city;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "state_id")
	private State state;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "country_id")
	private Country country;

	@Column(name = "fax")
	private String fax;

	@Column(name = "mobile")
	private String mobile;

	@Column(name = "telephone")
	private String telephone;

	@Column(name = "fax_cd")
	private String faxCd;

	@Column(name = "mobile_cd")
	private String mobileCd;

	@Column(name = "telephone_cd")
	private String telephoneCd;

	@Transient
	private Integer cityId;

	@Transient
	private Integer stateId;

	@Transient
	private Integer cntryId;

	public Address() {
		super();
	}

	public Address(Integer addId) {
		super();
		this.addId = addId;
	}

	/**
	 * @param addId
	 * @param add1
	 * @param add2
	 * @param add3
	 * @param pinCode
	 * @param city
	 * @param state
	 * @param country
	 */
	public Address(Integer addId, String add1, String add2, String add3, String pinCode, City city, State state,
			Country country) {
		super();
		this.addId = addId;
		this.add1 = add1;
		this.add2 = add2;
		this.add3 = add3;
		this.pinCode = pinCode;
		this.city = city;
		this.state = state;
		this.country = country;
	}

	/**
	 * @return the addId
	 */
	public Integer getAddId() {
		return addId;
	}

	/**
	 * @param addId
	 *            the addId to set
	 */
	public void setAddId(Integer addId) {
		this.addId = addId;
	}

	/**
	 * @return the add1
	 */
	public String getAdd1() {
		return add1;
	}

	/**
	 * @param add1
	 *            the add1 to set
	 */
	public void setAdd1(String add1) {
		this.add1 = add1;
	}

	/**
	 * @return the add2
	 */
	public String getAdd2() {
		return add2;
	}

	/**
	 * @param add2
	 *            the add2 to set
	 */
	public void setAdd2(String add2) {
		this.add2 = add2;
	}

	/**
	 * @return the add3
	 */
	public String getAdd3() {
		return add3;
	}

	/**
	 * @param add3
	 *            the add3 to set
	 */
	public void setAdd3(String add3) {
		this.add3 = add3;
	}

	/**
	 * @return the pinCode
	 */
	public String getPinCode() {
		return pinCode;
	}

	/**
	 * @param pinCode
	 *            the pinCode to set
	 */
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	/**
	 * @return the city
	 */
	public City getCity() {
		return city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(City city) {
		this.city = city;
	}

	/**
	 * @return the state
	 */
	public State getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(State state) {
		this.state = state;
	}

	/**
	 * @return the country
	 */
	public Country getCountry() {
		return country;
	}

	/**
	 * @param country
	 *            the country to set
	 */
	public void setCountry(Country country) {
		this.country = country;
	}

	/**
	 * @return the fax
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * @param fax
	 *            the fax to set
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile
	 *            the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the telephone
	 */
	public String getTelephone() {
		return telephone;
	}

	/**
	 * @param telephone
	 *            the telephone to set
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getFaxCd() {
		return faxCd;
	}

	public void setFaxCd(String faxCd) {
		this.faxCd = faxCd;
	}

	public String getMobileCd() {
		return mobileCd;
	}

	public void setMobileCd(String mobileCd) {
		this.mobileCd = mobileCd;
	}

	public String getTelephoneCd() {
		return telephoneCd;
	}

	public void setTelephoneCd(String telephoneCd) {
		this.telephoneCd = telephoneCd;
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

	public Integer getCntryId() {
		return cntryId;
	}

	public void setCntryId(Integer cntryId) {
		this.cntryId = cntryId;
	}

}
