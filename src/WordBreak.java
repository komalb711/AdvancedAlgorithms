import java.util.Scanner;

/**
 * Word Search Problem - LeetCode Problem
 *
 * Input:
 * <Number of words in dictionary - n>
 * <word 1>
 * <word 2>
 *  ....
 * <word n>
 * <word to be searches>
 *
 */


public class WordBreak {
    private String inputText;
    private Trie trieStructure;

    public WordBreak(){
        trieStructure = new Trie();
    }

    public static void main(String[] args) {
        WordBreak obj = new WordBreak();
        obj.getUserInput();
        System.out.println("Is Word Break possible:"+
                obj.solveWordBreak(obj.inputText, obj.trieStructure.root));
    }

    public void getUserInput(){
        Scanner scanner = new Scanner(System.in);
        int count = scanner.nextInt();
        for(int i=0; i<count; i++){
            String word = scanner.next();
            trieStructure.insert(word);
        }
        inputText = scanner.next();
    }

    public boolean solveWordBreak(String inputText, TrieNode current){
        if(inputText.length()==0){return true;}
        for(int i=1; i<=inputText.length(); i++){
            if(trieStructure.search(inputText.substring(0,i), current) &&
                    solveWordBreak(inputText.substring(i,inputText.length()), current)){
                return true;
            }
        }
        return false;
    }
}
