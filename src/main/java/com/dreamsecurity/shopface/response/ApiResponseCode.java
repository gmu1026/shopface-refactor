package com.dreamsecurity.shopface.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApiResponseCode {
    OK("요청이 성공했습니다"),
    BAD_REQUEST("요청 파라미터가 잘못되었습니다"),
    PAGE_NOT_FOUND("요청 API가 업습니다"),
    NOT_FOUND("해당 자원이 없습니다"),
    UNAUTHORIZED("인증 토큰이 없습니다"),
    SERVER_ERROR("서버 에러입니다");

    private final String message;

    public String getId() {
        return name();
    }

    public String getMessage() {
        return message;
    }
}
