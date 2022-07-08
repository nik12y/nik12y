package com.idg.idgcore.coe.dto;

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
public class MemoDTO extends AuditableDTO{
    private String moduleId;
    private String taskCode;
    private String taskIdentifier;
    private String referenceNo;
    private Integer recordVersion;
    private String status;
    private String comments;
}
