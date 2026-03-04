import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
public class Data{
    //Where N =2^20 =1,048,576
    public static final int N = 1 << 20;
    // Pair class to hold key-value pairs
    public static class Pair {
        public int key;
        public String value;

        public Pair(int key, String value) {
            this.key = key;
            this.value = value;
        }
    }
    // Generating all the data
    public static Pair[] generateData() {
        System.out.println("Generating " + N + " key-value pairs...");
        // Create an array of keys 1 to N
        Integer[] keys = new Integer[N];
        for (int i = 0; i < N; i++) {
            keys[i] = i + 1;
        }
        // Shuffle the keys
        List<Integer> keyList = Arrays.asList(keys);
        Collections.shuffle(keyList);
        keys = keyList.toArray(new Integer[0]);

        // Create pairs
        Pair[] data = new Pair[N];
        for (int i = 0; i < N; i++) {
            data[i] = new Pair(keys[i], String.valueOf(i + 1));
        }


}