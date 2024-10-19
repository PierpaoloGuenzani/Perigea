package it.perigea.marketdatastorer.configuration;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

/**
 * A class created to configure Apache Kafka
 */
@Configuration
public class KafkaConsumerConfig
{
	@Value("${spring.kafka.bootstrap-servers}")
	private String boostrapAddress;
	
	/**
	 * A function for creating a Spring Bean that configure a Kafka consumer factory
	 * @return ConsumerFactory - a factory for producing consumer instance
	 */
	@Bean
	public ConsumerFactory<String, String> consumerFactory()
	{
		Map<String, Object> property = new HashMap<>();
		property.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, boostrapAddress);
		property.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		property.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		property.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		//TODO: da String a ExchangeRate
		return new DefaultKafkaConsumerFactory<>(property);
	}
	
	/**
	 * A function for creating ConcurrentKafkaListenerContainerFactory (create multiple
	 * KafkaMessageListenerContainer multi-thread reading)
	 * @return ConcurrentKafkaListenerContainerFactory - a factory of KafkaListener annotation method
	 */
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory()
	{
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<String, String>();
		factory.setConsumerFactory(consumerFactory());
		return factory;
	}
	
}
