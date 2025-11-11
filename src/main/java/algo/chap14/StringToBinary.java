package algo.chap14;

public class StringToBinary {
    public static void main(String[] args) {
        String text = "Hello World";
        StringBuilder binary = new StringBuilder();

        for (char c : text.toCharArray()) {
            String bin = String.format("%8s", Integer.toBinaryString(c))
                             .replace(' ', '0'); // complète à 8 bits
            binary.append(bin);
        }

        System.out.println(binary.toString());
    }
}

