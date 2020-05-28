package de.dkp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpellCheckTest {

    SpellChecker spellChecker;

    @BeforeEach
    void setUp() throws IOException {
        BloomFilter bloomFilter = new BloomFilter();
        initialiseBloomFilter(bloomFilter);
        this.spellChecker = new SpellChecker(bloomFilter);

    }

    private void initialiseBloomFilter(BloomFilter bloomFilter) throws IOException {
        bloomFilter.add(WordReader.readWords("/wordlist.txt"));
    }

    @Test
    void testACorrectSentence() {
        assertEquals(true, this.spellChecker.check("Correct sentence"));
    }

    @Test
    void testAnIncorrectSentence() {
        assertEquals(false, this.spellChecker.check("blahblahblahblahblahblahbalhblahblahblahblahblahblahblahblahblahbalhblahblahblah"));
    }
}
