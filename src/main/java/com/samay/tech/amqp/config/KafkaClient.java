package com.samay.tech.amqp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.samay.tech.events.NotificationEvent;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class KafkaClient {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	@Autowired
	private KafkaTemplate<String, NotificationEvent> notificationKafkaTemplate;

	public void sendMessage(String topicName, String msg) {
		kafkaTemplate.send(topicName, msg);
	}
	
	public void sendMessage(String topicName, NotificationEvent msg) {
		notificationKafkaTemplate.send(topicName, msg);
	}

	public void sendMessageWithCallBack(String topicName, NotificationEvent message) {
		log.info(String.format("#### -> Producing message -> %s", message));
		ListenableFuture<SendResult<String, NotificationEvent>> future = notificationKafkaTemplate.send(topicName, message);

		future.addCallback(new ListenableFutureCallback<SendResult<String, NotificationEvent>>() {

			@Override
			public void onSuccess(SendResult<String, NotificationEvent> result) {
				log.info(
						"Sent message=[" + message + "] with offset=[" + result.getRecordMetadata().offset() + "]");
			}

			@Override
			public void onFailure(Throwable ex) {
				log.info("Unable to send message=[" + message + "] due to : " + ex.getMessage());
			}
		});
	}
	
	
	@KafkaListener(topics = "${message.topic.name}", groupId = "${kafka.consumer.group-id}", containerFactory = "kafkaListenerContainerFactory")
    public void listenGroupFoo(NotificationEvent message) {
		log.info("Received Message in group : " + message);
    }

}
