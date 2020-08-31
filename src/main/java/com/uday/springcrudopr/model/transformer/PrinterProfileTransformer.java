package com.uday.springcrudopr.model.transformer;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.uday.springcrudopr.model.Employee;
import com.uday.springcrudopr.model.entity.EPrinterProfile;
import com.uday.springcrudopr.model.entity.EPrinterProfileIdentity;



@Component
public class PrinterProfileTransformer {
	public Function<EPrinterProfile,Employee> convertToPrinterProfile(){
		Function<EPrinterProfile,Employee>  mapper=(EPrinterProfile ePrinterProfile)->{
			Employee printerProfile=Employee.builder()
							.locationCode(ePrinterProfile.getPrinterProfileIdentity().getEmpId())
							.empName(ePrinterProfile.getPrinterProfileIdentity().getEmpName())
							.locationCode(ePrinterProfile.getLocationCode())
							.location(ePrinterProfile.getPrinterProfileIdentity().getLocation())
							.lastModifiedDate(ePrinterProfile.getLastModifiedDate())
							.salary(ePrinterProfile.getSalary())
					.build();
			return printerProfile;
		};
		
		return mapper;
		
	}
	public Function<Employee,EPrinterProfile> convertToEPrinterProfile(){
		Function<Employee,EPrinterProfile>  mapper=(Employee printerProfile)->{
			EPrinterProfile ePrinterProfile=new EPrinterProfile();
				EPrinterProfileIdentity ePrinterProfileIdentity=new EPrinterProfileIdentity();
				ePrinterProfileIdentity.setEmpName(printerProfile.getEmpName());
				ePrinterProfileIdentity.setEmpId(printerProfile.getEmpID());
				ePrinterProfileIdentity.setLocation(printerProfile.getLocation());
			ePrinterProfile.setPrinterProfileIdentity(ePrinterProfileIdentity);
			ePrinterProfile.setLocationCode(printerProfile.getLocationCode());
			ePrinterProfile.setLastModifiedDate(printerProfile.getLastModifiedDate());
			ePrinterProfile.setSalary(printerProfile.getSalary());
			return ePrinterProfile;
		};
		return mapper;
		
	}

}
