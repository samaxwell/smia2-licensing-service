package com.optimatgrowth.license.controller;

import com.optimatgrowth.license.model.License;
import com.optimatgrowth.license.services.LicenseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/v1/organization/{organizationId}/license")
public class LicenseController {

    LicenseService licenseService;

    @GetMapping(value="/{licenseId}")
    public ResponseEntity<License> getLicense(
            @PathVariable("organizationId") String organizationId,
            @PathVariable("licenseId") String licenseId) {
        log.info("Getting license id {} for org {}", licenseId, organizationId);

        License license = licenseService.getLicense(licenseId,organizationId);
        return ResponseEntity.ok(license);
    }

    @PutMapping
    public ResponseEntity<String> updateLicense(
            @PathVariable("organizationId") String organizationId,
            @RequestBody License request) {
        log.info("Updating licence {} for org {}", request.getLicenseId(), organizationId);

        return ResponseEntity.ok(licenseService.updateLicense(request, organizationId));
    }

    @PostMapping
    public ResponseEntity<String> createLicense(
            @PathVariable("organizationId") String organizationId,
            @RequestBody License request,
            @RequestHeader(value = "Accept-Language",required = false) Locale locale) {

        log.info("Adding new license for org {}", organizationId);

        return ResponseEntity.ok(licenseService.createLicense(request, organizationId, locale));
    }

    @DeleteMapping(value="/{licenseId}")
    public ResponseEntity<String> deleteLicense(
            @PathVariable("organizationId") String organizationId,
            @PathVariable("licenseId") String licenseId) {
        log.info("Removing license {} for org ", licenseId, organizationId);

        return ResponseEntity.ok(licenseService.deleteLicense(licenseId, organizationId));
    }

}
