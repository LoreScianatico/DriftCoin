package com.lorescianatico.driftcoin.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DriftCoinConfiguration.class)
@TestPropertySource(properties = { "driftcoin.mining.difficulty = 5" })
public class DriftCoinConfigurationTest {

    @Autowired
    private DriftCoinConfiguration driftCoinConfiguration;

    @Test
    public void driftcoinSettings() {
        assertEquals(5, driftCoinConfiguration.driftcoinSettings().getDifficulty().intValue());
    }
}