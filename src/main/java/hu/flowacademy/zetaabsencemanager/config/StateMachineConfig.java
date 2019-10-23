package hu.flowacademy.zetaabsencemanager.config;

import hu.flowacademy.zetaabsencemanager.model.Event;
import hu.flowacademy.zetaabsencemanager.model.Status;
import java.util.Arrays;
import java.util.HashSet;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

@Configuration
@EnableStateMachine
public class StateMachineConfig extends StateMachineConfigurerAdapter<Status, Event> {

  @Override
  public void configure(StateMachineConfigurationConfigurer<Status, Event> config)
      throws Exception {
    config
        .withConfiguration()
        .autoStartup(true)
        .listener(new StateMachineListener());
  }

  @Override
  public void configure(StateMachineStateConfigurer<Status, Event> states) throws Exception {
    states
        .withStates()
        .initial(Status.OPEN)
        .end(Status.DONE)
        .states(new HashSet<>(Arrays.asList(Status.UNDER_REVIEW, Status.APPROVED,
            Status.ADMINISTRATED, Status.REJECTED)));
  }

  @Override
  public void configure(StateMachineTransitionConfigurer<Status, Event> transitions)
      throws Exception {
    transitions.withExternal()
        .source(Status.OPEN).target(Status.UNDER_REVIEW).event(Event.SEND).and()
        .withExternal()
        .source(Status.UNDER_REVIEW).target(Status.APPROVED).event(Event.APPROVE).and()
        .withExternal()
        .source(Status.APPROVED).target(Status.ADMINISTRATED).event(Event.ADMINISTRATE).and()
        .withExternal()
        .source(Status.ADMINISTRATED).target(Status.DONE).event(Event.FINALIZE);
  }
}
