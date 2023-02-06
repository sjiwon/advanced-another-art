package com.sjiwon.anotherart.auction.domain.record;

import com.sjiwon.anotherart.art.domain.Art;
import com.sjiwon.anotherart.art.domain.ArtRepository;
import com.sjiwon.anotherart.auction.domain.Auction;
import com.sjiwon.anotherart.auction.domain.AuctionRepository;
import com.sjiwon.anotherart.auction.domain.Period;
import com.sjiwon.anotherart.common.RepositoryTest;
import com.sjiwon.anotherart.fixture.ArtFixture;
import com.sjiwon.anotherart.fixture.MemberFixture;
import com.sjiwon.anotherart.member.domain.Member;
import com.sjiwon.anotherart.member.domain.MemberRepository;
import com.sjiwon.anotherart.member.domain.point.PointDetailRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class AuctionRecordRepositoryTest extends RepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PointDetailRepository pointDetailRepository;

    @Autowired
    ArtRepository artRepository;

    @Autowired
    AuctionRepository auctionRepository;

    @Autowired
    AuctionRecordRepository auctionRecordRepository;

    private static final int INIT_AVAILABLE_POINT = 100_000_000;
    private static final LocalDateTime currentTime1DayLater = LocalDateTime.now().plusDays(1);
    private static final LocalDateTime currentTime3DayLater = LocalDateTime.now().plusDays(3);

    @Test
    @DisplayName("경매 작품에 대한 입찰 횟수를 조회한다")
    void test() {
        // given
        Member owner = createMemberA();
        Art auctionArt = createAuctionArt(owner);
        Auction auction = initAuction(auctionArt);
        assertThat(auctionRecordRepository.countByAuctionId(auction.getId())).isEqualTo(0);

        // when - then
        Member memberB = createMemberB();
        Member memberC = createMemberC();

        // 입찰 1회
        int bidAmount = auctionArt.getPrice() + 5000;
        bidProcess(auction, memberB, bidAmount);
        assertThat(auctionRecordRepository.countByAuctionId(auction.getId())).isEqualTo(1);

        // 입찰 2회
        bidAmount += 5000;
        bidProcess(auction, memberC, bidAmount);
        assertThat(auctionRecordRepository.countByAuctionId(auction.getId())).isEqualTo(2);

        // 입찰 3회
        bidAmount += 5000;
        bidProcess(auction, memberB, bidAmount);
        assertThat(auctionRecordRepository.countByAuctionId(auction.getId())).isEqualTo(3);
    }

    private void bidProcess(Auction auction, Member newBidder, int newBidAmount) {
        auction.applyNewBid(newBidder, newBidAmount);
        auctionRecordRepository.save(AuctionRecord.createAuctionRecord(auction, newBidder, newBidAmount));
    }

    private Member createMemberA() {
        Member member = MemberFixture.A.toMember();
        member.increasePoint(INIT_AVAILABLE_POINT);
        return memberRepository.save(member);
    }

    private Member createMemberB() {
        Member member = MemberFixture.B.toMember();
        member.increasePoint(INIT_AVAILABLE_POINT);
        return memberRepository.save(member);
    }

    private Member createMemberC() {
        Member member = MemberFixture.C.toMember();
        member.increasePoint(INIT_AVAILABLE_POINT);
        return memberRepository.save(member);
    }

    private Art createAuctionArt(Member owner) {
        return artRepository.save(ArtFixture.A.toArt(owner));
    }

    private Auction initAuction(Art art) {
        return auctionRepository.save(Auction.initAuction(art, Period.of(currentTime1DayLater, currentTime3DayLater)));
    }
}