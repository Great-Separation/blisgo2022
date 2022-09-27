package com.blisgo.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DictionaryDTO {
	private Integer dicNo;
	private String name;
	private String engName;
	private String category;
	private Integer popularity;
	private Short hit;
	private String thumbnail;
	private String treatment;

	@Builder
	public DictionaryDTO(Integer dicNo, String name, String engName, String category, Integer popularity, Short hit,
			String thumbnail, String treatment) {
		this.dicNo = dicNo;
		this.name = name;
		this.engName = engName;
		this.category = category;
		this.popularity = popularity;
		this.hit = hit;
		this.thumbnail = thumbnail;
		this.treatment = treatment;
	}
}
