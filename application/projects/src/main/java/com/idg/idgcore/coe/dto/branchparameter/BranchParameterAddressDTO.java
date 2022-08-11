package com.idg.idgcore.coe.dto.branchparameter;

import lombok.Setter;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BranchParameterAddressDTO {
    private String branchAddress1;
    private String branchAddress2;
    private String branchAddress3;
    private String branchAddress4;
    private String country;
    private String state;
    private String city;


}
