import java.util.Scanner;

/**
 * Bin Packing Algorithms
 * Online Algorithms
 * 1) Next Fit
 * 2) First Fit
 * 3) Best Fit
 * Offline Algorithm
 * 4) Decreasing First Fit
 * <p>
 * Input Format:
 * <Number of Weights>
 * <Bin Capacity>
 * <Weights separated by spaces>
 */

public class BinPacking {

    private int weightCount;
    private int weights[];
    private int binCapacity;
    private int bins[];
    private int weightAllocations[];

    public static void main(String[] args) {
        BinPacking pbProb = new BinPacking();
        pbProb.getUserInputs();
        pbProb.resetBins();
        pbProb.nextFit();
        pbProb.resetBins();
        pbProb.firstFit();
        pbProb.resetBins();
        pbProb.bestFit();
        pbProb.resetBins();
        pbProb.decreasingFirstFit();
    }

    void getUserInputs() {
        Scanner scan = new Scanner(System.in);
        weightCount = scan.nextInt();
        binCapacity = scan.nextInt();
        weights = new int[weightCount];
        weightAllocations = new int[weightCount];
        bins = new int[weightCount];
        for (int i = 0; i < weightCount; i++) {
            weights[i] = scan.nextInt();
        }
    }

    void resetBins() {
        for (int i = 0; i < weightCount; i++) {
            weightAllocations[i] = -1;
            bins[i] = binCapacity;
        }
    }

    void nextFit() {
        System.out.println("\n:Next Fit:");
        int binsCounter = 0;
        int counter = 0;
        for (int weight : weights) {
            if (bins[binsCounter] >= weight) {
                bins[binsCounter] -= weight;
            } else {
                binsCounter += 1;
                bins[binsCounter] = bins[binsCounter] - weight;
            }
            weightAllocations[counter++] = binsCounter + 1;
        }
        System.out.println("Number of bins Required:" + (binsCounter + 1));
        for (int i = 0; i < weightCount; i++) {
            System.out.println("Weight " + weights[i] + ": Bin " + weightAllocations[i]);
        }

    }

    void firstFit() {
        System.out.println("\n:First Fit:");
        int counter = 0;
        for (int weight : weights) {
            for (int i = 0; i < weightCount; i++) {
                if (bins[i] >= weight) {
                    bins[i] -= weight;
                    weightAllocations[counter++] = i;
                    break;
                }
            }
        }
        int binsRequired = 0;
        for (int i = 0; i < weightCount; i++) {
            if (bins[i] < binCapacity) {
                binsRequired++;
            }
        }
        System.out.println("Number of bins Required:" + binsRequired);
        for (int i = 0; i < weightCount; i++) {
            System.out.println("Weight " + weights[i] + ": Bin " + weightAllocations[i]);
        }
    }

    void bestFit() {
        System.out.println("\n:Best Fit:");
        int counter = 0;
        int minRemainingWeight = binCapacity + 1;
        int minRemainingBin = -1;
        for (int weight : weights) {
            for (int i = 0; i < weightCount; i++) {
                if (bins[i] >= weight && minRemainingWeight > bins[i]) {
                    minRemainingWeight = bins[i];
                    minRemainingBin = i;
                }
            }
            if (minRemainingBin != -1) {
                weightAllocations[counter++] = minRemainingBin + 1;
                bins[minRemainingBin] -= weight;
                minRemainingBin = -1;
                minRemainingWeight = binCapacity + 1;
                continue;
            }
        }

        int binsRequired = 0;
        for (int i = 0; i < weightCount; i++) {
            if (bins[i] != binCapacity) {
                binsRequired++;
            }
        }

        System.out.println("Number of bins Required:" + binsRequired);
        for (int i = 0; i < weightCount; i++) {
            System.out.println("Weight " + weights[i] + ": Bin " + weightAllocations[i]);
        }
    }

    void decreasingFirstFit() {
        System.out.println("\n:Decreasing First Fit:");
        int counter = 0;
        int minRemainingWeight = binCapacity + 1;
        for (int weight : weights) {
            for (int i = 0; i < weightCount; i++) {
                if (bins[i] >= weight && minRemainingWeight > bins[i]) {
                    weightAllocations[counter++] = i + 1;
                    bins[i] -= weight;
                    break;
                }
            }
        }

        int binsRequired = 0;
        for (int i = 0; i < weightCount; i++) {
            if (bins[i] != binCapacity) {
                binsRequired++;
            }
        }

        System.out.println("Number of bins Required:" + binsRequired);
        for (int i = 0; i < weightCount; i++) {
            System.out.println("Weight " + weights[i] + ": Bin " + weightAllocations[i]);
        }
    }

}
