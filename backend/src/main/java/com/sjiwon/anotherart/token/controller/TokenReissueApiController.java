package com.sjiwon.anotherart.token.controller;

import com.sjiwon.anotherart.token.service.TokenReissueService;
import com.sjiwon.anotherart.token.service.response.TokenResponse;
import com.sjiwon.anotherart.token.utils.ExtractPayload;
import com.sjiwon.anotherart.token.utils.ExtractToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/token/reissue")
public class TokenReissueApiController {
    private final TokenReissueService tokenReissueService;

    @PostMapping
    public ResponseEntity<TokenResponse> reissueTokens(@ExtractPayload Long memberId, @ExtractToken String refreshToken) {
        TokenResponse tokenResponse = tokenReissueService.reissueTokens(memberId, refreshToken);
        return ResponseEntity.ok(tokenResponse);
    }
}
