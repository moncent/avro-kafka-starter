package local.home.console.utils;

import local.home.config.lang.LanguageProps;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConsoleUtils {

    private final LanguageProps languageProps;

    public void logUnknownMsg() {
        System.out.println(languageProps.getUnknownCommand());
    }

    public void menu() {
        System.out.println();
        System.out.println(languageProps.getMenu());
        System.out.println();
    }

    public void detectedEmptyFileName() {
        System.out.println(languageProps.getDetectedEmptyFileName());
    }

    public void onlyYesNoAnswer() {
        System.out.print(languageProps.getOnlyYesNoAnswer() + " ");
    }


}
