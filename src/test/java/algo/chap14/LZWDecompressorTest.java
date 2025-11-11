package algo.chap14;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class LZWDecompressorTest {

    @Test
    void decompress_ABABCABCABC_returnsOriginalText() {
        List<Integer> compressed = List.of(65, 66, 256, 67, 258, 258);
        String result = LZWDecompressor.lzwDecompress(compressed);
        assertEquals("ABABCABCABC", result);
    }

    @Test
    void decompress_emptyList_returnsEmptyString() {
        List<Integer> compressed = List.of();
        String result = LZWDecompressor.lzwDecompress(compressed);
        assertEquals("", result);
    }
}
