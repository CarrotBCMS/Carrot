package com.boxedfolder.domain.util;

import com.boxedfolder.carrot.domain.util.RandomTokenGenerator;
import com.boxedfolder.carrot.domain.util.impl.RandomTokenGeneratorImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@RunWith(MockitoJUnitRunner.class)
public class RandomTokenGeneratorTest {
    private RandomTokenGenerator generator;

    @Before
    public void setUp() {
        generator = new RandomTokenGeneratorImpl();
    }

    @Test
    public void testGenerateToken() throws Exception {
        // Tokens can only be tested in length. They should be random
        String token = generator.getRandomToken();
        assertNotNull(token);
        assertTrue(token.length() == 26);
        System.out.println(token);
    }
    @Test

    public void testGenerateTokenNumBits() throws Exception {
        // Tokens can only be tested in length. They should be random
        String string = "1234567890";
        String token = generator.getRandomToken(50);
        assertNotNull(token);
        assertTrue(token.getBytes().length == 10);
    }
}
