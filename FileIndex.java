package org.dms.DMS.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "file_index", uniqueConstraints = { @UniqueConstraint(columnNames = "file_index_id") })
public class FileIndex implements Serializable {

	public FileIndex() {
		super();
	}

	private static final long serialVersionUID = -4161460480326642013L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "file_index_id", unique = true, nullable = false)
	private Integer fileIndexId;

	/*@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "document_id", nullable = false, foreignKey = @ForeignKey(name = "DOC_ID_PRO_TYPE_FK"))
	private Document document;*/
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "doc_index_id", nullable = false, foreignKey = @ForeignKey(name = "DOC_IDX_ID_PRO_TYPE_FK"))
	private DocIndex documentIndex;
	
	@Column(name = "index_value")
	private String indexValue;
	
	@Column(name = "task_id")
	private Integer taskId;

	/**
	 * @return the fileIndexId
	 */
	public Integer getFileIndexId() {
		return fileIndexId;
	}

	/**
	 * @param fileIndexId the fileIndexId to set
	 */
	public void setFileIndexId(Integer fileIndexId) {
		this.fileIndexId = fileIndexId;
	}

	/**
	 * @return the documentIndex
	 */
	public DocIndex getDocumentIndex() {
		return documentIndex;
	}

	/**
	 * @param documentIndex the documentIndex to set
	 */
	public void setDocumentIndex(DocIndex documentIndex) {
		this.documentIndex = documentIndex;
	}

	/**
	 * @return the indexValue
	 */
	public String getIndexValue() {
		return indexValue;
	}

	/**
	 * @param indexValue the indexValue to set
	 */
	public void setIndexValue(String indexValue) {
		this.indexValue = indexValue;
	}

	/**
	 * @return the taskId
	 */
	public Integer getTaskId() {
		return taskId;
	}

	/**
	 * @param taskId the taskId to set
	 */
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	
}
