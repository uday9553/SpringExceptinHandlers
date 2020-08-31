package com.uday.springcrudopr.model.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.uday.springcrudopr.model.entity.EPrinterProfile;
import com.uday.springcrudopr.model.entity.EPrinterProfileIdentity;

@Repository
public interface PrinterProfileRepository extends JpaRepository<EPrinterProfile, EPrinterProfileIdentity>{
   List<EPrinterProfile> findByPrinterProfileIdentityEmpName(@Param(value="countryCode") String countryCode);
   List<EPrinterProfile> findByPrinterProfileIdentityEmpId(@Param(value="locationCode") String locationCode);
}
