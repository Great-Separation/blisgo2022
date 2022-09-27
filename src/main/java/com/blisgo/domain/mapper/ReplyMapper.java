package com.blisgo.domain.mapper;

import com.blisgo.domain.entity.Reply;
import com.blisgo.domain.mapper.cmmn.GenericMapper;
import com.blisgo.web.dto.ReplyDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ReplyMapper extends GenericMapper<ReplyDTO, Reply> {
	ReplyMapper INSTANCE = Mappers.getMapper(ReplyMapper.class);
}
