package com.boxedfolder.carrot.domain.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.io.IOException;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {
    private static DateTimeFormatter formatter = ISODateTimeFormat.basicDateTime(); // Supposed to be ISO

    @Override
    public void serialize(LocalDateTime value, JsonGenerator generator, SerializerProvider serializerProvider) throws IOException {
        generator.writeString(formatter.print(value));
    }
}