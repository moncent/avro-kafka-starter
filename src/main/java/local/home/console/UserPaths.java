package local.home.console;

import local.home.utils.StringUtils;
import lombok.Getter;


@Getter
public class UserPaths {
    private static final UserPaths INSTANCE = new UserPaths();

    private String avscSchemaPath;
    private String jsonFilePath;

    private UserPaths() {

    }

    public static UserPaths getInstance() {
        return INSTANCE;
    }

    public void reset() {
        setAvscSchemaPath(null);
        setJsonFilePath(null);
    }

    public void setAvscSchemaPath(String avscSchemaPath) {
        if (!StringUtils.isNullOrEmpty(avscSchemaPath) && !avscSchemaPath.matches(".+\\.avsc$")) {
            avscSchemaPath = avscSchemaPath + ".avsc";
        }
        this.avscSchemaPath = avscSchemaPath;
    }

    public void setJsonFilePath(String jsonFilePath) {
        if (!StringUtils.isNullOrEmpty(jsonFilePath) && !jsonFilePath.matches(".+\\.json$")) {
            jsonFilePath = jsonFilePath + ".json";
        }
        this.jsonFilePath = jsonFilePath;
    }
}
