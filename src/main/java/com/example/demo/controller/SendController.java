package com.example.demo.controller;

import com.example.demo.request.ConnectRequest;
import com.example.demo.response.Ack;
import com.example.demo.util.ApiGatewayPublishUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendController {

  private final Logger logger = LoggerFactory.getLogger(SendController.class);

  private ApiGatewayPublishUtil apiGatewayPublishUtil;

  public SendController(ApiGatewayPublishUtil apiGatewayPublishUtil) {
    this.apiGatewayPublishUtil = apiGatewayPublishUtil;
  }

  @PostMapping("send")
  public Ack send(@org.springframework.web.bind.annotation.RequestBody ConnectRequest cr) {
    logger.info(cr.toString());

    apiGatewayPublishUtil.asyncRequest(cr.getConnectionId(), cr.getBody());

    return new Ack(cr.getMessageId());
  }

}
