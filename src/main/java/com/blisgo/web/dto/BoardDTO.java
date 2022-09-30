package com.blisgo.web.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.PositiveOrZero;

import com.blisgo.util.TimeManager;

import com.blisgo.domain.entity.Account;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardDTO {
	
	private Integer bdNo;
	private Account account;
	@NotNull(message = "제목을 입력해주세요")
	private String bdTitle;
	private String bdCategory;
	@NotNull(message = "내용을 입력해주세요")
	private String bdContent;
	@PositiveOrZero(message = "조회수는 0 또는 양수여야 합니다")
	private Integer bdViews;
	@Min(value = 0, message = "별점은 최소 0이상이어야 합니다")
	@Max(value = 10, message = "별점은 최대 10 이하이야 합니다")
	private Integer bdFavorite;
	private Integer bdReplyCount;
	private String bdThumbnail;
	@Null(message = "Controller단에서 계산되는 값입니다.")
	private LocalDateTime createdDate;
	@Null(message = "Controller단에서 계산되는 값입니다.")
	private LocalDateTime modifiedDate;
	private String timeDiff;

	@Builder
	public BoardDTO(Integer bdNo, Account account, String bdTitle, String bdCategory, String bdContent,
                    Integer bdViews, Integer bdFavorite, Integer bdReplyCount, String bdThumbnail, LocalDateTime createdDate,
                    LocalDateTime modifiedDate) {
		this.bdNo = bdNo;
		this.account = account;
		this.bdTitle = bdTitle;
		this.bdCategory = bdCategory;
		this.bdContent = bdContent;
		this.bdViews = bdViews;
		this.bdFavorite = bdFavorite;
		this.bdReplyCount = bdReplyCount;
		this.bdThumbnail = bdThumbnail;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.timeDiff = TimeManager.calcTimeDiff(modifiedDate);
	}
	
	public static BoardDTO selectBoardFilterContentImage(BoardDTO board, String bdContentImgRemoved) {
		return BoardDTO.builder().bdNo(board.getBdNo()).bdTitle(board.getBdTitle()).account(board.getAccount())
		.bdCategory(board.getBdCategory()).bdContent(bdContentImgRemoved).bdViews(board.getBdViews())
		.bdFavorite(board.getBdFavorite()).bdReplyCount(board.getBdReplyCount()).bdThumbnail(board.getBdThumbnail())
		.createdDate(board.getCreatedDate()).modifiedDate(board.getModifiedDate()).build();
	}
}
