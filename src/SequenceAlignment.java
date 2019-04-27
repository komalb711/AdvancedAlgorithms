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
    String X;
    String Y;

    int gapPenalty;
    int mismatchPenalty;
    int mismatchPenaltyCV;

    public static void main(String[] args) {
        SequenceAlignment obj = new SequenceAlignment();
        obj.getUserInputs();
        obj.sequenceAlignment();
        obj.linearSpaceSequenceAlignment();
    }

    public void getUserInputs() {
        Scanner scan = new Scanner(System.in);
        X = scan.next();
        Y = scan.next();

        gapPenalty = scan.nextInt();
        mismatchPenalty = scan.nextInt();
        mismatchPenaltyCV = scan.nextInt();
    }

    public void sequenceAlignment() {
        int[][] dpArray = new int[X.length() + 1][Y.length() + 1];

        for (int i = 1; i < X.length() + 1; i++) {
            dpArray[i][0] = gapPenalty * i;
        }
        for (int i = 1; i < Y.length() + 1; i++) {
            dpArray[0][i] = gapPenalty * i;
        }
        for (int i = 1; i < X.length() + 1; i++) {
            for (int j = 1; j < Y.length() + 1; j++) {
                int mismatchValue;
                if (X.charAt(i - 1) == Y.charAt(j - 1)) {
                    mismatchValue = 0;
                } else if (isVowel(X.charAt(i - 1)) && isVowel(Y.charAt(j - 1)) ||
                        isConsonant(X.charAt(i - 1)) && isConsonant(Y.charAt(j - 1))) {
                    mismatchValue = mismatchPenalty;
                } else {
                    mismatchValue = mismatchPenaltyCV;
                }
                dpArray[i][j] = min3(mismatchValue + dpArray[i - 1][j - 1],
                        gapPenalty + dpArray[i][j - 1],
                        gapPenalty + dpArray[i - 1][j]);
            }
        }

        System.out.println("Minimum Cost required to align the two strings:" + dpArray[X.length()][Y.length()]);
        printDPArray(dpArray);

        retraceSolution(dpArray);
    }

    public int[][] linearSpaceSequenceAlignment() {
        int[][] dpArray = new int[X.length()][2];

        for (int i = 0; i < X.length(); i++) {
            dpArray[i][0] = gapPenalty * i;
        }

        for (int j = 1; j < Y.length(); j++) {
            dpArray[0][1] = j*gapPenalty;
            for (int i = 1; i < X.length(); i++) {
                int mismatchValue;
                //if characters match of the two string, then no penalty is added
                if (X.charAt(i - 1) == Y.charAt(j - 1)) {
                    mismatchValue = 0;
                }
                //if both the characters are both vowels or consonants or not ,
                //then add the specific mismatch penalty
                else if (isVowel(X.charAt(i - 1)) && isVowel(Y.charAt(j - 1)) ||
                        isConsonant(X.charAt(i - 1)) && isConsonant(Y.charAt(j - 1))) {
                    mismatchValue = mismatchPenalty;
                } else {
                    mismatchValue = mismatchPenaltyCV;
                }
                // finally we add the minimum of the current match/mismatch or taking a gap
                // penalty for either X string ot Y string.
                dpArray[i][1] = min3(mismatchValue + dpArray[i - 1][0],
                        gapPenalty + dpArray[i][0],
                        gapPenalty + dpArray[i - 1][1]);
            }
            for (int i = 1; i < X.length(); i++) {
                dpArray[i][0] = dpArray[i][1];
            }
        }

        System.out.println("Minimum Cost required to align the two strings:" + dpArray[X.length()-1][0]);
        printDPArray(dpArray);

        return dpArray;
    }


    public void retraceSolution(int[][] dpArray) {
        String sol1 = "";
        String sol2 = "";
        int i = dpArray.length - 1;
        int j = dpArray[0].length - 1;
        while (i > 0 && j > 0) {

            int mismatchValue;
            if (isVowel(X.charAt(i - 1)) && isVowel(Y.charAt(j - 1)) ||
                    isConsonant(X.charAt(i - 1)) && isConsonant(Y.charAt(j - 1))) {
                mismatchValue = mismatchPenalty;
            } else {
                mismatchValue = mismatchPenaltyCV;
            }

            if (X.charAt(i - 1) == Y.charAt(j - 1)) {
                sol1 = X.charAt(i - 1) + sol1;
                sol2 = Y.charAt(j - 1) + sol2;
                i--;
                j--;
            } else if (mismatchValue + dpArray[i - 1][j - 1] < gapPenalty + dpArray[i][j - 1] && mismatchValue + dpArray[i - 1][j - 1]  < gapPenalty + dpArray[i - 1][j]) {
                sol1 = X.charAt(i - 1) + sol1;
                sol2 = Y.charAt(j - 1) + sol2;
                i--;
                j--;
            } else if (gapPenalty + dpArray[i][j - 1] < gapPenalty + dpArray[i - 1][j]) {
                sol1 = "_"+ sol1;
                sol2 = Y.charAt(j - 1) + sol2;
                j--;
            } else {
                sol2 =  "_"+ sol2;
                sol1 = X.charAt(i - 1) + sol1;
                i--;
            }
        }
        while(i>=0){
            X = X.charAt(i--) + X;
        }

        while(j>=0){
            Y = Y.charAt(j--) + Y;
        }
        System.out.println(" Alignment of the two string is as follows:");
        System.out.println("String1:" + sol1);
        System.out.println("String2:" + sol2);
    }

    public void divideAndConquerAlignment(){
        int m = X.length();
        int n = Y.length();

        if( m <= 2 && n <= 2 ){
            sequenceAlignment();
        }

        int[][] dpArray = linearSpaceSequenceAlignment();

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
        for (int i = 0; i < dpArray.length; i++) {
            System.out.println(" ");
            for (int j = 0; j < dpArray[0].length; j++) {
                System.out.print(dpArray[i][j] + " ");
            }
        }
        System.out.println(" ");
    }

}
