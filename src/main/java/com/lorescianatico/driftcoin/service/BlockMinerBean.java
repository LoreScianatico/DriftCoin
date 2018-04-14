package com.lorescianatico.driftcoin.service;

import com.lorescianatico.driftcoin.fault.NotFoundException;
import com.lorescianatico.driftcoin.model.Block;
import com.lorescianatico.driftcoin.model.BlockChain;
import com.lorescianatico.driftcoin.model.BlockFactory;
import com.lorescianatico.driftcoin.repository.BlockChainRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@Slf4j
public class BlockMinerBean implements BlockMiner {

    public static final int DIFFICULTY = 5;

    @Autowired
    private BlockChainRepository blockChainRepository;

    @Override
    public Mono<BlockChain> createChain() {
        String target = new String(new char[DIFFICULTY]).replace('\0', '0');

        Block block = BlockFactory.getBlock("0", "Mined block.");
        while(!block.getHash().startsWith(target)) {
            block.rehash();
        }
        logger.info("Block Mined!!! : " + block.getHash());

        BlockChain chain = BlockFactory.getBlockChain(block);

        return blockChainRepository.save(chain);
    }

    @Override
    public Mono<BlockChain> mineBlock(String chainId) {
        String target = new String(new char[DIFFICULTY]).replace('\0', '0');

        Optional<BlockChain> chainOptional = blockChainRepository.findById(Mono.justOrEmpty(chainId)).blockOptional();

        BlockChain chain = chainOptional.orElseThrow(() -> new NotFoundException("BlockChain not found: " + chainId));

        String lastHash = chain.getBlocks().get(chain.size()-1).getHash();

        Block block = BlockFactory.getBlock(lastHash, "Mined block.");
        while(!block.getHash().startsWith(target)) {
            block.rehash();
        }
        logger.info("Block Mined!!! : " + block.getHash());

        chain.addBlock(block);

        return blockChainRepository.save(chain);
    }
}
