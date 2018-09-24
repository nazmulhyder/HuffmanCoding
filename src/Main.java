import java.util.Scanner;

public class Main {
    public static void main(String [] args) {
        String string = "ENGINEERING";
        HuffmanCoding huffmanCoding = new HuffmanCoding(string.toCharArray());
        huffmanCoding.frequencyCount();
        Node tree = huffmanCoding.huffmanTreeConstruction();
        tree.code = null;
        huffmanCoding.huffmanCodeGeneration(tree);
        huffmanCoding.createCharactersCode(tree);
        huffmanCoding.stringToCode();
        huffmanCoding.compression();
        huffmanCoding.decompression();
        System.out.print("\nEnter Character: ");
        Scanner scanner = new Scanner(System.in);
        char c = scanner.next(".").charAt(0);
        huffmanCoding.outputCode(c);
    }
}
