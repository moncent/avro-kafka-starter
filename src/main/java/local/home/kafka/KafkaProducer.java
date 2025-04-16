package local.home.kafka;

import local.home.config.lang.LanguageProps;
import local.home.kafka.config.KafkaProducerProperties;
import lombok.RequiredArgsConstructor;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class KafkaProducer {
    private final Logger log = LoggerFactory.getLogger(KafkaProducer.class);

    private final KafkaProducerProperties properties;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final LanguageProps languageProps;

    public void sendMessage(GenericRecord record) {
        try {
            ProducerRecord<String, Object> producerRecord = new ProducerRecord<>(properties.getTopic(),
                    null, properties.getKey(), record, resolveHeaders());
            SendResult<String, Object> result = kafkaTemplate.send(producerRecord).get();
            boolean isSent = Optional.of(result.getRecordMetadata()).map(RecordMetadata::hasOffset).orElse(false);
            if (isSent) {
                RecordMetadata recordMetadata = result.getRecordMetadata();
                log.info(languageProps.getKafkaProducerLogInfo(), recordMetadata.partition(), recordMetadata.offset());
            } else {
                log.error(languageProps.getKafkaProducerLogError1());
            }
        } catch (InterruptedException | ExecutionException e) {
            log.error(languageProps.getKafkaProducerLogError2(), e);
            throw new RuntimeException(e);
        }
    }

    private List<Header> resolveHeaders() {
        List<Header> headers = new ArrayList<>();
        headers.addAll(Optional.ofNullable(properties.getHeaders())
                .map(Map::entrySet)
                .map(set -> set.stream()
                        .map(entry -> new RecordHeader(entry.getKey().replaceAll("^\\d+\\.", ""),
                                Optional.ofNullable(entry.getValue()).map(v -> v.getBytes(StandardCharsets.UTF_8)).orElse(null)))
                        .toList())
                .orElse(new ArrayList<>(1)));
        return headers.isEmpty() ? null : headers;
    }

}
