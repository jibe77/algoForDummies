package algo.chap14;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class LZWCompressorTest {

    @Test
    void compress_ABABCABCABC_matchesPython() {
        String text = "ABABCABCABC";
        List<Integer> result = LZWCompressor.lzwCompress(text);

        // Séquence attendue alignée sur l'implémentation Python donnée
        List<Integer> expected = List.of(65, 66, 256, 67, 258, 258);
        assertEquals(expected, result);
    }

    @Test
    void compress_singleChar() {
        String text = "A";
        List<Integer> result = LZWCompressor.lzwCompress(text);
        assertEquals(List.of(65), result);
    }

    @Test
    void compress_emptyString_returnsEmptyList() {
        String text = "";
        List<Integer> result = LZWCompressor.lzwCompress(text);
        assertTrue(result.isEmpty());
    }
}
