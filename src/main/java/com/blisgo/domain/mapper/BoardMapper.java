package com.blisgo.domain.mapper;

import com.blisgo.domain.entity.Board;
import com.blisgo.domain.mapper.cmmn.GenericMapper;
import com.blisgo.util.HtmlContentParse;
import com.blisgo.web.dto.AccountDTO;
import com.blisgo.web.dto.BoardDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BoardMapper extends GenericMapper<BoardDTO, Board> {
    BoardMapper INSTANCE = Mappers.getMapper(BoardMapper.class);

    default List<BoardDTO> toDTOListProcessedBdContent(List<Board> boards) {
        if (boards.isEmpty()) {
            return null;
        }

        return toDTOList(
                boards.stream().map(board -> Board.builder()
                        .bdNo(board.getBdNo())
                        .bdTitle(board.getBdTitle())
                        .account(board.getAccount())
                        .bdCategory(board.getBdCategory())
                        .bdContent(HtmlContentParse.parseContentPreview(board.getBdContent()))
                        .bdViews(board.getBdViews())
                        .bdFavorite(board.getBdFavorite())
                        .bdReplyCount(board.getBdReplyCount())
                        .bdThumbnail(board.getBdThumbnail())
                        .createdDate(board.getCreatedDate())
                        .modifiedDate(board.getModifiedDate()).build()).toList()
        );
    }

    default Board toEntityProcessedThumbnail(AccountDTO accountDTO, BoardDTO boardDTO) {
        return Board.builder()
                .bdNo(boardDTO.bdNo())
                .account(AccountMapper.INSTANCE.toEntity(accountDTO))
                .bdTitle(boardDTO.bdTitle())
                .bdCategory(boardDTO.bdCategory())
                .bdContent(boardDTO.bdContent())
                .bdViews(boardDTO.bdViews())
                .bdFavorite(boardDTO.bdFavorite())
                .bdReplyCount(boardDTO.bdReplyCount())
                .bdThumbnail(HtmlContentParse.parseThumbnail(boardDTO.bdContent()))
                .build();
    }

}