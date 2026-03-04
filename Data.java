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

}