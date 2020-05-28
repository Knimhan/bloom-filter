package de.dkp;


import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.BitSet;
import java.util.Random;

public class BloomFilter {
    private int TOTAL_NO_OF_HASH_FUNCTIONS = 5;
    private int TOTAL_NO_OF_BITS_TOBE_EXTRACTED_FROM_HASH = 5;
    private MessageDigest hasher;
    private int[] randomArray;

    public BitSet bitSet;

    public BloomFilter() {
        try {
            this.bitSet = new BitSet(5000000);
            this.hasher = MessageDigest.getInstance("MD5");
            this.randomArray = getRandomArray(TOTAL_NO_OF_HASH_FUNCTIONS * TOTAL_NO_OF_BITS_TOBE_EXTRACTED_FROM_HASH);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BloomFilter(BitSet bitSet,
                       MessageDigest messageDigest,
                       int totalNoOfHashFunctions,
                       int noOfBitsToBeExtracted) {
        this.bitSet = bitSet;
        this.hasher = messageDigest;
        this.TOTAL_NO_OF_HASH_FUNCTIONS = totalNoOfHashFunctions;
        this.TOTAL_NO_OF_BITS_TOBE_EXTRACTED_FROM_HASH = noOfBitsToBeExtracted;
        this.getRandomArray(totalNoOfHashFunctions * noOfBitsToBeExtracted);
    }

    public void add(String word) {
        add(word.getBytes());
    }

    public boolean contains(String word) {
        for (int hash : createHashes(word.getBytes())) {
            if (!this.bitSet.get(getBitIndex(hash))) {
                return false;
            }
        }
        return true;
    }

    private int[] createHashes(byte[] data) {
        int[] result = new int[TOTAL_NO_OF_HASH_FUNCTIONS];
        for (int i = 0; i < TOTAL_NO_OF_HASH_FUNCTIONS; i++) {
            this.hasher.update(BigInteger.valueOf(i).toByteArray());
            byte[] digest = this.hasher.digest(data);
            int counter = 0;
            int tempResult = 1;
            while (counter < TOTAL_NO_OF_BITS_TOBE_EXTRACTED_FROM_HASH) {
                tempResult = tempResult * digest[this.randomArray[(i * TOTAL_NO_OF_BITS_TOBE_EXTRACTED_FROM_HASH) + counter]];
                counter++;
            }
            result[i] = tempResult;
            result[i] = tempResult;
        }
        return result;
    }

    private void add(byte[] bytes) {
        int[] hashes = createHashes(bytes);
        for (int hash : hashes)
            this.bitSet.set(getBitIndex(hash), true);
    }

    private int getBitIndex(int hash) {
        return Math.abs(hash % this.bitSet.size());
    }

    private int[] getRandomArray(int randomArraySize) {
        this.randomArray = new int[randomArraySize];
        Random random = new Random();
        for (int i = 0; i < this.randomArray.length; i++) {
            this.randomArray[i] = random.nextInt(16);
        }
        return this.randomArray;
    }
}
