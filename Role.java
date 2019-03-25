package org.dms.DMS.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Table(name = "roles")
@Component
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_id")
	private Integer roleId;
	
	@Column(name = "role_name") 
	private String roleNm;
	
	@Column(name = "role_desc")
	private String roleDesc;
	
	@OneToOne(targetEntity = Dept.class)
	@JoinColumn (name = "dept_id")
	private Dept dept; 
	
	@OneToOne(targetEntity = Company.class)
	@JoinColumn(name = "COMPANY_ID")
	private Company company;
	
	public Role() {
		super();
	}
	
	public Role(Integer roleId, String roleNm) {
		super();
		this.roleId = roleId;
		this.roleNm = roleNm;
	}
	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleNm() {
		return roleNm;
	}

	public void setRoleNm(String roleNm) {
		this.roleNm = roleNm;
	}

	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}
	
	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
}
