package com.example.demo.util;

import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.apigatewaymanagementapi.AmazonApiGatewayManagementApiAsync;
import com.amazonaws.services.apigatewaymanagementapi.AmazonApiGatewayManagementApiAsyncClientBuilder;
import com.amazonaws.services.apigatewaymanagementapi.model.PostToConnectionRequest;
import com.amazonaws.services.apigatewaymanagementapi.model.PostToConnectionResult;
import com.example.demo.response.Message;
import com.google.gson.Gson;
import java.nio.ByteBuffer;
import java.util.concurrent.Future;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ApiGatewayPublishUtil {

  @Async
  public Future<PostToConnectionResult> asyncRequest(String connectionId, String sendData) {
    EndpointConfiguration ec = new EndpointConfiguration(
        "https://qh8poob9gf.execute-api.ap-northeast-1.amazonaws.com/Prod",
        "ap-northeast-1");
    AmazonApiGatewayManagementApiAsync client = AmazonApiGatewayManagementApiAsyncClientBuilder
        .standard()
        .withEndpointConfiguration(ec)
        .build();
    Message message = new Message(sendData);
    Gson gson = new Gson();
    String dataStr = gson.toJson(message);
    ByteBuffer data = ByteBuffer.wrap(dataStr.getBytes());

    PostToConnectionRequest postToConnectionRequest = new PostToConnectionRequest()
        .withConnectionId(connectionId).withData(data);

    return client.postToConnectionAsync(postToConnectionRequest);
  }

}
