package com.waitless.restaurant.menu.domain.entity;

import java.util.UUID;

import org.hibernate.annotations.Where;

import com.waitless.common.domain.BaseTimeEntity;
import com.waitless.restaurant.menu.domain.entity.enums.MenuCategory;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
