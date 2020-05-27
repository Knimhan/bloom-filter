package de.dkp;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class BloomFilterImpl {

    private static String filename = "/wordlist.txt";

    public static List<String> readWordList(String filename) throws IOException {
        BufferedReader br = null;
        List<String> list = new ArrayList<>();
        InputStream inputStream = BloomFilterImpl.class.getResourceAsStream(filename);
        if (inputStream == null) throw new FileNotFoundException(filename + " not found");
        InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        br = new BufferedReader(streamReader);
        String available;
        while ((available = br.readLine()) != null) {
            list.add(available);
        }
        br.close();
        return list;
    }

    public static void main(String[] args) {
        try {
            List<String> wordsInDictionary = readWordList(filename);
            BloomFilter bloomFilter = null;
            bloomFilter = new BloomFilter();
            for (String word : wordsInDictionary) {
                bloomFilter.add(word);
            }
            System.out.println("Check if contains first word:  "+ bloomFilter.contains("A'asia"));
            System.out.println("Check if contains first word:  "+ bloomFilter.contains("�v�nements"));
            System.out.println("Check if does not contain a word:  "+ bloomFilter.contains("SOMEFAKEWORD"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
