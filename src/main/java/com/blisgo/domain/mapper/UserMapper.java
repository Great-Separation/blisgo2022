package com.blisgo.domain.mapper;

import com.blisgo.domain.entity.User;
import com.blisgo.domain.mapper.cmmn.GenericMapper;
import com.blisgo.web.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper extends GenericMapper<UserDTO, User> {
	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
}
