/*
 * Carrot - beacon management
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
