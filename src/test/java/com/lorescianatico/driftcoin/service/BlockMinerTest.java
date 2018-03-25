package com.lorescianatico.driftcoin.service;

import com.lorescianatico.driftcoin.model.BlockChain;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
public class BlockMinerTest {

    @Autowired
    private BlockMiner miner;

    @TestConfiguration
    static class BlockMinerImplTestContextConfiguration {

        @Bean
        public BlockMiner minerService() {
            return new BlockMinerBean();
        }
    }

    @Test
    public void mineBlock() {
            BlockChain block = miner.mineBlock();
            assertNotNull(block);
    }
}