package algo.chap14;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.zip.Deflater;

public class CompareCompression {
    public static void main(String[] args) {
        try {
            // 1. Télécharger le texte
            String urlString = "https://github.com/lmassaron/datasets/releases/download/1.0/1661-0.txt";
            URL url = new URL(urlString);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line).append("\n");
            }
            in.close();

            // 2. Tronquer les 932 premiers caractères
            String sh = sb.toString().substring(932);
            int shLength = sh.length();

            // 3. Créer une chaîne aléatoire de même longueur
            Random rand = new Random();
            StringBuilder rnd = new StringBuilder();
            for (int i = 0; i < shLength; i++) {
                rnd.append((char) (rand.nextInt(127))); // caractères ASCII 0–126
            }

            // 4. Fonction de compression (zlib)
            int zippedSh = zipped(sh);
            int zippedRnd = zipped(rnd.toString());

            // 5. Affichage des résultats
            System.out.println("Original size for both texts: " + shLength + " characters");
            System.out.println("The Adventures of Sherlock Holmes to " + zippedSh);
            System.out.println("Random file to " + zippedRnd);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Fonction équivalente à zlib.compress(text.encode())
    public static int zipped(String text) {
        try {
            byte[] input = text.getBytes(StandardCharsets.UTF_8);
            Deflater deflater = new Deflater();
            deflater.setInput(input);
            deflater.finish();

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream(input.length);
            byte[] buffer = new byte[1024];
            while (!deflater.finished()) {
                int count = deflater.deflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
            return outputStream.toByteArray().length;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
