package com.fintech.events.kafka.commons;

public class Commons {
    public final static String EXAMPLE_KAFKA_TOPIC = System.getenv("EXAMPLE_KAFKA_TOPIC") != null ?
            System.getenv("EXAMPLE_KAFKA_TOPIC") : "loans";
    public final static String EXAMPLE_KAFKA_SERVER = System.getenv("EXAMPLE_KAFKA_SERVER") != null ?
            System.getenv("EXAMPLE_KAFKA_SERVER") : "localhost:9092";
    public final static String EXAMPLE_ZOOKEEPER_SERVER = System.getenv("EXAMPLE_ZOOKEEPER_SERVER") != null ?
            System.getenv("EXAMPLE_ZOOKEEPER_SERVER") : "localhost:32181";
    public final static String PDM_KAFKA_TOPIC = System.getenv("PDM_KAFKA_TOPIC") != null ?
            System.getenv("PDM_KAFKA_TOPIC") : "topic_smartOp";
    public final static String ACASH_KAFKA_TOPIC = System.getenv("ACASH_KAFKA_TOPIC") != null ?
            System.getenv("ACASH_KAFKA_TOPIC") : "credits_acash";
}
