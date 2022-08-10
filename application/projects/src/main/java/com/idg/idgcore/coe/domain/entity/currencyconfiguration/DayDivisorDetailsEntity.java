package com.idg.idgcore.coe.domain.entity.currencyconfiguration;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "day_divisor_meta_info")
public class DayDivisorDetailsEntity {

    @Id
    @Column(name="sr_no")
    private int srNo;

    @Column(name="interest_method")
    private String interestMethod;

}
