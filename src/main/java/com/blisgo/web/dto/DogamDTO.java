package com.blisgo.web.dto;

import com.blisgo.domain.entity.Account;
import com.blisgo.domain.entity.Dictionary;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DogamDTO {
	private Account account;
	private Dictionary dictionary;

	@Builder
	public DogamDTO(Account account, Dictionary dictionary) {
		this.account = account;
		this.dictionary = dictionary;
	}
}
