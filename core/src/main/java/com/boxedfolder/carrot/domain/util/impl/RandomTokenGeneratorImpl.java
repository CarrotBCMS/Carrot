package com.boxedfolder.carrot.domain.util.impl;

import com.boxedfolder.carrot.domain.util.RandomTokenGenerator;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@Component
public class RandomTokenGeneratorImpl implements RandomTokenGenerator {
    private SecureRandom random = new SecureRandom();

    @Override
    public String getRandomToken() {
        return getRandomToken(130);
    }

    @Override
    public String getRandomToken(int numBits) {
        return new BigInteger(numBits, random).toString(32);
    }
}
