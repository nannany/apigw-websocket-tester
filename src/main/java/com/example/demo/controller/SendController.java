package com.example.demo.controller;

import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.apigatewaymanagementapi.AmazonApiGatewayManagementApi;
import com.amazonaws.services.apigatewaymanagementapi.AmazonApiGatewayManagementApiClientBuilder;
import com.amazonaws.services.apigatewaymanagementapi.model.PostToConnectionRequest;
import com.example.demo.request.ConnectRequest;
import com.example.demo.response.Ack;
import com.example.demo.response.Message;
import com.google.gson.Gson;
import java.io.IOException;
import java.nio.ByteBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendController {

  private final Logger logger = LoggerFactory.getLogger(SendController.class);

  @PostMapping("send")
  public Ack send(@org.springframework.web.bind.annotation.RequestBody ConnectRequest cr)
      throws IOException {
    logger.info(cr.toString());

    EndpointConfiguration ec = new EndpointConfiguration(
        "https://qh8poob9gf.execute-api.ap-northeast-1.amazonaws.com/Prod",
        "ap-northeast-1");
    AmazonApiGatewayManagementApi client = AmazonApiGatewayManagementApiClientBuilder.standard()
        .withEndpointConfiguration(ec)
        .build();

    Message message = new Message(cr.getBody());
    Gson gson = new Gson();
    String dataStr = gson.toJson(message);
    ByteBuffer data = ByteBuffer.wrap(dataStr.getBytes());

    PostToConnectionRequest postToConnectionRequest = new PostToConnectionRequest()
        .withConnectionId(cr.getConnectionId()).withData(data);

    client.postToConnection(postToConnectionRequest);

    return new Ack(cr.getMessageId());
  }

}
