package com.example.server.controller;

import com.example.server.certificates.Constants;
import com.example.server.dto.CertificateDTO;
import com.example.server.dto.CertificateExchangeDTO;
import com.example.server.enumeration.KeyUsages;
import com.example.server.exception.CustomException;
import com.example.server.service.AdminService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PreAuthorize("hasAuthority('PKI_ADMINISTRATION')")
    @RequestMapping(value = "/createCertificate", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> issueCertificate(@RequestBody CertificateDTO certificateDTO) {
        try {
            hasNecessaryFields(certificateDTO);
            return new ResponseEntity<>(adminService.createCertificate(certificateDTO), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private void hasNecessaryFields(@RequestBody CertificateDTO certificateDTO) throws CustomException {
        if (certificateDTO.getCommonName().equals("") || certificateDTO.getCity().equals("") || certificateDTO.getCountry().equals("") || certificateDTO.getCountryOfState().equals("") || certificateDTO.getMail().equals("") || certificateDTO.getNotBefore() == null || certificateDTO.getNotAfter() == null || certificateDTO.getKeyUsages().length == 0 || certificateDTO.getIssuer().equals("") || certificateDTO.getOrganization().equals("")) {
            throw new CustomException("Fields must not be empty.", HttpStatus.NOT_ACCEPTABLE);
        } else if (!Constants.emailFormat.matcher(certificateDTO.getMail()).matches()) {
            throw new CustomException("Improper email format.", HttpStatus.NOT_ACCEPTABLE);
        } else if (!Constants.commonNameFormat.matcher(certificateDTO.getCommonName()).matches()) {
            throw new CustomException("Common name doesn't match requirements.", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PreAuthorize("hasAuthority('PKI_ADMINISTRATION')")
    @RequestMapping(value = "/keyUsages", method = RequestMethod.GET)
    public ResponseEntity<?> getAllKeyUsages() {
        try {
            return new ResponseEntity<>(KeyUsages.values(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('PKI_ADMINISTRATION')")
    @RequestMapping(value = "/issuerCerts", method = RequestMethod.PUT)
    public ResponseEntity<?> getAllIssuerCerts(@RequestBody KeyUsages[] keyUsages) {
        try {
            if (keyUsages.length < 1)
                throw new CustomException("Should contain at least one key usage.", HttpStatus.NOT_ACCEPTABLE);
            return new ResponseEntity<>(adminService.getCACerts(keyUsages), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('PKI_ADMINISTRATION')")
    @RequestMapping(value = "/certs", method = RequestMethod.GET)
    public ResponseEntity<?> getAllCerts() {
        try {
            return new ResponseEntity<>(adminService.getAllCerts(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('PKI_ADMINISTRATION')")
    @RequestMapping(value = "/revocatedCerts", method = RequestMethod.GET)
    public ResponseEntity<?> getAllRevocatedCerts() {
        try {
            return new ResponseEntity<>(adminService.getAllRevocatedCerts(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('PKI_ADMINISTRATION')")
    @RequestMapping(value = "/download", method = RequestMethod.PUT)
    public ResponseEntity<?> downloadCertificate(@RequestBody CertificateExchangeDTO certificateExchangeDTO) {
        try {
            hasSerialNumberAndName(certificateExchangeDTO);
            CertificateExchangeDTO c = adminService.downloadCertificate(certificateExchangeDTO);
            return new ResponseEntity<>(c, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @PreAuthorize("hasAuthority('PKI_ADMINISTRATION')")
    @RequestMapping(value = "/revokeCertificate", method = RequestMethod.PUT)
    public ResponseEntity<?> revokeCertificate(@RequestBody CertificateExchangeDTO certificateExchangeDTO) {
        try {
            hasSerialNumberNameAndReason(certificateExchangeDTO);
            return new ResponseEntity<>(adminService.revokeCertificate(certificateExchangeDTO), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private void hasSerialNumberNameAndReason(CertificateExchangeDTO certificateExchangeDTO) throws CustomException {
        if(StringUtils.isEmpty(certificateExchangeDTO.getName()) || certificateExchangeDTO.getSerialNumber() == null || StringUtils.isEmpty(certificateExchangeDTO.getReason()))
            throw new CustomException("Fields must not be empty.", HttpStatus.NOT_ACCEPTABLE);
    }

    @PreAuthorize("hasAuthority('PKI_ADMINISTRATION')")
    @RequestMapping(value = "/validity", method = RequestMethod.POST)
    public ResponseEntity<?> checkValidity(@RequestBody CertificateExchangeDTO certificateExchangeDTO) {
        try {
            hasSerialNumberAndName(certificateExchangeDTO);
            return new ResponseEntity<>(adminService.checkValidity(certificateExchangeDTO), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private void hasSerialNumberAndName(CertificateExchangeDTO certificateExchangeDTO) throws CustomException {
        if (StringUtils.isEmpty(certificateExchangeDTO.getName()) || certificateExchangeDTO.getSerialNumber() == null) {
            throw new CustomException("Fields must not be empty.", HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
