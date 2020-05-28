package de.dkp;


import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.BitSet;
import java.util.List;
import java.util.Random;

public class BloomFilter {

    private static final int DEFAULT_BITSET_SIZE = 5000000;
    private static final String ALGORITHM = "MD5";
    private int totalNoOfHashFunctions = 5;
    private int noOfBitsToBeExtracted = 5;
    private MessageDigest hasher;
    private int[] randomArray;
    private BitSet bitSet;

    public BloomFilter() {
        this.bitSet = new BitSet(DEFAULT_BITSET_SIZE);
        this.hasher = getDefaultMessageDigest();
        this.randomArray = getRandomArray(totalNoOfHashFunctions * noOfBitsToBeExtracted);
    }

    public BloomFilter(BitSet bitSet,
                       MessageDigest messageDigest,
                       int totalNoOfHashFunctions,
                       int noOfBitsToBeExtracted) {
        this.bitSet = bitSet;
        this.hasher = messageDigest;
        this.totalNoOfHashFunctions = totalNoOfHashFunctions;
        this.noOfBitsToBeExtracted = noOfBitsToBeExtracted;
        this.getRandomArray(totalNoOfHashFunctions * noOfBitsToBeExtracted);
    }

    public void add(List<String> words) {
        for (String word : words) {
            add(word.toLowerCase().getBytes());
        }
    }

    public boolean contains(String word) {
        for (int hash : createHashes(word.toLowerCase().getBytes())) {
            if (!this.bitSet.get(getBitIndex(hash))) {
                return false;
            }
        }
        return true;
    }

    private int[] createHashes(byte[] data) {
        int[] result = new int[totalNoOfHashFunctions];
        for (int i = 0; i < totalNoOfHashFunctions; i++) {
            this.hasher.update(BigInteger.valueOf(i).toByteArray());
            result[i] = extractNextBit(i, this.hasher.digest(data));
        }
        return result;
    }

    private int extractNextBit(int i, byte[] digest) {
        int tempResult = 1;
        int counter = 0;
        while (counter < noOfBitsToBeExtracted) {
            tempResult = tempResult * digest[this.randomArray[(i * noOfBitsToBeExtracted) + counter]];
            counter++;
        }
        return tempResult;
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

    private MessageDigest getDefaultMessageDigest() {
        try {
            return MessageDigest.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
