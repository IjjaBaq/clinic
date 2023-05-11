package com.manage.clinicBack.seviceImpl;


import com.manage.clinicBack.Dao.StaffDao;
import com.manage.clinicBack.JWT.JwtFilter;
import com.manage.clinicBack.JWT.JwtUtil;
import com.manage.clinicBack.JWT.MedSecurityConfig;
import com.manage.clinicBack.JWT.MedUserDetailsService;
import com.manage.clinicBack.constents.cliniqueConstants;
import com.manage.clinicBack.module.Staff;
import com.manage.clinicBack.service.StaffService;
import com.manage.clinicBack.utils.ClinicUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Slf4j
@Service

public class StaffServiceImpl implements StaffService {

    @Autowired
    StaffDao hospitalDao;

    @Autowired
    JwtFilter jwtFilter ;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    MedUserDetailsService medUserDetailsService;

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        log.info("Inside signup{}", requestMap);
        try {
            if (validateSignUpMap(requestMap)) {
                Staff hospital = hospitalDao.findByEmailId(requestMap.get("email"));
                if (Objects.isNull(hospital)) {

                    hospitalDao.save(getUserFromMap(requestMap));
                    return ClinicUtils.getResponseEntity("Compte bien créer", HttpStatus.OK);
                } else {
                    return ClinicUtils.getResponseEntity("Email existe déja", HttpStatus.BAD_REQUEST);
                }

            } else {
                return ClinicUtils.getResponseEntity(cliniqueConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ClinicUtils.getResponseEntity(cliniqueConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }




    private boolean validateSignUpMap(Map<String, String> requestMap) {
        if (requestMap.containsKey("name") && requestMap.containsKey("contactNumber") &&
                requestMap.containsKey("email") && requestMap.containsKey("password") && requestMap.containsKey("role")
                && requestMap.containsKey("clinic") && requestMap.containsKey("address")) {
            return true;
        }
        return false;
    }

    private Staff getUserFromMap(Map<String, String> requestMap) {
        Staff hospital = new Staff();
        hospital.setName(requestMap.get("name"));
        hospital.setContactNumber(requestMap.get("contactNumber"));
        hospital.setEmail(requestMap.get("email"));
        hospital.setPassword(requestMap.get("password"));
        hospital.setStatus("false");
        hospital.setRole(requestMap.get("role"));
        hospital.setClinic(requestMap.get("clinic"));
        hospital.setAddress(requestMap.get("address"));
        return hospital;
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {

        log.info("Inside login{} ", requestMap);
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("password"))
            );
            if (auth.isAuthenticated()) {
                if (medUserDetailsService.getUserDetails().getStatus().equalsIgnoreCase("true")) {
                    return new ResponseEntity<String>("{\"token\":\"" +
                            jwtUtil.generateToken(medUserDetailsService.getUserDetails().getEmail()
                                    , medUserDetailsService.getUserDetails().getRole()) + "\"}",
                            HttpStatus.OK);
                } else {
                    return new ResponseEntity<String>("{\"message\":\"" + "Attendez authorisation de l'admin." + "\"}",
                            HttpStatus.BAD_REQUEST);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<String>("{\"message\":\"" + "Données fausses" + "\"}",
                HttpStatus.BAD_REQUEST);
    }

}
