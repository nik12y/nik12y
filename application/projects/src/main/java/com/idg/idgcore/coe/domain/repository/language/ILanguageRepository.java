package com.idg.idgcore.coe.domain.repository.language;

import com.idg.idgcore.coe.domain.entity.language.LanguageEntity;
import com.idg.idgcore.coe.domain.entity.language.LanguageEntityKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILanguageRepository extends JpaRepository<LanguageEntity, LanguageEntityKey> {
    LanguageEntity findByLanguageCode(String languageCode);

}
