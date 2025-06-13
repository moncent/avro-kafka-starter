package com.develop.home.kafka.config;

import com.develop.home.AbstractTest;
import com.develop.home.avro.AvroBinarySerializer;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Profile("dev")
@Configuration
@RequiredArgsConstructor
public class KafkaProducerConfig {

    private final KafkaProducerProperties properties;

    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, AbstractTest.kafka.getBootstrapServers());
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,  StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, AvroBinarySerializer.class);

        boolean isSslEnabled = Optional.ofNullable(properties.getSsl().getEnabled()).orElse(false);
        if (isSslEnabled) {
            configProps.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, properties.getSsl().getKeystore().getLocation());
            configProps.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, properties.getSsl().getKeystore().getPassword());
            configProps.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, properties.getSsl().getTruststore().getLocation());
            configProps.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, properties.getSsl().getTruststore().getPassword());
            configProps.put(SslConfigs.SSL_PROTOCOL_CONFIG, properties.getSsl().getProtocol());
            configProps.put("security.protocol", properties.getSsl().getSecurityProtocol());
            configProps.put(SslConfigs.SSL_ENDPOINT_IDENTIFICATION_ALGORITHM_CONFIG, properties.getSsl().getEndpointIdentificationAlgorithm());
        }
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean("devKafkaConsumer")
    public KafkaConsumer<String, String> devKafkaConsumer() {
        return new KafkaConsumer<>(consumerConfig());
    }

    private Map<String, Object> consumerConfig() {

        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, AbstractTest.kafka.getBootstrapServers());
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group");
        configProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        configProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

        boolean isSslEnabled = Optional.ofNullable(properties.getSsl().getEnabled()).orElse(false);
        if (isSslEnabled) {
            configProps.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, properties.getSsl().getKeystore().getLocation());
            configProps.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, properties.getSsl().getKeystore().getPassword());
            configProps.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, properties.getSsl().getTruststore().getLocation());
            configProps.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, properties.getSsl().getTruststore().getPassword());
            configProps.put(SslConfigs.SSL_PROTOCOL_CONFIG, properties.getSsl().getProtocol());
            configProps.put("security.protocol", properties.getSsl().getSecurityProtocol());
            configProps.put(SslConfigs.SSL_ENDPOINT_IDENTIFICATION_ALGORITHM_CONFIG, properties.getSsl().getEndpointIdentificationAlgorithm());
        }
        return configProps;
    }
}