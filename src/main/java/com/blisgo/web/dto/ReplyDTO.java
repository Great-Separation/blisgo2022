package com.blisgo.web.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import com.blisgo.domain.entity.Board;
import com.blisgo.domain.entity.User;
import com.blisgo.util.TimeManager;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReplyDTO {
	private Integer replyNo;
	private Board board;
	private User user;
	@NotNull(message = "댓글 내용을 입력해주세요")
	private String content;
	@Null(message = "Controller단에서 계산되는 값입니다.")
	private LocalDateTime createdDate;
	@Null(message = "Controller단에서 계산되는 값입니다.")
	private LocalDateTime modifiedDate;
	private String timeDiff;

	@Builder
	public ReplyDTO(Integer replyNo, Board board, User user, String content, LocalDateTime createdDate,
			LocalDateTime modiDateTime) {
		this.replyNo = replyNo;
		this.board = board;
		this.user = user;
		this.content = content;
		this.createdDate = createdDate;
		this.modifiedDate = modiDateTime;
		this.timeDiff = TimeManager.calcTimeDiff(createdDate);
	}

}
