package com.blisgo.domain.mapper;

import com.blisgo.domain.entity.Dictionary;
import com.blisgo.domain.mapper.cmmn.GenericMapper;
import com.blisgo.web.dto.DictionaryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DictionaryMapper extends GenericMapper<DictionaryDTO, Dictionary> {
	DictionaryMapper INSTANCE = Mappers.getMapper(DictionaryMapper.class);
}
