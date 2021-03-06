package com.lorescianatico.driftcoin.model;

import com.lorescianatico.driftcoin.util.HashUtil;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Document
@RequiredArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public final class BlockChain {

    @Id
    private String id;

    @Singular
    @NonNull
    private List<Block> blocks;

    BlockChain(){
        this(new ArrayList<>());
    }

    public int size(){
        return this.blocks.size();
    }

    public boolean isEmpty(){
        return this.blocks.isEmpty();
    }

    public boolean isChainValid() {

        if (isEmpty()){
            return true;
        }

        if(!isFirstBlockValid(blocks.get(0))){
            return false;
        }

        Block currentBlock;
        Block previousBlock;

        //loop through blockchain to check hashes:
        for(int i=1; i < blocks.size(); i++) {
            currentBlock = blocks.get(i);
            previousBlock = blocks.get(i-1);
            //compare registered hash and calculated hash:
            if(!currentBlock.getHash().equals(HashUtil.applySha256(currentBlock))){
                return false;
            }
            //compare previous hash and registered previous hash
            if(!previousBlock.getHash().equals(currentBlock.getPreviousHash()) ) {
                return false;
            }
        }
        return true;
    }

    private boolean isFirstBlockValid(Block block) {
        return ("0".equals(block.getPreviousHash()) && HashUtil.applySha256(block).equals(block.getHash()));
    }

    public void addBlock(Block block) {
        this.blocks.add(block);
    }
}
