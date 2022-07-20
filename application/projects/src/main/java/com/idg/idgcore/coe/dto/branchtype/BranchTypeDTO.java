package com.idg.idgcore.coe.dto.branchtype;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

import static com.idg.idgcore.coe.common.Constants.BRANCHTYPE;

@ToString(callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class BranchTypeDTO extends CoreEngineBaseDTO {



        private String BranchTypeCode;
        private String BranchTypeName;


        @Override
        public String getTaskCode () {
            return BRANCHTYPE;
        }

        @Override
        public String getTaskIdentifier () {
            return this.getBranchTypeCode();
        }

    }

