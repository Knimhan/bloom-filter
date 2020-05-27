package de.dkp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class BloomFilterImplTest {

    private BloomFilterImpl bloomFilterImpl;

    public BloomFilterImplTest() {
        this.bloomFilterImpl = new BloomFilterImpl();
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
}
