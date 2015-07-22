package com.boxedfolder.carrot.domain.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.io.IOException;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
public class DateTimeDeserializer extends JsonDeserializer<DateTime> {
    @Override
    public DateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        DateTime dateTime = null;
        try {
            JsonToken currentToken = jsonParser.getCurrentToken();
            System.out.println(jsonParser.getText());
            if (currentToken == JsonToken.VALUE_STRING) {
                String dateTimeAsString = jsonParser.getText().trim();
                DateTimeFormatter formatter = ISODateTimeFormat.dateTime().withZoneUTC();
                dateTime = formatter.parseDateTime(dateTimeAsString);
            }
        } catch (Exception e) {
            throw deserializationContext.mappingException(getClass());
        }

        return dateTime;
    }
}