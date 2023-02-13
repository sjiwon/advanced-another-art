package com.sjiwon.anotherart.art.infra.query.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BasicAuctionArt {
    // 경매 정보
    private final Long auctionId;
    private final Integer highestBidPrice;
    private final LocalDateTime auctionStartDate;
    private final LocalDateTime auctionEndDate;

    // 최고 입찰자 정보
    private final Long highestBidderId;
    private final String highestBidderNickname;
    private final String highestBidderSchool;

    // 작품 정보
    private final Long artId;
    private final String artName;
    private final String artDescription;
    private final int artPrice;
    private final LocalDateTime artRegisterDate;
    private final String artStorageName;

    // 작품 주인 정보
    private final Long ownerId;
    private final String ownerNickname;
    private final String ownerSchool;

    @Builder
    @QueryProjection
    public BasicAuctionArt(
            Long auctionId, Integer highestBidPrice, LocalDateTime auctionStartDate, LocalDateTime auctionEndDate,
            Long highestBidderId, String highestBidderNickname, String highestBidderSchool,
            Long artId, String artName, String artDescription, int artPrice, LocalDateTime artRegisterDate, String artStorageName,
            Long ownerId, String ownerNickname, String ownerSchool
    ) {
        this.auctionId = auctionId;
        this.highestBidPrice = highestBidPrice;
        this.auctionStartDate = auctionStartDate;
        this.auctionEndDate = auctionEndDate;
        this.highestBidderId = highestBidderId;
        this.highestBidderNickname = highestBidderNickname;
        this.highestBidderSchool = highestBidderSchool;
        this.artId = artId;
        this.artName = artName;
        this.artDescription = artDescription;
        this.artPrice = artPrice;
        this.artRegisterDate = artRegisterDate;
        this.artStorageName = artStorageName;
        this.ownerId = ownerId;
        this.ownerNickname = ownerNickname;
        this.ownerSchool = ownerSchool;
    }
}
