package com.mediscreen.patientmicroservice;

import com.mediscreen.patientmicroservice.model.Patient;
import com.mediscreen.patientmicroservice.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class PatientMicroserviceApplication implements CommandLineRunner {

	private final PatientService patientService;

	@Autowired
	public PatientMicroserviceApplication(PatientService patientService){
		this.patientService = patientService;
	}

	public static void main(String[] args) {
		SpringApplication.run(PatientMicroserviceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//Patient(String firstName, String lastName,String family, String sex,
		//                   String address, String phone, String dateOfBirth)
		if(patientService.findAll().isEmpty()){
			patientService.save(new Patient("test1","patient","TestNone","F","1 Brookside St","1002223333","31/12/1966"));
			patientService.save(new Patient("test2","patient","TestBorderline","M","2 High St","2003334444","25/06/1945"));
			patientService.save(new Patient("test3","TestInDanger","TestNone","M","3 Club Road","3004445555","18/06/2004"));
			patientService.save(new Patient("test4","TestEarlyOnSet","TestNone","F","4 Valley D","4005556666","28/06/2002"));
		}
	}
}
