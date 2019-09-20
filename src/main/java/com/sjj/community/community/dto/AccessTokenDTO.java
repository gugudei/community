package com.sjj.community.community.dto;

import lombok.Data;

@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secrect;
    private String code;
    private String redirect_uri;
    private String state;
}
