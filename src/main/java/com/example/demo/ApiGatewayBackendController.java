package com.example.demo;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiGatewayBackendController {
  private final Logger logger = LoggerFactory.getLogger(ApiGatewayBackendController.class);

  private Connection connection;

  public ApiGatewayBackendController(Connection connection) {
    this.connection = connection;
  }

  @PostMapping("connect")
  public ConnectionInfo connect(@RequestBody ConnectRequest cr){
    Map<String,String> map = connection.getConnectionMap();
    map.put(cr.getConnectionId(), "1");
    logger.info(cr.toString());
    logger.info(map.toString());

    return new ConnectionInfo(cr.getConnectionId());
  }

  @PostMapping("disconnect")
  public ConnectionInfo disconnect(@RequestBody ConnectRequest cr){
    Map<String,String> map = connection.getConnectionMap();
    map.put(cr.getConnectionId(), "1");
    logger.info(cr.toString());
    logger.info(map.toString());

    return new ConnectionInfo(cr.getConnectionId());
  }
}
