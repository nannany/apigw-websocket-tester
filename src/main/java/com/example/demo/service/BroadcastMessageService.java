package com.example.demo.service;

import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.apigatewaymanagementapi.AmazonApiGatewayManagementApiAsync;
import com.amazonaws.services.apigatewaymanagementapi.AmazonApiGatewayManagementApiAsyncClientBuilder;
import com.amazonaws.services.apigatewaymanagementapi.model.PostToConnectionRequest;
import com.example.demo.request.Connection;
import com.example.demo.response.Message;
import com.google.gson.Gson;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class BroadcastMessageService {

  private static final Logger logger = LoggerFactory.getLogger(BroadcastMessageService.class);

  private Connection connection;

  public BroadcastMessageService(Connection connection) {
    this.connection = connection;
  }

  @Async
  public void broadcast(String broadcastData) {
    logger.info("★★" + broadcastData);

    EndpointConfiguration ec = new EndpointConfiguration(
        "https://qh8poob9gf.execute-api.ap-northeast-1.amazonaws.com/Prod",
        "ap-northeast-1");
    AmazonApiGatewayManagementApiAsync client = AmazonApiGatewayManagementApiAsyncClientBuilder
        .standard()
        .withEndpointConfiguration(ec)
        .build();
    Message message = new Message(broadcastData);
    Gson gson = new Gson();
    String dataStr = gson.toJson(message);
    ByteBuffer data = ByteBuffer.wrap(dataStr.getBytes());

    List<String> connectionIds = new ArrayList<>(connection.getConnectionMap().keySet());

    connectionIds.forEach(connectionId -> {
          logger.info("★★"+ connectionId);
          PostToConnectionRequest postToConnectionRequest = new PostToConnectionRequest()
              .withConnectionId(connectionId).withData(data);
          client.postToConnectionAsync(postToConnectionRequest);
        }
    );
  }

}
