package com.minsk.frontendpracticeservice.domain.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record CardResponseDto(String id,
                              String number,
                              @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
                              LocalDate expirationDate,
                              @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
                              LocalDate issueDate,
                              String type) {
}

