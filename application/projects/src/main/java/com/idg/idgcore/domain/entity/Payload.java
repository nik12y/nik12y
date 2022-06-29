package com.idg.idgcore.domain.entity;

import lombok.*;

import java.io.*;

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
