package com.example.demo.controller;

import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.apigatewaymanagementapi.AmazonApiGatewayManagementApiAsync;
import com.amazonaws.services.apigatewaymanagementapi.AmazonApiGatewayManagementApiAsyncClientBuilder;
import com.amazonaws.services.apigatewaymanagementapi.model.PostToConnectionRequest;
import com.amazonaws.services.apigatewaymanagementapi.model.PostToConnectionResult;
import com.example.demo.request.ConnectRequest;
import com.example.demo.request.Connection;
import com.example.demo.response.ConnectAck;
import com.example.demo.response.ConnectionInfo;
import com.example.demo.response.Message;
import com.google.gson.Gson;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
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
    Map<String, String> map = connection.getConnectionMap();
    map.put(cr.getConnectionId(), "1");
    logger.info(cr.toString());
    logger.info(map.toString());

    asyncRequest(cr);

    return new ConnectAck("200");
  }


  @Async
  Future<PostToConnectionResult> asyncRequest(ConnectRequest cr) {
    EndpointConfiguration ec = new EndpointConfiguration(
        "https://qh8poob9gf.execute-api.ap-northeast-1.amazonaws.com/Prod",
        "ap-northeast-1");
    AmazonApiGatewayManagementApiAsync client = AmazonApiGatewayManagementApiAsyncClientBuilder
        .standard()
        .withEndpointConfiguration(ec)
        .build();
    Message message = new Message(cr.getConnectionId());
    Gson gson = new Gson();
    String dataStr = gson.toJson(message);
    ByteBuffer data = ByteBuffer.wrap(dataStr.getBytes());

    PostToConnectionRequest postToConnectionRequest = new PostToConnectionRequest()
        .withConnectionId(cr.getConnectionId()).withData(data);

    return client.postToConnectionAsync(postToConnectionRequest);
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
