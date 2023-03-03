package com.example.actuator.controller;

import org.springframework.web.bind.annotation.*;


// Also:  curl http://localhost:8080/actuator/beans
@RestController
public class Controller {

  @GetMapping("/")
  public String sayHello(@RequestParam(value = "name", defaultValue = "Greg") String name) {

    System.err.println("\n sayHello() \n");

    return "Hello " + name;
  }


  @GetMapping("/slowApi")
  public String waft(@RequestParam(value = "any", defaultValue = "0") Integer any)
      throws InterruptedException {

    com.example.actuator.readout.Publisher.publishAll();
    
    return "Delay: " + 333;
  }
}
