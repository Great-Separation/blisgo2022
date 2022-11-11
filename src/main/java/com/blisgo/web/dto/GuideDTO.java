package com.blisgo.web.dto;

import com.blisgo.domain.entity.cmmn.Wastes;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GuideDTO {
	private Wastes guideCode;
	private String guideName;
	private String guideContent;
	private String imagePath;

	@Builder
	public GuideDTO(Wastes guideCode, String guideName, String guideContent, String imagePath) {
		this.guideCode = guideCode;
		this.guideName = guideName;
		this.guideContent = guideContent;
		this.imagePath = imagePath;
	}
}
