package com.blisgo.domain.mapper.cmmn;

import java.util.List;

@SuppressWarnings("unused")
public interface GenericMapper<DTO, Entity> {

	Entity toEntity(DTO dto);

	DTO toDTO(Entity entity);

	List<DTO> toDTOList(List<Entity> entityList);

	List<Entity> toEntityList(List<DTO> dtoList);
}
