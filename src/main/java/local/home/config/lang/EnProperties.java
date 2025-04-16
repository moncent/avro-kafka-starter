package local.home.config.lang;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConditionalOnProperty(value = "app-language", havingValue = "en", matchIfMissing = false)
@PropertySource(value = "classpath:lang_en.properties", encoding = "UTF-8")
@Getter
@Setter
@ConfigurationProperties
public class EnProperties extends LanguageProperties {

}
