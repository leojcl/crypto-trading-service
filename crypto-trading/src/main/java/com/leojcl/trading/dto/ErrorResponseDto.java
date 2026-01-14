package com.leojcl.trading.dto;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponseDto {

    private Instant timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
}
