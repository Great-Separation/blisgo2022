package com.blisgo.web.dto;

import io.soabase.recordbuilder.core.RecordBuilder;

@RecordBuilder
public record DictionaryDTO(
        Integer dicNo,
        String name,
        String engName,
        String category,
        Integer popularity,
        Short hit,
        String thumbnail,
        String treatment
) {

}