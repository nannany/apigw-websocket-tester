package com.example.demo.configuration;

import com.example.demo.request.Connection;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConnectionConfiguration {

  @Bean
  public Connection connection(){
    return new Connection(new ConcurrentHashMap<>());
  }

}
