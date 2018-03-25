package com.lorescianatico.driftcoin.repository;

import com.lorescianatico.driftcoin.model.BlockChain;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockChainRepository extends ReactiveCrudRepository<BlockChain, String> {
}
