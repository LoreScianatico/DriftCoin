package com.lorescianatico.driftcoin.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;


import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
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