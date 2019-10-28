package hu.flowacademy.zetaabsencemanager.auth;

import hu.flowacademy.zetaabsencemanager.model.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  @Autowired
  private CustomUserDetailsService customUserDetailsService;

  @Autowired
  public void globalUserDetails(final AuthenticationManagerBuilder auth) throws Exception {

    auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder);
  }

  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(final HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
        .antMatchers("/", "/swagger-ui.html", "/webjars/**", "/login", "/swagger-resources/**",
            "/v2/api-docs/**", "/oauth/**", "/tokens/**", "/register").permitAll()
        .antMatchers("/admin/user**", "/admin/group**")
        .hasAnyAuthority(Roles.ADMIN.name(), Roles.LEADER.name())
        .antMatchers("/admin/absence/**").hasAnyAuthority(Roles.LEADER.name(), Roles.ADMIN.name())
        .anyRequest().authenticated()
        .and().formLogin().permitAll()
        .and().csrf().disable();
  }
}
