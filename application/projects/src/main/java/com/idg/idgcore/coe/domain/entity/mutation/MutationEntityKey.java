package com.idg.idgcore.coe.domain.entity.mutation;

import com.idg.idgcore.domain.*;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import java.io.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "IDGC_COE_MUTATION_STAGE_CNFG")
@ToString
public class MutationEntityKey  extends AbstractDomainKey
    implements Serializable
{
    @Id
    private String taskIdentifier;
    @Id
    private String taskCode;


    @Override
    public String keyAsString() {
        return taskIdentifier+taskCode;
    }

}