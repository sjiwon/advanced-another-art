package com.sjiwon.anotherart.common.fixture;

import com.sjiwon.anotherart.art.domain.model.Art;
import com.sjiwon.anotherart.art.domain.model.ArtName;
import com.sjiwon.anotherart.art.domain.model.ArtType;
import com.sjiwon.anotherart.art.domain.model.Description;
import com.sjiwon.anotherart.art.domain.model.UploadImage;
import com.sjiwon.anotherart.member.domain.model.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;

import static com.sjiwon.anotherart.art.domain.model.ArtType.AUCTION;
import static com.sjiwon.anotherart.art.domain.model.ArtType.GENERAL;

@Getter
@RequiredArgsConstructor
public enum ArtFixture {
    AUCTION_1(
            ArtName.from("경매 작품 1"), Description.from("경매 작품 1"),
            AUCTION, 100_000, new UploadImage("1.png", "S3/1.png"),
            new HashSet<>(Set.of("A", "B", "C"))
    ),
    AUCTION_2(
            ArtName.from("경매 작품 2"), Description.from("경매 작품 2"),
            AUCTION, 100_000, new UploadImage("2.png", "S3/2.png"),
            new HashSet<>(Set.of("A", "B", "C"))
    ),
    AUCTION_3(
            ArtName.from("경매 작품 3"), Description.from("경매 작품 3"),
            AUCTION, 100_000, new UploadImage("3.png", "S3/3.png"),
            new HashSet<>(Set.of("A", "B", "C"))
    ),
    AUCTION_4(
            ArtName.from("경매 작품 4"), Description.from("경매 작품 4"),
            AUCTION, 100_000, new UploadImage("4.png", "S3/4.png"),
            new HashSet<>(Set.of("A", "B", "C"))
    ),
    AUCTION_5(
            ArtName.from("경매 작품 5"), Description.from("경매 작품 5"),
            AUCTION, 100_000, new UploadImage("5.png", "S3/5.png"),
            new HashSet<>(Set.of("A", "B", "C"))
    ),
    AUCTION_6(
            ArtName.from("경매 작품 6"), Description.from("경매 작품 6"),
            AUCTION, 100_000, new UploadImage("6.png", "S3/6.png"),
            new HashSet<>(Set.of("A", "B", "C"))
    ),
    AUCTION_7(
            ArtName.from("경매 작품 7"), Description.from("경매 작품 7"),
            AUCTION, 100_000, new UploadImage("7.png", "S3/7.png"),
            new HashSet<>(Set.of("A", "B", "C"))
    ),
    AUCTION_8(
            ArtName.from("경매 작품 8"), Description.from("경매 작품 8"),
            AUCTION, 100_000, new UploadImage("8.png", "S3/8.png"),
            new HashSet<>(Set.of("A", "B", "C"))
    ),
    AUCTION_9(
            ArtName.from("경매 작품 9"), Description.from("경매 작품 9"),
            AUCTION, 100_000, new UploadImage("9.png", "S3/9.png"),
            new HashSet<>(Set.of("A", "B", "C"))
    ),
    AUCTION_10(
            ArtName.from("경매 작품 10"), Description.from("경매 작품 10"),
            AUCTION, 100_000, new UploadImage("10.png", "S3/10.png"),
            new HashSet<>(Set.of("A", "B", "C"))
    ),
    AUCTION_11(
            ArtName.from("경매 작품 11"), Description.from("경매 작품 11"),
            AUCTION, 100_000, new UploadImage("11.png", "S3/11.png"),
            new HashSet<>(Set.of("A", "B", "C"))
    ),
    AUCTION_12(
            ArtName.from("경매 작품 12"), Description.from("경매 작품 12"),
            AUCTION, 100_000, new UploadImage("12.png", "S3/12.png"),
            new HashSet<>(Set.of("A", "B", "C"))
    ),
    AUCTION_13(
            ArtName.from("경매 작품 13"), Description.from("경매 작품 13"),
            AUCTION, 100_000, new UploadImage("13.png", "S3/13.png"),
            new HashSet<>(Set.of("A", "B", "C"))
    ),
    AUCTION_14(
            ArtName.from("경매 작품 14"), Description.from("경매 작품 14"),
            AUCTION, 100_000, new UploadImage("14.png", "S3/14.png"),
            new HashSet<>(Set.of("A", "B", "C"))
    ),
    AUCTION_15(
            ArtName.from("경매 작품 15"), Description.from("경매 작품 15"),
            AUCTION, 100_000, new UploadImage("15.png", "S3/15.png"),
            new HashSet<>(Set.of("A", "B", "C"))
    ),
    AUCTION_16(
            ArtName.from("경매 작품 16"), Description.from("경매 작품 16"),
            AUCTION, 100_000, new UploadImage("16.png", "S3/16.png"),
            new HashSet<>(Set.of("A", "B", "C"))
    ),
    AUCTION_17(
            ArtName.from("경매 작품 17"), Description.from("경매 작품 17"),
            AUCTION, 100_000, new UploadImage("17.png", "S3/17.png"),
            new HashSet<>(Set.of("A", "B", "C"))
    ),
    AUCTION_18(
            ArtName.from("경매 작품 18"), Description.from("경매 작품 18"),
            AUCTION, 100_000, new UploadImage("18.png", "S3/18.png"),
            new HashSet<>(Set.of("A", "B", "C"))
    ),
    AUCTION_19(
            ArtName.from("경매 작품 19"), Description.from("경매 작품 19"),
            AUCTION, 100_000, new UploadImage("19.png", "S3/19.png"),
            new HashSet<>(Set.of("A", "B", "C"))
    ),
    AUCTION_20(
            ArtName.from("경매 작품 20"), Description.from("경매 작품 20"),
            AUCTION, 100_000, new UploadImage("20.png", "S3/20.png"),
            new HashSet<>(Set.of("A", "B", "C"))
    ),

    GENERAL_1(
            ArtName.from("일반 작품 1"), Description.from("일반 작품 1"),
            GENERAL, 100_000, new UploadImage("21.png", "S3/21.png"),
            new HashSet<>(Set.of("A", "B", "C"))
    ),
    GENERAL_2(
            ArtName.from("일반 작품 2"), Description.from("일반 작품 2"),
            GENERAL, 100_000, new UploadImage("22.png", "S3/22.png"),
            new HashSet<>(Set.of("A", "B", "C"))
    ),
    GENERAL_3(
            ArtName.from("일반 작품 3"), Description.from("일반 작품 3"),
            GENERAL, 100_000, new UploadImage("23.png", "S3/23.png"),
            new HashSet<>(Set.of("A", "B", "C"))
    ),
    GENERAL_4(
            ArtName.from("일반 작품 4"), Description.from("일반 작품 4"),
            GENERAL, 100_000, new UploadImage("24.png", "S3/24.png"),
            new HashSet<>(Set.of("A", "B", "C"))
    ),
    GENERAL_5(
            ArtName.from("일반 작품 5"), Description.from("일반 작품 5"),
            GENERAL, 100_000, new UploadImage("25.png", "S3/25.png"),
            new HashSet<>(Set.of("A", "B", "C"))
    ),
    GENERAL_6(
            ArtName.from("일반 작품 6"), Description.from("일반 작품 6"),
            GENERAL, 100_000, new UploadImage("26.png", "S3/26.png"),
            new HashSet<>(Set.of("A", "B", "C"))
    ),
    GENERAL_7(
            ArtName.from("일반 작품 7"), Description.from("일반 작품 7"),
            GENERAL, 100_000, new UploadImage("27.png", "S3/27.png"),
            new HashSet<>(Set.of("A", "B", "C"))
    ),
    GENERAL_8(
            ArtName.from("일반 작품 8"), Description.from("일반 작품 8"),
            GENERAL, 100_000, new UploadImage("28.png", "S3/28.png"),
            new HashSet<>(Set.of("A", "B", "C"))
    ),
    GENERAL_9(
            ArtName.from("일반 작품 9"), Description.from("일반 작품 9"),
            GENERAL, 100_000, new UploadImage("29.png", "S3/29.png"),
            new HashSet<>(Set.of("A", "B", "C"))
    ),
    GENERAL_10(
            ArtName.from("일반 작품 10"), Description.from("일반 작품 10"),
            GENERAL, 100_000, new UploadImage("30.png", "S3/30.png"),
            new HashSet<>(Set.of("A", "B", "C"))
    ),
    GENERAL_11(
            ArtName.from("일반 작품 11"), Description.from("일반 작품 11"),
            GENERAL, 100_000, new UploadImage("31.png", "S3/31.png"),
            new HashSet<>(Set.of("A", "B", "C"))
    ),
    GENERAL_12(
            ArtName.from("일반 작품 12"), Description.from("일반 작품 12"),
            GENERAL, 100_000, new UploadImage("32.png", "S3/32.png"),
            new HashSet<>(Set.of("A", "B", "C"))
    ),
    GENERAL_13(
            ArtName.from("일반 작품 13"), Description.from("일반 작품 13"),
            GENERAL, 100_000, new UploadImage("33.png", "S3/33.png"),
            new HashSet<>(Set.of("A", "B", "C"))
    ),
    GENERAL_14(
            ArtName.from("일반 작품 14"), Description.from("일반 작품 14"),
            GENERAL, 100_000, new UploadImage("34.png", "S3/34.png"),
            new HashSet<>(Set.of("A", "B", "C"))
    ),
    GENERAL_15(
            ArtName.from("일반 작품 15"), Description.from("일반 작품 15"),
            GENERAL, 100_000, new UploadImage("35.png", "S3/35.png"),
            new HashSet<>(Set.of("A", "B", "C"))
    ),
    GENERAL_16(
            ArtName.from("일반 작품 16"), Description.from("일반 작품 16"),
            GENERAL, 100_000, new UploadImage("36.png", "S3/36.png"),
            new HashSet<>(Set.of("A", "B", "C"))
    ),
    GENERAL_17(
            ArtName.from("일반 작품 17"), Description.from("일반 작품 17"),
            GENERAL, 100_000, new UploadImage("37.png", "S3/37.png"),
            new HashSet<>(Set.of("A", "B", "C"))
    ),
    GENERAL_18(
            ArtName.from("일반 작품 18"), Description.from("일반 작품 18"),
            GENERAL, 100_000, new UploadImage("38.png", "S3/38.png"),
            new HashSet<>(Set.of("A", "B", "C"))
    ),
    GENERAL_19(
            ArtName.from("일반 작품 19"), Description.from("일반 작품 19"),
            GENERAL, 100_000, new UploadImage("39.png", "S3/39.png"),
            new HashSet<>(Set.of("A", "B", "C"))
    ),
    GENERAL_20(
            ArtName.from("일반 작품 20"), Description.from("일반 작품 20"),
            GENERAL, 100_000, new UploadImage("40.png", "S3/40.png"),
            new HashSet<>(Set.of("A", "B", "C"))
    ),
    ;

    private final ArtName name;
    private final Description description;
    private final ArtType type;
    private final int price;
    private final UploadImage uploadImage;
    private final Set<String> hashtags;

    public Art toArt(final Member owner) {
        return Art.createArt(
                owner,
                name,
                description,
                type,
                price,
                uploadImage,
                hashtags
        );
    }

    public Art toArt(final Member owner, final int price) {
        return Art.createArt(
                owner,
                name,
                description,
                type,
                price,
                uploadImage,
                hashtags
        );
    }

    public Art toArt(final Member owner, final String keyword, final Set<String> hashtags) {
        return Art.createArt(
                owner,
                ArtName.from(keyword),
                Description.from(keyword),
                type,
                price,
                uploadImage,
                hashtags
        );
    }
}
