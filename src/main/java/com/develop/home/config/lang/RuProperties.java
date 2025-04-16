package com.develop.home.config.lang;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConditionalOnProperty(value = "app-language", havingValue = "ru", matchIfMissing = true)
@PropertySource(value = "classpath:lang_ru.properties", encoding = "UTF-8")
@Getter
@Setter
@ConfigurationProperties
public class RuProperties extends LanguageProperties {

}
