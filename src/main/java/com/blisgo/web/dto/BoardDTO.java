package com.blisgo.web.dto;

import com.blisgo.util.TimeManager;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BoardDTO {

    private Integer bdNo;
    private AccountDTO account;
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
    public BoardDTO(Integer bdNo, AccountDTO account, String bdTitle, String bdCategory, String bdContent,
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

    public static BoardDTO selectBoardFilterContentImage(BoardDTO boardDTO, String bdContentImgRemoved) {
        return BoardDTO.builder().bdNo(boardDTO.getBdNo()).bdTitle(boardDTO.getBdTitle()).account(boardDTO.getAccount())
                .bdCategory(boardDTO.getBdCategory()).bdContent(bdContentImgRemoved).bdViews(boardDTO.getBdViews())
                .bdFavorite(boardDTO.getBdFavorite()).bdReplyCount(boardDTO.getBdReplyCount()).bdThumbnail(boardDTO.getBdThumbnail())
                .createdDate(boardDTO.getCreatedDate()).modifiedDate(boardDTO.getModifiedDate()).build();
    }
}
