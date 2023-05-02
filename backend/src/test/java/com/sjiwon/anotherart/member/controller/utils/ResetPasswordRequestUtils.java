package com.sjiwon.anotherart.member.controller.utils;

import com.sjiwon.anotherart.member.controller.dto.request.ResetPasswordRequest;

public class ResetPasswordRequestUtils {
    public static ResetPasswordRequest createRequest(String loginId, String changePassword) {
        return new ResetPasswordRequest(loginId, changePassword);
    }
}
