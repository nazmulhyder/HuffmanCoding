public class HuffmanCoding {

    public int [] charactersFrequency;
    public Node [] characters;
    private PriorityQueue priorityQueue;
    private char [] chars;
    private Code stringCode, compressedCode, decompressedCode;

    public HuffmanCoding(char [] chars) {
        this.chars = new char[chars.length];
        System.arraycopy(chars, 0, this.chars, 0, chars.length);
        charactersFrequency = new int[256];
        characters = new Node[256];
        priorityQueue = new PriorityQueue(chars.length);
    }

    public void frequencyCount() {
        for (int i = 0; i < 256; i++) {
            characters[i] = new Node();
            characters[i].setData((char) i);
            characters[i].code = null;
            charactersFrequency[i] = 0;
        }

        for (int i = 0; i < chars.length; i++) {
            int low = 0, high = 255;
            boolean found = false;
            while (low <= high && !found) {
                int mid = (low + high) / 2;
                if (mid == chars[i]) {
                    charactersFrequency[mid]++;
                    found = true;
                }
                else if (mid > chars[i])
                    high = mid - 1;
                else low = mid + 1;
            }

        }
    }

    private int createNodes() {
        int totalNodes = 0;
        for (int i = 0; i < 256; i++) {
            if (charactersFrequency[i] > 0) {
                Node node = new Node();
                node.setData((char) i);
                node.setFrequency(charactersFrequency[i]);
                node.setLeft(null);
                node.setRight(null);
                priorityQueue.minHeapInsert(node);
                totalNodes++;
            }
        }
        return totalNodes;
    }

    public Node huffmanTreeConstruction() {
        int totalNodes = createNodes();

        if (totalNodes == 1) {
            Node node = new Node();
            Node left = priorityQueue.heapExtractMin();
            node.setLeft(left);
            node.setRight(null);
            node.setFrequency(left.getFrequency());
            priorityQueue.minHeapInsert(node);
        }
        for (int i = 1; i <= totalNodes - 1; i++) {
            Node node = new Node();
            Node left = priorityQueue.heapExtractMin();
            Node right = priorityQueue.heapExtractMin();
            node.setLeft(left);
            node.setRight(right);
            node.setFrequency(left.getFrequency() + right.getFrequency());
            priorityQueue.minHeapInsert(node);
        }

        return priorityQueue.heapExtractMin();
    }

    public void huffmanCodeGeneration(Node node) {
        Code code, code1, code2;

        if (node.getLeft() != null) {
            node.getLeft().code = null;
            code = node.code;

            while (code != null) {
                if (node.getLeft().code == null) {
                    node.getLeft().code = new Code();
                    node.getLeft().code.setBit(code.getBit());
                    node.getLeft().code.setNext(null);
                }
                else {
                    code1 = node.getLeft().code;
                    while (code1.getNext() != null)
                        code1 = code1.getNext();
                    code2 = new Code();
                    code2.setBit(code.getBit());
                    code2.setNext(null);
                    code1.setNext(code2);
                }

                code = code.getNext();
            }

            if (node.getLeft().code == null) {
                node.getLeft().code = new Code();
                node.getLeft().code.setBit(0);
                node.getLeft().code.setNext(null);
            }
            else {
                code1 = node.getLeft().code;
                while (code1.getNext() != null)
                    code1 = code1.getNext();
                code2 = new Code();
                code2.setBit(0);
                code2.setNext(null);
                code1.setNext(code2);
            }

            huffmanCodeGeneration(node.getLeft());
        }

        if (node.getRight() != null) {
            node.getRight().code = null;
            code = node.code;

            while (code != null) {
                if (node.getRight().code == null) {
                    node.getRight().code = new Code();
                    node.getRight().code.setBit(code.getBit());
                    node.getRight().code.setNext(null);
                }
                else {
                    code1 = node.getRight().code;
                    while (code1.getNext() != null)
                        code1 = code1.getNext();
                    code2 = new Code();
                    code2.setBit(code.getBit());
                    code2.setNext(null);
                    code1.setNext(code2);
                }

                code = code.getNext();
            }

            if (node.getRight().code == null) {
                node.getRight().code = new Code();
                node.getRight().code.setBit(1);
                node.getRight().code.setNext(null);
            }
            else {
                code1 = node.getRight().code;
                while (code1.getNext() != null)
                    code1 = code1.getNext();
                code2 = new Code();
                code2.setBit(1);
                code2.setNext(null);
                code1.setNext(code2);
            }

            huffmanCodeGeneration(node.getRight());
        }
    }

    private void setCharacterCode(Node node) {
        if ((int) node.getData() >= 0) {
            characters[node.getData()].code = node.code;
        }
    }

    public void createCharactersCode(Node node) {
        if (node.getLeft() != null) {
            if (node.getLeft().getLeft() == null && node.getLeft().getRight() == null)
                setCharacterCode(node.getLeft());

            createCharactersCode(node.getLeft());
        }
        if (node.getRight() != null) {
            if (node.getRight().getLeft() == null && node.getRight().getRight() == null)
                setCharacterCode(node.getRight());

            createCharactersCode(node.getRight());
        }
    }

    public void stringToCode() {
        Code characterCodePointer, stringCodePointer, stringCodeNext;

        for (int i = 0; i < chars.length; i++) {
            if (stringCode == null) {
                stringCode = new Code();
                stringCode.setBit(characters[chars[i]].code.getBit());
                stringCode.setNext(null);

                characterCodePointer = characters[chars[i]].code.getNext();
            }
            else characterCodePointer = characters[chars[i]].code;

            while (characterCodePointer != null) {
                stringCodePointer = stringCode;
                while (stringCodePointer.getNext() != null)
                    stringCodePointer = stringCodePointer.getNext();
                stringCodeNext = new Code();
                stringCodeNext.setBit(characterCodePointer.getBit());
                stringCodeNext.setNext(null);
                stringCodePointer.setNext(stringCodeNext);

                characterCodePointer = characterCodePointer.getNext();
            }
        }
    }

    private int binaryTodecimal(int [] bits, int n) {
        int decimal = 0;
        int i = 0;
        double j = n - 1;

        while (i < n) {
            if (bits[i] == 1)
                decimal += (int) Math.pow(2.0, j);
            i++;
            j--;
        }

        return decimal;
    }

    public void compression() {
        Code stringCodePointer, compressedCodePointer = null, code;
        int [] bits = new int[8];
        int i = 0, totalCompressed = 0;
        stringCodePointer = stringCode;
        while (stringCodePointer != null) {
            bits[i++] = stringCodePointer.getBit();
            if (i == 8 || stringCodePointer.getNext() == null) {
                int decimalValue = binaryTodecimal(bits, i);
                i = 0;
                if (compressedCode == null) {
                    compressedCode = new Code();
                    compressedCode.setBit(decimalValue);
                    compressedCode.setNext(null);
                    compressedCodePointer = compressedCode;
                }
                else {
                    code = new Code();
                    code.setBit(decimalValue);
                    code.setNext(null);
                    if (compressedCodePointer != null)
                        compressedCodePointer.setNext(code);
                    compressedCodePointer = code;
                }
                totalCompressed++;
            }
            stringCodePointer = stringCodePointer.getNext();
        }

        System.out.println("Compressed String:");
        compressedCodePointer = compressedCode;
        while (compressedCodePointer != null) {
            System.out.print((char) compressedCodePointer.getBit());
            compressedCodePointer = compressedCodePointer.getNext();
        }
        System.out.println("\nCompressed String Length: " + totalCompressed);
        System.out.println("Compression: " +  ((chars.length - totalCompressed) / (float) chars.length) * 100 + " %");
    }

    private int [] decimalToBinary(int decimalValue, int length) {
        int [] binary = new int[8];
        int i = 0, j;
        while (decimalValue != 0) {
            binary[i++] = decimalValue % 2;
            decimalValue /= 2;
        }
        if (length != 0) {
            for (j = i; j < length; j++)
                binary[j] = 0;
        }
        else if (i < 8) {
            for (j = i; j < 8; j++)
                binary[j] = 0;
        }
        else j = i;

        for (i = 0; i < j / 2; i++) {
            binary[i] = binary[i] + binary[j - 1 - i];
            binary[j - 1 - i] = binary[i] - binary[j - 1 - i];
            binary[i] = binary[i] - binary[j - 1 - i];
        }

        return binary;
    }

    private void printDecompressedString() {
        Code decompressedCodePointer, decompressedCodeNextPointer, characterCodePointer;

        System.out.println("After Decompressed:");
        decompressedCodePointer = decompressedCode;
        while (decompressedCodePointer != null) {
            boolean matchedCharacter = false;
            for (int i = 0; i < 256 && !matchedCharacter; i++) {
                if (characters[i].code != null && characters[i].code.getBit() == decompressedCodePointer.getBit()) {
                    decompressedCodeNextPointer = decompressedCodePointer.getNext();
                    characterCodePointer = characters[i].code.getNext();
                    while (characterCodePointer != null && decompressedCodeNextPointer != null && characterCodePointer.getBit() == decompressedCodeNextPointer.getBit()) {
                        characterCodePointer = characterCodePointer.getNext();
                        decompressedCodeNextPointer = decompressedCodeNextPointer.getNext();
                    }
                    if (characterCodePointer == null) {
                        System.out.print(characters[i].getData());
                        decompressedCodePointer = decompressedCodeNextPointer;
                        matchedCharacter = true;
                    }
                }
            }
        }
        System.out.println();
    }

    public void decompression() {
        int [] binary;
        int n, lastIndex, binaryLength, k;
        Code stringCodePointer, compressedCodePointer, decompressedCodePointer = null;

        int stringCodeLength = 0;
        stringCodePointer = stringCode;
        while (stringCodePointer != null) {
            stringCodeLength++;
            stringCodePointer = stringCodePointer.getNext();
        }

        if (stringCodeLength % 8 != 0) {
            n = (stringCodeLength / 8) + 1;
            lastIndex = (stringCodeLength / 8) + 1;
            binaryLength = stringCodeLength % 8;
        }
        else if (stringCodeLength < 8) {
            n = 1;
            lastIndex = 1;
            binaryLength = stringCodeLength;
        }
        else {
            n = stringCodeLength / 8;
            lastIndex = 0;
            binaryLength = 0;
        }

        compressedCodePointer = compressedCode;

        for (int i = 1; i <= n; i++) {
            if (i == lastIndex) {
                binary = decimalToBinary(compressedCodePointer.getBit(), binaryLength);
                k = binaryLength;
            }
            else {
                binary = decimalToBinary(compressedCodePointer.getBit(), 0);
                k = 8;
            }
            for (int j = 0; j < k; j++) {
                if (decompressedCode == null) {
                    decompressedCode = new Code();
                    decompressedCode.setBit(binary[j]);
                    decompressedCode.setNext(null);
                    decompressedCodePointer = decompressedCode;
                }
                else {
                    Code code = new Code();
                    code.setBit(binary[j]);
                    code.setNext(null);
                    if (decompressedCodePointer != null)
                        decompressedCodePointer.setNext(code);
                    decompressedCodePointer = code;
                }
            }

            compressedCodePointer = compressedCodePointer.getNext();
        }

        printDecompressedString();
    }

    public void outputCode(char c) {
        int low = 0, high = 255;
        int cIndex = -1;
        while (low <= high && cIndex == -1) {
            int mid = (low + high) / 2;
            if (mid == c && charactersFrequency[mid] > 0)
                cIndex = mid;
            else if (mid > c)
                high = mid - 1;
            else low = mid + 1;
        }

        if (cIndex == - 1)
            System.out.println("Not Found");
        else {
            Code code = characters[cIndex].code;
            while (code != null) {
                System.out.print(code.getBit());
                code = code.getNext();
            }
        }
    }
}