import java.util.Scanner;

/**
 * Simple Knapsack Algorithm
 * <p>
 * Input Format:
 * <No. of weights>
 * <Knapsack capacity>
 * <Item 1 weight> <Item 1 cost>
 * <Item 2 weight> <Item 2 cost>
 * ...
 * <Item n weight> <Item n cost>
 * <p>
 * <p>
 * Variations of the Knapsack:-
 * Divisible Knapsack
 * Indivisible Knapsack
 * Unbounded Knapsack
 */


public class Knapsack {

    private int weightCount;
    private int capacity;
    private int weights[];
    private int cost[];

    public static void main(String[] args) {
        Knapsack knapsack = new Knapsack();
        knapsack.getUserInputs();
        knapsack.indivisibleKnapsack();
        knapsack.unboundedKnapsack();
        knapsack.divisibleKnapsack();
    }

    public void getUserInputs() {
        Scanner scan = new Scanner(System.in);
        weightCount = scan.nextInt();
        capacity = scan.nextInt();
        weights = new int[weightCount];
        cost = new int[weightCount];
        for (int i = 0; i < weightCount; i++) {
            weights[i] = scan.nextInt();
            cost[i] = scan.nextInt();
        }

    }

    public void divisibleKnapsack() {
        System.out.println("\n:Divisible Knapsack:");
        double costPerUnit[] = new double[weightCount];
        for (int i = 0; i < weightCount; i++) {
            costPerUnit[i] = ((double) cost[i]) / weights[i];
        }
        for (int i = 0; i < weightCount; i++) {
            for (int j = i + 1; j < weightCount; j++) {
                if (costPerUnit[i] < costPerUnit[j]) {
                    double temp = costPerUnit[i];
                    costPerUnit[i] = costPerUnit[j];
                    costPerUnit[j] = temp;
                    int temp1 = weights[i];
                    weights[i] = weights[j];
                    weights[j] = temp1;
                    temp1 = cost[i];
                    cost[i] = cost[j];
                    cost[j] = temp1;
                }
            }
        }

        int remainingCapacity = capacity;
        double totalCost = 0;
        int counter = 0;
        while (remainingCapacity > 0) {
            if (counter >= weightCount)
                break;
            if (remainingCapacity >= weights[counter]) {
                remainingCapacity -= weights[counter];
                totalCost += cost[counter++];
            } else {
                totalCost += costPerUnit[counter++] * remainingCapacity;
                remainingCapacity = 0;
            }

            System.out.println("Cost:" + totalCost);
        }
        System.out.println("Maximum Cost:" + totalCost);
    }

    public void indivisibleKnapsack() {
        System.out.println("\n:Indivisible Knapsack:");
        int dpArray[][] = new int[weightCount + 1][capacity + 1];

        for (int i = 1; i < weightCount + 1; i++) {
            for (int j = 1; j < capacity + 1; j++) {
                if (weights[i - 1] <= j) {
                    dpArray[i][j] = max(dpArray[i - 1][j - weights[i - 1]] + cost[i - 1], dpArray[i - 1][j]);
                }
            }
        }
        System.out.println("Maximum Cost:" + dpArray[weightCount][capacity]);
    }

    public void unboundedKnapsack() {
        System.out.println("\n:Unbounded Knapsack:");
        int dpArray[] = new int[capacity + 1];

        for (int i = 0; i < capacity + 1; i++) {
            for (int j = 0; j < weightCount; j++) {
                if (weights[j] <= i) {
                    dpArray[i] = max(dpArray[i - weights[j]] + cost[j], dpArray[i]);
                }
            }
        }
        System.out.println("Maximum Cost:" + dpArray[capacity]);

    }

    public int max(int value1, int value2) {
        if (value1 > value2)
            return value1;
        return value2;
    }
}
