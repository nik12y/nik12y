package com.idg.idgcore.coe.dto;

import lombok.ToString;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@ToString (callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties (ignoreUnknown = true)
public class MutationDTO extends CoreEngineBaseDTO {
    private String taskCode;
    private String taskIdentifier;
    private PayloadDTO payload;
}
