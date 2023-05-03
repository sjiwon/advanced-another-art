package com.sjiwon.anotherart.token.controller;

import com.sjiwon.anotherart.common.ControllerTest;
import com.sjiwon.anotherart.global.security.exception.AuthErrorCode;
import com.sjiwon.anotherart.token.exception.TokenErrorCode;
import com.sjiwon.anotherart.token.service.response.TokenResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.sjiwon.anotherart.common.utils.TokenUtils.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Token [Controller Layer] -> TokenReissueApiController 테스트")
class TokenReissueApiControllerTest extends ControllerTest {
    @Nested
    @DisplayName("토큰 재발급 API [POST /api/token/reissue]")
    class reissueTokens {
        private static final String BASE_URL = "/api/token/reissue";

        @Test
        @DisplayName("Authorization Header에 RefreshToken이 없으면 토큰 재발급에 실패한다")
        void withoutRefreshToken() throws Exception {
            // when
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .post(BASE_URL);

            // then
            final AuthErrorCode expectedError = AuthErrorCode.INVALID_PERMISSION;
            mockMvc.perform(requestBuilder)
                    .andExpectAll(
                            status().isForbidden(),
                            jsonPath("$.status").exists(),
                            jsonPath("$.status").value(expectedError.getStatus().value()),
                            jsonPath("$.errorCode").exists(),
                            jsonPath("$.errorCode").value(expectedError.getErrorCode()),
                            jsonPath("$.message").exists(),
                            jsonPath("$.message").value(expectedError.getMessage())
                    )
                    .andDo(
                            document(
                                    "TokenReissueApi/Failure/Case1",
                                    getDocumentRequest(),
                                    getDocumentResponse(),
                                    getExceptionResponseFiels()
                            )
                    );
        }

        @Test
        @DisplayName("만료된 RefreshToken으로 인해 토큰 재발급에 실패한다")
        void expiredRefreshToken() throws Exception {
            // given
            given(jwtTokenProvider.isTokenValid(anyString())).willReturn(false);

            // when
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .post(BASE_URL)
                    .header(AUTHORIZATION, String.join(" ", BEARER_TOKEN, REFRESH_TOKEN));

            // then
            final TokenErrorCode expectedError = TokenErrorCode.AUTH_INVALID_TOKEN;
            mockMvc.perform(requestBuilder)
                    .andExpectAll(
                            status().isForbidden(),
                            jsonPath("$.status").exists(),
                            jsonPath("$.status").value(expectedError.getStatus().value()),
                            jsonPath("$.errorCode").exists(),
                            jsonPath("$.errorCode").value(expectedError.getErrorCode()),
                            jsonPath("$.message").exists(),
                            jsonPath("$.message").value(expectedError.getMessage())
                    )
                    .andDo(
                            document(
                                    "TokenReissueApi/Failure/Case2",
                                    getDocumentRequest(),
                                    getDocumentResponse(),
                                    getHeaderWithRefreshToken(),
                                    getExceptionResponseFiels()
                            )
                    );
        }

        @Test
        @DisplayName("이미 사용했거나 조작된 RefreshToken이면 토큰 재발급에 실패한다")
        void invalidRefreshToken() throws Exception {
            // given
            given(jwtTokenProvider.isTokenValid(anyString())).willReturn(false);

            // when
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .post(BASE_URL)
                    .header(AUTHORIZATION, String.join(" ", BEARER_TOKEN, REFRESH_TOKEN));

            // then
            final TokenErrorCode expectedError = TokenErrorCode.AUTH_INVALID_TOKEN;
            mockMvc.perform(requestBuilder)
                    .andExpectAll(
                            status().isForbidden(),
                            jsonPath("$.status").exists(),
                            jsonPath("$.status").value(expectedError.getStatus().value()),
                            jsonPath("$.errorCode").exists(),
                            jsonPath("$.errorCode").value(expectedError.getErrorCode()),
                            jsonPath("$.message").exists(),
                            jsonPath("$.message").value(expectedError.getMessage())
                    )
                    .andDo(
                            document(
                                    "TokenReissueApi/Failure/Case3",
                                    getDocumentRequest(),
                                    getDocumentResponse(),
                                    getHeaderWithRefreshToken(),
                                    getExceptionResponseFiels()
                            )
                    );
        }

        @Test
        @DisplayName("RefreshToken으로 AccessToken과 RefreshToken을 재발급받는다")
        void success() throws Exception {
            // given
            given(jwtTokenProvider.isTokenValid(anyString())).willReturn(true);
            given(jwtTokenProvider.getId(anyString())).willReturn(1L);

            TokenResponse response = new TokenResponse(ACCESS_TOKEN, REFRESH_TOKEN);
            given(tokenReissueService.reissueTokens(any(), any())).willReturn(response);

            // when
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .post(BASE_URL)
                    .header(AUTHORIZATION, String.join(" ", BEARER_TOKEN, REFRESH_TOKEN));

            // then
            mockMvc.perform(requestBuilder)
                    .andExpectAll(
                            status().isOk(),
                            jsonPath("$.accessToken").exists(),
                            jsonPath("$.accessToken").value(ACCESS_TOKEN),
                            jsonPath("$.refreshToken").exists(),
                            jsonPath("$.refreshToken").value(REFRESH_TOKEN)
                    )
                    .andDo(
                            document(
                                    "TokenReissueApi/Success",
                                    getDocumentRequest(),
                                    getDocumentResponse(),
                                    getHeaderWithRefreshToken(),
                                    responseFields(
                                            fieldWithPath("accessToken").description("새로 발급된 Access Token (Expire - 2시간)"),
                                            fieldWithPath("refreshToken").description("새로 발급된 Refresh Token (Expire - 2주)")
                                    )
                            )
                    );
        }
    }
}
