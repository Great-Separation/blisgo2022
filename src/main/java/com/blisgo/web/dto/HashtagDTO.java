package com.blisgo.web.dto;

import com.blisgo.domain.entity.Dictionary;
import com.blisgo.domain.entity.Guide;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HashtagDTO {
	private Dictionary dictionary;
	private Guide guide;

	@Builder
	public HashtagDTO(Dictionary dictionary, Guide guide) {
		this.dictionary = dictionary;
		this.guide = guide;
	}
}
