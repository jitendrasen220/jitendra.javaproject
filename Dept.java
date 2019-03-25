package org.dms.DMS.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.stereotype.Component;

@Entity
@Table(name = "department")
@Component
public class Dept implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5011903980182837028L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "dept_id")
	private Integer departmentId;

	@Column(name = "dept_code")
	private String code;

	@Column(name = "dept_name")
	private String name;

	@Column(name = "dept_desc")
	private String description;
	
	public static final int GENERAL = 1;
	
	@OneToOne(targetEntity = Company.class)
	@JoinColumn(name = "COMPANY_ID")
	private Company company;
	
	public Dept() {
		super();
	}

	public Dept(Integer departmentId) {
		super();
		this.departmentId = departmentId;
	}

	public Dept( String code, String name, String description, String action, int classId) {
		super();
		this.code = code;
		this.name = name;
		this.description = description;
	}



	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the action
	 */

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
	public static int getGeneral() {
		return GENERAL;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @param departmentId
	 * @param name
	 * @param companyId
	 */

}
