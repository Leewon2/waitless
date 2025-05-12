package com.waitless.reservation.application.service.message;

import org.springframework.stereotype.Service;

@Service
public class SlackMessageService implements MessageService {
    private static final String REVIEW_MESSAGE_TEMPLATE =
            "%s을 방문하셨네요.\n식당에 대한 리뷰를 작성해 보세요!\n포인트 100원을 적립해드립니다.\n%s";

    private static final String REVIEW_URL_TEMPLATE =
            "https://www.xxx.com/api/reservation?userId=%s";

    private static final String CANCEL_MESSAGE_TEMPLATE =
            "%s 예약이 취소되었습니다.\n취소하신 적이 없으면 고객센터로 연락주세요.";

    private static final String VISIT_REQUEST_MESSAGE_TEMPLATE =
            "%s 식당의 입장할 순번이 1번쨰가 되었습니다.\n 식당 앞에서 기다려 주세요.";


    @Override
    public String buildVisitCompleteMessage(String slackId, String restaurantName, Long userId) {
        String reviewLink = String.format(REVIEW_URL_TEMPLATE, userId);
        return String.format(REVIEW_MESSAGE_TEMPLATE, restaurantName, reviewLink);
    }

    @Override
    public String buildCancelMessage(String restaurantName) {
        return String.format(CANCEL_MESSAGE_TEMPLATE, restaurantName);
    }

    @Override
    public String buildVisitRequestMessage(String restaurantName) {
        return String.format(VISIT_REQUEST_MESSAGE_TEMPLATE, restaurantName);
    }
}
