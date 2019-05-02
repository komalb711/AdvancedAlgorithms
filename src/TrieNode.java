/**
 *  This is the node for the trie data structure.
 *  It has a array of 26 length
 */


public class TrieNode {
    TrieNode[] map;
    boolean endOfWord;

    public TrieNode() {
        map = new TrieNode[26];
        endOfWord = false;
    }
}

