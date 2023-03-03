package com.example.actuator.readout;

import java.util.*;
import java.io.*;

import java.nio.charset.StandardCharsets;

import java.util.concurrent.ConcurrentHashMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;


/*
 This class is not thread-safe.
 */
public final class Publisher {

  private static ConcurrentHashMap<String,String> selector;

  private static final String CURL =
    "curl -X GET --progress http://localhost:8080/";

  private static Set keys;


  public static void publishAll() {

    keys.forEach(
      any -> {
        publish(selector.get(any.toString()));
    });

  }

  public static final void publish(String model) {

  System.err.println("publish: " + model);

    String payload = "";
    Process process = null;

    try {

      String command = CURL + model;

      ProcessBuilder pb =
        new ProcessBuilder(command.split(" "));

      pb.redirectErrorStream(true);

      process = pb.start();

      process.waitFor();

      payload =
        new ObjectMapper().
          writerWithDefaultPrettyPrinter().
            writeValueAsString(
              new String(
                process.getInputStream().
                  readAllBytes()));

      process.destroy();
    }
    catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }

    System.err.println("Payload: " + payload);
  }


  static {

    selector = new ConcurrentHashMap();

    selector.put("health/diskSpace", "actuator/health/diskSpace");
    selector.put("env/JAVA_MAIN_CLASS_4543", "actuator/env/JAVA_MAIN_CLASS_4543");

    selector.put("caches", "actuator/caches");
    selector.put("health", "actuator/health");
    selector.put("info", "actuator/info");

    selector.put("shutdown", "actuator/shutdown");
    selector.put("configprops", "actuator/configprops");
    selector.put("env", "actuator/env");

    selector.put("loggers/ROOT", "actuator/loggers/ROOT");
    selector.put("logfile", "actuator/logfile");
    selector.put("loggers", "actuator/loggers");

    selector.put("beans", "actuator/beans");
    selector.put("conditions", "actuator/conditions");

    selector.put("threaddump", "actuator/threaddump");
    selector.put("prometheus", "actuator/prometheus");

    selector.put("metrics", "actuator/metrics");
    selector.put("metrics/jvm.memory.max", "actuator/metrics/jvm.memory.max");

    selector.put("scheduledtasks", "actuator/scheduledtasks");

    selector.put("mappings", "actuator/mappings");

    keys = selector.keySet();
  }
}
