package algo.chap13;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class DownloadText {
    public static void main(String[] args) {
        String baseUrl = "https://github.com/lmassaron/datasets/releases/";
        String fullUrl = baseUrl + "download/1.0/2600.txt";

        try {
            // Ouvre la connexion
            URL url = new URL(fullUrl);

            // Lis tout le contenu du flux en UTF-8
            String text;
            try (Scanner scanner = new Scanner(url.openStream(), StandardCharsets.UTF_8)) {
                scanner.useDelimiter("\\A"); // lit tout le flux d’un coup
                text = scanner.hasNext() ? scanner.next() : "";
            }

            System.out.println("Taille du texte téléchargé : " + text.length() + " caractères");
            System.out.println("Nombre de mots : " + text.split("\\s+").length);

            // Coupe les 627 premiers caractères (comme [627:] en Python)
            if (text.length() > 627) {
                text = text.substring(627);
            }

            System.out.println(text.substring(0, Math.min(text.length(), 200))); // aperçu
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
