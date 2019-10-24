package hu.flowacademy.zetaabsencemanager.config;

import hu.flowacademy.zetaabsencemanager.model.Absence;
import hu.flowacademy.zetaabsencemanager.model.Status;
import hu.flowacademy.zetaabsencemanager.repository.AbsenceRepository;
import java.util.EnumSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@Configuration
public class StateMachineConfig2 {

  private static final Logger LOGGER = LoggerFactory.getLogger(StateMachineConfig2.class);

  @Autowired
  private LockProvider lockProvider;

  @Bean
  public StateMachine<Absence, Status, Long> stateMachine() {
    StateRepository<Absence, Status, Long> repository = StateRepositoryBuilder.<Absence, Status, Long>configure()
        .setAvailableStates(EnumSet.allOf(Status.class))
        .setUnhandledMessageProcessor((item, state, type, ex) -> {
          LOGGER.error("Got unhandled item with id {}, issue is {}", item, type);
        })
        .setAnyBefore((BeforeAnyTransition<Absence, Status>) (item, state) -> {
          LOGGER.info("Started working on item with id {}", item.getId());
          return true;
        })
        .defineTransitions()
        .from(Status.OPEN)
        .to(Status.UNDER_REVIEW)
        .after(
            (AfterTransition<Absence>) item -> LOGGER.info("Moved from OPEN to UNDER_REVIEW"))
        .and()
        .from(Status.UNDER_REVIEW)
        .to(Status.APPROVED)
        .after(
            (AfterTransition<Absence>) item -> LOGGER.info("Moved from UNDER_REVIEW to APPROVED"))
        .and()
        .from(Status.APPROVED)
        .to(Status.ADMINISTRATED)
        .after(item -> LOGGER.info("Moved from APPROVED to ADMINISTRATED"))
        .and()
        .from(Status.ADMINISTRATED)
        .to(Status.DONE)
        .after(item -> LOGGER.info("Moved from ADMINISTRATED to DONE"))
        .and()
        .from(Status.OPEN, Status.UNDER_REVIEW, Status.APPROVED, Status.ADMINISTRATED)
        .to(Status.REJECTED)
        .after((AfterTransition<Absence>) item -> LOGGER.info("Rejected."))
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
          () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Absence not found."));
    }
  }

  public static class StateHandler implements StateChanger<Absence, Status> {

    @Autowired
    private AbsenceRepository store;

    @Override
    public void moveToState(Status state, Absence item, Object... infos) {
      Absence itemFound = store.findById(item.getId()).orElseThrow(
          () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Absence not found."));
      itemFound.setStatus(state);
      /*if (itemFound != null) {
        itemFound.setState(state);
      } else {
        throw new RuntimeException("Item not found");
      }*/
    }
  }
}
