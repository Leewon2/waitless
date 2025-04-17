//package com.waitless.message.test;
//
//import com.waitless.message.application.dto.SlackDeleteResponseDto;
//import com.waitless.message.application.service.SlackService;
//import com.waitless.message.domain.entity.SlackMessage;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.transaction.annotation.Transactional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//@TestPropertySource(properties = {
//        "slack.webhook.urls[0]=https://hooks.slack.com/services/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",
//        "slack.webhook.urls[1]=https://hooks.slack.com/services/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",
//        "slack.webhook.urls[2]=https://hooks.slack.com/services/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"
//})
//@Transactional
//public class SlackIntegrationTest {
//
//    @Autowired
//    private SlackService slackService;
//
//    private SlackMessage slackMessage;
//    private static final String RECEIVER_ID = "test-receiver-user";
//    private static final Integer MY_SEQUENCE = 10;
//
//    @BeforeEach
//    void setUp() {
//        slackMessage = slackService.createSlack(RECEIVER_ID, MY_SEQUENCE).join();
//    }
//
//    @Test
//    void createSlackMessageTest() {
//        assertThat(slackMessage.getReceiverId()).isEqualTo(RECEIVER_ID);
//        assertThat(slackMessage.getMessage()).contains(MY_SEQUENCE.toString());
//        assertThat(slackMessage.getId()).isNotNull();
//    }
//
//    @Test
//    void deleteSlackMessageTest() {
//        SlackDeleteResponseDto  slackDeleteResponseDto = slackService.deleteMessage(slackMessage.getId());
//
//        assertThat(slackMessage.getId()).isEqualTo(slackDeleteResponseDto.id());
//        assertThat(slackDeleteResponseDto.isDeleted()).isTrue();
//    }
//}
