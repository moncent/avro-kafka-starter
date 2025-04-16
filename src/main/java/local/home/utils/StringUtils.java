package local.home.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StringUtils {

    public boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }
}
