package com.blisgo.domain;

import com.blisgo.domain.entity.Dictionary;
import com.blisgo.domain.entity.Hashtag;
import com.blisgo.domain.repository.impl.DictionaryRepositoryImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional
class DictionaryRepositoryTest {

	@Autowired
	DictionaryRepositoryImpl dictionaryRepository;

	@Test
	@DisplayName("해시태그에 값이 매핑되는가")
	void hashtagTest() {
		Dictionary dic = Dictionary.builder().dicNo(1019).build();
		List<Hashtag> tags = dictionaryRepository.selectHashtagInnerJoinGuide(dic);

		for (Hashtag tag : tags) {
			System.out.println("guide_code>" + tag.getGuide().getGuideCode());
			System.out.println("guide_content>" + tag.getGuide().getGuideContent());
			System.out.println("guide_name>" + tag.getGuide().getGuideName());
		}

		assertThat(tags).isNotNull();
	}
	
	@Test
	@DisplayName("별점 값이 갱신되었는가")
	void dictionaryPopularityTest() {
		dictionaryRepository.updateDictionaryPopularity();
	}
	
}
