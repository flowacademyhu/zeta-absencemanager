package hu.flowacademy.zetaabsencemanager.config;

import hu.flowacademy.zetaabsencemanager.model.Events;
import hu.flowacademy.zetaabsencemanager.model.Status;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;

@Configuration
@EnableStateMachine
public class StateMachineConfig extends StateMachineConfigurerAdapter<Status, Events> {
}
