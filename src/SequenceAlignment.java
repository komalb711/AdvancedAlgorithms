import java.util.Scanner;

/**
 * Sequence Alignment  / String Similarity / Edit Distance
 * <p>
 * Input Format:
 * <String 1>
 * <String 2>
 * <Gap Penalty>
 * <Mismatch Penalty for 2 vowels or 2 consonant >
 * <Mismatch Penalty for vowel consonant combination>
 */


public class SequenceAlignment {
    String string1;
    String string2;

    int gapPenalty;
    int mismatchPenalty;
    int mismatchPenaltyCV;

    public static void main(String[] args) {
        SequenceAlignment obj = new SequenceAlignment();
        obj.getUserInputs();
        obj.sequenceAlignment();
    }

    public void getUserInputs() {
        Scanner scan = new Scanner(System.in);
        string1 = scan.next();
        string2 = scan.next();

        gapPenalty = scan.nextInt();
        mismatchPenalty = scan.nextInt();
        mismatchPenaltyCV = scan.nextInt();
    }

    public void sequenceAlignment() {
        int[][] dpArray = new int[string1.length() + 1][string2.length() + 1];


        for (int i = 1; i < string1.length() + 1; i++) {
            dpArray[i][0] = gapPenalty * i;
        }
//        printDPArray(dpArray);
        for (int i = 1; i < string2.length() + 1; i++) {
            dpArray[0][i] = gapPenalty * i;
        }
//        printDPArray(dpArray);

        for (int i = 1; i < string1.length() + 1; i++) {
            for (int j = 1; j < string2.length() + 1; j++) {
                int mismatchValue;
                if (string1.charAt(i - 1) == string2.charAt(j - 1)) {
                    mismatchValue = 0;
                } else if (isVowel(string1.charAt(i - 1)) && isVowel(string2.charAt(j - 1)) ||
                        isConsonant(string1.charAt(i - 1)) && isConsonant(string2.charAt(j - 1))) {
                    mismatchValue = mismatchPenalty;
                } else {
                    mismatchValue = mismatchPenaltyCV;
                }

                dpArray[i][j] = min3(mismatchValue + dpArray[i - 1][j - 1], gapPenalty + dpArray[i][j - 1], gapPenalty + dpArray[i - 1][j]);
            }
        }

        System.out.println("Minimum Cost required to align the two strings:" + dpArray[string1.length()][string2.length()]);
        printDPArray(dpArray);

        retraceSolution(dpArray, string1, string2);
    }

    public void retraceSolution(int[][] dpArray, String str1, String str2) {
        String sol1 = "";
        String sol2 = "";
        int i = dpArray.length - 1;
        int j = dpArray[0].length - 1;
        while (i > 0 && j > 0) {

            int mismatchValue = mismatchPenalty;
            if (isVowel(string1.charAt(i - 1)) && isVowel(string2.charAt(j - 1)) ||
                    isConsonant(string1.charAt(i - 1)) && isConsonant(string2.charAt(j - 1))) {
                mismatchValue = mismatchPenalty;
            } else {
                mismatchValue = mismatchPenaltyCV;
            }

            if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                sol1 = str1.charAt(i - 1) + sol1;
                sol2 = str2.charAt(j - 1) + sol2;
                i--;
                j--;
            } else if (mismatchValue + dpArray[i - 1][j - 1] < gapPenalty + dpArray[i][j - 1] && mismatchValue + dpArray[i - 1][j - 1]  < gapPenalty + dpArray[i - 1][j]) {
                sol1 = str1.charAt(i - 1) + sol1;
                sol2 = str2.charAt(j - 1) + sol2;
                i--;
                j--;
            } else if (gapPenalty + dpArray[i][j - 1] < gapPenalty + dpArray[i - 1][j]) {
                sol1 = "_"+ sol1;
                sol2 = str2.charAt(j - 1) + sol2;
                j--;
            } else {
                sol2 =  "_"+ sol2;
                sol1 = str1.charAt(i - 1) + sol1;
                i--;
            }
        }
        while(i>=0){
            str1 = str1.charAt(i--) + str1;
        }

        while(j>=0){
            str2 = str2.charAt(j--) + str2;
        }

        System.out.println("String1:" + sol1);
        System.out.println("String2:" + sol2);
    }

    public boolean isVowel(char ch) {
        if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u') {
            return true;
        }
        return false;
    }

    public boolean isConsonant(char ch) {
        return !isVowel(ch);
    }

    public int min3(int val1, int val2, int val3) {
        int minValue = 0;
        if (val1 == min(val1, val2) && val1 == min(val1, val3)) {
            minValue = val1;
        } else if (val2 == min(val1, val2) && val2 == min(val2, val3)) {
            minValue = val2;
        } else minValue = val3;
        return minValue;
    }

    public int min(int val1, int val2) {
        if (val1 < val2)
            return val1;
        return val2;
    }

    public void printDPArray(int[][] dpArray) {
        for (int i = 0; i < string1.length() + 1; i++) {
            System.out.println(" ");
            for (int j = 0; j < string2.length() + 1; j++) {
                System.out.print(dpArray[i][j] + " ");
            }
        }
        System.out.println(" ");
    }

}
