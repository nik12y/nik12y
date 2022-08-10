package com.idg.idgcore.coe.domain.entity.currencyconfiguration;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "currency_symb_config")
public class CurrencySymbolDetailsEntity {

    @Id
    @Column(name = "currency_code")
    private String currencyCode;

    @Column(name = "before_formatting")
    private boolean beforeFormattingFlag;

    @Column(name = "after_formatting")
    private boolean afterFormattingFlag;

    @Column(name = "include_space_formatting")
    private boolean includeSpaceFormattingFlag;

}
