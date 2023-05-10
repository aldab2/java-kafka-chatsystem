package org.example;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class Producer {

    public static void main(String[] args) {


        String bootstrapServers = "172.18.44.50:9092";

        // create Producer producerProperties
        Properties producerProperties = new Properties();
        producerProperties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        producerProperties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProperties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<>(producerProperties);

        ProducerRecord<String, String> producerRecord =
                new ProducerRecord<>("dev", "hello world");

        // send data - asynchronous
        producer.send(producerRecord);

        // flush data - synchronous
        producer.flush();
        // flush and close producer
        producer.close();

    }
}
