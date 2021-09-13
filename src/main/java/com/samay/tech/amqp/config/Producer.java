package com.samay.tech.amqp.config;

import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import com.samay.tech.events.NotificationEvent;
import com.samay.tech.model.Notification;

/**
 * @author Arquitectura
 */
@Service
@Slf4j
public class Producer implements ApplicationListener<NotificationEvent> {

    private final RabbitTemplate rabbitTemplate;
    private final Consumer receiver;

    public Producer(RabbitTemplate rabbitTemplate, Consumer receiver) {
        this.rabbitTemplate = rabbitTemplate;
        this.receiver = receiver;
    }

    public void sendMessage(Notification notification) throws InterruptedException {
        log.info(String.format("#### -> Producing message -> %s", notification));
        rabbitTemplate.convertAndSend(AmqpConfig.topicExchangeName, "foo.bar.baz", notification);
        receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
    }

    @Override
    public void onApplicationEvent(NotificationEvent notificationEvent) {
        try {
            sendMessage(notificationEvent.getNotification());
        } catch (InterruptedException e) {
            log.error("Problem with send notification", e);
        }
    }
}