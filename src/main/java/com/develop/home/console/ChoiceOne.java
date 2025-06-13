package com.develop.home.console;

import com.develop.home.avro.AvroService;
import com.develop.home.config.lang.LanguageProps;
import com.develop.home.console.utils.ConsoleUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Scanner;

import static com.develop.home.utils.StringUtils.*;

@Component
@RequiredArgsConstructor
public class ChoiceOne {

    private final Logger log = LoggerFactory.getLogger(ChoiceOne.class);

    @Value("${console.manual-boot.avsc-schema-path:#{null}}")
    private String manualAvscSchemaPath;

    private final UserPaths userPaths = UserPaths.getInstance();
    private final AvroService avroService;
    private final ConsoleUtils consoleUtils;
    private final LanguageProps languageProps;


    public void generateJson(String choice, Scanner avscScanner) throws IOException {
        if ("1?".equals(choice)) {
            System.out.println(languageProps.getChoiceOneHelp());
        } else if ("1".equals(choice)) {
            String avscSchemaPath = "";
            while (avscSchemaPath.isEmpty()) {
                System.out.print(languageProps.getEnterAvroFile() + " ");
                avscSchemaPath = avscScanner.nextLine();
            }
            userPaths.setAvscSchemaPath(avscSchemaPath);
            String jsonFilePath = avroService.generateJsonFromAvsc(userPaths.getAvscSchemaPath());
            userPaths.setJsonFilePath(jsonFilePath);
            System.out.printf(languageProps.getAvroSchemaHasBeenGeneratedSuccess(), jsonFilePath);
        } else {
           consoleUtils.logUnknownMsg();
        }
       consoleUtils.menu();
    }

    public void generateJson() throws IOException {
        if (isNullOrEmpty(manualAvscSchemaPath)) {
            log.warn(languageProps.getEnterAvroFile() + " ");
        } else {
            userPaths.setAvscSchemaPath(manualAvscSchemaPath);
            String jsonFilePath = avroService.generateJsonFromAvsc(userPaths.getAvscSchemaPath());
            userPaths.setJsonFilePath(jsonFilePath);
            log.info(String.format(languageProps.getAvroSchemaHasBeenGeneratedSuccess(), jsonFilePath));
            userPaths.reset();
        }
    }
}
