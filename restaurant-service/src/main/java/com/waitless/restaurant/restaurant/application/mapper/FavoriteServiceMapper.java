package com.waitless.restaurant.restaurant.application.mapper;

import com.waitless.restaurant.restaurant.application.dto.FavoriteResponseDto;
import com.waitless.restaurant.restaurant.domain.entity.Favorite;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FavoriteServiceMapper {

    @Mapping(source = "restaurant.name", target = "restaurantName")
    @Mapping(source = "restaurant.id", target = "restaurantId")
    FavoriteResponseDto toResponseDto(Favorite favorite);

    List<FavoriteResponseDto> toResponseDtoList(List<Favorite> favoritePage);

    default Page<FavoriteResponseDto> toResponseDtoPage(Page<Favorite> entityPage) {
        return new PageImpl<>(
            toResponseDtoList(entityPage.getContent()),  
            entityPage.getPageable(),
            entityPage.getTotalElements()
        );
    }

}
