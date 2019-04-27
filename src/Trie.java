/**
 *  This is a Trie Data Structure, which is a tree type data structure,
 *  which is used for prefix based problems
 *
 */

public class Trie {
    TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public void insert(String word) {
        word = word.toLowerCase();
        TrieNode current = root;
        for (int i = 0; i < word.length(); i++) {
            char alphabet = word.charAt(i);
            int  index = alphabet - 'a';
            TrieNode node = current.map[index];
            if (node == null) {
                node = new TrieNode();
                current.map[index] = node;
            }
            current = node;
        }
        current.endOfWord = true;
    }

    public boolean search(String word) {
        word = word.toLowerCase();
        TrieNode current = root;
        for (int i = 0; i < word.length(); i++) {
            char alphabet = word.charAt(i);
            int  index = alphabet - 'a';
            TrieNode node = current.map[index];
            if (node == null) {
                return false;
            }
            current = node;
        }
        return current.endOfWord;
    }

    public boolean search(String word, TrieNode current) {
        for (int i = 0; i < word.length(); i++) {
            char alphabet = word.charAt(i);
            int  index = alphabet - 'a';
            TrieNode node = current.map[index];
            if (node == null) {
                return false;
            }
            current = node;
        }
        return current.endOfWord;
    }


    public static void main(String[] args) {
        Trie trieObject = new Trie();
        trieObject.insert("able");
        trieObject.insert("abled");
        trieObject.insert("lame");
        trieObject.insert("dumb");
        trieObject.insert("dare");

        System.out.println("Search Result of 'abl':" + trieObject.search("abl"));
        System.out.println("Search Result of 'able':" + trieObject.search("able"));
        System.out.println("Search Result of 'dare':" + trieObject.search("dare"));
        System.out.println("Search Result of 'stare':" + trieObject.search("stare"));

    }
}
