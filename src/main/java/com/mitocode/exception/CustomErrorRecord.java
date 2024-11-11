package com.mitocode.exception;

import java.time.LocalDateTime;

public record CustomErrorRecord(
        LocalDateTime localDateTime,
        String message,
        String details)
{
}
