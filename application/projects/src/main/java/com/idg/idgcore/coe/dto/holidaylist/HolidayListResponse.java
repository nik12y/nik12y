    package com.idg.idgcore.coe.dto.holidaylist;

    import com.idg.idgcore.dto.response.BaseResponseDTO;
    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;

    import java.util.List;


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public class HolidayListResponse extends BaseResponseDTO {
        public List<HolidayListDTO> holidayList;
    }
