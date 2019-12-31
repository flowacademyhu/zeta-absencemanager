package hu.flowacademy.zetaabsencemanager.config;

import hu.flowacademy.zetaabsencemanager.model.Absence;
import hu.flowacademy.zetaabsencemanager.model.Status;
import hu.flowacademy.zetaabsencemanager.repository.AbsenceRepository;
import hu.flowacademy.zetaabsencemanager.service.AbsenceService;
import hu.flowacademy.zetaabsencemanager.service.MessageService;
import hu.flowacademy.zetaabsencemanager.utils.Constants;
import java.util.EnumSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import ru.sberned.statemachine.StateMachine;
import ru.sberned.statemachine.StateRepository;
import ru.sberned.statemachine.StateRepository.StateRepositoryBuilder;
import ru.sberned.statemachine.lock.LockProvider;
import ru.sberned.statemachine.state.AfterTransition;
import ru.sberned.statemachine.state.BeforeAnyTransition;
import ru.sberned.statemachine.state.ItemWithStateProvider;
import ru.sberned.statemachine.state.StateChanger;

@SuppressWarnings("unchecked")
@Slf4j
@Configuration
public class StateMachineConfig {

  @Autowired
  private LockProvider lockProvider;

  @Bean
  public StateMachine<Absence, Status, Long> stateMachine() {
    StateRepository<Absence, Status, Long> repository = StateRepositoryBuilder.<Absence, Status, Long>configure()
        .setAvailableStates(EnumSet.allOf(Status.class))
        .setUnhandledMessageProcessor((item, state, type, ex) -> {
          log.error("Got unhandled item with id {}, issue is {}", item, type);
        })
        .setAnyBefore((BeforeAnyTransition<Absence, Status>) (item, state) -> {
          log.info("Started working on item with id {}", item.getId());
          return true;
        })
        .defineTransitions()
        .from(Status.OPEN)
        .to(Status.UNDER_REVIEW)
        .after(
            (AfterTransition<Absence>) item -> log.info("Moved from OPEN to UNDER_REVIEW"))
        .and()
        .from(Status.UNDER_REVIEW)
        .to(Status.APPROVED)
        .after(
            (AfterTransition<Absence>) item -> log.info("Moved from UNDER_REVIEW to APPROVED"))
        .and()
        .from(Status.APPROVED)
        .to(Status.ADMINISTRATED)
        .after(item -> log.info("Moved from APPROVED to ADMINISTRATED"))
        .and()
        .from(Status.ADMINISTRATED)
        .to(Status.DONE)
        .after(item -> log.info("Moved from ADMINISTRATED to DONE"))
        .and()
        .from(Status.OPEN, Status.UNDER_REVIEW, Status.APPROVED, Status.ADMINISTRATED)
        .to(Status.REJECTED)
        .after((AfterTransition<Absence>) item -> log.info("Rejected."))
        .build();

    StateMachine<Absence, Status, Long> stateMachine = new StateMachine<>(stateProvider(),
        stateChanger(), lockProvider);
    stateMachine.setStateRepository(repository);
    return stateMachine;
  }

  @Bean
  public ItemWithStateProvider<Absence, Long> stateProvider() {
    return new ListStateProvider();
  }

  @Bean
  public StateChanger<Absence, Status> stateChanger() {
    return new StateHandler();
  }

  public static class ListStateProvider implements ItemWithStateProvider<Absence, Long> {

    @Autowired
    private AbsenceRepository store;

    @Override
    public Absence getItemById(Long id) {
      return store.findById(id).orElseThrow(
          () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.ABSENCE_NOT_FOUND));
    }
  }

  public static class StateHandler implements StateChanger<Absence, Status> {

    @Autowired
    private AbsenceRepository store;

    @Autowired
    private AbsenceService service;

    @Autowired
    private MessageService messageService;

    @Override
    public void moveToState(Status state, Absence item, Object... infos) {
      Absence itemFound = store.findById(item.getId()).orElseThrow(
          () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.ABSENCE_NOT_FOUND));
      String oldStatus = itemFound.getStatus().name();
      itemFound.setStatus(state);
      String message =
          "Az ön szabadságigényének státusza " + oldStatus + "-ról " + itemFound.getStatus()
              .toString() + "-re változott.";
      messageService.sendEmail(item.getReporter().getEmail(), "Státusz változás", message);
      if (state == Status.REJECTED) {
        service.removeFromUsedDays(item);
      }
    }
  }
}
