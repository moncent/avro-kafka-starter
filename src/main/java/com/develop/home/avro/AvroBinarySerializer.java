package com.develop.home.avro;

import com.develop.home.console.UserPaths;
import lombok.NoArgsConstructor;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.kafka.common.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
@NoArgsConstructor
public class AvroBinarySerializer implements Serializer<GenericRecord> {
    private final Logger log = LoggerFactory.getLogger(AvroBinarySerializer.class);

    @Override
    public byte[] serialize(String topic, GenericRecord record) {
        byte[] result;
        try(InputStream avscIs = Files.newInputStream(Paths.get(UserPaths.getInstance().getAvscSchemaPath()))) {
            Schema schema = new Schema.Parser().parse(avscIs);
            GenericDatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<>(schema);
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                Encoder encoder = EncoderFactory.get().binaryEncoder(outputStream, null);
                datumWriter.write(record, encoder);
                encoder.flush();
                result = outputStream.toByteArray();
            } catch (IOException e) {
                log.error("Exception raised:", e);
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            log.error("Exception raised:", e);
            throw new RuntimeException(e);
        }

        return result;
    }
}
