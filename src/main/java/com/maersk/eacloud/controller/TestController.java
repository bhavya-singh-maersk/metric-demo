package com.maersk.eacloud.controller;

import com.maersk.eacloud.kafka.KafkaProducer;
import com.maersk.eacloud.kafka.TestConfiguration;
import com.maersk.kafkautility.annotations.CounterMetric;
import com.maersk.kafkautility.service.AzureBlobService;
import com.microsoft.azure.storage.StorageException;
import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import net.apmoller.ohm.adapter.avro.model.EventNotificationsAdapterModel;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.Collections;
import java.util.UUID;

@Slf4j
@RestController
public class TestController<T extends Serializable> {

    @Autowired
    KafkaProducer producer;

    @Autowired
    AzureBlobService <T> azureBlobService;

    @Autowired
    TestConfiguration testConfiguration;

    @PostMapping(value = "/send/kafka/message")
    public String sendKafkaMessage(@RequestBody String message) throws URISyntaxException, InvalidKeyException, StorageException {
       // producer.sendMessage(message, "corId");
        //producer.publishMessageToKafka(message, "corId");
        log.info("Valid JDK: {}", TestConfiguration.isJDK7OrHigher());
        return "Success";
        //return new ResponseEntity<>(eventsList, HttpStatus.OK);
    }

    @PostMapping(value = "/send/avro/message")
    public String postAvroMessage(@RequestBody String message, @RequestParam String corId) throws JSONException, URISyntaxException, InvalidKeyException, StorageException {
        JSONObject response = new JSONObject(message);
        EventNotificationsAdapterModel avro= EventNotificationsAdapterModel.newBuilder()
                .setResponse(response.get("response").toString())
                .setCorrelationId("corId").setMessageType("xml").setMessageId("dfdf-dfd")
                .setSourceSystem("docbroker").setResponseConsumers(Collections.emptyList()).build();
        producer.sendMessage(avro, corId);
       // producer.sendMessageToKafka(avro, "corId");
        return "Success";
    }

    @PostMapping(value = "/send/json")
    public String postJsonMessage(@RequestBody String message) throws URISyntaxException, InvalidKeyException, StorageException {
        producer.publishMessageToKafka(message, UUID.randomUUID().toString());
        return "Success";
    }

    @PostMapping(value = "/send/avro")
    public String postAvroMessage(@RequestBody String message) throws URISyntaxException, InvalidKeyException, StorageException {
        JSONObject response = new JSONObject(message);
        EventNotificationsAdapterModel avro= EventNotificationsAdapterModel.newBuilder()
                .setResponse(response.get("response").toString())
                .setCorrelationId("corId").setMessageType("xml").setMessageId("dfdf-dfd")
                .setSourceSystem("docbroker").setResponseConsumers(Collections.emptyList()).build();
        producer.sendMessageToKafka(avro, UUID.randomUUID().toString());
        return "Success";
    }

    //@Timed(value = "Ping", description = "Execution Time")
    @CounterMetric
    @GetMapping(value = "/ping")
    public String ping()
    {
        return "Ping successful";
    }
}
