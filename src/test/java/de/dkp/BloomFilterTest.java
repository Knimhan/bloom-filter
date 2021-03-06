package de.dkp;

import com.sun.tools.javac.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BloomFilterTest {

    private BloomFilter bloomFilter;

    public BloomFilterTest() {
        this.bloomFilter = new BloomFilter();
    }

    @ParameterizedTest
    @ValueSource(strings = {"�trennes", "zucchetti", "fossiliferous", "ABC's"})
    public void testBloomFilterContainsAWord(String word) {
        //given
        bloomFilter.add(List.of(word));

        //when
        boolean result = bloomFilter.contains(word);

        //then
        assertEquals(true, result);
    }

    @ParameterizedTest
    @ValueSource(strings = {"X", "Y", "Z", "s"})
    public void testBloomFilterDoesNotContainAWord(String word) {
        //given
        bloomFilter.add(List.of(word));

        //when
        boolean result = bloomFilter.contains(word + "not");

        //then
        assertEquals(false, result);
    }
}