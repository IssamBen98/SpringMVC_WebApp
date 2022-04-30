package com.myapp.patientMVC;

import com.myapp.patientMVC.entities.Patient;
import com.myapp.patientMVC.repositories.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class patientsMvcApplication {

    public static void main(String[] args)
    {
        SpringApplication.run(patientsMvcApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(PatientRepository patientRepository){
        return args -> {
            /*
            patientRepository.save(
                    new Patient(null,"Hassan",new Date(),false,8));
            patientRepository.save(
                    new Patient(null,"Mohammed",new Date(),true,78));
            patientRepository.save(
                    new Patient(null,"Imane",new Date(),true,55));
            patientRepository.save(
                    new Patient(null,"Issam",new Date(),false,12));
*/
            patientRepository.findAll().forEach(p->{
                System.out.println(p.getNom());
            });
        };
    }
}
