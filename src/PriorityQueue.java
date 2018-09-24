public class PriorityQueue {

    private int heapsize;

    private Node [] nodes;

    public PriorityQueue(int size) {
        nodes = new Node[size];
    }

    private int parent(int i) {
        return (int) Math.ceil(i / 2.0) - 1;
    }

    private int leftChild(int i) {
        return (2 * i) + 1;
    }

    private int rightChild(int i) {
        return (2 * i) + 2;
    }

    private void minHeapify(int i) {
        int l, r, smallest;

        l = leftChild(i);
        r = rightChild(i);

        if (l < heapsize && nodes[l].getFrequency() < nodes[i].getFrequency())
            smallest = l;
        else smallest = i;

        if (r < heapsize && nodes[r].getFrequency() < nodes[smallest].getFrequency())
            smallest = r;

        if (smallest != i) {
            exchange(i, smallest);
            minHeapify(smallest);
        }
    }

    private void exchange(int i, int j) {
        Node swap = new Node();

        swap.setData(nodes[i].getData());
        swap.setFrequency(nodes[i].getFrequency());
        swap.setLeft(nodes[i].getLeft());
        swap.setRight(nodes[i].getRight());

        nodes[i].setData(nodes[j].getData());
        nodes[i].setFrequency(nodes[j].getFrequency());
        nodes[i].setLeft(nodes[j].getLeft());
        nodes[i].setRight(nodes[j].getRight());

        nodes[j].setData(swap.getData());
        nodes[j].setFrequency(swap.getFrequency());
        nodes[j].setLeft(swap.getLeft());
        nodes[j].setRight(swap.getRight());
    }

    public Node heapExtractMin() {
        if (heapsize <= 0) {
            System.out.println("Heap Underflow");
            return null;
        }

        Node min = new Node();
        min.setData(nodes[0].getData());
        min.setFrequency(nodes[0].getFrequency());
        min.setLeft(nodes[0].getLeft());
        min.setRight(nodes[0].getRight());

        nodes[0].setData(nodes[heapsize - 1].getData());
        nodes[0].setFrequency(nodes[heapsize - 1].getFrequency());
        nodes[0].setLeft(nodes[heapsize - 1].getLeft());
        nodes[0].setRight(nodes[heapsize - 1].getRight());

        heapsize--;

        minHeapify(0);

        return min;
    }

    private void heapDecreaseKey(int i, Node key) {
        if (key.getFrequency() > nodes[i].getFrequency())
            System.out.println("New key is bigger than current key");

        nodes[i].setData(key.getData());
        nodes[i].setFrequency(key.getFrequency());
        nodes[i].setLeft(key.getLeft());
        nodes[i].setRight(key.getRight());

        while (i > 0 && nodes[parent(i)].getFrequency() > nodes[i].getFrequency()) {
            exchange(i, parent(i));
            i = parent(i);
        }
    }

    public void minHeapInsert(Node key) {
        heapsize++;
        nodes[heapsize - 1] = new Node();
        nodes[heapsize - 1].setFrequency(Integer.MAX_VALUE);
        heapDecreaseKey(heapsize - 1, key);
    }
}