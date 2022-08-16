package com.idg.idgcore.coe.domain.entity.errorOverride;

import lombok.*;

import javax.persistence.*;
import java.io.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ErrorOverrideConversionsEntity implements Serializable {

    private String branchCode;
    private String functionCodeOverride;
    private String newErrorCode;

}