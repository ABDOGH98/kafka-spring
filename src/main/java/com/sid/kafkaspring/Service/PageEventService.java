package com.sid.kafkaspring.Service;

import com.sid.kafkaspring.Entities.PageEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;
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
}
