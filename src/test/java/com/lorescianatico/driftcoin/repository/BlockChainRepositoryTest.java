package com.lorescianatico.driftcoin.repository;

import com.lorescianatico.driftcoin.model.BlockChain;
import com.lorescianatico.driftcoin.model.BlockFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataMongoTest
public class BlockChainRepositoryTest {

    @Autowired
    private BlockChainRepository blockChainRepository;

    @Test
    public void testSave(){
        BlockChain blockChain = BlockFactory.getBlockChain();

        assertNull(blockChain.getId());

        blockChain = blockChainRepository.save(blockChain).block();

        BlockChain retrieved = blockChainRepository.findById(blockChain.getId()).block();

        assertEquals(blockChain.getId(), retrieved.getId());
    }

}