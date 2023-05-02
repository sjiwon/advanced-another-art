package com.sjiwon.anotherart.member.controller.dto.request;

import com.sjiwon.anotherart.member.domain.Address;
import com.sjiwon.anotherart.member.domain.Email;
import com.sjiwon.anotherart.member.domain.Member;
import com.sjiwon.anotherart.member.domain.Password;
import lombok.*;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SignUpRequest {
    @NotBlank(message = "이름은 필수입니다.")
    private String name;

    @NotBlank(message = "닉네임은 필수입니다.")
    private String nickname;

    @NotBlank(message = "아이디는 필수입니다.")
    private String loginId;

    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;

    @NotBlank(message = "학교 이름은 필수입니다.")
    private String school;

    @NotNull(message = "우편번호는 필수입니다.")
    private Integer postcode;

    @NotBlank(message = "주소는 필수입니다.")
    private String defaultAddress;

    @NotBlank(message = "상세주소는 필수입니다.")
    private String detailAddress;

    @NotBlank(message = "전화번호는 필수입니다.")
    private String phone;

    @NotBlank(message = "이메일은 필수입니다.")
    private String email;

    public Member toMemberEntity() {
        return Member.builder()
                .name(name)
                .nickname(nickname)
                .loginId(loginId)
                .password(Password.encrypt(password, PasswordEncoderFactories.createDelegatingPasswordEncoder()))
                .school(school)
                .address(Address.of(postcode, defaultAddress, detailAddress))
                .phone(phone)
                .email(Email.from(email))
                .build();
    }
}
