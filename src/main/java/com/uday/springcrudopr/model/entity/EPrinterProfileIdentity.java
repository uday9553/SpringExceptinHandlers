package com.uday.springcrudopr.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class EPrinterProfileIdentity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name="location_code")
	private String empId;
	
	@Column(name="route_number")
	private String location;
	
	@Column(name="emp_name")
	private String empName;
}
