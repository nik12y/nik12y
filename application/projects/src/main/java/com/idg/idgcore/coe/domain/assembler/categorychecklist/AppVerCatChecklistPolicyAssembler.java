package com.idg.idgcore.coe.domain.assembler.categorychecklist;

import com.idg.idgcore.coe.domain.assembler.generic.Assembler;
import com.idg.idgcore.coe.domain.entity.categorychecklist.AppVerCatChecklistPolicyEntity;
import com.idg.idgcore.coe.dto.categorychecklist.AppVerCatChecklistPolicyDTO;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Component
public class AppVerCatChecklistPolicyAssembler extends Assembler<AppVerCatChecklistPolicyDTO, AppVerCatChecklistPolicyEntity> {

    private final DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public Class getSpecificDTOClass() {
        return AppVerCatChecklistPolicyDTO.class;
    }

    @Override
    public Class getSpecificEntityClass() {
        return AppVerCatChecklistPolicyEntity.class;
    }

    @Override
    public AppVerCatChecklistPolicyDTO toDTO(AppVerCatChecklistPolicyEntity appVerCatChecklistPolicyEntity) {
        AppVerCatChecklistPolicyDTO appVerCatChecklistPolicyDTO = super.toDTO(appVerCatChecklistPolicyEntity);
        appVerCatChecklistPolicyDTO.setEffectiveDate(formatter.format(appVerCatChecklistPolicyEntity.getEffectiveDate()));
        return appVerCatChecklistPolicyDTO;
    }
}
