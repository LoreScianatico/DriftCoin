package com.lorescianatico.driftcoin.service;

import com.lorescianatico.driftcoin.config.DriftcoinSettings;
import com.lorescianatico.driftcoin.model.BlockChain;
import com.lorescianatico.driftcoin.repository.BlockChainRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Mono;

import static org.junit.Assert.assertEquals;
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

    @Mock
    private DriftcoinSettings settings;

    @Before
    public void setUp() {
        initMocks(this);
        when(settings.getDifficulty()).thenReturn(2);
    }

    @Test
    public void mineBlock() {

        when(blockChainRepository.save(any(BlockChain.class))).thenAnswer(invocationOnMock -> {
            BlockChain chain = invocationOnMock.getArgument(0);
            chain.setId("A");
            return Mono.just(chain);
        });
        BlockChain block = miner.createChain().block();
        assertNotNull(block);
        assertEquals("A",block.getId());
    }
}