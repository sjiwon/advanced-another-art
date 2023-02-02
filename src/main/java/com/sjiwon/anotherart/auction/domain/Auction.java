package com.sjiwon.anotherart.auction.domain;

import com.sjiwon.anotherart.art.domain.Art;
import com.sjiwon.anotherart.auction.domain.record.AuctionRecord;
import com.sjiwon.anotherart.auction.exception.AuctionErrorCode;
import com.sjiwon.anotherart.global.exception.AnotherArtException;
import com.sjiwon.anotherart.member.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "auction")
@EntityListeners(AuditingEntityListener.class)
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Period period;

    @Embedded
    private CurrentHighestBidder currentHighestBidder;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "art_id", referencedColumnName = "id", nullable = false, updatable = false)
    private Art art;

    @OneToMany(mappedBy = "auction")
    private List<AuctionRecord> auctionRecords = new ArrayList<>();

    @Builder
    private Auction(Art art, CurrentHighestBidder currentHighestBidder, Period period) {
        this.art = art;
        this.currentHighestBidder = currentHighestBidder;
        this.period = period;
    }

    public static Auction initAuction(Art art, Period period) {
        validateArtType(art);
        CurrentHighestBidder currentHighestBidder = CurrentHighestBidder.of(null, art.getPrice());
        return new Auction(art, currentHighestBidder, period);
    }

    private static void validateArtType(Art art) {
        if (!art.isAuctionType()) {
            throw AnotherArtException.type(AuctionErrorCode.INVALID_ART_TYPE);
        }
    }

    public void applyNewBid(Member newBidMember, int newBidPrice) {
        verifyArtOwnerBid(newBidMember.getId());
        this.currentHighestBidder = this.currentHighestBidder.applyNewBid(newBidMember, newBidPrice);
    }

    private void verifyArtOwnerBid(Long newBidMemberId) {
        if (this.art.isArtOwner(newBidMemberId)) {
            throw AnotherArtException.type(AuctionErrorCode.INVALID_OWNER_BID);
        }
    }
}
