package com.waitless.restaurant.menu.domain.entity;

import com.waitless.common.domain.BaseTimeEntity;
import com.waitless.restaurant.menu.domain.entity.enums.MenuCategory;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Entity
@Table(name = "p_menu")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    private int price;

}
