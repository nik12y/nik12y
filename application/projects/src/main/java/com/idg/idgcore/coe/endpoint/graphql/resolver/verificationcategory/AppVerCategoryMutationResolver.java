package com.idg.idgcore.coe.endpoint.graphql.resolver.verificationcategory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.verificationcategory.IAppVerCategoryApplicationService;
import com.idg.idgcore.coe.dto.verificationcategory.AppVerCategoryConfigDTO;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class AppVerCategoryMutationResolver implements GraphQLMutationResolver {
        @Autowired
        private IAppVerCategoryApplicationService appVerCategoryApplicationService;

        public TransactionStatus processAppVerCategoryConfigs(SessionContext sessionContext, AppVerCategoryConfigDTO appVerCategoryConfigDTO)
                throws FatalException, JsonProcessingException {
            return this.appVerCategoryApplicationService.processAppVerCategoryConfigs(sessionContext, appVerCategoryConfigDTO);
        }
}
