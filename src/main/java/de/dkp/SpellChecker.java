package de.dkp;

public class SpellChecker {

    BloomFilter bloomFilter;

    public SpellChecker(BloomFilter bloomFilter) {
        this.bloomFilter = bloomFilter;
    }

    public boolean check(String sentence) {
        String[] words = sentence.split(" ");
        for (int i = 0 ; i< words.length ; i++) {
            if(!this.bloomFilter.contains(words[i])) return false;
        }
        return true;
    }
}
