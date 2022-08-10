package com.idg.idgcore.coe.domain.entity.currencyconfiguration;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cut_off_config")
public class CurrencyCutOffDetailsEntity {

    @Id
    @Column(name = "currency_code")
    private String currencyCode;

    @Column(name = "days")
    private short days;

    @Column(name = "hours")
    private short hours;

    @Column(name = "minutes")
    private short minutes;

    @Column(name = "enable_mt_103_remit")
    private boolean enableMt101Remit;

    @Column(name = "enable_mt_103_stp")
    private boolean enableMt103Stp;

    @Column(name = "index_flag")
    private boolean indexFlag;

    @Column(name = "enable_mt_202cov")
    private boolean enableMt202Cov;

    @Column(name = "cls_currency_flag")
    private boolean clsCurrencyFlag;

    @Column(name = "validate_tag_50f_flag")
    private boolean validateTag50fFlag;

    @Column(name = "preferred_holiday_1")
    private String preferredHoliday1;

    @Column(name = "preferred_holiday_2")
    private String preferredHoliday2;

    @Column(name = "preferred_holiday_3")
    private String preferredHoliday3;
}
