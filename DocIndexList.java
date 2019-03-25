package org.dms.DMS.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

public class DocIndexList  implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8645720066038190704L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "doc_index_list_id")
	private Integer docIndxListId;
	
	@Column(name = "doc_index_list_val")
	private String docIndxListVal;
	
	@OneToOne(targetEntity = DocIndex.class)
	@JoinColumn(name = "doc_index_id")
	private DocIndex docIndex;

	public Integer getDocIndxListId() {
		return docIndxListId;
	}

	public void setDocIndxListId(Integer docIndxListId) {
		this.docIndxListId = docIndxListId;
	}

	public String getDocIndxListVal() {
		return docIndxListVal;
	}

	public void setDocIndxListVal(String docIndxListVal) {
		this.docIndxListVal = docIndxListVal;
	}

	public DocIndex getDocIndex() {
		return docIndex;
	}

	public void setDocIndex(DocIndex docIndex) {
		this.docIndex = docIndex;
	}
	
	
}
