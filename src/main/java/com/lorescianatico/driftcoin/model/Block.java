package com.lorescianatico.driftcoin.model;

import com.lorescianatico.driftcoin.util.HashUtil;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;

@Data
public class Block {

    private String hash;
    private String previousHash;
    private String message; //our data will be a simple message.
    private long timeStamp = new Date().getTime(); //as number of milliseconds since 1/1/1970.
    private int nonce = 0;

    Block(String previousHashToSet, String messageToSet) {
        this.previousHash = previousHashToSet;
        this.message = messageToSet;
        this.hash = HashUtil.applySha256(this);
    }

    public void rehash(){
        nonce++;
        this.hash = HashUtil.applySha256(this);
    }
}