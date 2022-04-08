package com.maersk.eacloud.kafka;

import com.maersk.kafkautility.annotations.LogEvent;
import com.maersk.kafkautility.service.AzureBlobService;
import com.microsoft.azure.storage.StorageException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.Objects;

@Service
public class KafkaConsumer<T extends Serializable> {

	@Autowired
	AzureBlobService<T> azureBlobService;

	private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

	//@LogEvent
	//@KafkaListener(topics = {"${kafka.notification.topic}"}, containerFactory ="kafkaListenerContainerFactory",
	//id = "eacloudGroup")
	public void listen(ConsumerRecord<String, String> consumerRecord,
					   @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
					   @Header(value = "isLargePayload", required = false) String isLargePayload,
					   Acknowledgment ack) throws URISyntaxException, InvalidKeyException, StorageException {
		logger.info("partition: {}", partition);
		logger.info("largePayload: {}", isLargePayload);
		try {
            if (Objects.nonNull(consumerRecord.value()))
			{
                int num  = 5/0;
				T payload = azureBlobService.readPayloadFromBlob(consumerRecord.value());
				//T payload = azureBlobService.getPayloadFromBlob((T) consumerRecord.value(), isLargePayload);
				logger.info("Kafka payload: {}", payload);
				/*EventNotificationsAdapterModel eventModel = (EventNotificationsAdapterModel) payload;
				var jsonPayload = new JsonConverter().convertToJson(eventModel.getResponse());
				logger.info("JSON payload after conversion: {}", jsonPayload);*/
			}
			ack.acknowledge();
		} catch (Exception e)
		{
			logger.error("Exception while reading Kafka event", e);
			ack.acknowledge();
			//throw e;
		}
	}

	/*@KafkaListener(topics = {"eacloud.DLT"}, id = "DLTGroup")
	public void listenFromDLT(ConsumerRecord<String, String> consumerRecord, Acknowledgment ack)
	{
		logger.info("Inside DLT method");
	}*/

}
