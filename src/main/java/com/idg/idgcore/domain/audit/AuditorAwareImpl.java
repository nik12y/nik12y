package com.idg.idgcore.domain.audit;

import com.idg.idgcore.infra.ThreadAttribute;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor () {
        String userId = null;
        //TODO :- ADD Logger
        userId = (String) ThreadAttribute.get(ThreadAttribute.USER_ID);
        return Optional.of(userId);
    }

}