package com.blisgo.web.dto;

import com.blisgo.domain.entity.cmmn.Wastes;
import io.soabase.recordbuilder.core.RecordBuilder;

@RecordBuilder
public record GuideDTO(
        Wastes guideCode,
        String guideName,
        String guideContent,
        String imagePath
) {

}