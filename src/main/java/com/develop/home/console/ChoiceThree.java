package com.develop.home.console;

import com.develop.home.avro.AvroService;
import com.develop.home.config.lang.LanguageProps;
import com.develop.home.console.utils.ConsoleUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Scanner;

import static com.develop.home.utils.StringUtils.*;

@Component
@RequiredArgsConstructor
public class ChoiceThree {

    private final UserPaths userPaths = UserPaths.getInstance();
    private final AvroService avroService;
    private final ConsoleUtils consoleUtils;
    private final LanguageProps languageProps;

    protected void createBinaryAvro(String choice, Scanner confirmScanner) throws IOException {
        if ("3?".equals(choice)) {
            System.out.println(languageProps.getChoiceThreeHelp());
        } else if ("3".equals(choice)) {
            userPaths.reset();
            System.out.print(languageProps.getEnterJsonFile() + " ");
            String answer = confirmScanner.nextLine();
            if (!answer.isEmpty()) {
                userPaths.setJsonFilePath(answer);
            }
            System.out.print(languageProps.getEnterAvroFile() + " ");
            answer = confirmScanner.nextLine();
            if (!answer.isEmpty()) {
                userPaths.setAvscSchemaPath(answer);
            }
            checkAndWriteToAvro();
            userPaths.reset();
        } else {
            consoleUtils.logUnknownMsg();
        }
        consoleUtils.menu();
    }

    private void checkAndWriteToAvro() throws IOException {
        if (!isNullOrEmpty(userPaths.getJsonFilePath()) && !isNullOrEmpty(userPaths.getAvscSchemaPath())) {
            System.out.printf(languageProps.getStartCreatingBinaryFileProcess(), userPaths.getJsonFilePath(), userPaths.getAvscSchemaPath());
            String binFileName = avroService.writeToAvro(userPaths.getJsonFilePath(), userPaths.getAvscSchemaPath());
            System.out.printf(languageProps.getCreatedBinaryFileSuccess(), binFileName);
        } else {
            consoleUtils.detectedEmptyFileName();
        }
    }
}
