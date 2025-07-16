package com.cosmetics.notification.kafka;

import com.cosmetics.kafka.BrandNotification;
import com.cosmetics.kafka.ProductNotification;
import com.cosmetics.notification.email.EmailService;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


import static java.lang.String.format;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationsConsumer {

        private final EmailService emailService;

        @KafkaListener(topics = "brand-topic")
        public void consumeBrandNotifications(BrandNotification brandNotification)
                        throws MessagingException {
                log.info(format("Consuming the message from brand-topic Topic:: %s", brandNotification));
                emailService.sendBrandNotificationEmail(
                                brandNotification.email(),
                                brandNotification.name(),
                                brandNotification.founder(),
                                brandNotification.country());
                log.info("INFO - Brand notification email sent successfully");
        }

        @KafkaListener(topics = "product-topic")
        public void consumeProductNotifications(ProductNotification productNotification)
                        throws MessagingException {
                log.info(format("Consuming the message from product-topic Topic:: %s", productNotification));
                emailService.sendProductNotificationEmail(
                                productNotification.email(),
                                productNotification.name(),
                                productNotification.brandId(),
                                productNotification.price(),
                                productNotification.category(),
                                productNotification.inStock());
                log.info("INFO - Product notification email sent successfully");
        }

}
