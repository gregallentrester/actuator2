package com.example.actuator.health;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;

import org.springframework.stereotype.Component;


@Component
public class CustomHealthIndicator extends AbstractHealthIndicator {

  private static final String HEALTHY_MESSAGE = "App Ok";
  private static final String OPPSLA = "Meh";

  @Override
  protected void doHealthCheck(Health.Builder builder) throws Exception {

    // build health status details; upon exception, status == 'DOWN'
    builder.up().
      withDetail("app", HEALTHY_MESSAGE).
      withDetail("error", OPPSLA);
  }
}
