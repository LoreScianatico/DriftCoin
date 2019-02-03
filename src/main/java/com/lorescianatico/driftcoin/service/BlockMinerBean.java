package com.lorescianatico.driftcoin.service;

import com.lorescianatico.driftcoin.config.DriftcoinSettings;
import com.lorescianatico.driftcoin.fault.NotFoundException;
import com.lorescianatico.driftcoin.model.Block;
import com.lorescianatico.driftcoin.model.BlockChain;
import com.lorescianatico.driftcoin.model.BlockFactory;
import com.lorescianatico.driftcoin.repository.BlockChainRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;


@Service
@Slf4j
public class BlockMinerBean implements BlockMiner {

    private DriftcoinSettings settings;

    private BlockChainRepository blockChainRepository;

    private String target;

    public BlockMinerBean(BlockChainRepository blockChainRepository, DriftcoinSettings driftcoinSettings){
        this.blockChainRepository = blockChainRepository;
        this.settings = driftcoinSettings;
        target = new String(new char[settings.getDifficulty()]).replace('\0', '0');
    }

    @Override
    public Mono<BlockChain> createChain() {
        String target = new String(new char[settings.getDifficulty()]).replace('\0', '0');

        Block block = BlockFactory.getBlock("0", "Mined block.");
        while (!block.getHash().startsWith(target)) {
            block.rehash();
        }
        logger.info("Block Mined!!! : " + block.getHash());

        BlockChain chain = BlockFactory.getBlockChain(block);

        return blockChainRepository.save(chain);
    }

    @Override
    public Mono<BlockChain> mineBlock(final String chainId) {

        return blockChainRepository.findById(chainId)
                .switchIfEmpty(Mono.error(new NotFoundException("BlockChain not found: " + chainId)))
                .flatMap(this::addBlockToExistingChain);
    }

    private Mono<BlockChain> addBlockToExistingChain(BlockChain blockChain){
        logger.debug("Found blockchain with id {} and size {}", blockChain.getId(), blockChain.size());

        String lastHash = blockChain.getBlocks().get(blockChain.size() - 1).getHash();

        Block block = BlockFactory.getBlock(lastHash, "Mined block.");
        while (!block.getHash().startsWith(target)) {
            block.rehash();
        }
        logger.info("Block Mined!!! : " + block.getHash());

        blockChain.addBlock(block);

        return blockChainRepository.save(blockChain);
    }
}
