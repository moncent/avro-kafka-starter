package com.develop.home;

import com.develop.home.kafka.config.KafkaProducerProperties;
import com.jayway.jsonpath.JsonPath;
import lombok.SneakyThrows;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.TestPropertySource;
import org.springframework.util.ResourceUtils;
import org.testcontainers.shaded.org.awaitility.Awaitility;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.List;

@TestPropertySource(properties = {
        "console.manual-boot.option=2",
        "console.manual-boot.avsc-schema-path=classpath:test_schema.avsc",
        "console.manual-boot.json-file-path:classpath:test.json"
})
public class BootOptionTwoTest extends AbstractTest {

    @Autowired
    @Qualifier("devKafkaConsumer")
    private KafkaConsumer<String, String> consumer;

    @Autowired
    private KafkaProducerProperties properties;

    @Test
    @SneakyThrows
    void boot() {
        final File[] file = new File[1];
        Assertions.assertDoesNotThrow(() -> file[0] = ResourceUtils.getFile("classpath:test.json"));
        Path path = file[0].toPath();
        String json = Files.readString(path, StandardCharsets.UTF_8);
        final String[] binaryAvro = new String[1];

        Awaitility.await()
                .untilAsserted(() -> {
                    consumer.subscribe(List.of(properties.getTopic()));
                    ConsumerRecord<String, String> record = consumer.poll(Duration.ofSeconds(10)).records(properties.getTopic()).iterator().next();
                    binaryAvro[0] = record.value();
                    Assertions.assertNotNull(binaryAvro[0]);
                });
        Assertions.assertTrue(binaryAvro[0].contains(JsonPath.read(json, "$.events[0].name")));
    }
}
