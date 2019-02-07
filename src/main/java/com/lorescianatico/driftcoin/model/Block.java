package com.lorescianatico.driftcoin.model;

import com.lorescianatico.driftcoin.util.HashUtil;
import lombok.*;

import java.util.Date;

@EqualsAndHashCode
@ToString
@Getter
public class Block {

    @Setter
    @NonNull
    private String hash;

    @NonNull
    @Setter
    private String previousHash;

    @NonNull
    @Setter
    private String message; //our data will be a simple message.

    private long timeStamp = new Date().getTime(); //as number of milliseconds since 1/1/1970.

    private long nonce = 0;

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