package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiGatewayBackendController {
  private final Logger logger = LoggerFactory.getLogger(ApiGatewayBackendController.class);

  @PostMapping("connect")
  public ConnectionInfo connect(@RequestBody String str){
    logger.info(str);
    return null;
  }

}
