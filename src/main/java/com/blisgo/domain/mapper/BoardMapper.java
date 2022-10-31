package com.blisgo.domain.mapper;

import com.blisgo.domain.entity.Board;
import com.blisgo.domain.mapper.cmmn.GenericMapper;
import com.blisgo.web.dto.BoardDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BoardMapper extends GenericMapper<BoardDTO, Board>{
	BoardMapper INSTANCE = Mappers.getMapper(BoardMapper.class);
}
