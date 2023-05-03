package com.sjiwon.anotherart.favorite.exception;

import com.sjiwon.anotherart.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum FavoriteErrorCode implements ErrorCode {
    ALREADY_FAVORITE_MARKED(HttpStatus.CONFLICT, "FAVORITE_001", "이미 찜한 작품입니다."),
    NOT_FAVORITE_MARKED(HttpStatus.CONFLICT, "FAVORITE_002", "찜 등록되어 있지 않은 작품입니다."),
    ;

    private final HttpStatus status;
    private final String errorCode;
    private final String message;

}
