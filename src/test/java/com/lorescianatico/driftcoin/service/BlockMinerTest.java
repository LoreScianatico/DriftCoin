package com.lorescianatico.driftcoin.service;

import com.lorescianatico.driftcoin.model.BlockChain;
import com.lorescianatico.driftcoin.model.BlockFactory;
import com.lorescianatico.driftcoin.repository.BlockChainRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Mono;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class BlockMinerTest {

    @InjectMocks
    private BlockMinerBean miner;

    @Mock
    private BlockChainRepository blockChainRepository;

    private BlockChain chain;

    @Before
    public void setUp() {
        initMocks(this);
        chain = BlockFactory.getBlockChain();
    }

    @Test
    public void mineBlock() {

        when(blockChainRepository.save(any(BlockChain.class))).thenReturn(Mono.just(chain));
        BlockChain block = miner.createChain().block();
        assertNotNull(block);
    }
}