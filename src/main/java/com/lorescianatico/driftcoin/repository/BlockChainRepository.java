package com.lorescianatico.driftcoin.repository;

import com.lorescianatico.driftcoin.model.BlockChain;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface BlockChainRepository extends ReactiveMongoRepository<BlockChain, String> {
}
