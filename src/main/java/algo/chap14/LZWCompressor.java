package algo.chap14;

import java.util.*;

public class LZWCompressor {

    public static List<Integer> lzwCompress(String text) {
        if (text == null || text.isEmpty()) {
            return List.of();
        }

        // Dictionnaire ASCII initial (0..255)
        Map<String, Integer> dictionary = new HashMap<>();
        for (int i = 0; i < 256; i++) {
            dictionary.put(String.valueOf((char) i), i);
        }
        int nextCode = 256; // prochain code libre

        List<Integer> encoded = new ArrayList<>();
        String s = String.valueOf(text.charAt(0));

        for (int i = 1; i < text.length(); i++) {
            char c = text.charAt(i);
            String sc = s + c;

            if (dictionary.containsKey(sc)) {
                s = sc; // on prolonge
            } else {
                // Sortie et logs comme en Python
                System.out.println("> " + s);
                encoded.add(dictionary.get(s));
                System.out.printf("found: %s compressed as %s%n", s, dictionary.get(s));

                dictionary.put(sc, nextCode);
                System.out.printf("New sequence %s indexed as %s%n", sc, nextCode);
                nextCode++;

                s = String.valueOf(c);
            }
        }

        // Dernier symbole
        encoded.add(dictionary.get(s));
        System.out.printf("found: %s compressed as %s%n", s, dictionary.get(s));

        return encoded;
    }

    public static void main(String[] args) {
        String text = "ABABCABCABC";
        List<Integer> compressed = lzwCompress(text);
        System.out.println("\nCompressed: " + compressed + "\n");
    }
}
