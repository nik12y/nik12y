package com.idg.idgcore.coe.domain.repository.aml;

import com.idg.idgcore.coe.domain.entity.aml.AmlEntity;
import com.idg.idgcore.coe.domain.entity.aml.AmlEntityKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAmlRepository  extends JpaRepository<AmlEntity, AmlEntityKey> {

    AmlEntity findByProductCategory(String productCategory);
}
