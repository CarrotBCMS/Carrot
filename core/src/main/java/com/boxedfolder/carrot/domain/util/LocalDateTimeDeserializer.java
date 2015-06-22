package com.boxedfolder.carrot.domain.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.io.IOException;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        LocalDateTime localDateTime = null;
        try {
            JsonToken currentToken = jsonParser.getCurrentToken();
            if (currentToken == JsonToken.VALUE_STRING) {
                String dateTimeAsString = jsonParser.getText().trim();
                DateTimeFormatter formatter = ISODateTimeFormat.dateTime().withZoneUTC();
                localDateTime = formatter.parseLocalDateTime(dateTimeAsString);
            }
        } catch (Exception e) {
            throw deserializationContext.mappingException(getClass());
        }

        return localDateTime;
    }
}
