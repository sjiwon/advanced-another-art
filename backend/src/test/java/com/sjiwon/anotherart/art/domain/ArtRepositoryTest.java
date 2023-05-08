package com.sjiwon.anotherart.art.domain;

import com.sjiwon.anotherart.common.RepositoryTest;
import com.sjiwon.anotherart.member.domain.Member;
import com.sjiwon.anotherart.member.domain.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static com.sjiwon.anotherart.fixture.ArtFixture.AUCTION_1;
import static com.sjiwon.anotherart.fixture.MemberFixture.MEMBER_A;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("Art [Repository Layer] -> ArtRepository 테스트")
class ArtRepositoryTest extends RepositoryTest {
    @Autowired
    private ArtRepository artRepository;

    @Autowired
    private MemberRepository memberRepository;

    private Member owner;
    private Art art;

    @BeforeEach
    void setUp() {
        owner = memberRepository.save(MEMBER_A.toMember());
        art = artRepository.save(AUCTION_1.toArt(owner));
    }

    @Test
    @DisplayName("ID(PK)로 작품을 조회한다")
    void findById() {
        // when
        Optional<Art> emptyArt = artRepository.findByIdWithOwner(art.getId() + 100L);
        Optional<Art> existsArt = artRepository.findByIdWithOwner(art.getId());

        // then
        assertAll(
                () -> assertThat(emptyArt).isEmpty(),
                () -> assertThat(existsArt).isPresent()
        );
        assertThat(existsArt.get()).isEqualTo(art);
    }
}
