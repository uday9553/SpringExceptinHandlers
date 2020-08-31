package com.uday.springcrudopr.model.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.util.StringUtils;

import com.uday.springcrudopr.model.Employee;
import com.uday.springcrudopr.model.entity.EPrinterProfile;
import com.uday.springcrudopr.model.entity.EPrinterProfileIdentity;
import com.uday.springcrudopr.model.repository.PrinterProfileRepository;
import com.uday.springcrudopr.model.transformer.PrinterProfileTransformer;


@Service
public class PrinterProfileService {
	
	private final PrinterProfileRepository printerProfileRepository;
	private final PrinterProfileTransformer printerProfileTransformer;
	
	@Autowired
	public PrinterProfileService(PrinterProfileRepository printerProfileRepository,PrinterProfileTransformer printerProfileTransformer){
		this.printerProfileRepository=printerProfileRepository;
		this.printerProfileTransformer=printerProfileTransformer;
	}
	
	public ResponseEntity<List<Employee>> fetchAll(){
		 List<EPrinterProfile> allRecords=printerProfileRepository.findAll();
		 List<Employee> printerProfiles= allRecords
				 .stream()
				 .map(printerProfileTransformer.convertToPrinterProfile())
				 .collect(Collectors.toList());
		 return ResponseEntity.ok(printerProfiles);
	}
	public ResponseEntity<Set<String>> fetchAllCountries(){
		 List<EPrinterProfile> allRecords= printerProfileRepository.findAll();
		 Set<String> countryCodes= allRecords
				 .stream()
				 .map(printerProfile->printerProfile.getPrinterProfileIdentity().getEmpName())
				 .collect(Collectors.toSet());
		 return ResponseEntity.ok(countryCodes);
	}
	public ResponseEntity<Set<String>> fetchLocationsByCountryCode(String countryCode){
		List<EPrinterProfile> printerProfiles=printerProfileRepository.findByPrinterProfileIdentityEmpName(countryCode);
		Set<String> locationCodes=printerProfiles
				.stream()
				.map(printerProfile->printerProfile.getPrinterProfileIdentity().getEmpId())
				.collect(Collectors.toSet());
	    return ResponseEntity.ok(locationCodes);
	}
	public ResponseEntity<Set<String>> fetchRoutesByLocationCode(String locationCode){
		List<EPrinterProfile> printerProfiles=printerProfileRepository.findByPrinterProfileIdentityEmpId(locationCode);
		Set<String> routeNumbers=printerProfiles
				.stream()
				.map(printerProfile->printerProfile.getPrinterProfileIdentity().getLocation())
				.collect(Collectors.toSet());
	    return ResponseEntity.ok(routeNumbers);
	}
	public ResponseEntity<?> savePrinterProfile(Employee printerProfile){
		 EPrinterProfile ePrinterProfile=printerProfileTransformer.convertToEPrinterProfile().apply(printerProfile);
		 //ePrinterProfile.setLastModifiedDate(LocalDate.now());
		 printerProfileRepository.save(ePrinterProfile);
		 return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	public ResponseEntity<?> deletePrinterProfile(String countryCode,String locationCode,String routeNumber){
		  EPrinterProfileIdentity eProfileIdentity=new EPrinterProfileIdentity();
		  eProfileIdentity.setEmpName(countryCode);
		  eProfileIdentity.setEmpId(locationCode);
		  eProfileIdentity.setLocation(routeNumber);
		  printerProfileRepository.deleteById(eProfileIdentity);
		 return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	public ResponseEntity<?> countPrintProfilesById(String countryCode,String locationCode,String routeNumber){
		EPrinterProfileIdentity ePrinterProfileIdentity=new EPrinterProfileIdentity();
		ePrinterProfileIdentity.setEmpName(countryCode);
		ePrinterProfileIdentity.setEmpId(locationCode);
		ePrinterProfileIdentity.setLocation(routeNumber);
		Optional<EPrinterProfile> printProfileOptional=	printerProfileRepository.findById(ePrinterProfileIdentity);
		Integer profileCount=0;
		if(printProfileOptional.isPresent()){
			profileCount=StringUtils.isEmpty(printProfileOptional.get().getLocationCode())?0:1;
		}
		return ResponseEntity.ok(profileCount);
	}

}
