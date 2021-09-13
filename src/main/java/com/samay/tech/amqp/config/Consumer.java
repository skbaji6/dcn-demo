package com.samay.tech.amqp.config;

import java.util.concurrent.CountDownLatch;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import com.samay.tech.model.Notification;

/**
 * @author Arquitectura
 */
@Component
@Slf4j
public class Consumer {

    private CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(Notification notification) {
        log.info("Received <" + notification + ">");
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }

}