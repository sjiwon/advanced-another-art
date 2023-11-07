package com.sjiwon.anotherart.art.application.usecase;

import com.sjiwon.anotherart.art.domain.model.ArtType;
import com.sjiwon.anotherart.art.domain.repository.query.ArtSingleQueryRepository;
import com.sjiwon.anotherart.art.domain.repository.query.dto.ArtDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArtQueryUseCase {
    private final ArtSingleQueryRepository artSingleQueryRepository;

    public ArtDetails getArtById(final Long artId) {
        final ArtType artType = artSingleQueryRepository.getArtType(artId);

        if (artType.isAuctionType()) {
            return artSingleQueryRepository.fetchAuctionArt(artId);
        }
        return artSingleQueryRepository.fetchGeneralArt(artId);
    }
}
