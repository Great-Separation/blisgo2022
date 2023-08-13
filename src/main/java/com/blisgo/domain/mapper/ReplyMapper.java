package com.blisgo.domain.mapper;

import com.blisgo.domain.entity.Reply;
import com.blisgo.domain.mapper.cmmn.GenericMapper;
import com.blisgo.web.dto.ReplyDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReplyMapper extends GenericMapper<ReplyDTO, Reply> {
	ReplyMapper INSTANCE = Mappers.getMapper(ReplyMapper.class);

	@Override
	@Mapping(target = "timeDiff", expression = "java(com.blisgo.util.TimeManager.calcTimeDiff(entity.getCreatedDate()))")
	ReplyDTO toDTO(Reply entity);
}
