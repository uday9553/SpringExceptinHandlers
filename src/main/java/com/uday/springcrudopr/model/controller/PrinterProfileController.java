package com.uday.springcrudopr.model.controller;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uday.springcrudopr.model.Employee;
import com.uday.springcrudopr.model.service.PrinterProfileService;
import com.uday.springcrudopr.model.util.SaveOrUpdate;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value="/printer-profile")
@Api(value = "PrinterProfile", protocols = "http,https", tags = { "PrinterProfile APIs" })
@CrossOrigin
public class PrinterProfileController {
	private final PrinterProfileService printerProfileService;
	
	@Autowired
	public PrinterProfileController(PrinterProfileService printerProfileService){
		this.printerProfileService=printerProfileService;
	}
	
	@GetMapping(value="/all")

	@ApiOperation(value = "Returns a list of availalbe PrinterProfiles", response = List.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 301, message = "Moved Permanently"),
			@ApiResponse(code = 302, message = "Moved Temporarily"),
			@ApiResponse(code = 400, message = "Bad Request Response"),
			@ApiResponse(code = 404, message = "Not Found")
			})
	public ResponseEntity<List<Employee>> fetchAllRecords(){
		return printerProfileService.fetchAll();
	}
	
	@ApiOperation(value = "Returns a Set of availalbe Countries", response = Set.class)
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 301, message = "Moved Permanently"),
			@ApiResponse(code = 302, message = "Moved Temporarily"),
			@ApiResponse(code = 400, message = "Bad Request Response"),
			@ApiResponse(code = 404, message = "Not Found")})
	@GetMapping(value="/countries")
	public ResponseEntity<Set<String>> fetchAllCountries(){
		return printerProfileService.fetchAllCountries();
	}
	
	@ApiOperation(value = "Returns a set of availalbe Locations for the given CountryCode", response = Set.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 301, message = "Moved Permanently"),
			@ApiResponse(code = 302, message = "Moved Temporarily"),
			@ApiResponse(code = 400, message = "Bad Request Response"),
			@ApiResponse(code = 404, message = "Not Found")})
	@GetMapping(value="/countryCode/{countryCode}")
	public ResponseEntity<Set<String>> fetchLocationsByCountryCode(@PathVariable("countryCode")String countryCode){
		return printerProfileService.fetchLocationsByCountryCode(countryCode);
	}
	
	@ApiOperation(value = "Returns a set of availalbe Routes for the given LocationCode", response = Set.class)
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 301, message = "Moved Permanently"),
			@ApiResponse(code = 302, message = "Moved Temporarily"),
			@ApiResponse(code = 400, message = "Bad Request Response"),
			@ApiResponse(code = 404, message = "Not Found")})
	@GetMapping(value="/locationCode/{locationCode}")
	public ResponseEntity<Set<String>> fetchRoutesByLocationCode(@PathVariable("locationCode")String locationCode){
		return printerProfileService.fetchRoutesByLocationCode(locationCode);
	}
	
	@ApiOperation(value = "Returns a number (1 or 0) for the given LocationCode and CountryCode,RouteNumber", response = Set.class)
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 301, message = "Moved Permanently"),
			@ApiResponse(code = 302, message = "Moved Temporarily"),
			@ApiResponse(code = 400, message = "Bad Request Response"),
			@ApiResponse(code = 404, message = "Not Found")})
	@GetMapping(value="/locationCode/{locationCode}/countryCode/{countryCode}/routeNumber/{routeNumber}")
	public ResponseEntity<?> fetchPrinterProfileCountById(
			    @PathVariable("countryCode")String countryCode,
				@PathVariable("locationCode")String locationCode,
				@PathVariable("routeNumber")String routeNumber
			){
		
		return printerProfileService.countPrintProfilesById(countryCode, locationCode, routeNumber);	
	}
	
	@ApiOperation(value = "Persist PrinterProfile data which is submitted by end User", response = Set.class)
	@ApiResponses(value = { 
			@ApiResponse(code = 201, message = "CREATED"),
			@ApiResponse(code = 301, message = "Moved Permanently"),
			@ApiResponse(code = 302, message = "Moved Temporarily"),
			@ApiResponse(code = 400, message = "Bad Request Response"),
			@ApiResponse(code = 404, message = "Not Found")})
	@PostMapping(value="/save",produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> savePrinterProfile(@Validated ({SaveOrUpdate.class}) @RequestBody Employee printerProfile){
		return printerProfileService.savePrinterProfile(printerProfile);
	}
	
	@ApiOperation(value = "Delete PrinterProfile data for the given locationCode, countryCode and routeNumber", response = Set.class)
	@ApiResponses(value = { 
			@ApiResponse(code = 204, message = "NO_CONTENT"),
			@ApiResponse(code = 301, message = "Moved Permanently"),
			@ApiResponse(code = 302, message = "Moved Temporarily"),
			@ApiResponse(code = 400, message = "Bad Request Response"),
			@ApiResponse(code = 404, message = "Not Found")})
	@DeleteMapping(value="/locationCode/{locationCode}/countryCode/{countryCode}/routeNumber/{routeNumber}")
	public ResponseEntity<?> deletePrinterProfile(
		    @PathVariable("countryCode") String countryCode,
			@PathVariable("locationCode") String locationCode,
			@PathVariable("routeNumber") String routeNumber
		){
		return printerProfileService.deletePrinterProfile(countryCode,locationCode,routeNumber);
	}
}
