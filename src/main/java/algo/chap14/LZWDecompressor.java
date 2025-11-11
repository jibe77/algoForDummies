package algo.chap14;

import java.util.*;

public class LZWDecompressor {

    public static String lzwDecompress(List<Integer> encoded) {
        if (encoded == null || encoded.isEmpty()) {
            return "";
        }

        // Dictionnaire inversé initial (code → caractère)
        Map<Integer, String> reverseDictionary = new HashMap<>();
        for (int i = 0; i < 256; i++) {
            reverseDictionary.put(i, String.valueOf((char) i));
        }
        int nextCode = 256;

        int current = encoded.get(0);
        String output = reverseDictionary.get(current);
        System.out.println("Decompressed " + output);
        System.out.println(">" + output);

        for (int i = 1; i < encoded.size(); i++) {
            int previous = current;
            current = encoded.get(i);

            String s;
            if (reverseDictionary.containsKey(current)) {
                s = reverseDictionary.get(current);
                System.out.println("Decompressed " + s);
                output += s;
                System.out.println(">" + output);

                reverseDictionary.put(nextCode, reverseDictionary.get(previous) + s.charAt(0));
                System.out.printf("New dictionary entry %s at index %s%n",
                        reverseDictionary.get(previous) + s.charAt(0), nextCode);
                nextCode++;
            } else {
                // Cas spécial : la séquence n'existe pas encore dans le dictionnaire
                String prev = reverseDictionary.get(previous);
                s = prev + prev.charAt(0);
                System.out.printf("Not found: %s Output: %s%n", current, s);

                reverseDictionary.put(nextCode, s);
                System.out.printf("New dictionary entry %s at index %s%n", s, nextCode);
                nextCode++;

                System.out.println("Decompressed " + s);
                output += s;
                System.out.println(">" + output);
            }
        }

        return output;
    }

    public static void main(String[] args) {
        List<Integer> compressed = List.of(65, 66, 256, 67, 258, 258);
        String decompressed = lzwDecompress(compressed);
        System.out.println("\nDecompressed text: " + decompressed);
    }
}
