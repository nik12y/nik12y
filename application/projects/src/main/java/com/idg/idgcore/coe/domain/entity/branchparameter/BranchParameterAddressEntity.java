package com.idg.idgcore.coe.domain.entity.branchparameter;

import lombok.Setter;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class BranchParameterAddressEntity implements Serializable {
    @Column (name="branch_address1")
    private String branchAddress1;
    @Column (name="branch_address2")
    private String branchAddress2;
    @Column (name="branch_address3")
    private String branchAddress3;
    @Column (name="branch_address4")
    private String branchAddress4;
    @Column (name="country_code")
    private String countryCode;
    @Column (name="state_code")
    private String stateCode;
    @Column (name="city_code")
    private String cityCode;

}
