package com.waitless.message.application.mapper;

import com.waitless.message.application.dto.SlackSaveDto;
import com.waitless.message.domain.entity.SlackMessage;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SlackServiceMapper {

    SlackMessage toSlackMessage(SlackSaveDto slackSaveDto);
}
