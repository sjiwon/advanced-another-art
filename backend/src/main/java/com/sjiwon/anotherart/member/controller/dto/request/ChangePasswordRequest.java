package com.sjiwon.anotherart.member.controller.dto.request;

import javax.validation.constraints.NotBlank;

public record ChangePasswordRequest(
        @NotBlank(message = "변경할 비밀번호는 필수입니다.")
        String changePassword
) {
}
