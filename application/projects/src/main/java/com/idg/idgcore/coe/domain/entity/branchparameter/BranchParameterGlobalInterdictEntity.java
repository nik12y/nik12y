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
public class BranchParameterGlobalInterdictEntity implements Serializable {
    @Column (name="global_interdict_function_id")
    private String globalInterdictFunctionId;
}
