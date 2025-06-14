package com.develop.home.console;

import com.develop.home.avro.AvroService;
import com.develop.home.config.lang.LanguageProps;
import com.develop.home.console.utils.ConsoleUtils;
import com.develop.home.kafka.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.apache.avro.generic.GenericRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Scanner;

import static com.develop.home.utils.StringUtils.*;

@Component
@RequiredArgsConstructor
public class ChoiceTwo {

    private final Logger log = LoggerFactory.getLogger(ChoiceTwo.class);

    @Value("${console.manual-boot.avsc-schema-path:#{null}}")
    private String manualAvscSchemaPath;
    @Value("${console.manual-boot.json-file-path:#{null}}")
    private String manualJsonFilePath;

    private final UserPaths userPaths = UserPaths.getInstance();
    private final AvroService avroService;
    private final KafkaProducer kafkaProducer;
    private final ConsoleUtils consoleUtils;
    private final LanguageProps languageProps;

    protected void autoSendKafka(String choice, Scanner confirmScanner) throws IOException {
        if ("2?".equals(choice)) {
            System.out.println(languageProps.getChoiceTwoHelp());
        } else if ("2".equals(choice)) {
            String yesPattern = languageProps.getYesPattern();
            String noPattern = languageProps.getNoPattern();
            if (!isNullOrEmpty(userPaths.getJsonFilePath()) && !isNullOrEmpty(userPaths.getAvscSchemaPath())) {//при условии, что сюда перешли сразу из 1-го действия
                System.out.printf(languageProps.getSendAvroFileToKafkaConfirm() + " ", userPaths.getJsonFilePath(), userPaths.getAvscSchemaPath());
                String answer = confirmScanner.nextLine();
                while (!answer.matches(yesPattern) && !answer.matches(noPattern)) {
                    consoleUtils.onlyYesNoAnswer();
                    answer = confirmScanner.nextLine();
                }
                if (answer.matches(yesPattern)) {
                    sendMsgToKafka();
                } else {
                    sendFileConsistingFromOtherFiles(confirmScanner);
                }
            } else {//если вдруг нужно отправить в кафку сообщение на основе уже существующих json и avro
                sendFileConsistingFromOtherFiles(confirmScanner);
            }
            userPaths.reset();
        } else {
            consoleUtils.logUnknownMsg();
        }
        consoleUtils.menu();
    }

    protected void autoSendKafka() throws IOException {
        if (isNullOrEmpty(manualAvscSchemaPath) || isNullOrEmpty(manualJsonFilePath)) {
            log.warn(languageProps.getDetectedEmptyFileName());
        } else {
            userPaths.setAvscSchemaPath(manualAvscSchemaPath);
            userPaths.setJsonFilePath(manualJsonFilePath);
            sendMsgToKafka();
            log.info(languageProps.getSendMsgKafkaSuccess());
        }
    }

    private void sendFileConsistingFromOtherFiles(Scanner confirmScanner) throws IOException {
        userPaths.reset();
        System.out.print(languageProps.getEnterJsonFile() + " ");
        String answer = confirmScanner.nextLine();
        if (!answer.isEmpty()) {
            userPaths.setJsonFilePath(answer.trim());
        }
        System.out.print(languageProps.getEnterAvroFile() + " ");
        answer = confirmScanner.nextLine();
        if (!answer.isEmpty()) {
            userPaths.setAvscSchemaPath(answer.trim());
        }
        if (!isNullOrEmpty(userPaths.getJsonFilePath()) && !isNullOrEmpty(userPaths.getAvscSchemaPath())) {
            sendMsgToKafka();
        } else {
            consoleUtils.detectedEmptyFileName();
        }
    }

    private void sendMsgToKafka() throws IOException {
        GenericRecord record = avroService.convertJsonToAvro(userPaths.getJsonFilePath(), userPaths.getAvscSchemaPath());
        kafkaProducer.sendMessage(record);
        System.out.println();
        System.out.println(languageProps.getSendMsgKafkaSuccess());
    }
}
