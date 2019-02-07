package com.lorescianatico.driftcoin.model;


import java.util.Arrays;

public final class BlockFactory {

    private BlockFactory(){}

    public static Block getBlock(String previousHash, String message) {
        return new Block(previousHash, message);
    }

    public static BlockChain getBlockChain(){
        return new BlockChain();
    }

    public static BlockChain getBlockChain(Block... blocks){
        return BlockChain.builder().blocks(Arrays.asList(blocks)).build();
    }
}
