package com.idg.idgcore.app.dto;

import lombok.Data;
import lombok.ToString;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

//@Data
//@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class AuditableDTO {
    private String createdBy;
    private Date creationTime;
    private String lastUpdatedBy;
    private Date lastUpdatedTime;
}
