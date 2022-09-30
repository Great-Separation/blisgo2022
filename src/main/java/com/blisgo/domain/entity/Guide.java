package com.blisgo.domain.entity;

import com.blisgo.domain.entity.cmmn.Wastes;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Guide {
	@Id
	@Enumerated(EnumType.STRING)
	@Comment("가이드 코드(PK)")
	@Column(nullable = false, length = 10)
	private Wastes guideCode;

	@Comment("폐기물 중분류")
	@Column(nullable = false, length = 50)
	private String guideName;

	@Column(nullable = false, length = 200)
	@Comment("폐기물 처리 안내")
	private String guideContent;

	@Column(nullable = false, length = 100)
	@Comment("폐기물 처리 안내 이미지 url")
	private String imagePath;

	@OneToMany(mappedBy = "guide", orphanRemoval = true)
	private final List<Hashtag> hashtag = new ArrayList<>();

	@Builder
	public Guide(Wastes guideCode, String guideName, String guideContent, String imagePath) {
		this.guideCode = guideCode;
		this.guideName = guideName;
		this.guideContent = guideContent;
		this.imagePath = imagePath;
	}

}