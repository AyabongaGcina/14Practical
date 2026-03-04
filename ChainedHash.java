import java.util.LinkedList;

public class ChainedHash {

    // Node class to store key-value pairs
    private static class Node {
        int key;
        String value;

        Node(int key, String value) {
            this.key = key;
            this.value = value;
        }
    }
    // Array of linked lists
    private LinkedList<Node>[] table;  
    private int m;                       // Size of the table
    private int size;                     // Number of items inserted

    // Constructor
    @SuppressWarnings("unchecked")
    public ChainedHash(int tableSize) {
        this.m = tableSize;
        this.table = new LinkedList[m + 1];  // indices 1 to m
        this.size = 0;

        // Initialize each slot with an empty linked list
        for (int i = 1; i <= m; i++) {
            table[i] = new LinkedList<>();
        }
    }

    // Hash function - same as open hashing
    private int hash(int key) {
        return (key % m) + 1;
    }

    // Insert a key-value pair
    public void insert(int key, String value) {
        int index = hash(key);
        LinkedList<Node> chain = table[index];

        // Check if key already exists in this chain
        for (Node node : chain) {
            if (node.key == key) {
                // Found it - update value
                node.value = value;
                return;
            }
        }

        // Key not found - add new node to the END of the list
        chain.add(new Node(key, value));
        size++;
    }

    // Look up a key and return its value
    public String lookup(int key) {
        int index = hash(key);
        LinkedList<Node> chain = table[index];

        // Search the chain
        for (Node node : chain) {
            if (node.key == key) {
                return node.value;  // Found it
            }
        }
        return null;  // Not found
    }

    // Check if key is in table
    public boolean isInTable(int key) {
        return lookup(key) != null;
    }

    // Get current load factor
    public double loadFactor() {
        return (double) size / m;
    }

    // Clear the table for new experiment
    @SuppressWarnings("unchecked")
    public void clear() {
        table = new LinkedList[m + 1];
        for (int i = 1; i <= m; i++) {
            table[i] = new LinkedList<>();
        }
        size = 0;
    }
}

