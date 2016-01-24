/*
 * Carrot - beacon content management
 * Copyright (C) 2016 Heiko Dreyer
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
public class DateTimeDeserializerTests {
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
