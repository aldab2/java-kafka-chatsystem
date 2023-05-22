package org.example;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class StressClient {
    KafkaConsumer<String,String> consumer;
    KafkaProducer<String, String> producer;

    String topic;
    String username;

    String servers = "20.4.51.187:9092,165.22.26.150:9092,159.65.113.104:9092,46.101.119.158:9092";



    public StressClient(String topic, int id){


        this.topic = topic;
        this.username = "StressTester_"+ id;
        this.producer = setupProducer(this.username);
        this.consumer = setupConsumer(this.username);


    }
    public void performTest(int numMessages) {
        for (int i = 0; i < numMessages; i++) {
            ProducerRecord<String, String> producerRecord =
                    new ProducerRecord<>(topic, username + ":" + "Test Message#" + i);

            producer.send(producerRecord);
            producer.flush();
            System.out.println("Produced>>" + username + ":" + "Test Message#" + i);
        }
        producer.close();
        consumer.close();
    }

        private KafkaProducer<String, String> setupProducer(String username) {
            // create consumer configs


            // -----
            Properties producerProperties = new Properties();
            producerProperties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
            producerProperties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
            producerProperties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
            KafkaProducer<String, String> producer = new KafkaProducer<>(producerProperties);
            return producer;
            //System.out.println("Connecting to "+ bootstrapServers+ "...");
        }

        private KafkaConsumer<String, String> setupConsumer(String username){
            Properties consumerProperties = new Properties();
            consumerProperties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
            consumerProperties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
            consumerProperties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
            consumerProperties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, username);
            consumerProperties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
            consumerProperties.setProperty(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG,"1500");
            consumerProperties.setProperty(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG,"4000");
            KafkaConsumer<String,String> consumer =new KafkaConsumer<>(consumerProperties);

            return consumer;
        }

}
