package com.sjiwon.anotherart.global.security.handler;

import com.sjiwon.anotherart.common.ControllerTest;
import com.sjiwon.anotherart.common.ObjectMapperUtils;
import com.sjiwon.anotherart.fixture.MemberFixture;
import com.sjiwon.anotherart.global.security.handler.utils.MemberLoginRequestUtils;
import com.sjiwon.anotherart.global.security.principal.MemberLoginRequest;
import com.sjiwon.anotherart.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Security [Handler] -> AjaxAuthenticationSuccessHandler 테스트")
@RequiredArgsConstructor
class AjaxAuthenticationSuccessHandlerTest extends ControllerTest {
    private final MockMvc mockMvc;
    private final MemberRepository memberRepository;

    private static final String BASE_URL = "/api/login";
    private static final MemberFixture MEMBER = MemberFixture.A;
    private static final String DEFAULT_LOGIN_ID = MEMBER.getLoginId();
    private static final String DEFAULT_LOGIN_PASSWORD = MEMBER.getPassword();

    @Test
    @DisplayName("로그인을 성공하면 Access Token & Refresh Token이 발급된다")
    void test() throws Exception {
        // given
        createMember();
        MemberLoginRequest loginRequest = MemberLoginRequestUtils.createRequest(DEFAULT_LOGIN_ID, DEFAULT_LOGIN_PASSWORD);

        // when
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(BASE_URL)
                .content(ObjectMapperUtils.objectToJson(loginRequest))
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        // then
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists())
                .andExpect(jsonPath("$.refreshToken").exists())
                .andDo(
                        document(
                                "Security/Authentication/Success",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestFields(
                                        fieldWithPath("loginId").description("로그인 아이디"),
                                        fieldWithPath("loginPassword").description("로그인 비밀번호")
                                ),
                                responseFields(
                                        fieldWithPath("accessToken").description("발급된 Access Token (Expire - 2시간)"),
                                        fieldWithPath("refreshToken").description("발급된 Refresh Token (Expire - 2주)")
                                )
                        )
                );
    }

    private void createMember() {
        memberRepository.save(MEMBER.toMember());
    }
}