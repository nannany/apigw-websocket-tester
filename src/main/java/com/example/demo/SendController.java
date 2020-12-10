package com.example.demo;

import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendController {

  private final Logger logger = LoggerFactory.getLogger(SendController.class);
  public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

  @PostMapping("send")
  public Ack send(@org.springframework.web.bind.annotation.RequestBody ConnectRequest cr) throws IOException {
    Gson gson = new Gson();

    OkHttpClient client = new OkHttpClient();
    String url =
        "https://qh8poob9gf.execute-api.ap-northeast-1.amazonaws.com/Prod/@connections/" + cr.getConnectionId();
    RequestBody body = com.squareup.okhttp.RequestBody.create(JSON, gson.toJson(cr));
    Request request = new Request.Builder().url(url).post(body).build();
    Response response = client.newCall(request).execute();

    logger.info(response.toString());

    return new Ack(cr.getMessageId());
  }

}
