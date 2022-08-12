package com.idg.idgcore.coe.dto.branchparameter;

import lombok.Setter;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BranchParameterContactInfoDTO {
    private String telephoneNo;
    private String faxNo;
    private String emailId;
    private String bankWebsite;

}
