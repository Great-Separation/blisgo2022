package com.blisgo.web.dto;

import io.soabase.recordbuilder.core.RecordBuilder;

@RecordBuilder
public record DogamDTO(
        AccountDTO account,
        DictionaryDTO dictionary
) {

}