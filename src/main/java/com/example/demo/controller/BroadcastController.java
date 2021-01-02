package com.example.demo.controller;

import com.example.demo.request.ConnectRequest;
import com.example.demo.response.Ack;
import com.example.demo.service.BroadcastMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BroadcastController {

  private final Logger logger = LoggerFactory.getLogger(BroadcastController.class);
  private final BroadcastMessageService broadcastMessageService;

  public BroadcastController(
      BroadcastMessageService broadcastMessageService) {
    this.broadcastMessageService = broadcastMessageService;
  }

  @PostMapping("broadcast")
  public Ack broadcast(@RequestBody ConnectRequest cr) {
    logger.info(cr.toString());

    broadcastMessageService.broadcast(cr.getBody());

    return new Ack(cr.getMessageId());
  }

}
