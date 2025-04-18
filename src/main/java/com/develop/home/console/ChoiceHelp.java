package com.develop.home.console;

import com.develop.home.config.lang.LanguageProps;
import com.develop.home.console.utils.ConsoleUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChoiceHelp {

    private static final String EASTER_EGG = """
                      %s
            
                    ──────▄▀▄─────▄▀▄
                    ─────▄█░░▀▀▀▀▀░░█▄
                    ─▄▄──█░░░░░░░░░░░█──▄▄
                    █▄▄█─█░░▀░░┬░░▀░░█─█▄▄█
            

            """;

    private final ConsoleUtils consoleUtils;
    private final LanguageProps languageProps;

    protected void exitHelp() {
        System.out.println(languageProps.getChoiceFiveHelp());
        consoleUtils.menu();
    }

    protected void helpMenu(String choice) {
        if ("?".equals(choice)) {
            System.out.println(languageProps.getChoiceHelp());
            System.out.println();
        } else if ("??".equals(choice)) {
            receiveEasterEgg();
        } else {
            consoleUtils.logUnknownMsg();
        }
        consoleUtils.menu();
    }

    private void receiveEasterEgg() {
        System.out.printf(EASTER_EGG, languageProps.getEasterEggDescr());
    }
}
