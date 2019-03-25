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
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "city",
uniqueConstraints = { @UniqueConstraint(columnNames = "city_id") })
public class City implements Serializable {

	private static final long serialVersionUID = -2576670215015463100L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "city_id", length = 10, unique = true, nullable = false)
	private Integer cityId;

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "state")
	private State state;
	
	@Column(name = "name", length = 30, nullable = false)
	private String name;
	
	/**
	 * @return the cityId
	 */
	public Integer getCityId() {
		return cityId;
	}
	
	/**
	 * @param cityId the cityId to set
	 */
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	
	/**
	 * @return the state
	 */
	public State getState() {
		return state;
	}
	
	/**
	 * @param state the state to set
	 */
	public void setState(State state) {
		this.state = state;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}