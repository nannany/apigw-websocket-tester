package com.example.demo.controller;

import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.apigatewaymanagementapi.AmazonApiGatewayManagementApi;
import com.amazonaws.services.apigatewaymanagementapi.AmazonApiGatewayManagementApiClientBuilder;
import com.amazonaws.services.apigatewaymanagementapi.model.PostToConnectionRequest;
import com.example.demo.request.ConnectRequest;
import com.example.demo.response.Ack;
import com.example.demo.response.Test;
import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import java.io.IOException;
import java.nio.ByteBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendController {

  private final Logger logger = LoggerFactory.getLogger(SendController.class);
  public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
  private final String url =
      "https://qh8poob9gf.execute-api.ap-northeast-1.amazonaws.com/Prod/@connections/";

  @PostMapping("send")
  public Ack send(@org.springframework.web.bind.annotation.RequestBody ConnectRequest cr)
      throws IOException {

    EndpointConfiguration ec = new EndpointConfiguration(
        "https://qh8poob9gf.execute-api.ap-northeast-1.amazonaws.com/Prod",
        "ap-northeast-1");
    AmazonApiGatewayManagementApi client = AmazonApiGatewayManagementApiClientBuilder.standard()
        .withEndpointConfiguration(ec)
        .build();

    Test test = new Test("test");
    Gson gson = new Gson();
    String dataStr = gson.toJson(test);
    ByteBuffer data = ByteBuffer.wrap(dataStr.getBytes());

    PostToConnectionRequest postToConnectionRequest = new PostToConnectionRequest()
        .withConnectionId(cr.getConnectionId()).withData(data);

    client.postToConnection(postToConnectionRequest);

    return new Ack(cr.getMessageId());
  }

}
