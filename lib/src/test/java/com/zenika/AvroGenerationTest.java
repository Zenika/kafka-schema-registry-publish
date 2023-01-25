package com.zenika;

import com.zenika.labs.movie.ScreeningId;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class AvroGenerationTest {
    @Test void shouldGenerateTheJavaClasses() {
        var screening = ScreeningId.newBuilder()
                .setScreeningId((CharSequence) "screeningId")
                .build();
        assertNotNull(screening);
    }
}
