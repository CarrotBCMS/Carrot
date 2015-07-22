package com.boxedfolder.domain.util;

import com.boxedfolder.carrot.domain.util.DateTimeDeserializer;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.Assert.assertEquals;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@RunWith(MockitoJUnitRunner.class)
public class DateTimeDeserializerTest {
    private ObjectMapper mapper;
    private DateTimeDeserializer deserializer;
    private DateTime dateTime;

    @Before
    public void setUp() {
        mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        deserializer = new DateTimeDeserializer();
        dateTime = new DateTime(1437550756302l).withZone(DateTimeZone.UTC); // Timestamp
    }

    @Test
    public void testDeserializer() throws Exception {
        String content = "\"2015-07-22T07:39:16.302Z\"";
        JsonParser parser = mapper.getFactory().createParser(content);
        DeserializationContext context = mapper.getDeserializationContext();
        parser.nextToken();
        DateTime result = deserializer.deserialize(parser, context);
        assertEquals(dateTime, result);
        parser.close();
    }
}
