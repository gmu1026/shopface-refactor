package com.dreamsecurity.shopface.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Message {
    private String status;
    private String message;
    private Object data;

    @Builder
    public Message(String status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
