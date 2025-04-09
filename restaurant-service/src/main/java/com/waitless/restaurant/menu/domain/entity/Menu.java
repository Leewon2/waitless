package com.waitless.restaurant.menu.domain.entity;

import com.waitless.common.domain.BaseTimeEntity;
import com.waitless.restaurant.menu.application.dto.CreateMenuDto;
import com.waitless.restaurant.menu.domain.entity.enums.MenuCategory;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Filter;

import java.util.UUID;

@Getter
@Entity
@Table(name = "p_menu")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Filter(name = "deletedFilter", condition = "(deleted_at IS NOT NULL) = :isDeleted")
public class Menu extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID restaurantId;

    @Enumerated(EnumType.STRING)
    private MenuCategory menuCategory;

    @Column(nullable = false)
    private int amount;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String name;

    public static Menu of(CreateMenuDto createMenuDto){
        return new Menu(createMenuDto.restaurantId(), createMenuDto.category(), createMenuDto.amount(), createMenuDto.price(), createMenuDto.name());
    }

    private Menu(UUID restaurantId, MenuCategory menuCategory, int amount, int price, String name){
        this.restaurantId=restaurantId;
        this.menuCategory=menuCategory;
        this.amount=amount;
        this.price=price;
        this.name=name;
    }
}
