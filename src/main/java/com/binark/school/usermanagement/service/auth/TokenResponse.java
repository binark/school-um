package com.binark.school.usermanagement.service.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenResponse {

    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("espires_in")
    private long expiresIn;
    @JsonProperty("refresh_expires_in")
    private long refreshExpriesIn;
    @JsonProperty("refresh_token")
    private String refreshToken;
    @JsonProperty("token_type")
    private String tokenType;
    @JsonProperty("not_before_policy")
    private int notBeforePolicy;
}
