package com.blisgo.domain.mapper;

import com.blisgo.domain.entity.Hashtag;
import com.blisgo.domain.mapper.cmmn.GenericMapper;
import com.blisgo.web.dto.HashtagDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HashtagMapper extends GenericMapper<HashtagDTO, Hashtag> {
	HashtagMapper INSTANCE = Mappers.getMapper(HashtagMapper.class);
}
