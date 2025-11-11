package algo.chap15;

import java.util.*;
import java.util.stream.Collectors;

public class BinaryHeapEncoding {

    // --- 1. Génération aléatoire d'ADN ---
    public static String generateDNASequence(long seed, int length) {
        List<String> generator = new ArrayList<>();
        for (int i = 0; i < 6; i++) generator.add("A");
        for (int i = 0; i < 4; i++) generator.add("C");
        for (int i = 0; i < 2; i++) generator.add("G");
        for (int i = 0; i < 2; i++) generator.add("T");

        Random random = new Random(seed);
        StringBuilder text = new StringBuilder();

        for (int i = 0; i < length; i++) {
            Collections.shuffle(generator, random);
            text.append(generator.get(0));
        }
        return text.toString();
    }

    // --- 2. Comptage des fréquences ---
    public static Map<String, Integer> countFrequencies(String text) {
        Map<String, Integer> freq = new HashMap<>();
        for (char c : text.toCharArray()) {
            String s = String.valueOf(c);
            freq.put(s, freq.getOrDefault(s, 0) + 1);
        }
        return freq;
    }

    // --- 3. Classe du tas binaire ---
    public static class BinaryHeap {
        private final List<Map.Entry<String, Integer>> heap = new ArrayList<>();

        private void swap(int i, int j) {
            Collections.swap(heap, i, j);
        }

        public void insert(String key, int value) {
            heap.add(Map.entry(key, value));
            int index = heap.size() - 1;

            while (index != 0) {
                int parent = (index - 1) / 2;
                if (heap.get(parent).getValue() < heap.get(index).getValue()) {
                    swap(parent, index);
                }
                index = parent;
            }
        }

        public List<Map.Entry<String, Integer>> getHeap() {
            return heap;
        }
    }

    // --- 4. Construction du codage simple ---
    public static Map<String, String> buildEncoding(BinaryHeap heap) {
        Map<String, String> encoding = heap.getHeap().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> ""));

        List<Map.Entry<String, Integer>> list = heap.getHeap();

        for (int i = 1; i < list.size(); i++) {
            List<Map.Entry<String, Integer>> aggregate = list.subList(list.size() - i, list.size());
            Map.Entry<String, Integer> newEntry = list.get(list.size() - i - 1);

            for (Map.Entry<String, Integer> item : aggregate) {
                encoding.put(item.getKey(), "1" + encoding.get(item.getKey()));
            }
            encoding.put(newEntry.getKey(), "0" + encoding.get(newEntry.getKey()));

            System.out.println(aggregate + " + " + newEntry + " =");
            System.out.println(encoding + "\n");
        }

        return encoding;
    }

    // --- Programme principal ---
    public static void main(String[] args) {
        String text = generateDNASequence(4, 1000);
        System.out.println(text.substring(0, 25) + " ...");

        Map<String, Integer> frequencies = countFrequencies(text);
        System.out.println(frequencies);

        BinaryHeap heap = new BinaryHeap();
        for (Map.Entry<String, Integer> entry : frequencies.entrySet()) {
            heap.insert(entry.getKey(), entry.getValue());
        }

        System.out.println(heap.getHeap());
        buildEncoding(heap);
    }
}
