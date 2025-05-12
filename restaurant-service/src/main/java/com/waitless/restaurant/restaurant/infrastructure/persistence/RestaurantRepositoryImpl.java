package com.waitless.restaurant.restaurant.infrastructure.persistence;

import com.waitless.restaurant.restaurant.domain.entity.Restaurant;
import com.waitless.restaurant.restaurant.domain.repository.RestaurantRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RestaurantRepositoryImpl implements RestaurantRepository {

    private final RestaurantJpaRepository RestaurantJpaRepository;

    public Restaurant save(Restaurant restaurant) {return RestaurantJpaRepository.save(restaurant);}

    public Optional<Restaurant> findById(UUID id) {return RestaurantJpaRepository.findById(id);}

    public List<Restaurant> findAll() {return RestaurantJpaRepository.findAll();}

    @Override
    public List<Restaurant> findByIdIn(List<UUID> idList) {return RestaurantJpaRepository.findByIdIn(idList);}
}
