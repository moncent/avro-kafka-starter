package local.home.console;

import local.home.avro.AvroService;
import local.home.config.lang.LanguageProps;
import local.home.console.utils.ConsoleUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class ChoiceOne {

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
}
