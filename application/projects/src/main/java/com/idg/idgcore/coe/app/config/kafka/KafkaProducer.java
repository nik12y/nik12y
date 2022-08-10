package com.idg.idgcore.coe.app.config.kafka;

import com.idg.idgcore.coe.exception.*;
import com.idg.idgcore.coe.exception.Error;
import org.springframework.beans.factory.annotation.*;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {
    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    public String sendMessage(final String topic, final String key, final String message)
    {
        try {
            kafkaTemplate.send(topic, key, message);
        }
        catch (Exception e) {
            ExceptionUtil.handleException(Error.KAFKA_MESSAGE_PUBLISH_FAILED);
        }
        return "Message published successfully";
    }
}