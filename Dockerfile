FROM openjdk:8u151-jdk-alpine3.7
MAINTAINER ISCIT Mx (contacto@iscit.com.mx)

# Install Bash
RUN apk add --no-cache bash libc6-compat

# Copy resources
WORKDIR /
COPY wait-for-it.sh wait-for-it.sh
COPY target/fintech-events-kafka-flink-1.0-SNAPSHOT-jar-with-dependencies.jar fintech-events-kafka-flink.jar

# Wait for Zookeeper and Kafka to be available and run application
CMD ./wait-for-it.sh -s -t 30 $EXAMPLE_ZOOKEEPER_SERVER -- ./wait-for-it.sh -s -t 30 $EXAMPLE_KAFKA_SERVER -- java -Xmx512m -jar fintech-events-kafka-flink.jar