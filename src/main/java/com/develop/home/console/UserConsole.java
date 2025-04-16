package com.develop.home.console;

import com.develop.home.config.lang.LanguageProps;
import com.develop.home.console.utils.ConsoleUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class UserConsole implements CommandLineRunner {

    private final ChoiceOne choiceOne;
    private final ChoiceTwo choiceTwo;
    private final ChoiceThree choiceThree;
    private final ChoiceFour choiceFour;
    private final ChoiceHelp choiceHelp;
    private final ConsoleUtils consoleUtils;
    private final LanguageProps languageProps;


    @Override
    public void run(String... args) throws Exception {
        init();
    }

    private void init() {
        consoleUtils.menu();
        String choice;

        try (Scanner scanner = new Scanner(System.in)) {
            choice = scanner.nextLine();
            while (!"5".equals(choice)) {
                if (choice.startsWith("1")) {
                    choiceOne.generateJson(choice, scanner);
                } else if (choice.startsWith("2")) {
                    choiceTwo.autoSendKafka(choice, scanner);
                } else if (choice.startsWith("3")) {
                    choiceThree.createBinaryAvro(choice, scanner);
                } else if (choice.startsWith("4")) {
                    choiceFour.manualSendKafka(choice, scanner);
                } else if ("5?".equals(choice)) {
                    choiceHelp.exitHelp();
                } else if (choice.startsWith("?")) {
                    choiceHelp.helpMenu(choice);
                } else if (!choice.isEmpty() && !choice.isBlank()) {
                   consoleUtils.logUnknownMsg();
                }
                choice = scanner.nextLine();
            }
        } catch (IOException e) {
            System.out.println(languageProps.getErrorMain());
            System.out.println(languageProps.getErrorMainMore());
            throw new RuntimeException(e);
        }
        System.exit(0);
    }
}
