package com.develop.home.avro;

import lombok.RequiredArgsConstructor;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

@Service
@RequiredArgsConstructor
public class AvroService {

    private final AvroBinarySerializer avroBinarySerializer;
    private final AvroBinaryDeserializer avroBinaryDeserializer;

    public String generateJsonFromAvsc(String fileName) throws IOException {
        String jsonFileName = fileName.trim().replaceAll("^(classpath:)?(.+)(\\.avsc)$", "$2.json").trim();
        try (InputStream is = Files.newInputStream(ResourceUtils.getFile(fileName.trim()).toPath());
             PrintWriter writer = new PrintWriter(jsonFileName, StandardCharsets.UTF_8)) {
            Schema schema = new Schema.Parser().parse(is);
            Iterator<Object> it = new RandomData(schema, 1).iterator();
            writer.print(it.next());
        }
        return jsonFileName;
    }

    public GenericRecord convertJsonToAvro(String jsonPath, String avscSchemaPath) throws IOException {
        jsonPath = jsonPath.trim();
        avscSchemaPath =  avscSchemaPath.trim();
        try (InputStream avscIs = Files.newInputStream(ResourceUtils.getFile(avscSchemaPath).toPath());
             InputStream jsonIs = Files.newInputStream(ResourceUtils.getFile(jsonPath).toPath())) {
            Schema schema = new Schema.Parser().parse(avscIs);
            DatumReader<GenericRecord> reader = new GenericDatumReader<>(schema);
            Decoder decoder = DecoderFactory.get().jsonDecoder(schema, jsonIs);
            return reader.read(null, decoder);
        } catch (IOException e) {
            throw new IOException("Error converting JSON to Avro", e);
        }
    }

    public String writeToAvro(String jsonPath, String avscSchemaPath) throws IOException {
        jsonPath = jsonPath.trim();
        avscSchemaPath = avscSchemaPath.trim();
        GenericRecord record = convertJsonToAvro(jsonPath, avscSchemaPath);
        String binFileName = avscSchemaPath.replace(".avsc", ".bin");
        try(OutputStream os = Files.newOutputStream(Paths.get(binFileName))) {
            byte[] binData = avroBinarySerializer.serialize(null, record);
            os.write(binData);
        }
        return binFileName;
    }

    public GenericRecord readBinary(String binFileName) throws IOException {
        GenericRecord record;
        try (InputStream is = Files.newInputStream(Paths.get(binFileName))) {
            record = avroBinaryDeserializer.deserialize(null, IOUtils.toByteArray(is));
        }
        return record;
    }
}
