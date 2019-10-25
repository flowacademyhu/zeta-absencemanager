package hu.flowacademy.zetaabsencemanager.auth;

import hu.flowacademy.zetaabsencemanager.model.Roles;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@EnableResourceServer
@Configuration
public class ResourceConfig extends ResourceServerConfigurerAdapter {

  @Override
  public void configure(HttpSecurity http) throws Exception {

    http.authorizeRequests()
        .antMatchers("/", "/login", "/swagger-ui.html", "/webjars/**", "/swagger-resources/**",
            "/v2/api-docs/**", "/oauth/**", "/tokens/**", "/register").permitAll()
        .antMatchers("/admin/user**", "/admin/group**").hasAuthority(Roles.ADMIN.name())
        .antMatchers("/admin/absence/**").hasAnyAuthority(Roles.LEADER.name(), Roles.ADMIN.name())
        .anyRequest().authenticated();
  }

}