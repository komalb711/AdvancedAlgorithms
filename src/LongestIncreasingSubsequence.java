import java.util.Scanner;

public class LongestIncreasingSubsequence {

    private int numberCount;
    private int numbers[];

    public static void main(String[] args) {
        LongestIncreasingSubsequence obj = new LongestIncreasingSubsequence();
        obj.getUserInputs();
        obj.simpleLIS();
    }

    public void getUserInputs() {
        Scanner scan = new Scanner(System.in);
        numberCount = scan.nextInt();
        numbers = new int[numberCount];
        for (int i = 0; i < numberCount; i++) {
            numbers[i] = scan.nextInt();
        }
    }

    public void simpleLIS() {
        int dpArray[] = new int[numberCount];
        for (int i = 0; i < numberCount; i++) {
            dpArray[i] = 1;
        }
        for (int i = 0; i < numberCount; i++) {
            for (int j = 0; j < numberCount; j++) {
                if (numbers[j] < numbers[i] && dpArray[j] >= dpArray[i]) {
                    dpArray[i] = dpArray[j] + 1;
                }
            }
        }

        int max = -1;
        int maxIdx = -1;
        for (int i = 0; i < numberCount; i++) {
            if (dpArray[i] > max) {
                max = dpArray[i];
                maxIdx = i;
            }
        }

        System.out.println("Lenght of LIS:"+ max);

        int currIdx = maxIdx;
        String msg = String.valueOf(numbers[currIdx]);
        for (int i = maxIdx; i >= 0; i--) {
            if(dpArray[currIdx]-1 ==dpArray[i]){
                msg = numbers[i] + " " + msg;
                currIdx = i;
            }
        }
        System.out.println(msg);
    }

    public void optimizedLIS(){

    }


}
