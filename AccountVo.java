package org.dms.DMS.model;

import java.io.Serializable;

public class AccountVo implements Serializable {

	private static final long serialVersionUID = -2054386655979281969L;

	private String userName;
	private String password;
	private boolean active;
	private String userRole;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String toString() {
		return "[" + this.userName + "," + this.password + "," + this.userRole + "]";
	}

}