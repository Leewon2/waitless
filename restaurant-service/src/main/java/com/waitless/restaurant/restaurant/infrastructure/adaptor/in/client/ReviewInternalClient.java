package com.waitless.restaurant.restaurant.infrastructure.adaptor.in.client;


import com.waitless.restaurant.restaurant.presentation.dto.ReviewStatisticsBatchRequestDto;
import com.waitless.restaurant.restaurant.presentation.dto.ReviewStatisticsBatchResponseDto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
    name = "review-service",
    url = "http://localhost:19095/api/reviews/app"
)
public interface ReviewInternalClient {

    @PostMapping("/statistics")
    public List<ReviewStatisticsBatchResponseDto> getStaticsList(@RequestBody ReviewStatisticsBatchRequestDto requestDto);

}
