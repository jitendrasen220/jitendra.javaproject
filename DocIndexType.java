package org.dms.DMS.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Table(name = "doc_index_type")
@Component
public class DocIndexType implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "doc_index_type_id")
	private Integer docIndxTypId;

	@Column(name = "doc_index_type_name")
	private String docIndxTypNm;

	public Integer getDocIndxTypId() {
		return docIndxTypId;
	}

	public void setDocIndxTypId(Integer docIndxTypId) {
		this.docIndxTypId = docIndxTypId;
	}

	public String getDocIndxTypNm() {
		return docIndxTypNm;
	}

	public void setDocIndxTypNm(String docIndxTypNm) {
		this.docIndxTypNm = docIndxTypNm;
	}

	
}
