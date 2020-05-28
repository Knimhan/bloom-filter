package de.dkp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class BloomFilterImplTest {

    private BloomFilterImpl bloomFilterImpl;
    private BloomFilter bloomFilter;

    public BloomFilterImplTest() {
        this.bloomFilterImpl = new BloomFilterImpl();
        this.bloomFilter = new BloomFilter();
    }

    @Test
    public void testBloomFilterImplForIOException() {

        Assertions.assertThrows(IOException.class, () -> {
            String filename = "/fakefilename.txt";
            this.bloomFilterImpl.readWordList(filename);
        });
    }

    @Test
    public void testBloomFilterImplIfItReadsEntireFile() {

        String filename = "/wordlist.txt";
        try {
            List<String> words = this.bloomFilterImpl.readWordList(filename);
            String lastWord = words.get(words.size() - 1);
            assertEquals("�v�nements", lastWord);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"Abbevillean", "Abbottstown's", "roussettes", "salaciousness's"})
    public void testBloomFilterToReadFileAndIfContainsWords(String input) {
        String filename = "/wordlist.txt";
        try {
            for (String word : this.bloomFilterImpl.readWordList(filename)) {
                this.bloomFilter.add(word);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(true, this.bloomFilter.contains(input));
    }
}
