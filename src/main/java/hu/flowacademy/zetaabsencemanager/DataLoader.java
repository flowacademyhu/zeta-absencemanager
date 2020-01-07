package hu.flowacademy.zetaabsencemanager;

import hu.flowacademy.zetaabsencemanager.model.Roles;
import hu.flowacademy.zetaabsencemanager.model.User;
import hu.flowacademy.zetaabsencemanager.repository.UserRepository;
import hu.flowacademy.zetaabsencemanager.service.AbsenceService;
import java.time.LocalDate;
import java.time.Month;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class DataLoader implements CommandLineRunner {

  private final UserRepository userRepository;
  private final AbsenceService absenceService;

  @Autowired
  @Lazy
  private BCryptPasswordEncoder passwordEncoder;

  @Autowired
  public DataLoader(
      UserRepository userRepository, AbsenceService absenceService) {
    this.userRepository = userRepository;
    this.absenceService = absenceService;
  }

  @Override
  public void run(String... args) throws Exception {
    User admin = User.builder()
        .email("admin@admin.com")
        .password(passwordEncoder.encode("admin")) // passwordEncoder.encode("admin")
        .firstName("Allan")
        .lastName("Morgan")
        .role(Roles.ADMIN)
        .dateOfBirth(LocalDate.of(1970, Month.FEBRUARY, 28))
        .dateOfEntry(LocalDate.of(2019, Month.FEBRUARY, 12))
        .dateOfEndTrial(LocalDate.of(2019, Month.MAY, 12))
        .extraAbsenceDays(0)
        .position("testposition")
        .numberOfChildren(3)
        .build();
    admin.setTotalAbsenceDays(absenceService.availableAbsence(admin));
    admin.setTotalSickLeaveDays(absenceService.availableSickLeave(admin));
    admin.setUsedAbsenceDays(0);
    admin.setUsedSickLeaveDays(0);
    admin.setUsedSickPay(0);
    admin.setUsedChildSickPay(0);
    admin.setUsedSickPay(0);
    this.userRepository.save(admin);

  }

}