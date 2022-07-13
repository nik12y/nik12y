package com.idg.idgcore.coe.dto.mutation;

import com.idg.idgcore.coe.dto.base.*;
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
public class MutationDTO extends CoreEngineBaseDTO {
    private PayloadDTO payload;

}
