package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
  private final Logger logger = LoggerFactory.getLogger(TestController.class);

  @PostMapping("test")
  public Test test(@RequestBody String str){
    logger.info(str);
    return new Test("test");
  }

}
