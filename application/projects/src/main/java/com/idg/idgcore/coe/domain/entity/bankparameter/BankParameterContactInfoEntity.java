package com.idg.idgcore.coe.domain.entity.bankparameter;

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
public class BankParameterContactInfoEntity implements Serializable {
    @Column (name="telephone_no")
    private String telephoneNo;
    @Column (name="fax_no")
    private String faxNo;
    @Column (name="email_id")
    private String emailId;
    @Column (name="bank_website")
    private String bankWebsite;
}
