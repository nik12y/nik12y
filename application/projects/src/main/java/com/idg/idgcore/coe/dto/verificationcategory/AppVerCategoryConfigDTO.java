package com.idg.idgcore.coe.dto.verificationcategory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import lombok.NoArgsConstructor;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.OneToMany;

import java.util.List;

import static com.idg.idgcore.coe.common.Constants.CATEGORY;

@ToString(callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)


public class AppVerCategoryConfigDTO extends CoreEngineBaseDTO {

        private String appVerificationCategoryId;
        private String verificationCategoryDesc;
        private boolean isExternal;


        private List<AppVerTypeConfigDTO> appVerTypeConfig;

        public boolean getIsExternal() {
                return isExternal;
        }


        @Override
        public String getTaskCode () {
            return CATEGORY;
        }

        @Override
        public void setTaskCode(String taskCode)
        {
            super.setTaskCode(CATEGORY);
        }

        @Override
        public String getTaskIdentifier () {
            return this.getAppVerificationCategoryId();
        }

}
