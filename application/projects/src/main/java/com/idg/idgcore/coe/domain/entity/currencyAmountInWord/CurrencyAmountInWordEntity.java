package com.idg.idgcore.coe.domain.entity.currencyAmountInWord;

import com.idg.idgcore.domain.*;
import lombok.*;

import javax.persistence.*;
import java.io.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "idgc_mcy_cnfg_amt_in_words")
@Inheritance(strategy = InheritanceType.JOINED)
public class CurrencyAmountInWordEntity extends AbstractAuditableDomainEntity
        implements Serializable
{

    @Id
    @Column(name = "currency_code")
    private String currencyCode;

    @Column(name = "language_code")
    private String languageCode;

    @Column(name = "language_name")
    private String languageName;

    @Column(name = "locale_code")
    private String localeCode;

    @Column(name = "locale_name")
    private String localeName;

    @Column(name = "before_decimal_verbose")
    private String beforeDecimalVerbose;

    @Column(name = "after_decimal_verbose")
    private String afterDecimalVerbose;

    @Column(name = "suffix_text")
    private String suffixText;

    @Column(name = "prefix_text_currency")
    private String prefixTextWithCurrency;

    @Column(name = "fraction")
    private char fraction;

    @Column(name = "text_between")
    private String textBetween;

    @Column(name = "amount_in_figure")
    private int amountInFigures;

    @Column(name = "amount_in_word")
    private String amountInWords;

    @Column(name="life_cycle_id")
    private String lifeCycleId;

    @Column (name="reference_no")
    private String referenceNo;

    @Column (name="record_status")
    private String status;

    @Column (name="record_version")
    private Integer recordVersion;

    @Column (name="is_authorized")
    private String authorized;

    @Column (name="last_configuration_action")
    private String lastConfigurationAction;


}
