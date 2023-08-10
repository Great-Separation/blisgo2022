package com.blisgo.web.dto;

import io.soabase.recordbuilder.core.RecordBuilder;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDateTime;

@RecordBuilder
public record BoardDTO(
        Integer bdNo,
        AccountDTO account,
        @NotNull(message = "제목을 입력해주세요")
        String bdTitle,
        String bdCategory,
        String bdContent,
        @PositiveOrZero(message = "조회수는 0 또는 양수여야 합니다")
        Integer bdViews,
        @PositiveOrZero(message = "좋아요는 0 또는 양수여야 합니다")
        Integer bdFavorite,
        Integer bdReplyCount,
        String bdThumbnail,
        @Null(message = "Controller단에서 계산되는 값입니다.")
        LocalDateTime createdDate,
        @Null(message = "Controller단에서 계산되는 값입니다.")
        LocalDateTime modifiedDate,
        String timeDiff
) {
    public static BoardDTO selectBoardFilterContentImage(BoardDTO boardDTO, String bdContentImgRemoved) {
        return BoardDTOBuilder.builder()
                .bdNo(boardDTO.bdNo())
                .bdTitle(boardDTO.bdTitle())
                .account(boardDTO.account())
                .bdCategory(boardDTO.bdCategory())
                .bdContent(bdContentImgRemoved)
                .bdViews(boardDTO.bdViews())
                .bdFavorite(boardDTO.bdFavorite())
                .bdReplyCount(boardDTO.bdReplyCount())
                .bdThumbnail(boardDTO.bdThumbnail())
                .createdDate(boardDTO.createdDate())
                .modifiedDate(boardDTO.modifiedDate()).build();
    }
}