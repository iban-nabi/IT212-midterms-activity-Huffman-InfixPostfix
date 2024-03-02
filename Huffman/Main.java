package Huffman;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.*;

public class Main {
    static StringBuilder decodedMessage;

    static Scanner kbd = new Scanner(System.in);
    public static void main(String[] args){
        System.out.println("""
                +=====================================================+
                |                Saint Louis University               |
                |         Data Structures | Midterm Activity 1        |
                |      This program converts text to Huffman Code     |
                |                    and vice-versa                   |
                |                                                     |
                |                                                     |
                |      Programmers: Arzadon, Maxwell John             |
                |                   Del Rosario, Carliah Beatriz      |
                |                   Lalwet, Carl Joshua               |
                |                   Masaba, Jessalyn Mae              |
                |                   Paguyo, Jan Ivan Ezra  ggggg      |
                |                                                     |
                |    Submitted on: November 19, 2022                  |
                +=====================================================+""");

        String text = "";
        while (!text.matches("[A-Za-z]+")) {
            System.out.print("Enter a text string: ");
            text = kbd.nextLine();
            System.out.println("Enter the correct string.");
        }
        generateHuffmanCode(text);
    }

    /**
     * This method generates the huffman code of the entered text string
     * The method uses a Map in order to store the different characters of the entered string and its frequency, once
     * a character is repeated, the frequency of the existing stored character in the map would increment.
     *
     * After counting and storing each character and frequency, a priority queue will be used to store and identify
     * which character have the highest frequency, the character that has the higher priority is the character with the
     * less frequency. The characters that will be stored in the priority queue will be setup as a node.
     *
     * After setting up the priority queue, the combination of characters with the least two frequency will be done
     * through a while loop. The 2 characters with the least number of frequency will be removed in the priority stack,
     * set those characters as the right and left leaf of a branch, and find its sum. The sum (which will be setup as node)
     * will be then stored in the priority queue. The process of adding the nodes with the least sum is continuous until
     * the priority queue only stores a single node.
     *
     * The text entered will be then encoded by using the encodeData method, with a parameter of root, which is the whole
     * summation of the huffman tree, a map, which will store the huffman code by traversing the huffman tree (traversing
     * will be done within the encodeData method) and an empty string that will be used to hold the generated huffman code
     * through recursion.
     * @param text is the string the to be generated as a huffman tree
     */
    public static void generateHuffmanCode(String text){

        //end the method if text is null
        if (text == null || text.length() == 0){
            return;
        }

        //stores each character from the entered string as well as their frequency
        Map<Character, Integer> freq = new HashMap<>();
        for (char c: text.toCharArray()){
            freq.put(c, freq.getOrDefault(c, 0) + 1);
        }

        //stores the nodes of the Huffman tree. The lesser the frequency, the higher the priority.
        PriorityQueue<HuffmanNode> characterPriority = new PriorityQueue<>(Comparator.comparingInt(l -> l.freq));

        //create a Set view of the mapping contained in Map freq.
        for (var entry: freq.entrySet()){
            //adds a new leaf node to the queue
            characterPriority.add(new HuffmanNode(entry.getKey(), entry.getValue()));
        }

        //loop while the priorityQueue is not equal to 1
        while (characterPriority.size() != 1){
            //two low frequency nodes are removed
            HuffmanNode left = characterPriority.poll();
            HuffmanNode right = characterPriority.poll();
            //adds the values of the two removed nodes and enters the sum as a new node
            int sum = left.freq + right.freq;
            characterPriority.add(new HuffmanNode(null, sum, left, right));
        }

        //root node points to the root of the Huffman Tree
        HuffmanNode root = characterPriority.peek();

        //traverse the Huffman tree and acquire the codes for each character in the entered string
        Map<Character, String> huffmanCode = new HashMap<>();
        encodeData(root,"",huffmanCode);

        //prints the initial entered string
        System.out.println("The initial string is: " + text);

        //create and display the Huffman code table
        showTable(huffmanCode, freq, text);
        String encodedString=encodeStringInput(text,huffmanCode);
        System.out.println("The encoded string is: " + encodedString);

        //provide a list of options for the user after generating the table
        choices(root,huffmanCode);
    }

    /**
     * The choices method will allow the user to select what to do next after generating the huffman code:
     * [1] Decode Sequence of Huffman Code - calls the decodeHuffmanCode method
     * [2] Encode Text - calls the encodeStringInput method
     * [3] Create new Huffman Code - calls the createHuffmanTree
     * [4] Print Huffman Tree - calls printNode method in HuffmanTreePrinter class
     * [5] Exit Program
     * @param root is the root(overall summation) of the huffman tree
     * @param huffmanCode is the Map which stores the huffman code of a specific character
     */
    public static void choices(HuffmanNode root, Map<Character, String> huffmanCode){
        int choice=0;
        boolean isValid = false;
        Scanner kbd = new Scanner(System.in);

        while(true){
            System.out.println("""

                +================================================+
                |   Choose from the following what to do next:   |
                |     [1] Convert Huffman Code to Text           |
                |     [2] Convert Text to Huffman Code           |
                |     [3] Enter a new set of Characters          |
                |     [4] Print Huffman Tree                     |
                |     [5] Exit Program                           |
                +================================================+""");

            do{
                try{
                    System.out.print("Choice: ");
                    choice = Integer.parseInt(kbd.nextLine());
                    if(choice< 1 || choice> 4) {
                        System.out.println("Invalid Input! Please Try Again.");
                    } else {
                        isValid = true;
                    }
                } catch (NumberFormatException e){
                    System.out.println("Enter a number.");
                }
            } while (!isValid);

            switch (choice) {
                case 1 ->{
                    //huffman code to text
                    decodeHuffmanCode(root);
                    System.out.println();
                }

                case 2 -> {
                    //text to huffman code depending on the generated tree
                    String encodeString;
                    System.out.print("Enter a text which could be encoded using the huffman code generated: ");
                    encodeString = kbd.nextLine();
                    encodeString = encodeStringInput(encodeString, huffmanCode);
                    if (encodeString.equals("")) {
                        System.out.println("The string cannot be encoded");
                    } else {
                        System.out.println("The encoded string is: " + encodeString);
                    }
                    System.out.println();
                }

                case 3 -> {
                    //creates a new huffman code table
                    String text = "";
                    if (text.matches("[A-Za-z]+")) {
                        System.out.print("Enter a set of Characters: ");
                        text = kbd.nextLine();
                    }
                    while (!text.matches("[A-Za-z]+")) {
                        System.out.print("Enter a set of Characters: ");
                        text = kbd.nextLine();
                        System.out.println("Enter the correct string.");
                    }
                    generateHuffmanCode(text);
                    System.out.println();
                }

                case 4 -> {
                    HuffmanTreePrinter.printNode(root);
                }

                case 5 -> {
                    //closes the program
                    System.out.println("Thank you for using the program!");
                    System.exit(0);
                }
            }
        }
    }

    /**
     * The bits method checks the entered sequence of huffman code. If the entered code includes numbers which are not
     * 0 or 1, or it includes characters, return the value of 1, meaning it is invalid, else return 0.
     * @param i is the entered sequence of huffman code to be decoded
     * @return 1 if invalid, 0 if valid
     */
    public static int bits (String i) {
        if (i.contains("3") || i.contains("4") || i.contains("5") || i.contains("6")
                || i.contains("7") || i.contains("8") || i.contains("9"))
            return 1;

        try{
            new BigInteger(i);

        }catch (Exception e){
            return 1;
        }

        return 0;
    }

    /**
     * The decodeHuffmanCode method ask the user to enter a sequence of huffman code to be decoded. The entered code will
     * be verified by the bit method if the input is valid. The decodeData method will be used in order to process the
     * decoding of the sequence of huffman code.
     * @param root is the root(overall summation) of the huffman tree
     */
    public static void decodeHuffmanCode(HuffmanNode root) {
        decodedMessage = new StringBuilder();
        System.out.print("Enter a sequence of huffman code to be decoded: ");
        String input = kbd.nextLine();
        if (!input.matches("0-1")) {
            while(bits(input) == 1) {
                System.out.println("The entered huffman code to be decoded is INVALID.");
                System.out.print("Enter a sequence of huffman code to be decoded: ");
                input = kbd.nextLine();
            }
        }
        try{
            int index = -1;
            while (index < input.length() - 1) {
                index = decodeData(root, index, input);
            }
            System.out.print("The decoded message is "+ decodedMessage);
        } catch (Exception e) {
            System.out.print("The entered huffman code cannot be DECODED.");
        }
        System.out.println();
    }

    /**
     * The encodeStringInput method checks each character of the text input the user wants to encode. Checking of each
     * character will be done within the for loop. The character will be used to identify the specific huffman code that
     * is stored in a Map. If the character is not found within the Map, the method will return a blank text, meaning that
     * the text entered by the user cannot be encoded to a sequence of huffman code
     * @param text which is the input of the user
     * @param huffmanCode which is the Map which stores the huffman code of a specific character
     * @return the generated encoded text input
     */
    public static String encodeStringInput(String text, Map<Character, String> huffmanCode){
        StringBuilder tempEncodedString = new StringBuilder();
        String encodedString="";
        for (char c: text.toCharArray()) {
            if(huffmanCode.get(c)==null){
                encodedString="";
                break;
            }
            tempEncodedString.append(huffmanCode.get(c));
            encodedString=tempEncodedString.toString();
        }
        return encodedString;
    }

    /**
     * The showTable method generates the table for the huffman code which includes:
     * "Character","Frequency","Huffman Code","No. of Bits", "Bits Needed (ASCII)", "Bits Needed (Huffman)"
     * The method also computes for the Percentage of Storage Savings
     * @param huffmanCode is the Map which stores the huffman code of a specific character
     * @param freq is the Map which stores the frequency of a specific character
     * @param text string of characters entered by the user
     */
    public static void showTable(Map<Character, String> huffmanCode, Map<Character, Integer> freq, String text) {
        DecimalFormat formatDecimal = new DecimalFormat("0.00");
        int asciiStorageBits;
        int asciiStorageBitsTotal = 0;
        int huffmanStorageBits;
        int huffmanStorageBitsTotal = 0;
        double percentageStorageSaving;

        //each symbol of the entered text, with no duplicates
        char[] symbols = removeDuplicates(text).toCharArray();

        //huffman code corresponding to each symbol
        String[] code = new String[symbols.length];

        //bit length of each huffman code
        int[] bits = new int[symbols.length];
        int[] frequency = new int[symbols.length];

        //population of arrays needed for table
        for (int x = 0; x < symbols.length; x++) {
            code[x] = huffmanCode.get(symbols[x]);
            bits[x] = huffmanCode.get(symbols[x]).length();
            frequency[x]=freq.get(symbols[x]);
        }

        //printing of table
        System.out.println("+==============================================================================================================+");
        System.out.printf("%-3s %-15s %-15s %-15s %-15s %-20s %5s %1s %n","|","Character","Frequency","Huffman Code","No. of Bits", "Bits Needed (ASCII)", "Bits Needed (Huffman)","|");
        for (int x = 0; x < symbols.length; x++) {
            asciiStorageBits=frequency[x]*7;
            asciiStorageBitsTotal=asciiStorageBitsTotal+asciiStorageBits;
            huffmanStorageBits=frequency[x]*bits[x];
            huffmanStorageBitsTotal=huffmanStorageBitsTotal+huffmanStorageBits;
            System.out.printf("%-6s %-12s %-15s %-10s %10s %20s %20s %12s %n","|",symbols[x],frequency[x], code[x], bits[x], asciiStorageBits, huffmanStorageBits,"|");
        }
        System.out.println("+==============================================================================================================+");
        percentageStorageSaving= (double)(asciiStorageBitsTotal-huffmanStorageBitsTotal)/asciiStorageBitsTotal*100;
        System.out.println("Total Number of Bits needed for ASCII code: "+asciiStorageBitsTotal);
        System.out.println("Total  Number of Bits needed for Huffman code: "+huffmanStorageBitsTotal);
        System.out.println("Percentage of Storage Savings: "+formatDecimal.format(percentageStorageSaving)+"%");
        System.out.println();
    }

    /**
     * The removeDuplicated method remove the repeated characters found in the string. The method uses a for loop to
     * traverse the string and check for the repeated characters. Then an if statement will be used to check if the
     * currently read character is not in the StringBuilder tempString already before appending it.
     * @param str is the text string entered by the user
     * @return a string where duplicates of a character are removed
     */
    public static String removeDuplicates(String str){
        //Create an empty string
        StringBuilder tempString = new StringBuilder();

        // Traverse the string and check for the repeated characters
        for (int i = 0; i < str.length(); i++){
            // store the character available at ith index in the string
            char charAtPosition = str.charAt(i);

            // check the index of the charAtPosition. If the indexOf() method returns true add it to the resulting string
            if (tempString.toString().indexOf(charAtPosition) < 0){
                tempString.append(charAtPosition);
            }
        }
        return tempString.toString();
    }

    /**
     * The encodeData will be used to traverse the huffman tree until it reaches the leaf node which holds a character.
     * The encodeData method uses recursion, two separate method calls where one follows the path of going to the left
     * exclusively and the other method call is set to traverse to the right. While the recursion is happening, the str
     * parameter would be appended either 0 or 1 depending on the on where it is traversing, this is done in order to
     * build a string huffman code of a specific character. Once the method has reached the leaf node through recursion,
     * the character stored in the leaf node as well as the generated huffman code in the str parameter will be stored
     * within the Map huffmanCode.
     * @param root is the root(overall summation) of the huffman tree, the root will be changed during the recursion
     * @param str will be used to build the string huffman code of a specific character
     * @param huffmanCode stores the character and its huffman code
     */
    public static void encodeData(HuffmanNode root, String str, Map<Character, String> huffmanCode){
        if (root == null){
            return;
        }

        if (isLeaf(root)){
            huffmanCode.put(root.nodeCharacter, str.length() > 0 ? str : "1");
        }
        encodeData(root.left, str + '0', huffmanCode);
        encodeData(root.right, str + '1', huffmanCode);
    }

    /**
     * The decodeData method will decode the entered sequence of huffman code in order to translate it to text. The
     * textToBeDecoded stores the sequence of huffman code entered by the user, this will be used to guide the
     * program in which direction it should traverse. Recursion was used in order to traverse the huffman tree, if the
     * character read is a 0, the current root will be set to left, else it will be set to right. During the process of
     * recursion, once the method reaches the leaf node, it retrieves the character stored within it and prints it.
     * @param root is the root(overall summation) of the huffman tree, the root will be changed during the recursion
     * @param index is used to iterate over the sequence of huffman code
     * @param textToBeDecoded is entered sequence of huffman code
     * @return index which is the identifier how many characters has been iterated during the method call or recursion
     */
    //traverse the Huffman Tree and decode the encoded string function that decodes the encoded data
    public static int decodeData(HuffmanNode root, int index, String textToBeDecoded){
        //checks if the root node is null or not
        if (root == null){
            return index;
        }

        if (isLeaf(root)){
            decodedMessage.append(root.nodeCharacter);
            return index;
        }
        index++;
        root = (textToBeDecoded.charAt(index) == '0') ? root.left : root.right;
        index = decodeData(root, index, textToBeDecoded);
        return index;
    }

    /**
     * The isLeaf method checks if the current node is a leaf or not. A leaf in a huffman tree
     * is a node where there is no node attach to it. The method checks if the current read node has a left or right
     * node attach to it.
     * @param root is the overall summation (top of the huffman tree) of the huffman tree
     * @return true if the node is a leaf, no nodes attach to if, otherwise false
     */
    public static boolean isLeaf(HuffmanNode root){
        return root.left == null && root.right == null;
    }
}
