package com.lorescianatico.driftcoin.model;


import java.util.Arrays;

import static com.lorescianatico.driftcoin.model.BlockChain.*;

public class BlockFactory {

    private BlockFactory(){}

    public static Block getBlock(String previousHash, String message) {
        return new Block(previousHash, message);
    }

    public static BlockChain getBlockChain(){
        return new BlockChain();
    }

    public static BlockChain getBlockChain(Block... blocks){
        BlockChainBuilder builder = BlockChain.builder();
        Arrays.stream(blocks).forEach(builder::block);
        return builder.build();
    }
}
