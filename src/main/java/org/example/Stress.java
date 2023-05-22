package org.example;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import javax.xml.transform.Result;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.apache.kafka.common.utils.Crc32C.compute;

public class Stress {



    static final int numbTesters= 100;


    public static void main(String[] args) throws InterruptedException {



        ExecutorService EXEC = Executors.newCachedThreadPool();
        List<Callable<Integer>> tasks = new ArrayList<Callable<Integer>>();
        for (int i = 0; i< numbTesters; i++) {
            int finalI = i;
            Callable<Integer> c = new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    StressClient tester = new StressClient("dev", finalI);
                    tester.performTest(100);
                    return 0;
                }
            };
            tasks.add(c);
        }
        List<Future<Integer>> results = EXEC.invokeAll(tasks);



    }


}
