package com.idg.idgcore.coe.domain.entity.errorOverride;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ErrorOverrideLanguageDetailsEntity implements Serializable {

    private String languageCode;
    private String languageName;
    private String localeCode;
    private String localeName;

}