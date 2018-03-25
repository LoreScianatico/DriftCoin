package com.lorescianatico.driftcoin.service;

import com.lorescianatico.driftcoin.model.BlockChain;
import reactor.core.publisher.Mono;

public interface BlockMiner {

    BlockChain mineBlock();

    Mono<BlockChain> createChain();

    Mono<BlockChain> mineBlock(String chainId);

}
