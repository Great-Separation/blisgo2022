package com.blisgo.web.dto;

import io.soabase.recordbuilder.core.RecordBuilder;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

import java.time.LocalDateTime;

@RecordBuilder
public record ReplyDTO(
        Integer replyNo,
        BoardDTO board,
        AccountDTO account,
        @NotNull(message = "댓글 내용을 입력해주세요")
        String content,
        @Null(message = "Controller단에서 계산되는 값입니다.")
        LocalDateTime createdDate,
        @Null(message = "Controller단에서 계산되는 값입니다.")
        LocalDateTime modifiedDate,
        String timeDiff
) {

}