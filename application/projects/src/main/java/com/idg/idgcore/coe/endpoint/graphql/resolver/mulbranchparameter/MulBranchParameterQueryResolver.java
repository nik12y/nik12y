package com.idg.idgcore.coe.endpoint.graphql.resolver.mulbranchparameter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.appvertype.IAppVerTypeApplicationService;
import com.idg.idgcore.coe.app.service.mulbranchparameter.IMulBranchParameterApplicationService;
import com.idg.idgcore.coe.dto.appvertype.AppVerTypeDTO;
import com.idg.idgcore.coe.dto.mulbranchparameter.MulBranchParameterDTO;
import com.idg.idgcore.coe.dto.questionCategory.QuestionCategoryDTO;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class MulBranchParameterQueryResolver implements GraphQLQueryResolver {

    @Autowired
    IMulBranchParameterApplicationService iMulBranchParameterApplicationService;

    public MulBranchParameterDTO getBranchParameterByCurrencyCodeAndEntityCodeAndEntityType (SessionContext sessionContext, MulBranchParameterDTO mulBranchParameterDTO)
            throws FatalException, JsonProcessingException {
        return this.iMulBranchParameterApplicationService.getByCurrencyCodeAndEntityCodeAndEntityType(sessionContext, mulBranchParameterDTO);
    }

    public List<MulBranchParameterDTO> getMulBranchParameters (SessionContext sessionContext) throws FatalException {
        return this.iMulBranchParameterApplicationService.getMulBranchParameters(sessionContext);
    }
}
