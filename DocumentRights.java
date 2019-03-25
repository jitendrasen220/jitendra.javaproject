package org.dms.DMS.entity;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "document_permission", uniqueConstraints = { @UniqueConstraint(columnNames = "doc_perm_id") })
public class DocumentRights implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * @Column is DocumentRights id create been:'documentRightsId'
	 * 
	 * DataBase Name:'doc_perm_id'
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "doc_perm_id", length = 10, unique = true, nullable = false)
	private Integer documentRightsId;

	/*
	 * Users object
	 * 
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "users_id")
	private Users users;

	@ManyToOne(targetEntity = DocType.class)
	@JoinColumn(name = "doc_type_id", nullable = false, foreignKey = @ForeignKey(name = "PROCESS_RIGHTS_PRO_TYPE_FK"))
	private DocType docTypeId;

	@Column(name = "preview", length = 10)
	private Integer preview;

	@Column(name = "download", length = 10)
	private Integer download;

	@Column(name = "print", length = 10)
	private Integer print;

	@Column(name = "mail", length = 10)
	private Integer mail;

	/*
	 * @Column(name = "fax", length = 10) private Integer fax;
	 */

	@Column(name = "upload", length = 10)
	private Integer upload;

	@Column(name = "del", length = 10)
	private Integer del;

	@Column(name = "share", length = 10)
	private Integer share;

	@Column(name = "creator", length = 10)
	private Integer creator;

	@Column(name = "verifier", length = 10)
	private Integer verifier;

	@Column(name = "approve", length = 10)
	private Integer approve;

	public Integer getApprove() {
		return approve;
	}

	public void setApprove(Integer approve) {
		this.approve = approve;
	}

	public Integer getVerifier() {
		return verifier;
	}

	public void setVerifier(Integer verifier) {
		this.verifier = verifier;
	}

	public Integer getCreator() {
		return creator;
	}

	public void setCreator(Integer creator) {
		this.creator = creator;
	}

	public Integer getDocumentRightsId() {
		return documentRightsId;
	}

	public void setDocumentRightsId(Integer documentRightsId) {
		this.documentRightsId = documentRightsId;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public DocType getDocTypeId() {
		return docTypeId;
	}

	public void setDocTypeId(DocType docTypeId) {
		this.docTypeId = docTypeId;
	}

	public Integer getPreview() {
		return preview;
	}

	public void setPreview(Integer preview) {
		this.preview = preview;
	}

	public Integer getDownload() {
		return download;
	}

	public void setDownload(Integer download) {
		this.download = download;
	}

	public Integer getPrint() {
		return print;
	}

	public void setPrint(Integer print) {
		this.print = print;
	}

	public Integer getMail() {
		return mail;
	}

	public void setMail(Integer mail) {
		this.mail = mail;
	}

	/*
	 * public Integer getFax() { return fax; }
	 * 
	 * public void setFax(Integer fax) { this.fax = fax; }
	 */

	public Integer getUpload() {
		return upload;
	}

	public void setUpload(Integer upload) {
		this.upload = upload;
	}

	public Integer getDel() {
		return del;
	}

	public void setDel(Integer del) {
		this.del = del;
	}

	public Integer getShare() {
		return share;
	}

	public void setShare(Integer share) {
		this.share = share;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */

	

}
