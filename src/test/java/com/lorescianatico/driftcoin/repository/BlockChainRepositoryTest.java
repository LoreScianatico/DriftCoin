package com.lorescianatico.driftcoin.repository;

import com.lorescianatico.driftcoin.model.BlockChain;
import com.lorescianatico.driftcoin.model.BlockFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataMongoTest
public class BlockChainRepositoryTest {

    @Autowired
    private BlockChainRepository blockChainRepository;

    @Test
    public void testSave(){
        BlockChain blockChain = BlockFactory.getBlockChain();

        assertNull(blockChain.getId());

        blockChain = blockChainRepository.save(blockChain)
                .blockOptional().orElseThrow(NullPointerException::new);

        BlockChain retrieved = blockChainRepository.findById(blockChain.getId())
                .blockOptional().orElseThrow(NullPointerException::new);

        assertEquals(blockChain.getId(), retrieved.getId());
    }

}