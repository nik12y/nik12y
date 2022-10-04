package com.idg.idgcore.coe.domain.assembler.branchSystem;

import com.idg.idgcore.coe.domain.assembler.branchSystemDate.BranchSystemDateAssembler;
import com.idg.idgcore.coe.domain.entity.branchSystemDate.BranchSystemDateEntity;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.dto.branchSystemDate.BranchSystemDateDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith (MockitoExtension.class)
public class BranchSystemAssemblerTest {
    @InjectMocks
    private BranchSystemDateAssembler branchSystemDateAssembler;

    @Test
    @DisplayName ("Should set the authorized field in branchSystemDateAssembler")
    void setAuditFieldsShouldSetAuthorizedFieldInStateDTO () {
        MutationEntity mutationEntity = new MutationEntity();
        mutationEntity.setAuthorized("Y");
        BranchSystemDateDTO branchSystemDateDTO = BranchSystemDateDTO.builder().build();
        branchSystemDateAssembler.setAuditFields(mutationEntity,
                branchSystemDateDTO);
        assertEquals("Y", branchSystemDateDTO.getAuthorized());
    }

    @Test
    @DisplayName ("Should set the authorized field in branchSystemDateDTO")
    void convertEntityToDTO () {
        BranchSystemDateEntity branchSystemDateEntity = new BranchSystemDateEntity();
        branchSystemDateEntity.setBranchCode("BC0002");
        branchSystemDateEntity.setCurrentWorkingDate(getDate("2022-08-06"));
        branchSystemDateEntity.setPreviousWorkingDate(getDate("2022-08-05"));
        branchSystemDateEntity.setNextWorkingDate(getDate("2022-08-07"));
        BranchSystemDateDTO branchSystemDateDTO = branchSystemDateAssembler.toDTO(
                branchSystemDateEntity);
        assertThat (branchSystemDateDTO).isNotNull();
    }

    @Test
    @DisplayName ("Should set the authorized field in branchSystemDateDTO")
    void convertDTOToEntity () {
        BranchSystemDateDTO branchSystemDateDTO = new BranchSystemDateDTO();
        branchSystemDateDTO.setBranchCode("BC0002");
        branchSystemDateDTO.setCurrentWorkingDate("06-08-2022");
        branchSystemDateDTO.setPreviousWorkingDate("10-07-2022");
        branchSystemDateDTO.setNextWorkingDate("15-08-2022");
        BranchSystemDateEntity branchSystemDateEntity = branchSystemDateAssembler.toEntity(
                branchSystemDateDTO);
        assertThat (branchSystemDateEntity).isNotNull();
    }

    private Date getDate (String s) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(s);
        }
        catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

}
