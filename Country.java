package org.dms.DMS.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


@Entity
@Table(name = "country",
uniqueConstraints = { @UniqueConstraint(columnNames = "country_id")})
public class Country  implements Serializable {


private static final long serialVersionUID = -7777466842297433669L;

private Integer countryId;
private String name;
private String code;
private String alfaCode;

 
@Id 
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "country_id", unique = true, length = 10, nullable = false)
public Integer getCountryId() {
	return countryId;
}
public void setCountryId(Integer countryId) {
	this.countryId = countryId;
}

@Column(name = "name", length = 30, nullable = false)
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}

@Column(name = "code", length = 10, nullable = false)
public String getCode() {
	return code;
}
public void setCode(String code) {
	this.code = code;
}

@Column(name = "alfa_code", length = 10, nullable = false)
public String getAlfaCode() {
	return alfaCode;
}
public void setAlfaCode(String alfaCode) {
	this.alfaCode = alfaCode;
}

}
