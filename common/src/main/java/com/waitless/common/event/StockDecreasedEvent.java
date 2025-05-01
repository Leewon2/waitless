package com.waitless.common.event;

import com.waitless.common.dto.StockDto;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StockDecreasedEvent {

    private UUID reservationId;
    private List<StockDto> stockDtoList;

}
