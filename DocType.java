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

@Entity
@Table(name = "doc_type")
public class DocType implements Serializable {

	public DocType() {
		super();
	}

	private static final long serialVersionUID = -4161460480326642013L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "doc_type_id", unique = true, nullable = false)
	private Integer docTypeId;

	@Column(name = "doc_type_code")
	private String docTypeCode;
	
	@Column(name = "doc_type_name")
	private String docTypeName;
	
	@Column(name = "doc_type_desc")
	private String docTypeDesc;
	
	@OneToOne(targetEntity = Dept.class)
	@JoinColumn(name = "dept_id")
	private Dept dept;
	
	@OneToOne(targetEntity = Company.class)
	@JoinColumn(name = "COMPANY_ID")
	private Company company;
	
	public Integer getDocTypeId() {
		return docTypeId;
	}
	public void setDocTypeId(Integer docTypeId) {
		this.docTypeId = docTypeId;
	}
	
	public String getDocTypeCode() {
		return docTypeCode;
	}
	public void setDocTypeCode(String docTypeCode) {
		this.docTypeCode = docTypeCode;
	}
	public String getDocTypeName() {
		return docTypeName;
	}
	public void setDocTypeName(String docTypeName) {
		this.docTypeName = docTypeName;
	}
	public String getDocTypeDesc() {
		return docTypeDesc;
	}
	public void setDocTypeDesc(String docTypeDesc) {
		this.docTypeDesc = docTypeDesc;
	}
	public Dept getDept() {
		return dept;
	}
	public void setDept(Dept dept) {
		this.dept = dept;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
}
