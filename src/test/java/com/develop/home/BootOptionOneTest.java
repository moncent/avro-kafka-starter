package com.develop.home;

import com.jayway.jsonpath.JsonPath;
import io.qameta.allure.*;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestPropertySource;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;


@TestPropertySource(properties = {
        "console.manual-boot.option=1",
        "console.manual-boot.avsc-schema-path=classpath:test_schema.avsc"
})
public class BootOptionOneTest extends AbstractTest {

    @Test
    @Epic("Автозапуск приложения без консольного взаимодействия")
    @DisplayName("Запуск приложения с параметром = 1")
    @Description("""
    Запуск приложения с параметром "console.manual-boot.option=1" и
     "console.manual-boot.avsc-schema-path=classpath:test_schema.avsc"
    """)
    @SneakyThrows
    void bootOptionOne() {
        //given
        //when
        Path path = getGeneratedJsonPath();
        String json = getGeneratedJsonContent(path);
        //then
        checkJsonFields(json);
        //after
        after(path);
    }

    @SneakyThrows
    @Step("Поиск сгенерированного json файла")
    Path getGeneratedJsonPath() {
        final File[] file = new File[1];
        Assertions.assertDoesNotThrow(() -> file[0] = ResourceUtils.getFile("test_schema.json"));
        return file[0].toPath();
    }

    @Step("Чтение содержимого json файла")
    @SneakyThrows
    String getGeneratedJsonContent(Path path) {
        return Files.readString(path, StandardCharsets.UTF_8);
    }

    @Step("Проверка полей в json")
    void checkJsonFields(String json) {
        Assertions.assertNotNull(JsonPath.read(json, "$.events[0].name"));
        Assertions.assertNotNull(JsonPath.read(json, "$.task.status"));
        Assertions.assertNotNull(JsonPath.read(json, "$.task.taskInfo"));
        Assertions.assertInstanceOf(Map.class, JsonPath.read(json, "$.task.taskInfo"));
        Assertions.assertEquals(2, ((Map<Object, Object>) JsonPath.read(json, "$.task.taskInfo")).size());
    }

    @Step("Удаление сгенерированного json файла")
    @SneakyThrows
    void after(Path path) {
        Files.deleteIfExists(path);
        Assertions.assertFalse(Files.exists(path));
    }
}
