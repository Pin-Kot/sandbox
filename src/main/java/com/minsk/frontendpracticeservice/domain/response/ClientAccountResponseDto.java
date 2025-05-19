package com.minsk.frontendpracticeservice.domain.response;

import java.util.List;

public record ClientAccountResponseDto(String id,
                                       String userId,
                                       List<AccountResponseDto> accounts) {
}
