# AvroKafka


| Version                | Status                                                                                                                                                                                                                                                                                                                                                                                                                                            |
|------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| develop                | [![Java CI with Maven](https://github.com/moncent/avro-kafka-starter/actions/workflows/maven.yml/badge.svg?branch=develop)](https://github.com/moncent/avro-kafka-starter/actions/workflows/maven.yml) <br/> [![CodeQL Advanced](https://github.com/moncent/avro-kafka-starter/actions/workflows/codeql.yml/badge.svg)](https://github.com/moncent/avro-kafka-starter/actions/workflows/codeql.yml)                                               |
| releases/release-1.0.0 | [![Java CI with Maven](https://github.com/moncent/avro-kafka-starter/actions/workflows/maven.yml/badge.svg?branch=releases%2Frelease-1.0.0)](https://github.com/moncent/avro-kafka-starter/actions/workflows/maven.yml) <br/> [![CodeQL Advanced](https://github.com/moncent/avro-kafka-starter/actions/workflows/codeql.yml/badge.svg?branch=releases%2Frelease-1.0.0)](https://github.com/moncent/avro-kafka-starter/actions/workflows/codeql.yml) |
| releases/release-1.1.0 | [![Java CI with Maven](https://github.com/moncent/avro-kafka-starter/actions/workflows/maven.yml/badge.svg?branch=releases%2Frelease-1.1.0)](https://github.com/moncent/avro-kafka-starter/actions/workflows/maven.yml) <br/> [![CodeQL Advanced](https://github.com/moncent/avro-kafka-starter/actions/workflows/codeql.yml/badge.svg?branch=releases%2Frelease-1.1.0)](https://github.com/moncent/avro-kafka-starter/actions/workflows/codeql.yml) |
| releases/release-1.2.0 | [![Java CI with Maven](https://github.com/moncent/avro-kafka-starter/actions/workflows/maven.yml/badge.svg?branch=releases%2Frelease-1.2.0)](https://github.com/moncent/avro-kafka-starter/actions/workflows/maven.yml) <br/> [![CodeQL Advanced](https://github.com/moncent/avro-kafka-starter/actions/workflows/codeql.yml/badge.svg?branch=releases%2Frelease-1.2.0)](https://github.com/moncent/avro-kafka-starter/actions/workflows/codeql.yml) |
| releases/release-1.3.0 | [![Java CI with Maven](https://github.com/moncent/avro-kafka-starter/actions/workflows/maven.yml/badge.svg?branch=releases%2Frelease-1.3.0)](https://github.com/moncent/avro-kafka-starter/actions/workflows/maven.yml) <br/> [![CodeQL Advanced](https://github.com/moncent/avro-kafka-starter/actions/workflows/codeql.yml/badge.svg?branch=releases%2Frelease-1.3.0)](https://github.com/moncent/avro-kafka-starter/actions/workflows/codeql.yml) |

Allure  https://moncent.github.io/avro-kafka-starter/

---
Программа предназначена для отправки своих сообщений в бинарном формате avro в топик кафки.

Системные требования:
1) Java (JRE/JDK) 17 версии и выше

Запуск программы в консоли (CLI):
- на Windows: запустить RU_windows_run.bat или EN_windows_run.bat (русская/английская версия)
- на Linux/MacOS: запустить RU_linux_run.sh или EN_linux_run.sh (русская/английская версия)

Запуск программы без взаимодействия с консолью:

ВНИМАНИЕ! В скриптах проверьте правильность заполнения аргументов -Dconsole.manual-boot.avsc-schema-path=test_schema.avsc и -Dconsole.manual-boot.json-file-path=test.json
- на Windows:

   1) Чтобы сгенерировать json, запустите RU_AUTO_windows_run_1_generate_json.bat или EN_AUTO_windows_run_1_generate_json.bat (русская/английская версия)
   
   2) Чтобы отправить сообщение в формате авро в кафку, запустите RU_AUTO_windows_run_2_send_kafka.bat или EN_AUTO_windows_run_2_send_kafka.bat (русская/английская версия)
 
- на Linux/MacOS:

  1) Чтобы сгенерировать json, запустите RU_AUTO_linux_run_1_generate_json.sh или EN_AUTO_linux_run_1_generate_json.sh (русская/английская версия)
   
  2) Чтобы отправить сообщение в формате авро в кафку, запустите RU_AUTO_linux_run_2_send_kafka.sh или EN_AUTO_linux_run_2_send_kafka.sh (русская/английская версия)
---
The program is developed for sending your messages in binary avro format to kafka topics.

System Requirements:
1) Java (JRE/JDK) 17 version and higher

Launch the program in the console (CLI):
- on Windows: launch RU_windows_run.bat or EN_windows_run.bat (russian/english version)
- on Linux/MacOS: launch RU_linux_run.sh or EN_linux_run.sh (russian/english version)

Launching a program without console interaction:

ATTENTION! Check arguments in scripts -Dconsole.manual-boot.avsc-schema-path=test_schema.avsc and -Dconsole.manual-boot.json-file-path=test.json
- on Windows:

    1) To generate json, run RU_AUTO_windows_run_1_generate_json.bat or EN_AUTO_windows_run_1_generate_json.bat (russian/english version)

    2) To send avro message to Kafka, run RU_AUTO_windows_run_2_send_kafka.bat or EN_AUTO_windows_run_2_send_kafka.bat (russian/english version)

- on Linux/MacOS:

    1) To generate json, run RU_AUTO_linux_run_1_generate_json.sh or EN_AUTO_linux_run_1_generate_json.sh (russian/english version)

    2) To send avro message to Kafka, run RU_AUTO_linux_run_2_send_kafka.sh or EN_AUTO_linux_run_2_send_kafka.sh (russian/english version)