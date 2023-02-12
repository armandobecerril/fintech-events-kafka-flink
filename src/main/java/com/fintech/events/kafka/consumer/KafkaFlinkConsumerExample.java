package com.fintech.events.kafka.consumer;


import com.fintech.events.kafka.utils.Tuple5SerializationSchema;
import com.fintech.events.kafka.utils.FilterFields;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple5;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer010;
import org.apache.flink.streaming.util.serialization.KeyedSerializationSchemaWrapper;
import org.apache.flink.util.Collector;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.fintech.events.kafka.commons.Commons;

import java.util.Properties;

public class KafkaFlinkConsumerExample {
    private static final Logger logger = LogManager.getLogger(KafkaFlinkConsumerExample.class);

    public static void main(final String... args) {
        // Create execution environment
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // Properties
        final Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, Commons.EXAMPLE_KAFKA_SERVER);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "FlinkConsumerGroup");

        DataStream<String> messageStream = env.addSource(new FlinkKafkaConsumer010<>(Commons.EXAMPLE_KAFKA_TOPIC, new SimpleStringSchema(), props));

        DataStream<Tuple5<String, String, String, Integer, Double>> filteredStream = messageStream.map(new FilterFields());

        filteredStream.print();

        FlinkKafkaProducer010<Tuple5<String, String, String, Integer, Double>> kafkaProducer = new FlinkKafkaProducer010<>(
                Commons.ACASH_KAFKA_TOPIC,
                new KeyedSerializationSchemaWrapper<>(new Tuple5SerializationSchema()),
                props);

        filteredStream.addSink(kafkaProducer);


        // Split up the lines in pairs (2-tuples) containing: (word,1)
       /* messageStream.flatMap(new Tokenizer())
                // group by the tuple field "0" and sum up tuple field "1"
                .keyBy(0)
                .sum(1)
                .print();
*/
        try {
            env.execute();
        } catch (Exception e) {
            logger.error("An error occurred.", e);
        }
    }

    public static final class Tokenizer implements FlatMapFunction<String, Tuple2<String, Integer>> {

        @Override
        public void flatMap(String value, Collector<Tuple2<String, Integer>> out) {
            // normalize and split the line
            String[] tokens = value.toLowerCase().split("\\W+");

            // emit the pairs
            for (String token : tokens) {
                if (token.length() > 0) {
                    out.collect(new Tuple2<>(token, 1));
                }
            }
        }
    }
}
/*
package com.smartfactory.kafka.consumer;

import com.smartfactory.kafka.utils.FilterFields;
import com.smartfactory.kafka.utils.Tuple5SerializationSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.api.java.tuple.Tuple5;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import org.apache.flink.streaming.connectors.kafka.internals.KeyedSerializationSchemaWrapper;
import org.apache.flink.streaming.util.serialization.SimpleStringSchema;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Properties;
import com.smartfactory.kafka.commons.Commons;
import java.nio.charset.StandardCharsets;

public class KafkaFlinkConsumerExample {

    private static final Logger logger = LoggerFactory.getLogger(KafkaFlinkConsumerExample.class);

    public static void main(final String... args) {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, Commons.EXAMPLE_KAFKA_SERVER);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "FlinkConsumerGroup");

        DataStream<String> messageStream = env.addSource(new FlinkKafkaConsumerBase<>(Commons.EXAMPLE_KAFKA_TOPIC, new SimpleStringSchema(), props));

        DataStream<Tuple5<String, String, String, Integer, Double>> filteredStream = messageStream.map(new FilterFields());

        filteredStream.print();

        FlinkKafkaProducerBase<Tuple5<String, String, String, Integer, Double>> kafkaProducer = new FlinkKafkaProducerBase<>(
                "credits_kcash",
                new KeyedSerializationSchemaWrapper<>(new Tuple5SerializationSchema()),
                props);

        filteredStream.addSink(kafkaProducer);

        try {
            env.execute();
        } catch (Exception e) {
            logger.error("An error occurred.", e);
        }
    }
}*/