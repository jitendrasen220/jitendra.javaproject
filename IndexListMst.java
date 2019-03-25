package org.dms.DMS.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "index_list_mst")
public class IndexListMst implements Serializable {

	private static final long serialVersionUID = 5449149724886849030L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "indx_mst_id")
	private Integer indxMstId;
	
	@Column(name = "indx_mst_nm")
	private String indxMstNm;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "indexListMst", orphanRemoval = true, fetch = FetchType.LAZY)
	protected List<IndexListMstSub> indxSubList;
	
	public Integer getIndxMstId() {
		return indxMstId;
	}

	public void setIndxMstId(Integer indxMstId) {
		this.indxMstId = indxMstId;
	}

	public String getIndxMstNm() {
		return indxMstNm;
	}

	public void setIndxMstNm(String indxMstNm) {
		this.indxMstNm = indxMstNm;
	}

	/**
	 * @return the indxSubList
	 */
	public List<IndexListMstSub> getIndxSubList() {
		return indxSubList;
	}

	/**
	 * @param indxSubList the indxSubList to set
	 */
	public void setIndxSubList(List<IndexListMstSub> indxSubList) {
		this.indxSubList = indxSubList;
	}
	
	
}
