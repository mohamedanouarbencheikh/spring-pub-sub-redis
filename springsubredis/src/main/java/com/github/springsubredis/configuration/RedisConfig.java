package com.github.springsubredis.configuration;


import com.github.springsubredis.subscriber.RedisMessageSubscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.StringRedisSerializer;
/**
 * @author Mohamed Anouar BENCHEIKH
 * @project springredis
 */
@Configuration
@PropertySource({"classpath:application.properties"})
public class RedisConfig {

    @Autowired
    private Environment env;

    @Bean
    public RedisStandaloneConfiguration redisStandaloneConfiguration() {
        RedisStandaloneConfiguration RedisStandaloneConfiguration = new RedisStandaloneConfiguration();
        RedisStandaloneConfiguration.setPassword("anouar");
        RedisStandaloneConfiguration.setHostName(env.getProperty("ip.redis"));
        RedisStandaloneConfiguration.setPort(env.getProperty("port.redis", Integer.class));
        return RedisStandaloneConfiguration;
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(redisStandaloneConfiguration());
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate() {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(redisConnectionFactory());
        stringRedisTemplate.setEnableTransactionSupport(true);
        return stringRedisTemplate;
    }


    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());

        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        // redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());


        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
/*************************************** SUB ***********************************/

    @Bean
    MessageListenerAdapter messageListener() {
       return new MessageListenerAdapter(new RedisMessageSubscriber());
    }

    @Bean
    RedisMessageListenerContainer redisContainer() {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory());
        container.addMessageListener(messageListener(), topic());
        return container;
    }

    @Bean
    ChannelTopic topic() {
        return new ChannelTopic("messageQueue");
    }



}
