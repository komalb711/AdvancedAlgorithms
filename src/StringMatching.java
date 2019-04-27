import java.util.Scanner;

/**
 * String Matching Problem
 * <p>
 * Input Format:
 * <Text String>
 * <Pattern String>
 * <p>
 * Algorithms of the String Matching:-
 * Brute Force - O(mn)
 * Knuth Morris Pratt (KMP) - O(m+n);
 */


public class StringMatching {

    private String text;
    private String pattern;
    private int[] prefixArray;

    public static void main(String[] args) {
        StringMatching obj = new StringMatching();
        obj.getUserInputs();
        System.out.println("Naive Approach/ Brute Force Approach");
        if (obj.naiveApproach()) {
            System.out.println("Match Found!");
        } else {
            System.out.println("No match found!");
        }

        System.out.println("Knuth Morris Pratt Approach");
        obj.createPrefixArray();
        if (obj.knuthMorrisPrattAlgorithm()) {
            System.out.println("Match Found!");
        } else {
            System.out.println("No match found!");
        }

    }

    public void getUserInputs() {
        Scanner scan = new Scanner(System.in);
        text = scan.next();
        pattern = scan.next();
    }

    public boolean naiveApproach() {

        text = text.toLowerCase();
        pattern = pattern.toLowerCase();
        int textLength = text.length();
        int patternLength = pattern.length();

        if (textLength == 0 || patternLength == 0) {
            System.out.println("Text or Pattern is empty!");
            return false;
        }

        int i = 0;
        while (i < (textLength - patternLength + 1)) {
            boolean match = true;
            for (int j = 0; j < patternLength; j++) {
                if (text.charAt(i + j) != pattern.charAt(j)) {
                    match = false;
                    break;
                }
            }
            if (match) {
                return true;
            }
            i += 1;
        }
        return false;
    }

    public void createPrefixArray() {
        prefixArray = new int[pattern.length()];

        int i = 1, j = 0;
        while (i < pattern.length() && j < pattern.length()) {
            if (pattern.charAt(i) == pattern.charAt(j)) {
                prefixArray[i] = j + 1;
                i++;
                j++;
            } else {
                if (j != 0) {
                    j = prefixArray[j - 1];
                } else {
                    prefixArray[i] = 0;
                    i++;
                }
            }
        }
    }

    public boolean knuthMorrisPrattAlgorithm() {
        int textLength = text.length();
        int patternLength = pattern.length();
        if (textLength == 0 || patternLength == 0) {
            System.out.println("Text or Pattern is empty!");
            return false;
        }

        int i = 0, j = 0;
        while (i < textLength && j < patternLength) {
            if (text.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
            } else {
                if (j != 0) {
                    j = prefixArray[j - 1];
                } else {
                    i++;
                }
            }
        }
        return j == patternLength;

    }

}
