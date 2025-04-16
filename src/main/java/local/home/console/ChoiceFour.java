package local.home.console;

import local.home.avro.AvroService;
import local.home.config.lang.LanguageProps;
import local.home.console.utils.ConsoleUtils;
import local.home.kafka.KafkaProducer;
import local.home.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.apache.avro.generic.GenericRecord;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class ChoiceFour {

    private final UserPaths userPaths = UserPaths.getInstance();
    private final AvroService avroService;
    private final KafkaProducer kafkaProducer;
    private final ConsoleUtils consoleUtils;
    private final LanguageProps languageProps;

    protected void manualSendKafka(String choice, Scanner confirmScanner) throws IOException {
        if ("4?".equals(choice)) {
            System.out.println(languageProps.getChoiceFourHelp());
        } else if ("4".equals(choice)) {
            userPaths.reset();
            System.out.print(languageProps.getEnterBinaryFileName() + " ");
            String binaryFileName = confirmScanner.nextLine();
            System.out.print(languageProps.getEnterAvroFile() + " ");
            String avroSchemaFileName = confirmScanner.nextLine();
            if (!StringUtils.isNullOrEmpty(binaryFileName) && !StringUtils.isNullOrEmpty(avroSchemaFileName)) {
                userPaths.setAvscSchemaPath(avroSchemaFileName);
                System.out.printf(languageProps.getStartSendingBinaryFileToKafkaProcess(), binaryFileName);
                sendMsgToKafka(binaryFileName);
                System.out.printf(languageProps.getSendBinaryFileSuccess(), binaryFileName);
            } else {
                consoleUtils.detectedEmptyFileName();
            }
        } else {
           consoleUtils.logUnknownMsg();
        }
        consoleUtils.menu();
    }

    private void sendMsgToKafka(String binFileName) throws IOException {
        GenericRecord record = avroService.readBinary(binFileName);
        kafkaProducer.sendMessage(record);
    }
}
