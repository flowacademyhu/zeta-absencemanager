package hu.flowacademy.zetaabsencemanager.config;

import hu.flowacademy.zetaabsencemanager.model.User;
import hu.flowacademy.zetaabsencemanager.repository.UserRepository;
import hu.flowacademy.zetaabsencemanager.service.AbsenceService;
import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class ScheduledTasksConfig {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private AbsenceService absenceService;

  @Scheduled(cron = "0 0 6 * * ?")
  public void trialCheck() {
    userRepository
        .findByDeletedAtNullAndDateOfEndTrialEquals(
            LocalDate.now().minusDays(1))
        .forEach(this::availableAbsenceRecalculation);
  }

  private void availableAbsenceRecalculation(@NotNull User user) {
    int allAbsence = 20;
    int allSickLeave = 15;
    int[] borders = {25, 28, 31, 33, 35, 37, 39, 41, 43, 45};
    int age = LocalDate.now().getYear() - user.getDateOfBirth().getYear();
    double multiplier = (365 - user.getDateOfEntry().getDayOfYear()) / 365.0;
    for (int border : borders) {
      if (age >= border) {
        allAbsence = allAbsence + 1;
      }
    }
    switch (user.getNumberOfChildren()) {
      case 0:
        break;
      case 1:
        allAbsence = allAbsence + 2;
        break;
      case 2:
        allAbsence = allAbsence + 4;
        break;
      default:
        allAbsence = allAbsence + 7;
    }
    user.setTotalAbsenceDays((int) Math.round(allAbsence * multiplier));
    user.setTotalSickLeaveDays((int) Math.round(allSickLeave * multiplier));
    userRepository.save(user);
  }
}

// TODO yearlyrecalc