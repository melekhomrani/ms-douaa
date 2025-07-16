package com.cosmetics.brand.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.cosmetics.kafka.BrandNotification;

import static org.springframework.kafka.support.KafkaHeaders.TOPIC;

@Service
@RequiredArgsConstructor
@Slf4j
public class BrandProducer {

    private final KafkaTemplate<String, BrandNotification> kafkaTemplate;

    public void sendBrandNotification(BrandNotification brandNotification) {
        log.info("Sending brand notification");
        Message<BrandNotification> message = MessageBuilder
                .withPayload(brandNotification)
                .setHeader(TOPIC, "brand-topic")
                .build();

        kafkaTemplate.send(message);
        log.info("Brand notification sent successfully");
    }
}
