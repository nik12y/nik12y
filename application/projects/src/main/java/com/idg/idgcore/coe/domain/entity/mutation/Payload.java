package com.idg.idgcore.coe.domain.entity.mutation;

import lombok.Setter;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import java.io.Serializable;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payload
        implements Serializable
{
    String data;
}
