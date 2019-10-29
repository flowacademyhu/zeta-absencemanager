package hu.flowacademy.zetaabsencemanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ZetaAbsencemanagerApplication {

  public static void main(String[] args) {
    SpringApplication.run(ZetaAbsencemanagerApplication.class, args);
  }

}
