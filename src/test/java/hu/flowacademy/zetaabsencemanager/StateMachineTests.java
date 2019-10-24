package hu.flowacademy.zetaabsencemanager;

import hu.flowacademy.zetaabsencemanager.config.StateMachineConfig;
import hu.flowacademy.zetaabsencemanager.model.Event;
import hu.flowacademy.zetaabsencemanager.model.Status;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.test.StateMachineTestPlan;
import org.springframework.statemachine.test.StateMachineTestPlanBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = StateMachineConfig.class)
public class StateMachineTests {

  @Autowired
  private StateMachine<Status, Event> stateMachine;

  @Before
  public void setup() {
    stateMachine.start();
  }

  @Test
  public void testInitial() throws Exception {
    StateMachineTestPlan<Status, Event> plan = StateMachineTestPlanBuilder.<Status, Event>builder()
        .stateMachine(stateMachine)
        .step()
        .expectState(Status.OPEN)
        .and()
        .build();
    plan.test();
  }

  @Test
  public void testSendEvent() {
    Assertions.assertEquals(Status.OPEN, stateMachine.getState().getId());
    stateMachine.sendEvent(Event.SEND);
    Assertions.assertEquals(Status.UNDER_REVIEW, stateMachine.getState().getId());
  }

  @After
  public void tearDown() {
    stateMachine.stop();
  }
}
