package org.dms.DMS.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;




@Entity
@Table(name = "document", uniqueConstraints = { @UniqueConstraint(columnNames = "document_id") })
public class Document implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "document_id", length = 10, unique = true, nullable = false)
	private Integer documentId;

	@Column(name = "upd_file_name")
	private String updFileName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "doc_type_id", nullable = false, foreignKey = @ForeignKey(name = "PROCESS_RIGHTS_PRO_TYPE_FK"))
	private DocType docTypeId;

	@Column(name = "uploaded_date")
	private Date UploadedDate;

	@Column(name = "size")
	private Integer size;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dept_id", nullable = false, foreignKey = @ForeignKey(name = "DEPT_ID_PRO_TYPE_FK"))
	private Dept dept;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "users_id", nullable = false, foreignKey = @ForeignKey(name = "USERS_ID_PRO_TYPE_FK"))
	private Users users;

	@Column(name = "process_id")
	private Integer processId;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "doc_file_map",  joinColumns = { @JoinColumn(name = "doc_id") },  inverseJoinColumns = { @JoinColumn(name = "file_index_id") })
	private List<FileIndex> fileIndxList;

	@OneToOne(targetEntity = Company.class)
	@JoinColumn(name = "COMPANY_ID")
	private Company company;
	
	/**
	 * @return the documentId
	 */
	public Integer getDocumentId() {
		return documentId;
	}

	/**
	 * @param documentId the documentId to set
	 */
	public void setDocumentId(Integer documentId) {
		this.documentId = documentId;
	}

	/**
	 * @return the updFileName
	 */
	public String getUpdFileName() {
		return updFileName;
	}

	/**
	 * @param updFileName the updFileName to set
	 */
	public void setUpdFileName(String updFileName) {
		this.updFileName = updFileName;
	}

	/**
	 * @return the docTypeId
	 */
	public DocType getDocTypeId() {
		return docTypeId;
	}

	/**
	 * @param docTypeId the docTypeId to set
	 */
	public void setDocTypeId(DocType docTypeId) {
		this.docTypeId = docTypeId;
	}

	/**
	 * @return the uploadedDate
	 */
	public Date getUploadedDate() {
		return UploadedDate;
	}

	/**
	 * @param uploadedDate the uploadedDate to set
	 */
	public void setUploadedDate(Date uploadedDate) {
		UploadedDate = uploadedDate;
	}

	/**
	 * @return the size
	 */
	public Integer getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(Integer size) {
		this.size = size;
	}

	/**
	 * @return the dept
	 */
	public Dept getDept() {
		return dept;
	}

	/**
	 * @param dept the dept to set
	 */
	public void setDept(Dept dept) {
		this.dept = dept;
	}

	/**
	 * @return the users
	 */
	public Users getUsers() {
		return users;
	}

	/**
	 * @param users the users to set
	 */
	public void setUsers(Users users) {
		this.users = users;
	}

	/**
	 * @return the processId
	 */
	public Integer getProcessId() {
		return processId;
	}

	/**
	 * @param processId the processId to set
	 */
	public void setProcessId(Integer processId) {
		this.processId = processId;
	}

	/**
	 * @return the fileIndxList
	 */
	public List<FileIndex> getFileIndxList() {
		return fileIndxList;
	}

	/**
	 * @param fileIndxList the fileIndxList to set
	 */
	public void setFileIndxList(List<FileIndex> fileIndxList) {
		this.fileIndxList = fileIndxList;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
}
