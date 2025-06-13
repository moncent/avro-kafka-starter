package com.develop.home;

import com.jayway.jsonpath.JsonPath;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
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
    @SneakyThrows
    void boot() {
        //given
        //when
        final File[] file = new File[1];
        Assertions.assertDoesNotThrow(() -> file[0] = ResourceUtils.getFile("test_schema.json"));
        Path path = file[0].toPath();
        String json = Files.readString(path, StandardCharsets.UTF_8);

        //then
        Assertions.assertNotNull(JsonPath.read(json, "$.events[0].name"));
        Assertions.assertNotNull(JsonPath.read(json, "$.task.status"));
        Assertions.assertNotNull(JsonPath.read(json, "$.task.taskInfo"));
        Assertions.assertInstanceOf(Map.class, JsonPath.read(json, "$.task.taskInfo"));
        Assertions.assertEquals(2, ((Map<Object, Object>) JsonPath.read(json, "$.task.taskInfo")).size());

        //after
        Files.deleteIfExists(path);
    }
}
