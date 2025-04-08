package com.waitless.restaurant.menu.presentation.controller;

import com.waitless.common.dto.response.SingleResponse;
import com.waitless.restaurant.menu.application.dto.CreatedMenuResponseDto;
import com.waitless.restaurant.menu.application.mapper.MenuMapper;
import com.waitless.restaurant.menu.application.service.MenuService;
import com.waitless.restaurant.menu.presentation.dto.CreateMenuRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/menus")
public class MenuController {
    private final MenuService menuService;
    private final MenuMapper menuMapper;

    // TODO : 예외처리, 권한설정
    @PostMapping
    public ResponseEntity<SingleResponse<CreatedMenuResponseDto>> createMenu(@RequestBody CreateMenuRequestDto createMenuRequestDto){
        return ResponseEntity.ok(SingleResponse.success(menuService.createMenu(menuMapper.toServiceDto(createMenuRequestDto))));
    }

    /**
     * 연동 테스트를 위한 API
     */
    @GetMapping("/hello")
    public ResponseEntity<?> hello(){
        return ResponseEntity.ok("hello");
    }

}
