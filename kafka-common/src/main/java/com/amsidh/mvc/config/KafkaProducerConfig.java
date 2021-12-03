package com.amsidh.mvc.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka.producer.bootstrap-servers:localhost:29092,localhost:39092}")
    private String bootstrapServerConfig;

    @Bean
    public KafkaProducer<String, byte[]> getKafkaProducer() {
        log.debug("Creating bean of KafkaProducer!!!!");
        Map<String, Object> kafkaProducerConfigMap = new HashMap<>();
        kafkaProducerConfigMap.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServerConfig);

        kafkaProducerConfigMap.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        kafkaProducerConfigMap.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class);
		return new KafkaProducer<String, byte[]>(kafkaProducerConfigMap);
    }
}
