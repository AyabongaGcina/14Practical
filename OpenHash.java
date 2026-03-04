public class OpenHash{
    private static class Node{
        int key;
        String value; 
        Node(int key,String value){
            this.key=key;
            this.value=value;
        }

    }
     // The hash table array
    private Node[] table;  
    private int m;          // Size of the table
    private int size;       // Number of items inserted

    // Constructor
    public OpenHash(int tableSize) {
        this.m = tableSize;
        this.table = new Node[m + 1];  // indices 1 to m (ignores the index 0)
        this.size = 0;
    }
    // Hash function: maps key to index between 1 and m
    private int hash(int key) {
        return (key % m) + 1;
    }

    // Insert a key-value pair
    public void insert(int key, String value) {
        int index = hash(key);
        int originalIndex = index;

        // Linear probing - find next empty slot
        while (table[index] != null) {
            // If key already exists, update value
            if (table[index].key == key) {
                table[index].value = value;
                return;
            }
            // Move to next slot
            index = (index % m) + 1;

            // If we've gone all the way around, table is full
            if (index == originalIndex) {
                System.out.println("Error: Table is full!");
                return;
            }
        }

        // Found empty slot
        table[index] = new Node(key, value);
        size++;
    }

    // Look up a key and return its value
    public String lookup(int key) {
        int index = hash(key);
        int originalIndex = index;

        // Search until we find key or hit empty slot
        while (table[index] != null) {
            if (table[index].key == key) {
                return table[index].value;  // Found it
            }
            index = (index % m) + 1;

            if (index == originalIndex) {
                break;  // Went all the way around
            }
        }
        return null;  // Not found
    }

    // Get current load factor
    public double loadFactor() {
        return (double) size / m;
    }

    // Clear the table for new experiment
    public void clear() {
        table = new Node[m + 1];
        size = 0;
    }
}



