package com.hhplus.api.application;

import java.time.LocalDateTime;

public record RegistInfo(LocalDateTime courseDate, String lectureName, String userName) {
}
