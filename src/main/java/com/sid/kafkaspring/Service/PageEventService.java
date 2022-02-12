package com.sid.kafkaspring.Service;

import com.sid.kafkaspring.Entities.PageEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Service
public class PageEventService {

    @KafkaListener(topics = "test" , groupId = "group-ms")
    public void onMessage(ConsumerRecord<String,String> message){
        System.out.println("**********************");
        System.out.println(message);
        System.out.println("**********************");
    }

    @Bean
    public Consumer<PageEvent> pageEventConsumer(){
        return (input)->{
            System.out.println("********************");
            System.out.println(input.toString());
            System.out.println("********************");
        };
    }
    @Bean
    public Supplier<PageEvent> pageEventSupplier(){
        return ()-> new PageEvent(Math.random()>0.5?"P1":"P2",Math.random()>0.5?"U1":"U2",new Date(),new Random().nextInt(9000));

    }
    @Bean
    public Function<PageEvent,PageEvent> pageEventFunction(){
        return (input)->{
            input.setName("abdogh");
            input.setUser("FAR");
            return input ;
        };
    }
    @Bean
    public Function<KStream<String, PageEvent>,KStream<String,Long>> kStreamFunction(){
        return (input)-> input.filter((k, v)->v.getDuration()>5000)
               .map((k,v)->new KeyValue<>(v.getName(),0L))
               .groupBy((k,v)->k,Grouped.with(Serdes.String(),Serdes.Long()))
                .windowedBy(TimeWindows.of(Duration.ofMillis(5000)))
               .count(Materialized.as("page-count"))
               .toStream()
                .map((k,v)->new KeyValue<>(k.key(),v));
    }
}
