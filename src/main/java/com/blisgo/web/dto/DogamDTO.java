package com.blisgo.web.dto;

import com.blisgo.domain.entity.Dictionary;
import com.blisgo.domain.entity.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DogamDTO {
	private User user;
	private Dictionary dictionary;

	@Builder
	public DogamDTO(User user, Dictionary dictionary) {
		this.user = user;
		this.dictionary = dictionary;
	}
}
