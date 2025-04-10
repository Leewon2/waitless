package com.waitless.restaurant.menu.presentation.controller;

import com.waitless.common.exception.response.SingleResponse;
import com.waitless.restaurant.menu.application.dto.CreatedMenuResponseDto;
import com.waitless.restaurant.menu.application.service.MenuService;
import com.waitless.restaurant.menu.presentation.dto.CreateMenuRequestDto;
import com.waitless.restaurant.menu.presentation.dto.UpdateMenuRequestDto;
import com.waitless.restaurant.menu.presentation.mapper.MenuControllerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/menus")
public class MenuController {
    private final MenuService menuService;
    private final MenuControllerMapper menuControllerMapper;

    // TODO : 예외처리, 권한설정
    @PostMapping
    public ResponseEntity<SingleResponse<CreatedMenuResponseDto>> createMenu(@RequestBody CreateMenuRequestDto createMenuRequestDto){
        return ResponseEntity.ok(SingleResponse.success(
                menuService.createMenu(
                        menuControllerMapper.toServiceDto(createMenuRequestDto))));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMenu(@PathVariable UUID id){
        return ResponseEntity.ok(SingleResponse.success(menuService.getMenu(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMenu(@PathVariable UUID id){
        return ResponseEntity.ok(SingleResponse.success(menuService.deleteMenu(id)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateMenu(@PathVariable UUID id, @RequestBody(required = false)UpdateMenuRequestDto updateMenuRequestDto){
        return ResponseEntity.ok(SingleResponse.success(
                menuService.updateMenu(
                        id,menuControllerMapper.toUpdateMenuDto(updateMenuRequestDto))));
    }


    /**
     * 테스트용 API
     */
    @GetMapping("/hello")
    public ResponseEntity<?> hello(){
        return ResponseEntity.ok("hello");
    }

    @GetMapping("menus/{id}")
    public ResponseEntity<?> getMenus(@PathVariable UUID id){
        return ResponseEntity.ok(SingleResponse.success(menuService.getMenus(id)));
    }

}
