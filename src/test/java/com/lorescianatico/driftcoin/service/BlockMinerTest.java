package com.lorescianatico.driftcoin.service;

import com.lorescianatico.driftcoin.config.DriftcoinSettings;
import com.lorescianatico.driftcoin.fault.NotFoundException;
import com.lorescianatico.driftcoin.model.Block;
import com.lorescianatico.driftcoin.model.BlockChain;
import com.lorescianatico.driftcoin.model.BlockFactory;
import com.lorescianatico.driftcoin.repository.BlockChainRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Mono;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BlockMinerTest {

    @InjectMocks
    private BlockMinerBean miner;

    @Mock
    private BlockChainRepository blockChainRepository;

    @Spy
    private DriftcoinSettings settings = new DriftcoinSettings(2);

    @Before
    public void setUp() {
        when(blockChainRepository.save(any(BlockChain.class))).thenAnswer(invocationOnMock -> {
            BlockChain chain = invocationOnMock.getArgument(0);
            chain.setId("A");
            return Mono.just(chain);
        });
    }

    @Test
    public void createChain() {
        BlockChain block = miner.createChain().block();
        assertNotNull(block);
        assertEquals("A",block.getId());
    }

    @Test
    public void mineBlock(){
        when(blockChainRepository.findById(anyString())).thenAnswer(invocationOnMock -> {
            BlockChain chain = BlockFactory.getBlockChain();
            Block block = BlockFactory.getBlock("0", "msg");
            chain.addBlock(block);
            chain.setId("A");
            return Mono.just(chain);
        });
        BlockChain chain = blockChainRepository.findById("A").block();
        int size = chain.size();
        BlockChain chainNew = miner.mineBlock("A").block();
        assertEquals(size+1, chainNew.size());
    }

    @Test(expected = NotFoundException.class)
    public void unexistingChain(){
        when(blockChainRepository.findById(anyString())).thenReturn(Mono.empty());
        miner.mineBlock("B").block();
    }
}