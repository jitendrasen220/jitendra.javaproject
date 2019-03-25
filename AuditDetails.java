package org.dms.DMS.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AuditDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	public AuditDetails() {
		super();
	}

	@Column(name = "CR_USR", updatable= false)
	protected String createUser;
	
	@Column(name = "MD_USR", insertable = false)
	protected String modifyUser;
	
	//@CreationTimestamp
	//@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CR_TS", updatable= false)
	protected Date createTime;
	
	//@UpdateTimestamp
	//@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MD_TS", insertable = false)
	protected Date modifyTime;
	
	public AuditDetails(String loginId, Date date) {
		super();
		this.createUser = loginId;
		this.modifyUser = loginId;
		this.createTime = date;
		this.modifyTime= date;
	}
	
	
	/**
	 * @return the createUser
	 */
	public String getCreateUser() {
		return createUser;
	}
	
	
	/**
	 * @param createUser the createUser to set
	 */
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	
	
	/**
	 * @return the modifyUser
	 */
	public String getModifyUser() {
		return modifyUser;
	}
	
	
	/**
	 * @param modifyUser the modifyUser to set
	 */
	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}
	
	
	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	
	
	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
	/**
	 * @return the modifyTime
	 */
	public Date getModifyTime() {
		return modifyTime;
	}
	
	
	/**
	 * @param modifyTime the modifyTime to set
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
		+ ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result
		+ ((createUser == null) ? 0 : createUser.hashCode());
		result = prime * result
		+ ((modifyTime == null) ? 0 : modifyTime.hashCode());
		result = prime * result
		+ ((modifyUser == null) ? 0 : modifyUser.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AuditDetails other = (AuditDetails) obj;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (createUser == null) {
			if (other.createUser != null)
				return false;
		} else if (!createUser.equals(other.createUser))
			return false;
		if (modifyTime == null) {
			if (other.modifyTime != null)
				return false;
		} else if (!modifyTime.equals(other.modifyTime))
			return false;
		if (modifyUser == null) {
			if (other.modifyUser != null)
				return false;
		} else if (!modifyUser.equals(other.modifyUser))
			return false;
		return true;
	}
}
