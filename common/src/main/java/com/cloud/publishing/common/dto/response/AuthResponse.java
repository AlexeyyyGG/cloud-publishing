package com.cloud.publishing.common.dto.response;

import java.util.List;

public record AuthResponse(String accessToken, String refreshToken, List<String> roles) {
}