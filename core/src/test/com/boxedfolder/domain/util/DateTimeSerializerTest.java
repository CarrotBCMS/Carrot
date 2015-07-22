package com.boxedfolder.domain.util;

import com.boxedfolder.carrot.domain.util.DateTimeSerializer;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.StringWriter;
import java.io.Writer;

import static junit.framework.Assert.assertEquals;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@RunWith(MockitoJUnitRunner.class)
public class DateTimeSerializerTest {
    private ObjectMapper mapper;
    private DateTimeSerializer serializer;
    private DateTime dateTime;

    @Before
    public void setUp() {
        mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        serializer = new DateTimeSerializer();
        dateTime = new DateTime(1437550756302l); // Timestamp
    }

    @Test
    public void testSerializing() throws Exception {
        Writer writer = new StringWriter();
        JsonGenerator generator = new JsonFactory().createGenerator(writer);
        serializer.serialize(dateTime, generator, mapper.getSerializerProvider());
        generator.close();
        assertEquals("\"2015-07-22T07:39:16.302Z\"", writer.toString()); // Output
    }
}
