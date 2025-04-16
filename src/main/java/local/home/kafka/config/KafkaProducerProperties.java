package local.home.kafka.config;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Map;

@Configuration
@PropertySource(value = "file:./config.yml", factory = YamlPropertySourceFactory.class)
@ConfigurationProperties(prefix = "kafka.producer")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class KafkaProducerProperties {
    private final Logger log = LoggerFactory.getLogger(KafkaProducerProperties.class);

    private String bootstrapServers;
    private String topic;
    private String key;
    private Map<String, String> headers;
    private SslConfig ssl = new SslConfig();

    @Getter
    @Setter
    public static class SslConfig {
        private Boolean enabled;
        private String protocol;
        private KeystoreConfig keystore = new KeystoreConfig();
        private KeystoreConfig truststore = new KeystoreConfig();
    }

    @Getter
    @Setter
    public static class KeystoreConfig {
        private String location;
        private String password;
    }

    @PostConstruct
    public void postConstruct() {
        log.info("Kafka producer params: bootstrapServers = {}, topic = {}, key = {}, headers = {}, " +
                "ssl.enabled = {}, ssl.protocol = {}, ssl.keystore.location = {}, ssl.keystore.password = {}, " +
                "ssl.truststore.location = {}, ssl.truststore.password = {}", getBootstrapServers(), getTopic(), getKey(),
                getHeaders(), getSsl().getEnabled(), getSsl().getProtocol(), getSsl().getKeystore().getLocation(),
                getSsl().getKeystore().getPassword(), getSsl().getTruststore().getLocation(), getSsl().getTruststore().getPassword());
    }
}
