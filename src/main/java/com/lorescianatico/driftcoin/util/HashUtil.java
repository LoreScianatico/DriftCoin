package com.lorescianatico.driftcoin.util;
import com.google.common.hash.Hashing;
import com.lorescianatico.driftcoin.model.Block;

import java.nio.charset.StandardCharsets;

public class HashUtil {

    private HashUtil() {}

    //Applies Sha256 to a string and returns the result.
    public static String applySha256(String input) {
        return Hashing.sha256().hashString(input, StandardCharsets.UTF_8).toString();
    }

    //Applies Sha256 to a string and returns the result.
    public static String applySha256(Block input) {
        return applySha256(input.getMessage() + input.getPreviousHash() + Long.toString(input.getTimeStamp()) + Integer.toString(input.getNonce()));
    }
}
