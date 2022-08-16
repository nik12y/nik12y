package com.idg.idgcore.coe.dto.errorOverride;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorOverrideLanguageDetailsDTO {

    private String languageCode;
    private String languageName;
    private String localeCode;
    private String localeName;

}