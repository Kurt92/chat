package com.jm.chat.framework.kafka.conf;

import com.jm.chat.biz.dto.ChatDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConf {

    // application.yml 에서 불러온 bootstrap 서버 정보
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public DefaultKafkaConsumerFactory<String, ChatDto.Request.ChatMsg> consumerFactory() {
        Map<String,Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "chat-group");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                // DTO 역직렬화용 JsonDeserializer
                new JsonDeserializer<>(ChatDto.Request.ChatMsg.class, false).trustedPackages("com.jm.chat.biz.dto")
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ChatDto.Request.ChatMsg> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ChatDto.Request.ChatMsg> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());

        // concurrency 코드로 관리
        factory.setConcurrency(3);

        return factory;
    }
}
