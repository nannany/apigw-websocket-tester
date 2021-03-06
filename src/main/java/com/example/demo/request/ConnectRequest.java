package com.example.demo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConnectRequest {

  private String connectionId;
  private String domainName;
  private String stage;
  private String body;
  private String messageId;

}
