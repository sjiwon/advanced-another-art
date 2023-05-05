package com.sjiwon.anotherart.global.security.handler;

import com.sjiwon.anotherart.common.SecurityTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.sjiwon.anotherart.common.utils.TokenUtils.ACCESS_TOKEN;
import static com.sjiwon.anotherart.common.utils.TokenUtils.BEARER_TOKEN;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Security [Handler] -> JwtLogoutSuccessHandler 테스트")
class JwtLogoutSuccessHandlerTest extends SecurityTest {
    private static final String BASE_URL = "/api/logout";

    @Test
    @DisplayName("Authorization Header에 Access Token이 존재하면 로그아웃에 성공하고 PayloadID에 해당하는 Refresh Token은 DB에서 삭제된다")
    void success() throws Exception {
        // given
        given(jwtTokenProvider.isTokenValid(anyString())).willReturn(true);
        given(jwtTokenProvider.getId(anyString())).willReturn(1L);

        // when
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(BASE_URL)
                .header(AUTHORIZATION, String.join(" ", BEARER_TOKEN, ACCESS_TOKEN));

        // then
        mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$").doesNotExist()
                )
                .andDo(
                        document(
                                "Security/Logout",
                                getDocumentRequest(),
                                getDocumentResponse(),
                                requestHeaders(
                                        headerWithName(AUTHORIZATION).description("Access Token")
                                )
                        )
                );
    }
}
