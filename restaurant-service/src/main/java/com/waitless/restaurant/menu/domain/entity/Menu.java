package com.waitless.restaurant.menu.domain.entity;

import com.waitless.common.domain.BaseTimeEntity;
import com.waitless.restaurant.menu.domain.entity.enums.MenuCategory;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.Where;

import java.util.UUID;

@Getter
@Entity
@Table(name = "p_menu")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Filter(name = "deletedFilter", condition = "is_deleted = :isDeleted")
@Where(clause = "is_deleted=false")
public class Menu extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID restaurantId;

    @Enumerated(EnumType.STRING)
    private MenuCategory menuCategory;

    @Column(nullable = false)
    private Integer amount;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private String name;

    public Menu(UUID id, UUID restaurantId, MenuCategory category, Integer amount, Integer price, String name) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.menuCategory = category;
        this.amount = amount;
        this.price = price;
        this.name = name;
    }

    public static Menu of(Menu menuId, Menu menu) {
        return new Menu(menuId.id, menuId.restaurantId,
                menu.menuCategory == null ? menuId.menuCategory : menu.menuCategory,
                menu.amount == null ? menuId.amount : menu.amount,
                menu.price == null ? menuId.price : menu.price,
                menu.name == null ? menuId.name : menu.name);
    }
}
