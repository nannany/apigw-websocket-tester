package com.example.demo.controller;

import com.example.demo.response.ConnectAck;
import com.example.demo.request.ConnectRequest;
import com.example.demo.request.Connection;
import com.example.demo.response.ConnectionInfo;
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
  public ConnectAck connect(@RequestBody ConnectRequest cr) throws Exception {
    Map<String,String> map = connection.getConnectionMap();
    map.put(cr.getConnectionId(), "1");
    logger.info(cr.toString());
    logger.info(map.toString());

    return new ConnectAck("200");
//    throw new Exception();
  }

  @PostMapping("disconnect")
  public ConnectionInfo disconnect(@RequestBody ConnectRequest cr){
    logger.info(cr.toString());
    Map<String,String> map = connection.getConnectionMap();
    map.remove(cr.getConnectionId());
    logger.info(map.toString());

    return new ConnectionInfo(cr.getConnectionId());
  }
}
