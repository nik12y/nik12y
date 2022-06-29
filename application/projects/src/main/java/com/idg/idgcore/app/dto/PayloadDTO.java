package com.idg.idgcore.app.dto;

import lombok.Data;
import lombok.ToString;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@ToString (callSuper = true)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayloadDTO {
    String data;
}
