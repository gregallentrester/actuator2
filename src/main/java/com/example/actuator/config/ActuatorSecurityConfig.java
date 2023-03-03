package com.example.actuator.config;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.actuate.context.ShutdownEndpoint;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;

import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


/*
  git clone https://github.com/callicoder/spring-boot-actuator-demo.git
  http://localhost:8080

  Actuator endpoints
  http://localhost:8080/actuator

  Actuator endpoints protected with
  Spring Security HTTP Basic Auth uaw:

  Username 'actuator'
  password 'actuator'
  */
@Configuration
public class ActuatorSecurityConfig extends WebSecurityConfigurerAdapter {

  /*
    1. Restricts access to the Shutdown endpoint to the ACTUATOR_ADMIN role.
    2. Allows access to all other actuator endpoints.
    3. Allows access to static resources.
    4. Allows access to the home page (/).
    5. Authenticates all other requests.
    5. Enables HTTP Basic Auth (others, too)
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http.
      authorizeRequests().
        requestMatchers(EndpointRequest.to(ShutdownEndpoint.class)).
          hasRole("ACTUATOR_ADMIN").  // ACTUATOR ?
        requestMatchers(EndpointRequest.toAnyEndpoint()).
          permitAll().
        requestMatchers(PathRequest.toStaticResources().atCommonLocations()).
          permitAll().
        antMatchers("/", "/slowApi", "/actuator/beans").
          permitAll().
        antMatchers("/**").
          authenticated().
    and().
      httpBasic();
  }
}
