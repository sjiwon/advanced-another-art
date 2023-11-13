package com.sjiwon.anotherart.art.domain.repository.query;

import com.sjiwon.anotherart.art.domain.model.Art;
import com.sjiwon.anotherart.art.domain.repository.ArtRepository;
import com.sjiwon.anotherart.art.domain.repository.query.dto.AuctionArt;
import com.sjiwon.anotherart.art.domain.repository.query.dto.GeneralArt;
import com.sjiwon.anotherart.auction.domain.model.Auction;
import com.sjiwon.anotherart.auction.domain.repository.AuctionRepository;
import com.sjiwon.anotherart.common.RepositoryTest;
import com.sjiwon.anotherart.common.fixture.MemberFixture;
import com.sjiwon.anotherart.favorite.domain.model.Favorite;
import com.sjiwon.anotherart.favorite.domain.repository.FavoriteRepository;
import com.sjiwon.anotherart.member.domain.model.Member;
import com.sjiwon.anotherart.member.domain.repository.MemberRepository;
import com.sjiwon.anotherart.purchase.domain.model.Purchase;
import com.sjiwon.anotherart.purchase.domain.repository.PurchaseRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.util.List;

import static com.sjiwon.anotherart.art.domain.model.ArtType.AUCTION;
import static com.sjiwon.anotherart.art.domain.model.ArtType.GENERAL;
import static com.sjiwon.anotherart.common.fixture.ArtFixture.AUCTION_1;
import static com.sjiwon.anotherart.common.fixture.ArtFixture.GENERAL_1;
import static com.sjiwon.anotherart.common.fixture.AuctionFixture.AUCTION_OPEN_NOW;
import static com.sjiwon.anotherart.common.fixture.MemberFixture.DUMMY_1;
import static com.sjiwon.anotherart.common.fixture.MemberFixture.DUMMY_2;
import static com.sjiwon.anotherart.common.fixture.MemberFixture.DUMMY_3;
import static com.sjiwon.anotherart.common.fixture.MemberFixture.DUMMY_4;
import static com.sjiwon.anotherart.common.fixture.MemberFixture.DUMMY_5;
import static com.sjiwon.anotherart.common.fixture.MemberFixture.MEMBER_A;
import static com.sjiwon.anotherart.common.fixture.MemberFixture.MEMBER_B;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@Import(ArtSingleQueryRepositoryImpl.class)
@DisplayName("Art -> ArtSingleQueryRepository 테스트")
class ArtSingleQueryRepositoryTest extends RepositoryTest {
    @Autowired
    private ArtSingleQueryRepositoryImpl sut;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ArtRepository artRepository;

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @PersistenceContext
    private EntityManager em;

    private static final int MEMBER_INIT_POINT = 1_000_000;

    @Nested
    @DisplayName("작품 타입 조회 Query")
    class GetArtType {
        private Art artA;
        private Art artB;

        @BeforeEach
        void setUp() {
            final Member owner = memberRepository.save(MEMBER_A.toMember());
            artA = artRepository.save(GENERAL_1.toArt(owner));
            artB = artRepository.save(AUCTION_1.toArt(owner));
        }

        @Test
        @DisplayName("작품 타입을 조회한다")
        void success() {
            assertAll(
                    () -> assertThat(sut.getArtType(artA.getId())).isEqualTo(GENERAL),
                    () -> assertThat(sut.getArtType(artB.getId())).isEqualTo(AUCTION)
            );
        }
    }

    @Nested
    @DisplayName("경매 작품 조회 Query")
    class FetchAuctionArt {
        private final Member[] members = new Member[5];
        private Member owner;
        private Art art;
        private Auction auction;

        @BeforeEach
        void setUp() {
            members[0] = createMember(DUMMY_1);
            members[1] = createMember(DUMMY_2);
            members[2] = createMember(DUMMY_3);
            members[3] = createMember(DUMMY_4);
            members[4] = createMember(DUMMY_5);

            owner = createMember(MEMBER_A);
            art = artRepository.save(AUCTION_1.toArt(owner));
            auction = auctionRepository.save(AUCTION_OPEN_NOW.toAuction(art));
        }

        @Test
        @DisplayName("경매 작품을 조회한다")
        void success() {
            final AuctionArt result1 = sut.fetchAuctionArt(art.getId());
            assertThatAuctionArtMatch(
                    result1,
                    art.getPrice(),
                    0,
                    List.of(),
                    null
            );

            /* members[0] bid + members[0], members[4] like */
            final int newBidPrice1 = auction.getHighestBidPrice() + 50_000;
            bid(members[0], newBidPrice1);
            like(art, members[0]);
            like(art, members[4]);

            final AuctionArt result2 = sut.fetchAuctionArt(art.getId());
            assertThatAuctionArtMatch(
                    result2,
                    newBidPrice1,
                    1,
                    List.of(members[0].getId(), members[4].getId()),
                    members[0]
            );

            /* members[2] bid + members[1], members[2], members[3] like */
            final int newBidPrice2 = newBidPrice1 + 50_000;
            bid(members[2], newBidPrice2);
            like(art, members[1]);
            like(art, members[2]);
            like(art, members[3]);

            final AuctionArt result3 = sut.fetchAuctionArt(art.getId());
            assertThatAuctionArtMatch(
                    result3,
                    newBidPrice2,
                    2,
                    List.of(members[0].getId(), members[1].getId(), members[2].getId(), members[3].getId(), members[4].getId()),
                    members[2]
            );

            /* members[0], members[4] like cancel */
            likeCancel(art, members[0]);
            likeCancel(art, members[4]);

            final AuctionArt result4 = sut.fetchAuctionArt(art.getId());
            assertThatAuctionArtMatch(
                    result4,
                    newBidPrice2,
                    2,
                    List.of(members[1].getId(), members[2].getId(), members[3].getId()),
                    members[2]
            );
        }

        private void bid(final Member bidder, final int bidPrice) {
            auction.applyNewBid(bidder, bidPrice);
        }

        private void assertThatAuctionArtMatch(
                final AuctionArt auctionArt,
                final int highestBidPrice,
                final int bidCount,
                final List<Long> likeMemberIds,
                final Member highestBidder
        ) {
            assertAll(
                    () -> assertThat(auctionArt.getAuctionId()).isEqualTo(auction.getId()),
                    () -> assertThat(auctionArt.getHighestBidPrice()).isEqualTo(highestBidPrice),
                    () -> assertThat(auctionArt.getBidCount()).isEqualTo(bidCount),
                    () -> assertThat(auctionArt.getArtId()).isEqualTo(art.getId()),
                    () -> assertThat(auctionArt.getHashtags()).containsExactlyInAnyOrderElementsOf(art.getHashtags()),
                    () -> assertThat(auctionArt.getLikeMembers()).containsExactlyInAnyOrderElementsOf(likeMemberIds),
                    () -> assertThat(auctionArt.getOwnerId()).isEqualTo(owner.getId()),
                    () -> {
                        if (highestBidder == null) {
                            assertThat(auctionArt.getHighestBidderId()).isNull();
                        } else {
                            assertThat(auctionArt.getHighestBidderId()).isEqualTo(highestBidder.getId());
                        }
                    }
            );
        }
    }

    @Nested
    @DisplayName("일반 작품 조회 Query")
    class FetchGeneralArt {
        private final Member[] members = new Member[5];
        private Member owner;
        private Member buyer;
        private Art art;

        @BeforeEach
        void setUp() {
            members[0] = createMember(DUMMY_1);
            members[1] = createMember(DUMMY_2);
            members[2] = createMember(DUMMY_3);
            members[3] = createMember(DUMMY_4);
            members[4] = createMember(DUMMY_5);

            owner = createMember(MEMBER_A);
            buyer = createMember(MEMBER_B);

            art = artRepository.save(GENERAL_1.toArt(owner));
        }

        @Test
        @DisplayName("일반 작품을 조회한다")
        void success() {
            final GeneralArt result1 = sut.fetchGeneralArt(art.getId());
            assertThatGeneralArtMatch(
                    result1,
                    List.of(),
                    null
            );

            /* member[2], member[3] like */
            like(art, members[2]);
            like(art, members[3]);

            final GeneralArt result2 = sut.fetchGeneralArt(art.getId());
            assertThatGeneralArtMatch(
                    result2,
                    List.of(members[2].getId(), members[3].getId()),
                    null
            );

            /* member[0], member[1], member[4] like + member[2] likeCancel */
            like(art, members[0]);
            like(art, members[1]);
            like(art, members[4]);
            likeCancel(art, members[2]);

            final GeneralArt result3 = sut.fetchGeneralArt(art.getId());
            assertThatGeneralArtMatch(
                    result3,
                    List.of(members[0].getId(), members[1].getId(), members[3].getId(), members[4].getId()),
                    null
            );

            /* buyer buy + members[1], members[4] likeCancel */
            purchase(buyer);
            likeCancel(art, members[1]);
            likeCancel(art, members[4]);

            final GeneralArt result4 = sut.fetchGeneralArt(art.getId());
            assertThatGeneralArtMatch(
                    result4,
                    List.of(members[0].getId(), members[3].getId()),
                    buyer
            );
        }

        private void purchase(final Member buyer) {
            purchaseRepository.save(Purchase.purchaseGeneralArt(art, buyer));
        }

        private void assertThatGeneralArtMatch(
                final GeneralArt generalArt,
                final List<Long> likeMemberIds,
                final Member buyer
        ) {
            assertAll(
                    () -> assertThat(generalArt.getArtId()).isEqualTo(art.getId()),
                    () -> assertThat(generalArt.getHashtags()).containsExactlyInAnyOrderElementsOf(art.getHashtags()),
                    () -> assertThat(generalArt.getLikeMembers()).containsExactlyInAnyOrderElementsOf(likeMemberIds),
                    () -> assertThat(generalArt.getOwnerId()).isEqualTo(owner.getId()),
                    () -> {
                        if (buyer == null) {
                            assertThat(generalArt.getBuyerId()).isNull();
                        } else {
                            assertThat(generalArt.getBuyerId()).isEqualTo(buyer.getId());
                        }
                    }
            );
        }
    }

    private Member createMember(final MemberFixture fixture) {
        final Member member = fixture.toMember();
        member.increaseTotalPoint(MEMBER_INIT_POINT);
        return memberRepository.save(member);
    }

    private void like(final Art art, final Member member) {
        favoriteRepository.save(Favorite.favoriteMarking(art, member));
    }

    private void likeCancel(final Art art, final Member member) {
        em.createQuery("DELETE FROM Favorite f WHERE f.art.id = :artId AND f.member.id = :memberId")
                .setParameter("artId", art.getId())
                .setParameter("memberId", member.getId())
                .executeUpdate();
    }
}
