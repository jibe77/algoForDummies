package algo.chap12;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.BitSet;

/**
 * Implémentation pédagogique d’un filtre de Bloom.
 * - m et k sont calculés à partir de n (taille attendue) et fpp (false positive probability).
 * - Hachage : schéma de Kirsch–Mitzenmacher à partir de deux hachages SHA-256.
 */
public class BloomFilter<T> {

    private final BitSet bits;
    private final int m;        // taille du tableau de bits
    private final int k;        // nombre de fonctions de hash
    private final MessageDigest md; // SHA-256

    public BloomFilter(long expectedInsertions, double fpp) {
        if (expectedInsertions <= 0) throw new IllegalArgumentException("expectedInsertions > 0");
        if (fpp <= 0 || fpp >= 1) throw new IllegalArgumentException("0 < fpp < 1");

        // m = - (n * ln p) / (ln 2)^2
        this.m = (int) Math.ceil(-(expectedInsertions * Math.log(fpp)) / (Math.pow(Math.log(2), 2)));
        // k = (m / n) * ln 2
        this.k = Math.max(1, (int) Math.round((m / (double) expectedInsertions) * Math.log(2)));

        this.bits = new BitSet(m);
        try {
            this.md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /** Ajoute un élément. */
    public void put(T element) {
        long[] hh = hash64Pair(element);
        long h1 = hh[0], h2 = hh[1];
        for (int i = 0; i < k; i++) {
            int pos = positiveMod(h1 + i * h2, m);
            bits.set(pos);
        }
    }

    /** Teste la présence probable (jamais de faux négatif, possibles faux positifs). */
    public boolean mightContain(T element) {
        long[] hh = hash64Pair(element);
        long h1 = hh[0], h2 = hh[1];
        for (int i = 0; i < k; i++) {
            int pos = positiveMod(h1 + i * h2, m);
            if (!bits.get(pos)) return false;
        }
        return true;
    }

    public int bitSize() { return m; }
    public int hashCount() { return k; }

    // --- Helpers ---

    /**
     * Produit deux hachages 64 bits h1, h2 à partir de SHA-256(element || salt).
     * Schéma suffisant pour une démo pédagogique.
     */
    private long[] hash64Pair(T element) {
        byte[] base = String.valueOf(element).getBytes(StandardCharsets.UTF_8);

        byte[] d1 = digestWithSalt(base, (byte) 0x00);
        byte[] d2 = digestWithSalt(base, (byte) 0x01);

        long h1 = bytesToLong(d1, 0);
        long h2 = bytesToLong(d2, 8);
        // Evite h2 = 0 qui dégraderait la distribution
        if (h2 == 0) h2 = 0x9e3779b97f4a7c15L; // constante d’or 64-bit
        return new long[]{h1, h2};
    }

    private byte[] digestWithSalt(byte[] base, byte salt) {
        md.reset();
        md.update(base);
        md.update(salt);
        return md.digest();
    }

    private static long bytesToLong(byte[] b, int off) {
        long v = 0;
        for (int i = 0; i < 8; i++) {
            v = (v << 8) | (b[off + i] & 0xFFL);
        }
        return v;
    }

    private static int positiveMod(long x, int mod) {
        long r = x % mod;
        return (int) (r < 0 ? r + mod : r);
    }

    // Petit exemple d’usage
    public static void main(String[] args) {
        BloomFilter<String> bf = new BloomFilter<>(10_000, 0.01);
        bf.put("alice");
        bf.put("bob");
        System.out.printf("m=%d, k=%d%n", bf.bitSize(), bf.hashCount());
        System.out.println("alice ? " + bf.mightContain("alice")); // true
        System.out.println("carol ? " + bf.mightContain("carol")); // true/false (probable false)
    }
}
