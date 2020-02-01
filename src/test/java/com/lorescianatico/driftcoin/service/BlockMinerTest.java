package com.lorescianatico.driftcoin.service;

import com.lorescianatico.driftcoin.config.DriftcoinSettings;
import com.lorescianatico.driftcoin.fault.NotFoundException;
import com.lorescianatico.driftcoin.model.Block;
import com.lorescianatico.driftcoin.model.BlockChain;
import com.lorescianatico.driftcoin.model.BlockFactory;
import com.lorescianatico.driftcoin.repository.BlockChainRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BlockMinerTest {

    @InjectMocks
    private BlockMinerBean miner;

    @Mock
    private BlockChainRepository blockChainRepository;

    @Spy
    private DriftcoinSettings settings = new DriftcoinSettings(2);

    @Test
    public void createChain() {
        when(blockChainRepository.save(any(BlockChain.class))).thenAnswer(invocationOnMock -> {
            BlockChain chain = invocationOnMock.getArgument(0);
            chain.setId("A");
            return Mono.just(chain);
        });

        BlockChain block = miner.createChain().block();
        assertNotNull(block);
        assertEquals("A",block.getId());
    }

    @Test
    public void mineBlock(){
        when(blockChainRepository.save(any(BlockChain.class))).thenAnswer(invocationOnMock -> {
            BlockChain chain = invocationOnMock.getArgument(0);
            chain.setId("A");
            return Mono.just(chain);
        });

        when(blockChainRepository.findById(any(Mono.class))).thenAnswer(invocationOnMock -> {
            BlockChain chain = BlockFactory.getBlockChain();
            Block block = BlockFactory.getBlock("0", "msg");
            chain.addBlock(block);
            chain.setId("A");
            return Mono.just(chain);
        });

        BlockChain chain = blockChainRepository.findById(Mono.just("A")).block();
        int size = chain.size();
        BlockChain chainNew = miner.mineBlock(Mono.just("A")).block();
        assertEquals(size+1, chainNew.size());
    }

    @Test
    public void unexistingChain(){
        when(blockChainRepository.findById(any(Mono.class))).thenReturn(Mono.empty());
        assertThrows(NotFoundException.class, () -> miner.mineBlock(Mono.just("B")).block());
    }
}