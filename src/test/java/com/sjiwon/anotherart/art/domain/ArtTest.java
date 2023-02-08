package com.sjiwon.anotherart.art.domain;

import com.sjiwon.anotherart.fixture.ArtFixture;
import com.sjiwon.anotherart.fixture.MemberFixture;
import com.sjiwon.anotherart.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.sjiwon.anotherart.common.utils.ArtUtils.HASHTAGS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("Art 도메인 테스트")
class ArtTest {
    private static final ArtFixture ART_A = ArtFixture.A;
    private static final Member member = MemberFixture.A.toMember();

    @Test
    @DisplayName("작품을 생성한다")
    void test1(){
        Art art = ART_A.toArt(member, HASHTAGS);

        assertAll(
                () -> assertThat(art.getName()).isEqualTo(ART_A.getName()),
                () -> assertThat(art.getDescription()).isEqualTo(ART_A.getDescription()),
                () -> assertThat(art.getArtType()).isEqualTo(ART_A.getArtType()),
                () -> assertThat(art.getArtStatus()).isEqualTo(ArtStatus.FOR_SALE),
                () -> assertThat(art.getUploadName()).isEqualTo(ART_A.getUploadName()),
                () -> assertThat(art.getHashtagList().size()).isEqualTo(HASHTAGS.size()),
                () -> assertThat(art.getOwner().getName()).isEqualTo(member.getName()),
                () -> assertThat(art.getOwner().getNickname()).isEqualTo(member.getNickname()),
                () -> assertThat(art.getOwner().getLoginId()).isEqualTo(member.getLoginId())
        );
    }

    @Test
    @DisplayName("작품을 생성한다 + 해시태그를 추가한다")
    void test2(){
        Art art = ART_A.toArt(member, HASHTAGS);

        assertAll(
                () -> assertThat(art.getName()).isEqualTo(ART_A.getName()),
                () -> assertThat(art.getDescription()).isEqualTo(ART_A.getDescription()),
                () -> assertThat(art.getArtType()).isEqualTo(ART_A.getArtType()),
                () -> assertThat(art.getArtStatus()).isEqualTo(ArtStatus.FOR_SALE),
                () -> assertThat(art.getUploadName()).isEqualTo(ART_A.getUploadName()),
                () -> assertThat(art.getHashtagList().size()).isEqualTo(5),
                () -> assertThat(art.getHashtagList()).containsAll(HASHTAGS),
                () -> assertThat(art.getOwner().getName()).isEqualTo(member.getName()),
                () -> assertThat(art.getOwner().getNickname()).isEqualTo(member.getNickname()),
                () -> assertThat(art.getOwner().getLoginId()).isEqualTo(member.getLoginId())
        );
    }
    
    @Test
    @DisplayName("작품 설명을 변경한다")
    void test3(){
        // given
        Art art = ART_A.toArt(member, HASHTAGS);
        
        // when
        final String updateDescription = "Hello World";
        art.changeDescription(updateDescription);
        
        // then
        assertThat(art.getDescription()).isEqualTo(updateDescription);
    }
    
    @Test
    @DisplayName("작품 상태를 변경한다")
    void test4(){
        // given
        Art art = ART_A.toArt(member, HASHTAGS);
        assertThat(art.getArtStatus()).isEqualTo(ArtStatus.FOR_SALE);
        
        // when
        final ArtStatus updateArtStatus = ArtStatus.SOLD_OUT;
        art.changeArtStatus(updateArtStatus);
        
        // then
        assertThat(art.getArtStatus()).isEqualTo(updateArtStatus);
        assertThat(art.isSoldOut()).isTrue();
    }
}