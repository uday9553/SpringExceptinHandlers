package com.uday.springcrudopr.model.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="e_printer_profile")
@Data
public class EPrinterProfile {
	
	@EmbeddedId
	EPrinterProfileIdentity printerProfileIdentity;
	
	@Column(name="printer_ip_code")
	private String locationCode;
	
	@Column(name="last_modified_date")
	private String lastModifiedDate;
	
	@Column(name="modified_id")
	private String salary;
	
}
