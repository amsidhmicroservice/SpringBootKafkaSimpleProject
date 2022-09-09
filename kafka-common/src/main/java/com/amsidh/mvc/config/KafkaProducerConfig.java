package com.amsidh.mvc.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class KafkaProducerConfig {

	@Value("${spring.kafka.producer.bootstrap-servers:localhost:29092,localhost:39092}")
	private String bootstrapServerConfig;

	@Bean
	public ProducerFactory<String, Object> getProducerFactory() {
		log.debug("Creating bean of ProducerFactory!!!!");
		Map<String, Object> kafkaProducerConfigMap = getKafkaProducerConfigMap();
		return new DefaultKafkaProducerFactory<>(kafkaProducerConfigMap);

	}

	@Bean
	public KafkaTemplate getKafkaTemplate() {
		log.debug("Creating bean of KafkaTemplate!!!!");
		KafkaTemplate kafkaTemplate = new KafkaTemplate(getProducerFactory());
		return kafkaTemplate;
	}

	@Bean
	public KafkaProducer<String, Object> getKafkaProducer() {
		return new KafkaProducer(getKafkaProducerConfigMap());
	}


	private Map<String, Object> getKafkaProducerConfigMap() {
		Map<String, Object> kafkaProducerConfigMap = new HashMap<>();
		kafkaProducerConfigMap.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServerConfig);
		kafkaProducerConfigMap.put(ProducerConfig.ACKS_CONFIG, "all");

		kafkaProducerConfigMap.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		kafkaProducerConfigMap.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		return kafkaProducerConfigMap;
	}

}
