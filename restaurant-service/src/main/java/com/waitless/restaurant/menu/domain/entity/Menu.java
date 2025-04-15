package com.waitless.restaurant.menu.domain.entity;

import com.waitless.common.domain.BaseTimeEntity;
import com.waitless.restaurant.menu.domain.entity.enums.MenuCategory;
import com.waitless.restaurant.restaurant.application.exception.RestaurantBusinessException;
import com.waitless.restaurant.restaurant.application.exception.RestaurantErrorCode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

@Getter
@Entity
@Table(name = "p_menu")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "is_deleted=false")
// @Filter(name = "deletedFilter", condition = "(deleted_at IS NOT NULL) = :isDeleted")

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

    public static Menu of(Menu oldMenu, Menu updateMenu) {
        return new Menu(oldMenu.id, oldMenu.restaurantId,
                updateMenu.menuCategory == null ? oldMenu.menuCategory : updateMenu.menuCategory,
                updateMenu.amount == null ? oldMenu.amount : updateMenu.amount,
                updateMenu.price == null ? oldMenu.price : updateMenu.price,
                updateMenu.name == null ? oldMenu.name : updateMenu.name);
    }

    public void decreaseAmount(int amount) {
        if(amount > this.amount) {
            throw new RestaurantBusinessException(RestaurantErrorCode.MENU_DECREASE_FAILED);
        }
        this.amount -= amount;
    }
}
