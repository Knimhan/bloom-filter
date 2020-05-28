package de.dkp;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class WordReader {

    public static List<String> readWords(String filename) throws IOException {
        BufferedReader br = null;
        List<String> list = new ArrayList<>();
        InputStream inputStream = WordReader.class.getResourceAsStream(filename);
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
}
