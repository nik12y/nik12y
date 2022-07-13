package com.idg.idgcore.coe.dto.audit;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.ToString;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;

@ToString (callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonInclude (JsonInclude.Include.NON_NULL)
public class AuditHistoryDTO
{
    private String taskCode;
    private String taskIdentifier;
    private Integer recordVersion;
    private String referenceNo;
}
