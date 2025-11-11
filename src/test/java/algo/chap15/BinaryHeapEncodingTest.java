package algo.chap15;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class BinaryHeapEncodingTest {

    @Test
    void testGenerateDNASequence() {
        String text = BinaryHeapEncoding.generateDNASequence(4, 20);
        assertEquals(20, text.length());
        assertTrue(text.matches("[ACGT]+"));
    }

    @Test
    void testCountFrequencies() {
        Map<String, Integer> freq = BinaryHeapEncoding.countFrequencies("AAGCTT");
        assertEquals(2, freq.get("A"));
        assertEquals(1, freq.get("G"));
        assertEquals(1, freq.get("C"));
        assertEquals(2, freq.get("T"));
    }

    @Test
    void testBinaryHeapInsertionOrder() {
        BinaryHeapEncoding.BinaryHeap heap = new BinaryHeapEncoding.BinaryHeap();
        heap.insert("A", 6);
        heap.insert("C", 4);
        heap.insert("G", 2);
        heap.insert("T", 2);

        List<Map.Entry<String, Integer>> h = heap.getHeap();
        // La racine devrait être le plus grand élément
        assertEquals("A", h.get(0).getKey());
        assertEquals("C", h.get(1).getKey());
        assertEquals("G", h.get(2).getKey());
        assertEquals("T", h.get(3).getKey());

        assertTrue(h.stream().anyMatch(e -> e.getKey().equals("C")));
    }

    @Test
    void testEncodingBuild() {
        BinaryHeapEncoding.BinaryHeap heap = new BinaryHeapEncoding.BinaryHeap();
        heap.insert("A", 6);
        heap.insert("C", 4);
        heap.insert("G", 2);
        heap.insert("T", 2);

        Map<String, String> encoding = BinaryHeapEncoding.buildEncoding(heap);

        assertEquals(4, encoding.size());
        assertEquals("0", encoding.get("A"));
        assertEquals("10", encoding.get("C"));
        assertEquals("110", encoding.get("G"));
        assertEquals("111", encoding.get("T"));
        assertTrue(encoding.keySet().containsAll(List.of("A", "C", "G", "T")));
    }
}
