package com.develop.home.kafka.config;

import com.develop.home.avro.AvroBinarySerializer;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Profile("prod")
@Configuration
@RequiredArgsConstructor
public class KafkaProducerConfig {

    private final KafkaProducerProperties properties;

    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());
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
}