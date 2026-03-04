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
    }
}