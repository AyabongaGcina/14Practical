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
        this.table = new Node[m + 1];  // indices 1 to m (ignore index 0)
        this.size = 0;
    }



}