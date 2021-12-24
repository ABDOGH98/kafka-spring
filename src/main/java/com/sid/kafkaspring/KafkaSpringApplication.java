package com.sid.kafkaspring;

import com.sid.kafkaspring.Entities.PageEvent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;

import java.util.function.Consumer;

@SpringBootApplication

public class KafkaSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaSpringApplication.class, args);
    }


}
