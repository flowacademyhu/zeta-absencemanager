package hu.flowacademy.zetaabsencemanager;

import hu.flowacademy.zetaabsencemanager.config.StateMachineConfig;
import hu.flowacademy.zetaabsencemanager.model.Event;
import hu.flowacademy.zetaabsencemanager.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;

@SpringBootTest(classes = StateMachineConfig.class)
public class StateMachineTests {

  @Autowired
  private StateMachineFactory stateMachineFactory;

  private StateMachine<Status, Event> stateMachine;
  
}
