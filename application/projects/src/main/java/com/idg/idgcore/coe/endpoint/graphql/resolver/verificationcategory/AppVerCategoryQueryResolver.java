package com.idg.idgcore.coe.endpoint.graphql.resolver.verificationcategory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.verificationcategory.IAppVerCategoryApplicationService;
import com.idg.idgcore.coe.dto.verificationcategory.AppVerCategoryConfigDTO;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Slf4j
@Component
public class AppVerCategoryQueryResolver implements GraphQLQueryResolver{

        @Autowired
        private IAppVerCategoryApplicationService appVerCategoryApplicationService;

        public AppVerCategoryConfigDTO getAppVerCategoryConfigByID(SessionContext sessionContext, AppVerCategoryConfigDTO appVerCategoryConfigDTO)
                throws FatalException, JsonProcessingException {
            return this.appVerCategoryApplicationService.getAppVerCategoryConfigByID(sessionContext, appVerCategoryConfigDTO);
        }

        public List<AppVerCategoryConfigDTO> getAppVerCategoryConfigs(SessionContext sessionContext)
                throws FatalException {
            return this.appVerCategoryApplicationService.getAppVerCategoryConfigs(sessionContext);
        }
}
