package com.lorescianatico.driftcoin.model;

import com.lorescianatico.driftcoin.util.HashUtil;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Data
@Builder
@Document
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class BlockChain {

    @Id
    private String id;

    @Singular
    private List<Block> blocks;

    public int size(){
        if (this.blocks == null){
            return 0;
        }
        return this.blocks.size();
    }

    public boolean isEmpty(){
        if (blocks == null) {
            return  true;
        }
        return this.blocks.isEmpty();
    }

    public boolean isChainValid() {
        Block currentBlock;
        Block previousBlock;

        if (isEmpty()){
            return true;
        }

        if(!isFirstBlockValid(blocks.get(0))){
            return false;
        }

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
        if(this.blocks==null){
            blocks = new ArrayList<>();
        }
        List<Block> newBlocks = new ArrayList<>(blocks);
        newBlocks.add(block);
        this.setBlocks(newBlocks);
    }
}
