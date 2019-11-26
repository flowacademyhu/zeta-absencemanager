package hu.flowacademy.zetaabsencemanager.config;

import hu.flowacademy.zetaabsencemanager.repository.UserRepository;
import hu.flowacademy.zetaabsencemanager.service.AbsenceService;
import java.time.LocalDate;
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

  @Scheduled(fixedRate = 3000)
  public void scheduledLogger() {
/*    var users = userRepository.findByDeletedAtNull();
    if (userRepository.findByIdAndDeletedAtNull(5L).get().getDateOfEndTrial().isAfter(LocalDate
        .of(2010, Month.AUGUST, 11))) {
      System.out.println("Szia!");
    }*/
  }

  @Scheduled(fixedRate = 3000)
  public void trialCheck() {
    // userRepository.findByIdAndDeletedAtNull(1).get().getDateOfEndTrial()
    userRepository.findByDeletedAtNullAndDateOfEndTrialEquals(LocalDate.now())
        .forEach(user -> {
          absenceService.availableAbsence(user);
          System.out.println(user.getDateOfEndTrial());
        });
  }
}
