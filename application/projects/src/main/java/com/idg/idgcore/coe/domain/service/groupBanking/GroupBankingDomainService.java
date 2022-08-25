package com.idg.idgcore.coe.domain.service.groupBanking;

import com.idg.idgcore.coe.domain.assembler.groupBanking.GroupBankingAssembler;
import com.idg.idgcore.coe.domain.entity.groupBanking.GroupBankingEntity;
import com.idg.idgcore.coe.domain.repository.groupBanking.IGroupBankingRepository;
import com.idg.idgcore.coe.dto.groupBanking.GroupBankingDTO;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.idg.idgcore.coe.exception.Error.DATA_ACCESS_ERROR;

@Slf4j
@Service
public class GroupBankingDomainService implements IGroupBankingDomainService {
   @Autowired
   private GroupBankingAssembler groupBankingAssembler;

   @Autowired
   private IGroupBankingRepository iGroupBankingRepository;

    public GroupBankingEntity getConfigurationByCode(GroupBankingDTO groupBankingDTO) {
        GroupBankingEntity groupBankingEntity = null;
        try {
            groupBankingEntity = this.iGroupBankingRepository.findByBankGroupCode(groupBankingDTO.getGroupBankingCode());
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return groupBankingEntity;
    }

    public List<GroupBankingEntity> getGroupBanks() {
        return this.iGroupBankingRepository.findAll();
    }

    public GroupBankingEntity getGroupBankByCode(String bankGroupCode) {
        GroupBankingEntity groupBankingEntity = null;
        try {
            groupBankingEntity = this.iGroupBankingRepository.findByBankGroupCode(bankGroupCode);
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return groupBankingEntity;
    }

    public void save(GroupBankingDTO groupBankingDTO) {
        try {
            GroupBankingEntity groupBankingEntity = groupBankingAssembler.convertDtoToEntity(groupBankingDTO);
            this.iGroupBankingRepository.save(groupBankingEntity);
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
    }

}
