package org.example;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
public class Consumer {
    public static void main(String[] args) {
        String bootstrapServers = "172.18.44.50:9092";
        String groupId = "group987548";
        String topic = "dev";

        // create consumer configs
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.setProperty(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG,"1000");
        properties.setProperty(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG,"4000");
        System.out.println("Connecting to "+ bootstrapServers+ "...");



        try (KafkaConsumer<String,String> consumer =new KafkaConsumer<>(properties)) {
            System.out.println("Subscribing to topic: "+ topic + "...");
            consumer.subscribe(Arrays.asList(topic));
            System.out.println("Subscription done.");
            int i = 0;
            while(true){
                ConsumerRecords<String, String> records =
                        consumer.poll(Duration.ofMillis(1));
//
//            if(i % 100 ==0 ){
//                System.out.println("Hi");
//            }
//            i++;
                for (ConsumerRecord<String, String> record : records){
                    System.out.println("Key: " + record.key() + ", Value: " + record.value());
                    System.out.println("Partition: " + record.partition() + ", Offset:" + record.offset());
                }
            }
        }





    }
}