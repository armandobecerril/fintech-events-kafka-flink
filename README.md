Fintech Events Kafka streaming with Spark and Flink Demo
===
Example project on how to use [Apache Kafka](https://kafka.apache.org) and streaming consumers:
- Producer sending new loans customers to Kafka  (from CSV)
- Consumer using Kafka to output received messages
- Streaming Consumer using [Apache Flink](https://flink.apache.org) to filter events information 

Requirements
---
- Docker
- Docker Compose
- Java 8
- Maven

Build
---
1. Build Java project
    ```
    mvn clean package
    ```
1. Build Docker image 
    ```
    docker build -t fintech-events-kafka-flink .
    ```

Run
---
1. Start docker containers
    ```
    docker-compose up -d
    ```

Check
---
1. Check producer logs
    ```
    docker logs fintech-events-kafka-flink-kafka-producer-1 -f
    ```
    
    Output should be similar to:
    ```
    [kafka-producer-network-thread | KafkaProducerExample] INFO  com.fintech.events.kafka.producer.KafkaProducerExample - Sent (14194,14047,1,00000100341841,0100341841,RUBEN                                    BOLIVAR,-497491200,1,1152230400,1048,99999,99999,1,154,20,2,1,500,1,,,,,,,,,,,,90560) to topic loans @ 1676140294823.
    ```

1. Check consumer with Flink logs
    ```
    docker logs fintech-events-kafka-flink-kafka-consumer-flink-1 -f
    ```
    
    Output should be similar to:
    ```
   6> (713,702,3,11777,11777.0)
   6> (1295,1273,3,34799,34799.0)
   6> (1813,1784,1,1723190573,1.723190573E9)
   6> (1840,1810,3,49775953,4.9775953E7)
   6> (1995,1962,3,14431116,1.4431116E7)
    ```
1. Check Producer ACash logs
    ```
    docker logs fintech-events-kafka-flink-kafka-consumerACash-flink-1 -f
    ```

   Output should be similar to:
    ```
   6> (713,702,3,11777,11777.0)
   6> (1295,1273,3,34799,34799.0)
   6> (1813,1784,1,1723190573,1.723190573E9)
   6> (1840,1810,3,49775953,4.9775953E7)
   6> (1995,1962,3,14431116,1.4431116E7)
    ```
   
Kafka Web UI
---
Kafka Manager Web UI available at [http://localhost:9000]().


Stop
---
1. Stop docker containers
    ```
    docker-compose down
    ```
