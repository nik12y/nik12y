package com.idg.idgcore.coe.domain.service.groupBanking;

import com.idg.idgcore.coe.domain.assembler.groupBanking.GroupBankingAssembler;
import com.idg.idgcore.coe.domain.entity.groupBanking.GroupBankingEntity;
import com.idg.idgcore.coe.domain.repository.groupBanking.IGroupBankingRepository;
import com.idg.idgcore.coe.domain.service.generic.DomainService;
import com.idg.idgcore.coe.dto.groupBanking.GroupBankingDTO;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.idg.idgcore.coe.exception.Error.DATA_ACCESS_ERROR;

@Slf4j
@Service
public class GroupBankingDomainService extends DomainService<GroupBankingDTO, GroupBankingEntity> {

   @Autowired
   private GroupBankingAssembler groupBankingAssembler;

   @Autowired
   private IGroupBankingRepository iGroupBankingRepository;

    @Override
    public GroupBankingEntity getEntityByIdentifier(String bankGroupCode) {
        GroupBankingEntity groupBankingEntity = null;
        try {
            groupBankingEntity = this.iGroupBankingRepository.findByBankGroupCode(bankGroupCode);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return groupBankingEntity;
    }

    @Override
    public List<GroupBankingEntity> getAllEntities() {
        return this.iGroupBankingRepository.findAll();
    }

    public void save(GroupBankingDTO groupBankingDTO) {
        try {
            GroupBankingEntity groupBankingEntity = groupBankingAssembler.toEntity(groupBankingDTO);
            this.iGroupBankingRepository.save(groupBankingEntity);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
    }
}
