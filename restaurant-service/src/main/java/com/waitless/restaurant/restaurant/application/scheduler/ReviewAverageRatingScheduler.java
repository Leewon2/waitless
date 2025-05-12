package com.waitless.restaurant.restaurant.application.scheduler;

import com.waitless.restaurant.restaurant.domain.entity.Restaurant;
import com.waitless.restaurant.restaurant.domain.repository.RestaurantRepository;
import com.waitless.restaurant.restaurant.infrastructure.adaptor.in.client.ReviewInternalClient;
import com.waitless.restaurant.restaurant.presentation.dto.ReviewStatisticsBatchRequestDto;
import com.waitless.restaurant.restaurant.presentation.dto.ReviewStatisticsBatchResponseDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReviewAverageRatingScheduler {
    private final RestaurantRepository restaurantRepository;
    private final ReviewInternalClient reviewInternalClient;

    @PersistenceContext
    private final EntityManager entityManager;

    private static final int BATCH_SIZE = 100;

    @Scheduled(fixedDelay = 240_000)
    @Transactional
    public void updateAverageRating() {
        List<Restaurant> restaurantList = restaurantRepository.findAll();


        for (int i = 0; i < restaurantList.size(); i += BATCH_SIZE) {
            int end = Math.min(i + BATCH_SIZE, restaurantList.size());

            List<UUID> restaurantIds = restaurantList.subList(i,end)
                .stream().map(Restaurant::getId).toList();

            try{
                List<ReviewStatisticsBatchResponseDto> reviewStatisticsList = reviewInternalClient.getStaticsList(
                    new ReviewStatisticsBatchRequestDto(restaurantIds));

                bulkUpdateAverageRating(reviewStatisticsList);
            }catch (Exception e) {
                log.error("Failed to get restaurant statistics {}-{}",i,end);
                log.error(e.getMessage(),e);
            }
        }
    }


    @Transactional
    public void bulkUpdateAverageRating(List<ReviewStatisticsBatchResponseDto> resultList) {
        StringBuilder query = new StringBuilder("UPDATE p_restaurant SET average_rating = CASE id ");

        for (ReviewStatisticsBatchResponseDto dto : resultList) {
            query.append("WHEN '")
                .append(dto.restaurantId().toString())
                .append("' THEN ")
                .append(dto.averageRating())
                .append(" ");
        }

        query.append("END WHERE id IN (");

        for (int i = 0; i < resultList.size(); i++) {
            query.append("'").append(resultList.get(i).restaurantId().toString()).append("'");
            if (i != resultList.size() - 1) {
                query.append(", ");
            };
        }
        query.append(")");

        log.info("Bulk update average rating {}", query.toString());

        entityManager.createNativeQuery(query.toString()).executeUpdate();
    }

}
