package com.blisgo.domain.mapper;

import com.blisgo.domain.entity.Account;
import com.blisgo.domain.mapper.cmmn.GenericMapper;
import com.blisgo.web.dto.AccountDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountMapper extends GenericMapper<AccountDTO, Account> {
	AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);
}
