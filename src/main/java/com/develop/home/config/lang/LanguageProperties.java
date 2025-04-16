package com.develop.home.config.lang;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class LanguageProperties implements LanguageProps {
    private String menu;
    private String unknownCommand;
    private String errorMain;
    private String errorMainMore;
    private String choiceOneHelp;
    private String avroSchemaHasBeenGeneratedSuccess;
    private String choiceTwoHelp;
    private String yesPattern;
    private String noPattern;
    private String sendAvroFileToKafkaConfirm;
    private String onlyYesNoAnswer;
    private String sendMsgKafkaSuccess;
    private String detectedEmptyFileName;
    private String choiceThreeHelp;
    private String createdBinaryFileSuccess;
    private String startCreatingBinaryFileProcess;
    private String enterJsonFile;
    private String enterAvroFile;
    private String choiceFourHelp;
    private String enterBinaryFileName;
    private String startSendingBinaryFileToKafkaProcess;
    private String sendBinaryFileSuccess;
    private String choiceHelp;
    private String easterEggDescr;
    private String choiceFiveHelp;
    private String kafkaProducerLogInfo;
    private String kafkaProducerLogError1;
    private String kafkaProducerLogError2;

}
