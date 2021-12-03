package com.amsidh.mvc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;

@RequiredArgsConstructor
@RestController
@Slf4j
public class MessagePublishController {

    private final KafkaProducer<String, byte[]> kafkaProducer;
    private final static String KAFKA_TOPIC_NAME = "spring-topic";

    @GetMapping("/publish/{name}")
    public String publishMessage(@PathVariable String name) {
        log.info("Sending message {}", name);
        try (FileInputStream fileInputStream = new FileInputStream(new File("C:\\Users\\amsid\\Documents\\workspace-2\\SpringBootKafkaSimpleProject\\kafka-producer\\pom.xml"))) {
            byte[] bytes = fileInputStream.readAllBytes();
            ProducerRecord<String, byte[]> producerRecord = new ProducerRecord<>(KAFKA_TOPIC_NAME, bytes);
            kafkaProducer.send(producerRecord, (metadata, exception) -> {
                if (exception == null) {
                    log.info("Successfully received the details as: \n" +
                            "Topic:" + metadata.topic() + "\n" +
                            "Partition:" + metadata.partition() + "\n" +
                            "Offset" + metadata.offset() + "\n" +
                            "Timestamp" + metadata.timestamp());
                } else {
                    log.error("Can't produce,getting error", exception);

                }
            });
            return "Message published successfully";
        } catch (Exception exception) {
            log.error("Error ", exception);
            return null;
        }
    }

}
