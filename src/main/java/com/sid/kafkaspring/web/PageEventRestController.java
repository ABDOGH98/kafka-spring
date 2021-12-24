package com.sid.kafkaspring.web;

import com.sid.kafkaspring.Entities.PageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Random;

@RestController
public class PageEventRestController {

    @Autowired
    private StreamBridge streamBridge;

    @GetMapping("/publish/{topic}/{name}")
    public PageEvent publish(@PathVariable("topic")String topic,@PathVariable("name")String name){
        PageEvent pageEvent = new PageEvent(name,"U1",new Date(),new Random().nextInt(9000)) ;
        streamBridge.send(topic,pageEvent);
        return pageEvent;
    }
}
