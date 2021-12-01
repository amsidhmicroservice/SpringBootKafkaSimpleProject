package com.amsidh.mvc.config;

import java.util.HashMap;
import java.util.Map;

import com.amsidh.mvc.model.PersonRequest;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableKafka
@Configuration
public class KafkaConsumerConfig {
	
	@Value("${spring.kafka.consumer.bootstrap-servers:localhost:29092,localhost:39092}")
	private String bootstrapServerConfig;


	@Bean
	public Map<String, Object> consumerConfigs() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServerConfig);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		return props;
	}

	@Bean
	public ConsumerFactory<String, Object> getConsumerFactory() {
		log.debug("Creating bean of ConsumerFactory!!!!");

		return new DefaultKafkaConsumerFactory(consumerConfigs(),new StringDeserializer(), new JsonDeserializer<>(PersonRequest.class));

	}

	@Bean
    public ConcurrentKafkaListenerContainerFactory<String, PersonRequest> kafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, PersonRequest> factory =  new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(getConsumerFactory());
		factory.setRecordFilterStrategy(consumerRecord -> !(consumerRecord.value() instanceof PersonRequest));
        return factory;
    }
}
