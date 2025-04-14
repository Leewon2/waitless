package com.waitless.restaurant.restaurant.presentation.controller;


import com.waitless.common.dto.RestaurantStockResponseDto;
import com.waitless.common.exception.response.MultiResponse;
import com.waitless.common.exception.response.SingleResponse;
import com.waitless.restaurant.restaurant.application.dto.RestaurantResponseDto;
import com.waitless.restaurant.restaurant.application.dto.RestaurantWithMenuResponseDto;
import com.waitless.restaurant.restaurant.application.service.RestaurantService;
import com.waitless.restaurant.restaurant.presentation.dto.CreateRestaurantRequestDto;
import com.waitless.restaurant.restaurant.presentation.dto.SearchRestaurantRequestDto;
import com.waitless.restaurant.restaurant.presentation.dto.UpdateRestaurantRequestDto;
import com.waitless.restaurant.restaurant.presentation.mapper.RestaurantControllerMapper;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final RestaurantControllerMapper restaurantControllerMapper;

    @PostMapping
    public ResponseEntity<?> createRestaurant(@Valid @RequestBody CreateRestaurantRequestDto requestDto) {
        RestaurantResponseDto responseDto = restaurantService.createRestaurant(
            restaurantControllerMapper.toServiceDto(requestDto)
        );
        return ResponseEntity.ok(SingleResponse.success(responseDto));
    }


    @PatchMapping("/{id}")
    public ResponseEntity<?> updateRestaurant(@PathVariable UUID id, @RequestBody UpdateRestaurantRequestDto requestDto){
        RestaurantResponseDto responseDto = restaurantService.updateRestaurant(id,
            restaurantControllerMapper.toServiceDto(requestDto));

        return ResponseEntity.ok(SingleResponse.success(responseDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRestaurant(@PathVariable UUID id) {
        RestaurantWithMenuResponseDto responseDto = restaurantService.getRestaurantWithMenu(id);

        return ResponseEntity.ok(SingleResponse.success(responseDto));
    }

    @GetMapping
    public ResponseEntity<?> getAllRestaurantList(@ModelAttribute SearchRestaurantRequestDto requestDto, Pageable pageable) {
        Page<RestaurantResponseDto> responseList =  restaurantService.getRestaurantList(
            restaurantControllerMapper.toServiceDto(requestDto), pageable
        );

        return ResponseEntity.ok(MultiResponse.success(responseList));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRestaurant(@PathVariable UUID id) {
        RestaurantResponseDto responseDto = restaurantService.deleteRestaurant(id);

        return ResponseEntity.ok(SingleResponse.success(responseDto));
    }

    @PostMapping("/app/stock")
    public ResponseEntity<?> getRestaurantStock(@RequestBody List<UUID> restaurantIdList) {
        List<RestaurantStockResponseDto> responseDto = restaurantService.getRestaurantStock(restaurantIdList);

        return ResponseEntity.ok(MultiResponse.success(responseDto));
    }
}
