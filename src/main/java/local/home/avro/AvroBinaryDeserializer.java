package local.home.avro;

import local.home.console.UserPaths;
import org.apache.avro.Schema;
import org.apache.avro.file.SeekableByteArrayInput;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DecoderFactory;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class AvroBinaryDeserializer implements Deserializer<GenericRecord> {
    private final Logger log = LoggerFactory.getLogger(AvroBinaryDeserializer.class);

    @Override
    public GenericRecord deserialize(String topic, byte[] data) {
        GenericRecord record;
        try (InputStream avscIs = Files.newInputStream(Paths.get(UserPaths.getInstance().getAvscSchemaPath()));
             SeekableByteArrayInput arrayInput = new SeekableByteArrayInput(data)) {
            Schema schema = new Schema.Parser().parse(avscIs);
            GenericDatumReader<GenericRecord> datumReader = new GenericDatumReader<>(schema);
            record = datumReader.read(null, DecoderFactory.get().binaryDecoder(arrayInput, null));
        } catch (IOException e) {
            log.error("Exception raised:", e);
            throw new RuntimeException(e);
        }
        return record;
    }
}
