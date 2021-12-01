package com.amsidh.mvc.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class AppConfig {

	@Bean
	public ProducerFactory<String, Object> getProducerFactory() {
		log.debug("Creating bean of ProducerFactory!!!!");
		Map<String, Object> kafkaProducerConfigMap = new HashMap<>();
		kafkaProducerConfigMap.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092,localhost:39092");

		kafkaProducerConfigMap.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		kafkaProducerConfigMap.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		return new DefaultKafkaProducerFactory<>(kafkaProducerConfigMap);

	}

	@Bean
	public KafkaTemplate<String, Object> getKafkaTemplate() {
		log.debug("Creating bean of KafkaTemplate!!!!");
		KafkaTemplate<String, Object> kafkaTemplate = new KafkaTemplate<String, Object>(getProducerFactory());
		return kafkaTemplate;
	}
}
