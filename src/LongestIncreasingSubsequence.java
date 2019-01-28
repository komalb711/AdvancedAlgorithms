import java.util.Scanner;

public class LongestIncreasingSubsequence {

    private int numberCount;
    private int numbers[];

    public static void main(String[] args) {
        LongestIncreasingSubsequence obj = new LongestIncreasingSubsequence();
        obj.getUserInputs();
        obj.simpleLIS();
        obj.optimizedLIS();
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
        System.out.println("\nLIS algo - O(n^2) complexity");
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

        System.out.println("Length of LIS:" + max);

        int currIdx = maxIdx;
        String msg = String.valueOf(numbers[currIdx]);
        for (int i = maxIdx; i >= 0; i--) {
            if (dpArray[currIdx] - 1 == dpArray[i]) {
                msg = numbers[i] + " " + msg;
                currIdx = i;
            }
        }
        System.out.println("Longest Increasing Sequence:" + msg);
    }

    public void optimizedLIS() {
        System.out.println("\nLIS algo - O(nlogn) complexity");
        int[] lastElement = new int[numberCount];
        int[] sequenceLinkedMap = new int[numberCount];

        for (int i = 0; i < numberCount; i++) {
            lastElement[i] = -1;
            sequenceLinkedMap[i] = -1;
        }
        lastElement[0] = 0;
        int len = 0;

        for (int i = 1; i < numberCount; i++) {
            if(numbers[lastElement[0]]>numbers[i]){
                lastElement[0] = i;
            }
            else if(numbers[lastElement[len]]<numbers[i]){
                len++;
                lastElement[len] = i;
                sequenceLinkedMap[lastElement[len]] = lastElement[len-1];
            }
            else{
                int position = ceilIndex(numbers, lastElement, 0, len, numbers[i]);
                lastElement[position] = i;
                sequenceLinkedMap[lastElement[position]] = lastElement[position-1];
            }
        }

        System.out.println("Length of LIS:" + (len+1));
        findSequence(sequenceLinkedMap, lastElement[len]);
    }

    public void findSequence(int[] sequenceLinkedMap, int lastValueIndex){
        String solution = String.valueOf(numbers[lastValueIndex]);
        while(sequenceLinkedMap[lastValueIndex]!=-1){
            solution = numbers[sequenceLinkedMap[lastValueIndex]] + " " + solution;
            lastValueIndex = sequenceLinkedMap[lastValueIndex];
        }
        System.out.println("Longest Increasing Sequence:" + solution);

    }

    public int ceilIndex(int arr[], int lastElement[], int left, int right, int key) {
        int mid;
        while (left <= right) {
            mid = (right + left) / 2;
            if (arr[lastElement[mid]] < key && arr[lastElement[mid+1]] >= key) {
                return mid + 1;
            } else if (arr[lastElement[mid]] < key) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }

    private int ceilIndex(int input[], int T[], int end, int s){
        int start = 0;
        int middle;
        int len = end;
        while(start <= end){
            middle = (start + end)/2;
            if(middle < len && input[T[middle]] < s && s <= input[T[middle+1]]){
                return middle+1;
            }else if(input[T[middle]] < s){
                start = middle+1;
            }else{
                end = middle-1;
            }
        }
        return -1;
    }


}
