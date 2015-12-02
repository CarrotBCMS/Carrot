package com.boxedfolder.carrot.domain.util;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
public interface RandomTokenGenerator {
    String getRandomToken();
    String getRandomToken(int numBits);
}

