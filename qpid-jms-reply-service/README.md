# qpid-jms-reply-service

Simple Reply Service using Qpid JMS and Spring Boot.

## Building and running the porject

Build the project from the top level dir using 'mvn clean install'

Setup you environment variables to point to the host and port of the AMQP broker

    MESSAGING_SERVICE_HOST = "localhost"
    MESSAGING_SERVICE_PORT = 5672

Run the project using the command line:

    linux:   java -jar qpid-jms-reply-service/target/qpid-jms-reply-service-0.1.0-SNAPSHOT.jar

    windows: java -jar qpid-jms-reply-service\target\qpid-jms-reply-service-0.1.0-SNAPSHOT.jar

For now you need to stop it via a **Ctrl+C**