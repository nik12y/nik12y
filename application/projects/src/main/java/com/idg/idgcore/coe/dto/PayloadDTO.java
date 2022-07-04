package com.idg.idgcore.coe.dto;

import lombok.ToString;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@ToString (callSuper = true)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayloadDTO {
    String data;
}
