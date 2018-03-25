package com.lorescianatico.driftcoin.service;

import com.lorescianatico.driftcoin.model.Block;
import com.lorescianatico.driftcoin.model.BlockChain;
import com.lorescianatico.driftcoin.model.BlockFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BlockMinerBean implements BlockMiner {

    BlockChain chain = null;

    @Override
    public BlockChain mineBlock() {
        String target = new String(new char[5]).replace('\0', '0');
        String lastHash = "0";
        if(chain != null && !chain.isEmpty())
        {
            lastHash=chain.getBlocks().get(chain.size()-1).getHash();
        }

        Block block = BlockFactory.getBlock(lastHash, "Mined block.");
        while(!block.getHash().startsWith(target)) {
            block.rehash();
        }
        log.info("Block Mined!!! : " + block.getHash());

        if (chain == null){
            chain = BlockFactory.getBlockChain(block);
        } else {
            chain.addBlock(block);
        }

        return chain;
    }
}
