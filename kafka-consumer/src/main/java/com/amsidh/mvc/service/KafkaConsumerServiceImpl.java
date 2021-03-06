package com.amsidh.mvc.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KafkaConsumerServiceImpl implements KafkaConsumerService {

	@KafkaListener(topics = "spring-topic", groupId = "test-consumer-group")
	@Override
	public void consumeKafkaMessage(@Payload String message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
		log.info("Message {} consumed from partition {}", message, partition);
	}
	
}
