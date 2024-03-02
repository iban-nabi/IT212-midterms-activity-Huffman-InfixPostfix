package Huffman;

//Creates the nodes to be used in the huffman tree
public class HuffmanNode {
    //stores the character of a node (stores nothing null if the node is not a leaf node)
    public Character nodeCharacter;
    //stores the frequency of the character if it is a leaf node (stores the sum of two nodes if the node is not a leaf node)
    public Integer freq;
    //stores the node on its left basing on the huffman tree (null if none)
    public HuffmanNode left = null;

    //stores the node on its right basing on the huffman tree (null if none)
    public HuffmanNode right = null;

    //constructor of the HuffmanNode class
    public HuffmanNode(Character nodeCharacter, Integer freq){
        this.nodeCharacter = nodeCharacter;
        this.freq = freq;
    }

    //constructor of the HuffmanNode class
    public HuffmanNode(Character nodeCharacter, Integer freq, HuffmanNode left, HuffmanNode right){
        this.nodeCharacter = nodeCharacter;
        this.freq = freq;
        this.left = left;
        this.right = right;
    }
}  