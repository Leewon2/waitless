package com.waitless.restaurant.menu.presentation.controller;

import com.waitless.common.aop.RoleCheck;
import com.waitless.common.domain.Role;
import com.waitless.common.exception.response.MultiResponse;
import com.waitless.common.exception.response.SingleResponse;
import com.waitless.restaurant.menu.application.dto.CreatedMenuResponseDto;
import com.waitless.restaurant.menu.application.dto.SearchResponseDto;
import com.waitless.restaurant.menu.application.service.MenuService;
import com.waitless.restaurant.menu.presentation.dto.CreateMenuRequestDto;
import com.waitless.restaurant.menu.presentation.dto.SearchRequestDto;
import com.waitless.restaurant.menu.presentation.dto.UpdateMenuRequestDto;
import com.waitless.restaurant.menu.presentation.mapper.MenuControllerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    @RoleCheck(roles = {Role.ADMIN, Role.OWNER})
    public ResponseEntity<SingleResponse<CreatedMenuResponseDto>> createMenu(@RequestBody CreateMenuRequestDto createMenuRequestDto){
        return ResponseEntity.ok(SingleResponse.success(
                menuService.createMenu(
                        menuControllerMapper.toServiceDto(createMenuRequestDto))));
    }

    @GetMapping("/{id}")
    @RoleCheck(roles = {Role.ADMIN, Role.OWNER, Role.USER})
    public ResponseEntity<?> getMenu(@PathVariable UUID id){
        return ResponseEntity.ok(SingleResponse.success(menuService.getMenu(id)));
    }

    @DeleteMapping("/{id}")
    @RoleCheck(roles = {Role.ADMIN, Role.OWNER})
    public ResponseEntity<?> deleteMenu(@PathVariable UUID id){
        return ResponseEntity.ok(SingleResponse.success(menuService.deleteMenu(id)));
    }

    @PatchMapping("/{id}")
    @RoleCheck(roles = {Role.ADMIN, Role.OWNER})
    public ResponseEntity<?> updateMenu(@PathVariable UUID id, @RequestBody(required = false)UpdateMenuRequestDto updateMenuRequestDto){
        return ResponseEntity.ok(SingleResponse.success(
                menuService.updateMenu(
                        id,menuControllerMapper.toUpdateMenuDto(updateMenuRequestDto))));
    }

    @GetMapping("search")
    @RoleCheck(roles = {Role.ADMIN, Role.OWNER, Role.USER})
    public ResponseEntity<?> search(@RequestParam(required = false) Integer minPrice,
                                    @RequestParam(required = false) Integer maxPrice,
                                    @RequestParam(required = false) String category,
                                    @RequestParam(required = false, defaultValue = "createdAt") String sortBy,
                                    @RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "10") int size
                                    ){

        SearchRequestDto searchRequestDto = new SearchRequestDto(minPrice, maxPrice, category, sortBy, page, size);
        Pageable pageable = PageRequest.of(page-1,size);
        Page<SearchResponseDto> res = menuService.searchMenu(menuControllerMapper.toSearchMenuDto(searchRequestDto),pageable);
        return ResponseEntity.ok(MultiResponse.success(res));
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
