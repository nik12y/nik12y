package com.idg.idgcore.coe.domain.entity.verificationcategory;

import com.idg.idgcore.domain.AbstractDomainKey;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity

@Inheritance(strategy = InheritanceType.JOINED)
@ToString

public class AppVerCategoryConfigEntityKey extends AbstractDomainKey
        implements Serializable{
        @Id
        private String appVerificationCategoryId;

        @Override
        public String keyAsString() {
            return appVerificationCategoryId;
        }

}
