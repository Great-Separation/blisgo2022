package com.blisgo.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GuideDTO {
	private Integer guideNo;
	private String guideName;
	private String guideContent;
	private String imagePath;

	@Builder
	public GuideDTO(Integer guideNo, String guideName, String guideContent, String imagePath) {
		this.guideNo = guideNo;
		this.guideName = guideName;
		this.guideContent = guideContent;
		this.imagePath = imagePath;
	}
}
