package com.fintech.events.kafka.utils;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple5;

public class FilterFields implements MapFunction<String, Tuple5<String, String, String, Integer, Double>> {

    @Override
    public Tuple5<String, String, String, Integer, Double> map(String value) throws Exception {
        String[] values = value.split(",");
        return new Tuple5<>(values[0], values[1], values[2], Integer.parseInt(values[3]), Double.parseDouble(values[4]));
    }
}
