package com.maersk.eacloud.kafka;/*
package com.maersk.eacloud.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.TopicSuffixingStrategy;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
public class DltConsumer {

    @RetryableTopic(attempts = "4", autoCreateTopics = "false")
    @KafkaListener(topics = {"${kafka.notification.topic}"}, containerFactory ="kafkaListenerContainerFactory")
    public void listen(ConsumerRecord<String, String> consumerRecord,
                       @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, Acknowledgment ack)
    {
        log.info("topic: {}", topic);
        try {
            if (Objects.nonNull(consumerRecord.value()))
            {
                log.info("consumerRecord: {}", consumerRecord.value());
            }
            ack.acknowledge();
        } catch (Exception e)
        {
            log.error("Exception while reading Kafka event", e);
            ack.acknowledge();
        }
    }

    @DltHandler
    public void errorHandler(@Header(KafkaHeaders.RECEIVED_TOPIC) String topic, Acknowledgment ack)
    {
        log.info("Inside DLT method");
    }
}
*/
