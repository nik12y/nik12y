//package com.idg.idgcore.coe.domain.entity.aml;
package com.idg.idgcore.coe.domain.entity.aml;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class LimitEntity
{
       @Column(name = "limit_code")
        private String limitCode;
        @Column(name = "limit_amount")
        private Float limitAmount;
        @Column(name = "limit_currency")
        private String limitCurrency;

}
