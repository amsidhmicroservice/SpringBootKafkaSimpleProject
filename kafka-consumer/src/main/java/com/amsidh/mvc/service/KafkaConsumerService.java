package com.amsidh.mvc.service;

import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

public interface KafkaConsumerService {
   void consumeKafkaMessage(@Payload Object object, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition);
}
