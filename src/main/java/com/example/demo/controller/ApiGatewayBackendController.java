package com.example.demo.controller;

import com.example.demo.request.ConnectRequest;
import com.example.demo.request.Connection;
import com.example.demo.response.ConnectAck;
import com.example.demo.response.ConnectionInfo;
import com.example.demo.util.ApiGatewayPublishUtil;
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
  private ApiGatewayPublishUtil apiGatewayPublishUtil;

  public ApiGatewayBackendController(Connection connection,
      ApiGatewayPublishUtil apiGatewayPublishUtil) {
    this.connection = connection;
    this.apiGatewayPublishUtil = apiGatewayPublishUtil;
  }

  @PostMapping("connect")
  public ConnectAck connect(@RequestBody ConnectRequest cr) {
    Map<String, String> map = connection.getConnectionMap();
    map.put(cr.getConnectionId(), "1");
    logger.info(cr.toString());
    logger.info(map.toString());

    apiGatewayPublishUtil.asyncRequest(cr.getConnectionId(), cr.getDomainName());

    return new ConnectAck("200");
  }


  @PostMapping("disconnect")
  public ConnectionInfo disconnect(@RequestBody ConnectRequest cr) {
    logger.info(cr.toString());
    Map<String, String> map = connection.getConnectionMap();
    map.remove(cr.getConnectionId());
    logger.info(map.toString());

    return new ConnectionInfo(cr.getConnectionId());
  }
}
