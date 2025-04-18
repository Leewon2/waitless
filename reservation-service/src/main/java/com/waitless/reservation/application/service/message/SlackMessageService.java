package com.waitless.reservation.application.service.message;

import org.springframework.stereotype.Service;

@Service
public class SlackMessageService implements MessageService {
    private static final String REVIEW_MESSAGE_TEMPLATE =
            "%s을 방문하셨네요.\n식당에 대한 리뷰를 작성해 보세요!\n포인트 100원을 적립해드립니다.\n%s";

    private static final String REVIEW_URL_TEMPLATE =
            "https://www.xxx.com/api/reservation?userId=%s";

    @Override
    public String buildVisitCompleteMessage(String slackId, String restaurantName, Long userId) {
        String reviewLink = String.format(REVIEW_URL_TEMPLATE, userId);
        return String.format(REVIEW_MESSAGE_TEMPLATE, restaurantName, reviewLink);
    }
}
