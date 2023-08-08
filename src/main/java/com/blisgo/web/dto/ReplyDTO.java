package com.blisgo.web.dto;

import com.blisgo.util.TimeManager;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ReplyDTO {
    private Integer replyNo;
    private BoardDTO board;
    private AccountDTO account;
    @NotNull(message = "댓글 내용을 입력해주세요")
    private String content;
    @Null(message = "Controller단에서 계산되는 값입니다.")
    private LocalDateTime createdDate;
    @Null(message = "Controller단에서 계산되는 값입니다.")
    private LocalDateTime modifiedDate;
    private String timeDiff;

    @Builder
    public ReplyDTO(Integer replyNo, BoardDTO board, AccountDTO account, String content, LocalDateTime createdDate,
                    LocalDateTime modiDateTime) {
        this.replyNo = replyNo;
        this.board = board;
        this.account = account;
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modiDateTime;
        this.timeDiff = TimeManager.calcTimeDiff(createdDate);
    }
}
