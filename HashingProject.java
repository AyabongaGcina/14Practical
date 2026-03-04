//Ayabonga Gcina
//4446494
//04 March 2026
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Random;

public class HashingProject{
    public static void main(String[]args){
       // Fix any comma/decimal point issue if there is one.
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        DecimalFormat defined = new DecimalFormat("0.00000", symbols);

        System.out.println("--------------------------------------------------");
        System.out.println("HASHING PERFORMANCE COMPARISON EXPERIMENT");
        System.out.printl("----------------------------------------------------");
       
       // Generating the data
        System.out.println("\n[STEP 1] Generating test data...");
        DataGenerator.Pair[] allData = DataGenerator.generateData();

        int N = 950000;
        DataGenerator.Pair[] data = DataGenerator.getFirstK(allData, N);
        System.out.println("Using first " + N + " key-value pairs for testing");

        double[] loadFactors = {0.75, 0.80, 0.85, 0.90, 0.95};
        int repetitions = 30;

        System.out.println("\n[STEP 2] Testing at these load factors:");
        for (double lf : loadFactors) {
            System.out.println("  - " + (lf * 100) + "%");
        }
        System.out.println("Each test will run " + repetitions + " repetitions");
        System.out.println("Each repetition performs 30 random key lookups");

        System.out.println("\n-------------------------------------------------");
        System.out.println("RESULTS TABLE");
        System.out.println("---------------------------------------------------");
        System.out.printf("%-12s %-8s %-15s %-15s %-15s %-15s\n",
                "Load Factor", "m", "Open Avg(ms)", "Open StdDev",
                "Chain Avg(ms)", "Chain StdDev");
        System.out.println("--------------------------------------------------------------------------------");

        for (double loadFactor : loadFactors) {
            runTest(data, loadFactor, repetitions, df);
        }

        System.out.println("\n------------------------------------------------");
        System.out.println("EXPERIMENT COMPLETE");
        System.out.println("--------------------------------------------------");
    }

    private static void runTest(DataGenerator.Pair[] data, double loadFactor,
                                int reps, DecimalFormat df) {

        int N = data.length;
        int m = (int) Math.ceil(N / loadFactor);

        while (!isPrime(m)) {
            m++;
        }

        int itemsToInsert = (int) (loadFactor * m);
        if (itemsToInsert > N) {
            itemsToInsert = N;
        }
        System.out.printf("\nTesting at %.0f%% load factor...\n", loadFactor * 100);
        System.out.println("  Table size m = " + m);
        System.out.println("  Inserting " + itemsToInsert + " items");

        // Use 300 keys temporarily to get the  measurable times
        int[] testKeys = new int[300];  // INCREASED FROM 30 TO 300
        Random rand = new Random();
        for (int i = 0; i < 300; i++) {
            testKeys[i] = data[rand.nextInt(itemsToInsert)].key;
        }

        double openTotalTime = 0;
        double openTotalTimeSq = 0;
        double chainTotalTime = 0;
        double chainTotalTimeSq = 0;

        // Checking if the lookups are working
        int openSuccessCount = 0;
        int chainSuccessCount = 0;

        for (int rep = 0; rep < reps; rep++) {

            OpenHash openTable = new OpenHash(m);
            ChainedHash chainTable = new ChainedHash(m);

            // Insert data
            for (int i = 0; i < itemsToInsert; i++) {
                openTable.insert(data[i].key, data[i].value);
                chainTable.insert(data[i].key, data[i].value);
            }

            // Check load factors
            if (rep == 0) {
                System.out.println("    Open hash load factor: " + openTable.loadFactor());
                System.out.println("    Chained hash load factor: " + chainTable.loadFactor());
            }

            // Test one key to verify lookup works
            if (rep == 0) {
                String openResult = openTable.lookup(testKeys[0]);
                String chainResult = chainTable.lookup(testKeys[0]);
                System.out.println("    Test key " + testKeys[0] +
                        " - Open: " + (openResult != null ? "FOUND" : "MISSING") +
                        ", Chain: " + (chainResult != null ? "FOUND" : "MISSING"));
            }

            //Time open hash
            long start = System.currentTimeMillis();
            for (int key : testKeys) {
                String result = openTable.lookup(key);
                if (result != null) openSuccessCount++;
            }
            long finish = System.currentTimeMillis();
            double time = finish - start;

            openTotalTime += time;
            openTotalTimeSq += time * time;

            // Time chained hash
            start = System.currentTimeMillis();
            for (int key : testKeys) {
                String result = chainTable.lookup(key);
                if (result != null) chainSuccessCount++;
            }
            finish = System.currentTimeMillis();
            time = finish - start;

            chainTotalTime += time;
            chainTotalTimeSq += time * time;

            shuffleArray(testKeys);
        }

        // Report success rates
        int totalLookups = reps * 300;
        System.out.println("Open hash success rate: " + openSuccessCount + "/" + totalLookups);
        System.out.println("Chained hash success rate: " + chainSuccessCount + "/" + totalLookups);

        double openAvg = openTotalTime / reps;
        double openStdDev = Math.sqrt((openTotalTimeSq / reps) - (openAvg * openAvg));

        double chainAvg = chainTotalTime / reps;
        double chainStdDev = Math.sqrt((chainTotalTimeSq / reps) - (chainAvg * chainAvg));

        System.out.printf("%-12.0f %-8d %-15s %-15s %-15s %-15s\n",
                loadFactor * 100, m,
                df.format(openAvg), df.format(openStdDev),
                df.format(chainAvg), df.format(chainStdDev));
    }

    private static boolean isPrime(int n) {
        if (n <= 1) return false;
        if (n <= 3) return true;
        if (n % 2 == 0 || n % 3 == 0) return false;

        for (int i = 5; i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) return false;
        }
        return true;
    }

    private static void shuffleArray(int[] array) {
        Random rand = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int index = rand.nextInt(i + 1);
            int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }
}
