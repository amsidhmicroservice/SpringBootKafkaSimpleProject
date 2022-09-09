package com.amsidh.mvc.controller;

import com.amsidh.mvc.model.PersonRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@Slf4j
public class MessagePublishController {

	private final KafkaTemplate<String, Object> kafkaTemplate;
	private final KafkaProducer<String, Object> kafkaProducer;
	private final static String KAFKA_TOPIC_NAME = "spring-topic";

	@GetMapping("/publish/{name}")
	public String publishMessage(@PathVariable String name) {
		log.info("Sending message {}", name);


		ProducerRecord<String, Object> producerRecord = new ProducerRecord<>(KAFKA_TOPIC_NAME, name);

		/*
		//Using KafkaTemplate
		ListenableFuture<SendResult<String, Object>> sendResultListenableFuture = kafkaTemplate.send(producerRecord);
		sendResultListenableFuture.addCallback(result -> log.info("Message published successfully {}", result.getProducerRecord().value().toString()), ex -> log.error("Failed to publish message", ex));
*/
		//Use KafkaProducer
		kafkaProducer.send(producerRecord, (metadata, exception) -> {
			if (exception != null) {
				log.error("Failed to publish message", exception);
			}
			if (metadata != null) {
				log.info("Message published successfully {}", name);
			}
		});
		return "Message published successfully";
	}
	
	@PostMapping("/publish")
	public String publishMessage( @RequestBody PersonRequest personRequest) {
		log.info("Sending person {}", personRequest);
		Optional.ofNullable(personRequest).ifPresent(person -> {
			if (null == person.getUuid()) {
				person.setUuid(UUID.randomUUID().toString());
			}
		});

		ProducerRecord<String, Object> producerRecord = new ProducerRecord<>(KAFKA_TOPIC_NAME, personRequest.getUuid(), personRequest);
		//Using KafkaTemplate
		ListenableFuture<SendResult<String, Object>> sendResultListenableFuture = kafkaTemplate.send(producerRecord);
		sendResultListenableFuture.addCallback(result -> log.info("Message published successfully {}", result.getProducerRecord().value().toString()), ex -> log.error("Failed to publish message", ex));

		/*
		// OR using KafkaProducer
		kafkaProducer.send(producerRecord, (metadata, exception) -> {
			if (exception != null) {
				log.error("Failed to publish message", exception);
			}
			if (metadata != null) {
				log.info("Message published successfully {}", personRequest);
			}
		});*/

		return "Message published successfully";
	}

}
