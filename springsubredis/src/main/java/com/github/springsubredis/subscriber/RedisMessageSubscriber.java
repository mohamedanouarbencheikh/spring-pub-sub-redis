package com.github.springsubredis.subscriber;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
/**
 * @author Mohamed Anouar BENCHEIKH
 * @project springredis
 */
@Service
public class RedisMessageSubscriber implements MessageListener {

    public static List<String> messageList = new ArrayList<String>();

    @PostConstruct
    public void start(){
        System.out.println("--------------- RedisMessageSubscriber ");
    }

    public void onMessage(final Message message, final byte[] pattern) {
        messageList.add(message.toString());
        System.out.println("Message received: " + new String(message.getBody()));
    }
}