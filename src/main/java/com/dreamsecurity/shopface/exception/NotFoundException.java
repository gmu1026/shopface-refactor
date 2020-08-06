package com.dreamsecurity.shopface.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException() {
        super("요청한 자원을 찾지 못했습니다");
    }
}
