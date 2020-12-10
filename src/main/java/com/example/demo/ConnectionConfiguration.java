package com.example.demo;

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
