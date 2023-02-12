package com.fintech.events.kafka.consumer;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import com.fintech.events.kafka.commons.Commons;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class KafkaConsumerACashExample {
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerACashExample.class);

    public static void main(final String... args) {
        // Create execution environment
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // Properties
        final Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, Commons.EXAMPLE_KAFKA_SERVER);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "ACashConsumerGroup");

        DataStream<String> acashStream = env.addSource(new FlinkKafkaConsumer010<>(
                Commons.ACASH_KAFKA_TOPIC, new SimpleStringSchema(), props));

        acashStream.print();

        try {
            env.execute();
        } catch (Exception e) {
            logger.error("An error occurred.", e);
        }
    }
}
