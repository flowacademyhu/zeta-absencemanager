package hu.flowacademy.zetaabsencemanager;

import hu.flowacademy.zetaabsencemanager.repository.AbsenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEventPublisher;

@SpringBootApplication
public class ZetaAbsencemanagerApplication {

  @Autowired
  private ApplicationEventPublisher publisher;
  @Autowired
  private AbsenceRepository store;

  public static void main(String[] args) {
    SpringApplication.run(ZetaAbsencemanagerApplication.class, args);
  }

}
