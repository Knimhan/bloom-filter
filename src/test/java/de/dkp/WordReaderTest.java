package de.dkp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class WordReaderTest {

    private BloomFilter bloomFilter;

    public WordReaderTest() {
        this.bloomFilter = new BloomFilter();
    }

    @Test
    public void testBloomFilterImplForIOException() {

        Assertions.assertThrows(IOException.class, () -> {
            String filename = "/fakefilename.txt";
            WordReader.readWords(filename);
        });
    }

    @Test
    public void testBloomFilterImplIfItReadsEntireFile() {

        String filename = "/wordlist.txt";
        try {
            List<String> words = WordReader.readWords(filename);
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
            this.bloomFilter.add(WordReader.readWords(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(true, this.bloomFilter.contains(input));
    }
}
