package com.waitless.message.presentation.controller;

import com.waitless.common.aop.RoleCheck;
import com.waitless.common.domain.Role;
import com.waitless.common.exception.response.SingleResponse;
import com.waitless.message.application.service.SlackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/slacks")
public class SlackController {

    private final SlackService slackService;


    @DeleteMapping("/{id}")
    @RoleCheck(roles = {Role.ADMIN})
    public ResponseEntity<?> deleteSlackMessage(@PathVariable UUID id){
        return ResponseEntity.ok(SingleResponse.success(slackService.deleteMessage(id)));
    }

    /**
     임시 controller
     추후 예약이 완료 되면 kafka를 통해 이벤트를 수신하고 slack으로 메세지를 전송
     */
    @PostMapping
    public ResponseEntity<?> createSlackMessage(@RequestParam String receiverId,
                                                @RequestParam Integer mySequence){
        slackService.createSlack(receiverId,mySequence);
        return ResponseEntity.ok(SingleResponse.success("성공"));
    }
}
