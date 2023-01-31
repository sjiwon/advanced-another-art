package com.sjiwon.anotherart.member.controller.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ResetPasswordRequest {
    @NotBlank(message = "로그인 아이디는 필수입니다")
    @ApiModelProperty(value = "사용자 로그인 아이디", example = "user1", required = true)
    private String loginId;

    @NotBlank(message = "변경할 비밀번호는 필수입니다")
    @ApiModelProperty(value = "사용자 로그인 아이디", example = "user1", required = true)
    private String changePassword;
}
