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

@Component
@Entity
@Table(name = "index_list_mst_sub")
public class IndexListMstSub {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "indx_sub_id")
	private Integer indxMstSubId;
	
	@Column(name = "indx_sub_value")
	private String indxMstSubVal;
	
	
	@OneToOne(targetEntity = IndexListMst.class)
	@JoinColumn(name = "indx_mst_id")
	private IndexListMst indexListMst;


	public IndexListMst getIndexListMst() {
		return indexListMst;
	}

	public void setIndexListMst(IndexListMst indexListMst) {
		this.indexListMst = indexListMst;
	}

	public String getIndxMstSubVal() {
		return indxMstSubVal;
	}

	public void setIndxMstSubVal(String indxMstSubVal) {
		this.indxMstSubVal = indxMstSubVal;
	}

	public Integer getIndxMstSubId() {
		return indxMstSubId;
	}

	public void setIndxMstSubId(Integer indxMstSubId) {
		this.indxMstSubId = indxMstSubId;
	}


}
