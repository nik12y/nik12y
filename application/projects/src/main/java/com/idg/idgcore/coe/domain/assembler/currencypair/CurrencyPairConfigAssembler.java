//package com.idg.idgcore.coe.domain.assembler.currencypair;
//
//import com.idg.idgcore.coe.domain.entity.currencypair.CurrencyPairConfigEntity;
//import com.idg.idgcore.coe.domain.entity.currencypair.VirtualConfigEntity;
//import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
//
//import com.idg.idgcore.coe.dto.currencypair.CurrencyPairConfigDTO;
//import com.idg.idgcore.coe.dto.currencypair.VirtualConfigDTO;
//import org.modelmapper.ModelMapper;
//import org.modelmapper.TypeToken;
//import org.modelmapper.convention.MatchingStrategies;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.lang.reflect.Type;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import static com.idg.idgcore.coe.common.Constants.CHAR_N;
//import static com.idg.idgcore.coe.common.Constants.CHAR_Y;
//
//@Component
//public class CurrencyPairConfigAssembler {
//
//    private ModelMapper modelMapper = new ModelMapper();
//
//    @PostConstruct
//    private void setMapperConfig() {
//        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
//        modelMapper.getConfiguration().setAmbiguityIgnored(true);
//    }
//
////    public CurrencyPairConfigDTO convertEntityToDto (CurrencyPairConfigEntity currencyPairConfigEntity) {
////        CurrencyPairConfigDTO currencyPairConfigDTO = modelMapper.map(currencyPairConfigEntity, CurrencyPairConfigDTO.class);
////        currencyPairConfigDTO.setId(currencyPairConfigEntity.getId());
////        currencyPairConfigDTO.setPairId(currencyPairConfigEntity.getPairId());
////        currencyPairConfigDTO.setVirtualConfigEntity(currencyPairConfigEntity.getVirtualConfigEntity());
////        return currencyPairConfigDTO;
////    }
////
////    public CurrencyPairConfigEntity convertDtoToEntity(CurrencyPairConfigDTO currencyPairConfigDTO) {
////        List<VirtualConfigDTO> virtualConfigDTO = currencyPairConfigDTO.getVirtualConfig();
////        List<VirtualConfigEntity> virtualConfigEntityList = new ArrayList<>();
////        virtualConfigEntityList.addAll(virtualConfigDTO.stream().map(entity->{
////            VirtualConfigEntity virtualConfigEntity1=null;
////            virtualConfigEntity1 = modelMapper.map(entity,VirtualConfigEntity.class);
////            return virtualConfigEntity1;
////        }).collect(Collectors.toList()));
////
////        CurrencyPairConfigEntity currencyPairConfigEntity = modelMapper.map(currencyPairConfigDTO, CurrencyPairConfigEntity.class);
////        currencyPairConfigEntity.setVirtualConfigEntity(virtualConfigEntityList);
////        return currencyPairConfigEntity;
////    }
//
//    public CurrencyPairConfigEntity convertDtoToEntity(CurrencyPairConfigDTO currencyPairConfigDTO) {
//
//        List<VirtualConfigDTO> virtualConfigDTOList = currencyPairConfigDTO.getVirtualConfigDTO();
//        Type listType = new TypeToken<List<VirtualConfigEntity>>(){}.getType();
//        List<VirtualConfigEntity> virtualConfigEntityList = modelMapper.map(virtualConfigDTOList,listType);
//        CurrencyPairConfigEntity currencyPairConfigEntity = modelMapper.map(currencyPairConfigDTO, CurrencyPairConfigEntity.class);
//        currencyPairConfigEntity.setVirtualConfigEntity(virtualConfigEntityList);
//        return currencyPairConfigEntity;
//    }
//
//    public CurrencyPairConfigDTO convertEntityToDto(CurrencyPairConfigEntity currencyPairConfigEntity) {
//
//        List<VirtualConfigEntity> virtualConfigEntityList = currencyPairConfigEntity.getVirtualConfigEntity();
//        Type listType = new TypeToken<List<VirtualConfigDTO>>(){}.getType();
//        List<VirtualConfigDTO> virtualConfigDTOList = modelMapper.map(virtualConfigEntityList,listType);
//        CurrencyPairConfigDTO currencyPairConfigDTO = modelMapper.map(currencyPairConfigEntity, CurrencyPairConfigDTO.class);
//        currencyPairConfigDTO.setVirtualConfigDTO(virtualConfigDTOList);
//        return currencyPairConfigDTO;
//    }
//
//    public CurrencyPairConfigDTO setAuditFields (MutationEntity mutationEntity, CurrencyPairConfigDTO currencyPairConfigDTO) {
//        currencyPairConfigDTO.setAction(mutationEntity.getAction());
//        currencyPairConfigDTO.setAuthorized(mutationEntity.getAuthorized());
//        currencyPairConfigDTO.setRecordVersion(mutationEntity.getRecordVersion());
//        currencyPairConfigDTO.setStatus(mutationEntity.getStatus());
//        currencyPairConfigDTO.setLastConfigurationAction(mutationEntity.getLastConfigurationAction());
//        currencyPairConfigDTO.setCreatedBy(mutationEntity.getCreatedBy());
//        currencyPairConfigDTO.setCreationTime(mutationEntity.getCreationTime());
//        currencyPairConfigDTO.setLastUpdatedBy(mutationEntity.getLastUpdatedBy());
//        currencyPairConfigDTO.setLastUpdatedTime(mutationEntity.getLastUpdatedTime());
//        currencyPairConfigDTO.setTaskCode(mutationEntity.getTaskCode());
//        currencyPairConfigDTO.setTaskIdentifier(mutationEntity.getTaskIdentifier());
//        return currencyPairConfigDTO;
//    }
//
//    public char getCharValueFromBoolean (boolean value) {
//        return value ? CHAR_Y : CHAR_N;
//    }
//
//    public boolean getBooleanValueFromChar (Character value) {
//        return value.equals(CHAR_Y);
//    }
//}