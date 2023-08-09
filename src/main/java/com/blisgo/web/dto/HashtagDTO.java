package com.blisgo.web.dto;

import io.soabase.recordbuilder.core.RecordBuilder;

@RecordBuilder
public record HashtagDTO(
        DictionaryDTO dictionary,
        GuideDTO guide
) {

}
