package com.sjiwon.anotherart.art.application.usecase.query;

import com.sjiwon.anotherart.art.utils.search.ArtDetailsSearchCondition;
import org.springframework.data.domain.Pageable;

public record GetArtsByKeyword(
        ArtDetailsSearchCondition condition,
        Pageable pageable
) {
}
