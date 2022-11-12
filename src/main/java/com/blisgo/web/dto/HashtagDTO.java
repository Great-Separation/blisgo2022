package com.blisgo.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HashtagDTO {
    private DictionaryDTO dictionary;
    private GuideDTO guide;

    @Builder
    public HashtagDTO(DictionaryDTO dictionary, GuideDTO guide) {
        this.dictionary = dictionary;
        this.guide = guide;
    }
}
