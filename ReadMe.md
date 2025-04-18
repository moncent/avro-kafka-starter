# AvroKafka


| Version | Status                                                                                                                                                                                                                                                                                                                                                                                              |
| ------- |-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| develop | [![Java CI with Maven](https://github.com/moncent/avro-kafka-starter/actions/workflows/maven.yml/badge.svg?branch=develop)](https://github.com/moncent/avro-kafka-starter/actions/workflows/maven.yml) <br/> [![CodeQL Advanced](https://github.com/moncent/avro-kafka-starter/actions/workflows/codeql.yml/badge.svg)](https://github.com/moncent/avro-kafka-starter/actions/workflows/codeql.yml) |

---
Программа предназначена для отправки своих сообщений в бинарном формате avro в топик кафки.

Системные требования:
1) Java (JRE/JDK) 17 версии и выше

Вы можете собрать самостоятельно программу, используя maven:

mvn clean compile assembly:single

Запуск программы в консоли (CLI):
- на Windows: запустить RU_windows_run.bat или EN_windows_run.bat (русская/английская версия)
- на Linux/MacOS: запустить RU_linux_run.sh или EN_linux_run.sh (русская/английская версия)

---
The program is developed for sending your messages in binary avro format to kafka topics.

System Requirements:
1) Java (JRE/JDK) 17 version and higher

You can build the program yourself using maven:

mvn clean compile assembly:single

Launch the program in the console (CLI):
- on Windows: launch RU_windows_run.bat or EN_windows_run.bat (russian/english version)
- on Linux/MacOS: launch RU_linux_run.sh or EN_linux_run.sh (russian/english version)