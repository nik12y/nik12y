package com.idg.idgcore.coe.app.config.kafka;

import com.idg.idgcore.dto.context.SessionContext;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BaseKafkaMessage {
    SessionContext sessionContext;
    String payload;
    String key;
}
