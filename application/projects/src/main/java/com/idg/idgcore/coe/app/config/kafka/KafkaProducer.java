package com.idg.idgcore.coe.app.config.kafka;

import org.springframework.beans.factory.annotation.*;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import java.util.Objects;

@Component
public class KafkaProducer {
	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;
		private static final String DEFAULT_TOPIC = "defaultTopic";


	public String sendMessage(final String topic, final String message)
	{

		if(Objects.nonNull(topic))
		{
			kafkaTemplate.send(topic, message);
		}
		else {
			kafkaTemplate.send(DEFAULT_TOPIC, message);
		}
		return "Message published successfully";
	}
}