package com.manage.clinicBack;

import com.manage.clinicBack.JWT.CustomerSecurityConfig;
import com.manage.clinicBack.JWT.MedSecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({CustomerSecurityConfig.class, MedSecurityConfig.class})
public class ClinicBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClinicBackApplication.class, args);
	}

}
