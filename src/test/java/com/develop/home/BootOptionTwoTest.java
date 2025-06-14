package com.develop.home;

import com.develop.home.kafka.config.KafkaProducerProperties;
import com.jayway.jsonpath.JsonPath;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Step;
import lombok.SneakyThrows;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
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
    @Epic("Автозапуск приложения без консольного взаимодействия")
    @DisplayName("Запуск приложения с параметром = 2")
    @Description("""
    Запуск приложения с параметрами "console.manual-boot.option=2",
     "console.manual-boot.avsc-schema-path=classpath:test_schema.avsc",
     "console.manual-boot.json-file-path:classpath:test.json"
    """)
    @SneakyThrows
    void boot() {
        Path path = getJsonPath();
        String json = getJsonContent(path);
        String binaryAvroContent = readKafkaMessage();
        checkBinaryAvroField(binaryAvroContent, json);
    }

    @SneakyThrows
    @Step("Поиск готового json файла")
    Path getJsonPath() {
        final File[] file = new File[1];
        Assertions.assertDoesNotThrow(() -> file[0] = ResourceUtils.getFile("classpath:test.json"));
        return file[0].toPath();
    }

    @Step("Чтение содержимого json файла")
    @SneakyThrows
    String getJsonContent(Path path) {
        return Files.readString(path, StandardCharsets.UTF_8);
    }

    @Step("Чтение отправленного бинарного avro сообщения в кафке")
    String readKafkaMessage() {
        String[] binaryAvro = new String[1];
        Awaitility.await()
                .untilAsserted(() -> {
                    consumer.subscribe(List.of(properties.getTopic()));
                    ConsumerRecord<String, String> record = consumer.poll(Duration.ofSeconds(10)).records(properties.getTopic()).iterator().next();
                    binaryAvro[0] = record.value();
                    Assertions.assertNotNull(binaryAvro[0]);
                });
        return binaryAvro[0];
    }

    @Step("Проверка полей avro сообщения")
    void checkBinaryAvroField(String binaryAvroContent, String json) {
        Assertions.assertTrue(binaryAvroContent.contains(JsonPath.read(json, "$.events[0].name")));
    }
}
