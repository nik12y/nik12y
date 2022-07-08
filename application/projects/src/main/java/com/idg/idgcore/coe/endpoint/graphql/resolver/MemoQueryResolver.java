package com.idg.idgcore.coe.endpoint.graphql.resolver;

import com.idg.idgcore.coe.dto.MemoDTO;
import com.idg.idgcore.coe.app.service.IMemoApplicationService;
import com.idg.idgcore.dto.context.SessionContext;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MemoQueryResolver implements GraphQLQueryResolver {
    @Autowired
    private IMemoApplicationService memoApplicationService;

    public MemoDTO getMemo (SessionContext sessionContext, MemoDTO memoDTO){
        return this.memoApplicationService.getMemo(sessionContext, memoDTO);
    }
}
