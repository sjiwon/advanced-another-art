package com.sjiwon.anotherart.member.controller.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class DuplicateCheckRequest {
    @NotBlank(message = "중복 체크 타입은 필수입니다 -> [nickname / loginId / phone / email]")
    @ApiModelProperty(value = "중복 체크 타입", example = "nickname", required = true)
    private String resource;

    @NotBlank(message = "중복 체크 값은 필수입니다")
    @ApiModelProperty(value = "중복 체크 값", example = "user", required = true)
    private String value;
}
