package com.sid.kafkaspring.Service;

import com.sid.kafkaspring.Entities.PageEvent;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
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
        return ()-> new PageEvent("ziza","U1",new Date(),new Random().nextInt(9000));

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
        return (input)->{
            return input.filter((k,v)->v.getDuration()>100)
                    .map((k,v)->new KeyValue<>(v.getName(),0L))
                    .groupByKey(Grouped.keySerde(Serdes.String()))
                    .windowedBy(TimeWindows.of(Duration.ofDays(TimeUnit.DAYS.toMillis(500))))
                    .count()
                    .toStream()
                    .map((k,v)-> new KeyValue<>("=>"+k.window().startTime()+k.window().endTime()+ k.key(),v));
        };
    }
}
