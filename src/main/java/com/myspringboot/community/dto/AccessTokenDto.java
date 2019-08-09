package com.myspringboot.community.dto;

import lombok.Data;

@Data
public class AccessTokenDto {
    private String client_id;
    private String client_secret;
    private String code;
    private String rediect_uri;
    private String state;
}
