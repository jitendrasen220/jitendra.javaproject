package org.dms.DMS.entity;

import java.io.Serializable;

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

import org.dms.DMS.model.DocIndexListVo;
import org.springframework.stereotype.Component;


@Entity
@Table(name = "doc_index")
@Component
public class DocIndex implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "doc_index_id")
	private Integer docIndxId;

	@Column(name = "doc_index_name")
	private String docIndxNm;
	
	@Column(name = "index_val")
	private Integer indexVal;
	
	@Column(name = "required")
	private Integer required;
	
	@Column(name = "reminder")
	private Integer reminder;

	@OneToOne(targetEntity = DocType.class)
	@JoinColumn(name = "doc_type_id")
	private DocType docType;
	
	@OneToOne(targetEntity = DocIndexType.class)
	@JoinColumn(name = "doc_index_type_id")
	private DocIndexType docIndexType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "indx_mst_id", nullable = true)
	private IndexListMst indexListMst;
	
	@OneToOne(targetEntity = Dept.class)
	@JoinColumn(name = "dept_id")
	private Dept dept;
	
	@OneToOne(targetEntity = Company.class)
	@JoinColumn(name = "COMPANY_ID")
	private Company company;
	
	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

	public Integer getReminder() {
		return reminder;
	}

	public void setReminder(Integer reminder) {
		this.reminder = reminder;
	}

	public Integer getDocIndxId() {
		return docIndxId;
	}

	public void setDocIndxId(Integer docIndxId) {
		this.docIndxId = docIndxId;
	}

	public String getDocIndxNm() {
		return docIndxNm;
	}

	public void setDocIndxNm(String docIndxNm) {
		this.docIndxNm = docIndxNm;
	}

	public Integer getIndexVal() {
		return indexVal;
	}

	public void setIndexVal(Integer indexVal) {
		this.indexVal = indexVal;
	}

	public Integer getRequired() {
		return required;
	}

	public void setRequired(Integer required) {
		this.required = required;
	}

	public DocType getDocType() {
		return docType;
	}

	public void setDocType(DocType docType) {
		this.docType = docType;
	}

	public DocIndexType getDocIndexType() {
		return docIndexType;
	}

	public void setDocIndexType(DocIndexType docIndexType) {
		this.docIndexType = docIndexType;
	}
	
	
	public IndexListMst getIndexListMst() {
		return indexListMst;
	}

	public void setIndexListMst(IndexListMst indexListMst) {
		this.indexListMst = indexListMst;
	}
	
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
}
