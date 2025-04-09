package com.waitless.restaurant.menu.domain.entity;

import com.waitless.common.domain.BaseTimeEntity;
import com.waitless.restaurant.menu.application.dto.CreateMenuDto;
import com.waitless.restaurant.menu.domain.entity.enums.MenuCategory;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    public Menu(UUID restaurantId, MenuCategory category, int amount, int price, String name){
        this.restaurantId=restaurantId;
        this.menuCategory=category;
        this.amount=amount;
        this.price=price;
        this.name=name;
    }
}
