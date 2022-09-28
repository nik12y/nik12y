package com.idg.idgcore.coe.dto.language;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

import static com.idg.idgcore.coe.common.Constants.LANGUAGE;

@ToString(callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LanguageDTO extends CoreEngineBaseDTO {

    private String languageCode;
    private String languageCodeAlternate;
    private String languageName;
    private String localeCode;
    private String localeName;

    @Override
    public String getTaskCode ()
    {
        return LANGUAGE;
    }

    @Override
    public void setTaskCode (String taskCode) {
        super.setTaskCode(LANGUAGE);
    }

    @Override
    public String getTaskIdentifier ()
    {
        return this.getLanguageCode();
    }


    public static Class<? extends CoreEngineBaseDTO> getSpecificType() {
        return LanguageDTO.class;
    }
}