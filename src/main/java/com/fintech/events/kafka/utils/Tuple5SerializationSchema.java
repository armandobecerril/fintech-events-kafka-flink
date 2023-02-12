package com.fintech.events.kafka.utils;

import org.apache.flink.api.java.tuple.Tuple5;
import org.apache.flink.streaming.util.serialization.SerializationSchema;

import java.nio.charset.StandardCharsets;

public class Tuple5SerializationSchema implements SerializationSchema<Tuple5<String, String, String, Integer, Double>> {

    @Override
    public byte[] serialize(Tuple5<String, String, String, Integer, Double> element) {
        StringBuilder sb = new StringBuilder();
        sb.append(element.f0).append(",")
                .append(element.f1).append(",")
                .append(element.f2).append(",")
                .append(element.f3).append(",")
                .append(element.f4);
        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }
}
