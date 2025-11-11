package algo.chap13;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class MapReduceExample {

    public static void main(String[] args) throws Exception {
        String urlString = "https://github.com/lmassaron/datasets/releases/download/1.0/2600.txt";
        URL url = new URL(urlString);
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append(" ");
        }
        reader.close();

        long start = System.currentTimeMillis();

        // --- Préparation du texte ---
        String text = sb.toString().substring(627);
        List<String> words = Arrays.asList(text.split("\\s+"));
        System.out.println("Number of words: " + words.size());

        // --- Config ---
        int cores = Runtime.getRuntime().availableProcessors();
        System.out.println("You have " + cores + " cores available for MapReduce");

        List<String> keywords = Arrays.asList("WAR", "PEACE", "RUSSIA", "NAPOLEON");

        // --- Partition ---
        List<List<String>> partitions = partition(words, words.size() / cores + 1);

        // --- Map phase (multithreaded) ---
        List<List<Pair<String, Integer>>> mapResults =
                distribute(partitions, part -> countWords(part, keywords), cores);

        System.out.println("map_result is a list made of " + mapResults.size() + " elements");
        if (!mapResults.isEmpty()) {
            System.out.println("Preview of one element: " + mapResults.get(0).subList(0, Math.min(5, mapResults.get(0).size())));
        }

        // --- Shuffle ---
        List<List<Pair<String, Integer>>> shuffled = shuffleSort(mapResults);
        System.out.println("Shuffled is a list made of " + shuffled.size() + " elements");

        if (shuffled.size() > 0)
            System.out.println("Preview of first element: " + shuffled.get(0).subList(0, Math.min(5, shuffled.get(0).size())));

        if (shuffled.size() > 1)
            System.out.println("Preview of second element: " + shuffled.get(1).subList(0, Math.min(5, shuffled.get(1).size())));

        // --- Reduce ---
        List<Pair<String, Integer>> reduced = shuffled.stream()
                .map(MapReduceExample::reduce)
                .collect(Collectors.toList());

        System.out.println("\n--- Final Counts ---");
        for (Pair<String, Integer> p : reduced) {
            System.out.println(p.key + ": " + p.value);
        }

        long end = System.currentTimeMillis();
        System.out.println("Durée d'exécution : " + (end - start) + " ms");
    }

    // -------------------- Fonctions utilitaires --------------------

    public static String removePunctuation(String text) {
        return text.chars()
                .filter(Character::isLetter)
                .mapToObj(c -> String.valueOf((char) c))
                .collect(Collectors.joining());
    }

    public static List<Pair<String, Integer>> countWords(List<String> listOfWords, List<String> keywords) {
        List<Pair<String, Integer>> results = new ArrayList<>();
        for (String word : listOfWords) {
            String cleanWord = removePunctuation(word).toUpperCase();
            for (String keyword : keywords) {
                if (keyword.equals(cleanWord)) {
                    results.add(new Pair<>(keyword, 1));
                }
            }
        }
        return results;
    }

    public static <T> List<List<T>> partition(List<T> list, int size) {
        List<List<T>> parts = new ArrayList<>();
        for (int i = 0; i < list.size(); i += size) {
            parts.add(list.subList(i, Math.min(list.size(), i + size)));
        }
        return parts;
    }

    public static List<List<Pair<String, Integer>>> shuffleSort(List<List<Pair<String, Integer>>> data) {
        Map<String, List<Pair<String, Integer>>> mapping = new HashMap<>();
        for (List<Pair<String, Integer>> sublist : data) {
            for (Pair<String, Integer> kv : sublist) {
                mapping.computeIfAbsent(kv.key, k -> new ArrayList<>()).add(kv);
            }
        }
        return new ArrayList<>(mapping.values());
    }

    public static Pair<String, Integer> reduce(List<Pair<String, Integer>> mapping) {
        String key = mapping.get(0).key;
        int sum = mapping.stream().mapToInt(p -> p.value).sum();
        return new Pair<>(key, sum);
    }

    /**
     * Distribute: exécute une fonction sur chaque élément de la liste en parallèle
     * @param data liste de sous-listes
     * @param task fonction à exécuter
     * @param cores nombre de threads à utiliser
     * @param <T> type d'entrée
     * @param <R> type de sortie
     * @return liste des résultats par tâche
     */
    public static <T, R> List<R> distribute(List<T> data, FunctionWithException<T, R> task, int cores) {
        ExecutorService pool = Executors.newFixedThreadPool(cores);
        List<Future<R>> futures = new ArrayList<>();
        for (T item : data) {
            futures.add(pool.submit(() -> task.apply(item)));
        }

        List<R> results = new ArrayList<>();
        for (Future<R> f : futures) {
            try {
                results.add(f.get());
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
        pool.shutdown();
        return results;
    }

    // Interface fonctionnelle qui peut lever une exception
    @FunctionalInterface
    public interface FunctionWithException<T, R> {
        R apply(T t) throws Exception;
    }

    // Classe utilitaire Pair
    public static class Pair<K, V> {
        public final K key;
        public final V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return "(" + key + "," + value + ")";
        }
    }
}
