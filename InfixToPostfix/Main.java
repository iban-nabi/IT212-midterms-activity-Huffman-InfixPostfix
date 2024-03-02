package InfixToPostfix;

import InfixToPostfix.ConverterForGUI;
import InfixToPostfix.LinkedStack;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Main extends ConverterForGUI {
    //instance variables for the introduction window
    static JFrame frame;
    static JPanel panel;
    static JButton buttonInToPost, buttonPostToIn, buttonForExit;

    //instance variables for infix to postfix conversion (input)
    static JFrame frameInfixToPostfix;
    static JPanel panelInfixToPostfix;
    static JButton buttonInfixToPostfix;
    static JTextField textInfixToPostfix;
    static String infix;

    //instance variables for infix to postfix conversion (output)
    static JFrame framePostfixOutput;
    static JPanel panelPostfixOutput;

    //instance variables for postfix to infix conversion
    static JFrame framePostfixToInfix;
    static JPanel panelPostfixToInfix;
    static JTextField textFPostfixToInfix;
    static JButton buttonPostfixToInfix;
    static String postfix;

    //instance variables for InfixToPostfix.Main Menu and Exit button
    static JButton buttonMainMenu;
    static JButton buttonExit;

    /**
     * The main method calls the mainMenu() method that displays a GUI window
     * @param args
     */
    public static void main(String [] args) throws IOException{
        mainMenu();
    } // End of main method

    /**
     * The mainMenu method throws an IOException and creates a graphical user interface that displays
     * the main menu from which the user can choose. It can be an infix to postfix, postfix to infix,
     * or exit the program. If the user's input is out of range and invalid, an error will appear informing
     * the user that the input is not acceptable or invalid.
     *
     * @throws IOException if there is something wrong with input or the output
     */
    private static void mainMenu() throws IOException{
        frame = new JFrame();
        panel = new JPanel();
        buttonInToPost = new JButton();

        //Sets a frame for the GUI
        frame.setSize(500, 280);
        frame.setTitle("InfixToPostfix.Main Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        //Sets the panel for the GUI
        panel.setLayout(null);
        panel.setBackground(Color.white);
        frame.add(panel);

        //Displays the multi-line text
        JLabel title = new JLabel("DATA STRUCTURES",SwingConstants.CENTER);
        title.setVerticalTextPosition(SwingConstants.TOP);
        title.setForeground(Color.DARK_GRAY);
        title.setFont(new Font("SansSerif", Font.BOLD,30 ));
        title.setBounds(20, 0, 450, 100);
        panel.add(title);

        JLabel label = new JLabel("Postfix and Infix Conversions", SwingConstants.CENTER);
        label.setForeground(Color.decode("#A52A2A"));
        label.setFont(new Font("SansSerif", Font.PLAIN,15 ));
        label.setBounds(150, 25, 200, 100);
        panel.add(label);

        //Adds a button. When clicked, will proceed to Infix to Postfix
        buttonInToPost = new JButton("Infix To Postfix");
        //button.setPreferredSize(new Dimension(50, 50));
        buttonInToPost.setBounds(18, 95, 100, 25);
        buttonInToPost.setSize(450, 30);
        buttonInToPost.setBackground(Color.darkGray);
        buttonInToPost.setForeground(Color.white);
        buttonInToPost.addActionListener(e -> {
            frame.dispose();
            inputInfixToPostfix();
        });
        panel.add(buttonInToPost);

        buttonPostToIn = new JButton("Postfix to Infix");
        //button.setPreferredSize(new Dimension(50, 50));
        buttonPostToIn.setBounds(18, 130, 100, 25);
        buttonPostToIn.setSize(450, 30);
        buttonPostToIn.setBackground(Color.darkGray);
        buttonPostToIn.setForeground(Color.white);
        buttonPostToIn.addActionListener(e -> {
            try {
                frame.dispose();
                inputPostfixToInfix();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        panel.add(buttonPostToIn);

        buttonForExit = new JButton("Exit");
        buttonForExit.setPreferredSize(new Dimension(50, 50));
        buttonForExit.setBounds(380, 190, 100, 25);
        buttonForExit.setSize(80, 30);
        buttonForExit.setBackground(Color.decode("#A52A2A"));
        buttonForExit.setForeground(Color.white);
        buttonForExit.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Thank you for using our program!",
                    "Exit Message", JOptionPane.INFORMATION_MESSAGE);
            frame.dispose();

        });
        panel.add(buttonForExit);

        //Sets the GUI Visible
        frame.setVisible(true);
    } // End of mainMenu method


    /**
     * The inputInfixToPostfix method will create a graphical user interface in which the user
     * can enter an infix expression that will be converted into its postfix form. Once the expression
     * is entered, a new window will appear for the output to display. If the entered expression is
     * not valid, an error window will pop up informing the user that it cannot convert the entered
     * expression. It also has a button to go back to the main menu to enter another expression or
     * exit the program.
     */
    public static void inputInfixToPostfix() {
        frameInfixToPostfix = new JFrame();
        panelInfixToPostfix = new JPanel();
        buttonInfixToPostfix = new JButton();
        textInfixToPostfix = new JTextField();

        //Creates a frame for the user's input
        frameInfixToPostfix.setSize(500, 400);
        frameInfixToPostfix.setTitle("Converter");
        frameInfixToPostfix.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameInfixToPostfix.setResizable(false);

        //Sets the panel for the GUI
        panelInfixToPostfix.setLayout(null);
        panelInfixToPostfix.setBackground(Color.white);
        frameInfixToPostfix.add(panelInfixToPostfix);

        //Displays the header of the converter
        JLabel inToPostHeader = new JLabel("Infix To Postfix Converter", SwingConstants.CENTER);
        inToPostHeader.setVerticalTextPosition(SwingConstants.TOP);
        inToPostHeader.setForeground(Color.DARK_GRAY);
        inToPostHeader.setFont(new Font("SansSerif", Font.BOLD,20 ));
        inToPostHeader.setBounds(20, 0, 450, 100);
        panelInfixToPostfix.add(inToPostHeader);

        //Displays instruction and note
        JLabel instruction = new JLabel("<html> <center>Note: Infix expressions are human readable but " +
                "<br> not efficient for machine reading." +
                "<br> Infix: (Operand1) (Operator) (Operand2) </center></html>", SwingConstants.CENTER);
        instruction.setFont(new Font("Verdana", Font.ITALIC, 12));
        instruction.setForeground(Color.decode("#AA4A44"));
        instruction.setBounds(15, 65, 450, 50);
        panelInfixToPostfix.add(instruction);

        JLabel inputInfix = new JLabel("Enter the Infix Expression: ");
        inputInfix.setForeground(Color.decode("#AA4A44"));
        inputInfix.setBounds(170, 140, 200, 20);
        panelInfixToPostfix.add(inputInfix);

        //A Text Field to input the infix expression
        textInfixToPostfix = new JTextField();
        textInfixToPostfix.setBounds(18, 160, 150, 20);
        textInfixToPostfix.setSize(450, 50);
        textInfixToPostfix.setCaretColor(Color.decode("#AA4A44"));
        textInfixToPostfix.setFont(new Font("Sanserif", Font.PLAIN, 20));
        textInfixToPostfix.setHorizontalAlignment(JTextField.CENTER);
        panelInfixToPostfix.add(textInfixToPostfix);

        //A button to assign and convert the entered infix
        buttonInfixToPostfix = new JButton("Convert");
        buttonInfixToPostfix.setBounds(17, 210, 100, 25);
        buttonInfixToPostfix.setSize(450, 30);
        buttonInfixToPostfix.setBackground(Color.darkGray);
        buttonInfixToPostfix.setForeground(Color.white);
        buttonInfixToPostfix.setFocusable(false);
        panelInfixToPostfix.add(buttonInfixToPostfix);
        buttonInfixToPostfix.addActionListener(e -> {
            infix = textInfixToPostfix.getText().replaceAll("\\s+", "");
            String message = "The Postfix expression to the given Infix expression is:  ";
            String process = "Infix to Postfix Converter";
            String convert = toPostfix(infix);
            if (symbolCheckerForInfixInput(infix).equals("verified")) {
                frameInfixToPostfix.dispose();
                showOutputInGUI(message, process, convert); // A method that will create a window for the output

                //Retrieves the InfixToPostfix.LinkedStack objects
                LinkedStack<Character> symbols = symbolRetrieve();
                LinkedStack<String> postfix = postfixRetrieve();
                LinkedStack<String> operator = operatorRetrieve();

                //Creates the title
                JLabel symbolTitle = new JLabel("SYMBOL");
                symbolTitle.setForeground(Color.DARK_GRAY);
                symbolTitle.setFont( new Font("Sanserif", Font.PLAIN, 10));
                symbolTitle.setBounds(70, 70, 100,50);
                panelPostfixOutput.add(symbolTitle);

                JLabel postfixTitle = new JLabel("EXPRESSION");
                postfixTitle.setForeground(Color.DARK_GRAY);
                postfixTitle.setFont( new Font("Sanserif", Font.PLAIN, 10));
                postfixTitle.setBounds(190, 70, 100,50);
                panelPostfixOutput.add(postfixTitle);

                JLabel operatorTitle = new JLabel("STACK");
                operatorTitle.setForeground(Color.DARK_GRAY);
                operatorTitle.setFont( new Font("Sanserif", Font.PLAIN, 10));
                operatorTitle.setBounds(350, 70, 100,50);
                panelPostfixOutput.add(operatorTitle);

                //Creates the Table
                int y = 80, width = 100, height = 50;
                int size = symbols.size();
                for (int i = 0; i < size; i++){
                    int x = 70;

                    //Retrieves the element on top of the InfixToPostfix.Stack
                    Character symbolValue = symbols.peek();
                    String postfixValue = postfix.peek();
                    String operatorValue = operator.peek();

                    //Display the element
                    JLabel displaySymbol = new JLabel("<html><br>" + symbolValue);
                    displaySymbol.setForeground(Color.DARK_GRAY);
                    displaySymbol.setFont( new Font("Sanserif", Font.PLAIN, 15));
                    displaySymbol.setBounds(x, y, width, height);

                    JLabel displayPostfix = new JLabel("<html><br>" + postfixValue);
                    displayPostfix.setForeground(Color.DARK_GRAY);
                    displayPostfix.setFont( new Font("Sanserif", Font.PLAIN, 15));
                    x = x + 120;
                    displayPostfix.setBounds(x, y, width, height);

                    JLabel displayOperator = new JLabel("<html><br>" + operatorValue);
                    displayOperator.setForeground(Color.DARK_GRAY);
                    displayOperator.setFont( new Font("Sanserif", Font.PLAIN, 15));
                    x = x + 160;
                    displayOperator.setBounds(x, y, width, height);

                    y = y+20;

                    //Pop the element on top
                    symbols.pop();
                    postfix.pop();
                    operator.pop();

                    panelPostfixOutput.add(displaySymbol);
                    panelPostfixOutput.add(displayPostfix);
                    panelPostfixOutput.add(displayOperator);
                }

            } else {
                //A window which will display the error message
                JOptionPane.showMessageDialog(null, symbolCheckerForInfixInput(infix), "Error! Cannot be converted.",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        //A button to go back to InfixToPostfix.Main Menu
        JButton buttonMainMenuForInfix = new JButton("InfixToPostfix.Main Menu");
        buttonMainMenuForInfix.setBounds(17, 250, 100, 25);
        buttonMainMenuForInfix.setSize(220, 30);
        buttonMainMenuForInfix.setBackground(Color.decode("#355E3B"));
        buttonMainMenuForInfix.setForeground(Color.white);
        buttonMainMenuForInfix.addActionListener( e -> {
            frameInfixToPostfix.dispose();
            try {
                mainMenu();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        panelInfixToPostfix.add(buttonMainMenuForInfix);

        //A button to exit the program
        JButton buttonExitForInfix = new JButton("Exit");
        buttonExitForInfix.setBounds(245, 250, 100, 25);
        buttonExitForInfix.setSize(220, 30);
        buttonExitForInfix.setBackground(Color.decode("#A52A2A"));
        buttonExitForInfix.setForeground(Color.white);
        panelInfixToPostfix.add(buttonExitForInfix);
        buttonExitForInfix.addActionListener( e -> {
            JOptionPane.showMessageDialog(null, "Thank you for using our program!",
                    "Exit Message", JOptionPane.INFORMATION_MESSAGE);
            frameInfixToPostfix.dispose();
        });
        frameInfixToPostfix.setVisible(true);

    } // End of inputInfixToPostfix method

    /**
     * The showOutputGUI method creates a window that will display the converted expression
     * of the entered expression either in infix to postfix or postfix to infix. It has also
     * buttons to go back to the main menu to convert another expression or to exit the program.
     *
     * @param message displays the specific header of the converter
     * @param process displays the title of the window
     * @param convert displays the output or the converted expression
     */
    public static void showOutputInGUI(String message, String process, String convert) {
        framePostfixOutput = new JFrame();
        panelPostfixOutput = new JPanel();

        //This will create the frame for the output of conversion (not the process)
        framePostfixOutput.setSize(500, 500);
        framePostfixOutput.setTitle(process);
        framePostfixOutput.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        framePostfixOutput.setResizable(false);

        //Sets the panel for the GUI
        panelPostfixOutput.setLayout(null);
        panelPostfixOutput.setBackground(Color.white);
        framePostfixOutput.add(panelPostfixOutput);

        JLabel output = new JLabel(message, SwingConstants.CENTER);
        output.setForeground(Color.darkGray);
        output.setFont(new Font("Verdana", Font.BOLD, 12));
        output.setBounds(15, 10, 450, 50);
        panelPostfixOutput.add(output);

        JLabel postfixOutput = new JLabel(convert, SwingConstants.CENTER);
        postfixOutput.setForeground(Color.decode("#AA4A44"));
        postfixOutput.setFont(new Font("Sanserif", Font.BOLD, 30));
        postfixOutput.setBounds(15, 15, 450, 100);
        panelPostfixOutput.add(postfixOutput);

        //A button to go back to the InfixToPostfix.Main Menu
        buttonMainMenu = new JButton("InfixToPostfix.Main Menu");
        buttonMainMenu.setBounds(17, 400, 100, 25);
        buttonMainMenu.setSize(220, 30);
        buttonMainMenu.setBackground(Color.decode("#355E3B"));
        buttonMainMenu.setForeground(Color.white);
        panelPostfixOutput.add(buttonMainMenu);
        buttonMainMenu.addActionListener( e -> {
            framePostfixOutput.dispose();
            try {
                mainMenu();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        //A button to exit the program
        buttonExit = new JButton("Exit");
        buttonExit.setBounds(245, 400, 100, 25);
        buttonExit.setSize(220, 30);
        buttonExit.setBackground(Color.decode("#A52A2A"));
        buttonExit.setForeground(Color.white);
        panelPostfixOutput.add(buttonExit);
        buttonExit.addActionListener( e -> {
            framePostfixOutput.dispose();
            JOptionPane.showMessageDialog(null, "Thank you for using our program!",
                    "Exit Message", JOptionPane.INFORMATION_MESSAGE);
        });
        framePostfixOutput.setVisible(true);
    } // End of showOutputInGUI method


    /**
     * The inputPostfixToInfix method will create a graphical user interface in which the user can enter
     * a postfix expression that will be converted into its postfix form. Once the expression is entered,
     * a new window will appear for the output to display. If the entered expression is not valid, an error
     * window will pop up informing the user that it cannot convert the entered expression. It also has a
     * button to go back to the main menu to enter another expression or exit the program.
     *
     * @throws IOException if there is something wrong with input or the output
     */
    public static void inputPostfixToInfix() throws IOException {
        framePostfixToInfix = new JFrame();
        panelPostfixToInfix = new JPanel();
        buttonPostfixToInfix = new JButton();
        buttonMainMenu = new JButton();
        buttonExit = new JButton();
        textFPostfixToInfix = new JTextField();

        //Creates a frame for the user's input
        framePostfixToInfix.setSize(500, 400);
        framePostfixToInfix.setTitle("Converter");
        framePostfixToInfix.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        framePostfixToInfix.setResizable(false);

        //Sets the panel for the GUI
        panelPostfixToInfix.setLayout(null);
        panelPostfixToInfix.setBackground(Color.white);
        framePostfixToInfix.add(panelPostfixToInfix);

        //Displays a message about this particular conversion
        JLabel labelPostfixToInfix = new JLabel("Postfix to Infix Converter", SwingConstants.CENTER);
        labelPostfixToInfix.setVerticalTextPosition(SwingConstants.TOP);
        labelPostfixToInfix.setForeground(Color.DARK_GRAY);
        labelPostfixToInfix.setFont(new Font("SansSerif", Font.BOLD,20 ));
        labelPostfixToInfix.setBounds(20, 0, 450, 100);
        panelPostfixToInfix.add(labelPostfixToInfix);

        JLabel instruction = new JLabel("<html> <center>Note: Postfix is a notation that the compiler " +
                "<br> uses/converts to while reading left to right. " +
                "<br> You can input either a character or a number in postfix notation." +
                "<br> Postfix: (Operand1) (Operator2) (Operator) </center></html>", SwingConstants.CENTER);
        instruction.setFont(new Font("Verdana", Font.ITALIC, 12));
        instruction.setForeground(Color.decode("#AA4A44"));
        instruction.setBounds(15, 65, 450, 60);
        panelPostfixToInfix.add(instruction);

        JLabel labelAns = new JLabel("Enter the Postfix Expression: ");
        labelAns.setForeground(Color.decode("#AA4A44"));
        labelAns.setBounds(170, 140, 200, 20);
        panelPostfixToInfix.add(labelAns);

        //Creates a Text Field for user's input
        textFPostfixToInfix = new JTextField();
        textFPostfixToInfix.setBounds(18, 160, 150, 20);
        textFPostfixToInfix.setSize(450, 50);
        textFPostfixToInfix.setCaretColor(Color.decode("#AA4A44"));
        textFPostfixToInfix.setFont(new Font("Sanserif", Font.PLAIN, 20));
        textFPostfixToInfix.setHorizontalAlignment(JTextField.CENTER);
        panelPostfixToInfix.add(textFPostfixToInfix);

        //A button to convert the value
        buttonPostfixToInfix = new JButton("Convert");
        buttonPostfixToInfix.setBounds(17, 210, 100, 25);
        buttonPostfixToInfix.setSize(450, 30);
        buttonPostfixToInfix.setBackground(Color.darkGray);
        buttonPostfixToInfix.setForeground(Color.white);
        buttonPostfixToInfix.setFocusable(false);
        panelPostfixToInfix.add(buttonPostfixToInfix);
        buttonPostfixToInfix.addActionListener( e -> {
            String process = "Postfix to Infix Conversion";
            postfix = textFPostfixToInfix.getText();
            String display = getInfix(postfix).replaceAll("\\s+","");
            String header = "";

            if (symbolCheckerForPostfixInput(postfix).equals("verified")) {
                if (isAlgebraicExpression) {
                    header = "The Infix expression to the given Postfix expression is: ";
                } else if (isNumericExpression) {
                   header = "The value of the given Postfix expression is: ";
                }

                if (symbolCheckerForPostfixInput(postfix).equals("verified")){
                    framePostfixToInfix.dispose();
                    showOutputInGUI(header, process, display);

                    //Retrieves the InfixToPostfix.LinkedStack objects
                    LinkedStack<Character> operators = operatorForPostfixRetrieve();
                    LinkedStack<String> operand1 = retrieveFirstOperand();
                    LinkedStack<String> operand2 = retrieveSecondOperand();
                    LinkedStack<String> output = retrieveOutput();

                    //Creates the title
                    JLabel operatorTitle = new JLabel("OPERATOR");
                    operatorTitle.setForeground(Color.DARK_GRAY);
                    operatorTitle.setFont(new Font("Sanserif", Font.PLAIN, 10));
                    operatorTitle.setBounds(40, 70, 150, 50);
                    panelPostfixOutput.add(operatorTitle);

                    JLabel operand1Title = new JLabel("OPERAND 1");
                    operand1Title.setForeground(Color.DARK_GRAY);
                    operand1Title.setFont(new Font("Sanserif", Font.PLAIN, 10));
                    operand1Title.setBounds(150, 70, 150, 50);
                    panelPostfixOutput.add(operand1Title);

                    JLabel operand2Title = new JLabel("OPERAND 2");
                    operand2Title.setForeground(Color.DARK_GRAY);
                    operand2Title.setFont(new Font("Sanserif", Font.PLAIN, 10));
                    operand2Title.setBounds(260, 70, 150, 50);
                    panelPostfixOutput.add(operand2Title);

                    JLabel outputTitle = new JLabel("OUTPUT");
                    outputTitle.setForeground(Color.DARK_GRAY);
                    outputTitle.setFont(new Font("Sanserif", Font.PLAIN, 10));
                    outputTitle.setBounds(370, 70, 150, 50);
                    panelPostfixOutput.add(outputTitle);

                    //Creates the Table
                    int y = 80, width = 100, height = 50;
                    int size = operators.size();
                    for (int i = 0; i < size; i++) {
                        int x = 40;

                        //Retrieves the element on top of the InfixToPostfix.Stack
                        Character operatorValue = operators.peek();
                        String operand1Value = operand1.peek();
                        String operand2Value = operand2.peek();
                        String outputValue = output.peek();

                        //Display the element
                        JLabel displayOperator = new JLabel("<html><br>" + operatorValue);
                        displayOperator.setForeground(Color.DARK_GRAY);
                        displayOperator.setFont(new Font("Sanserif", Font.PLAIN, 15));
                        displayOperator.setBounds(x, y, width, height);

                        JLabel displayOperand1 = new JLabel("<html><br>" + operand1Value);
                        displayOperand1.setForeground(Color.DARK_GRAY);
                        displayOperand1.setFont(new Font("Sanserif", Font.PLAIN, 15));
                        x = x + 110;
                        displayOperand1.setBounds(x, y, width, height);

                        JLabel displayOperand2 = new JLabel("<html><br>" + operand2Value);
                        displayOperand2.setForeground(Color.DARK_GRAY);
                        displayOperand2.setFont(new Font("Sanserif", Font.PLAIN, 15));
                        x = x + 110;
                        displayOperand2.setBounds(x, y, width, height);

                        JLabel displayOutput = new JLabel("<html><br>" + outputValue);
                        displayOutput.setForeground(Color.DARK_GRAY);
                        displayOutput.setFont(new Font("Sanserif", Font.PLAIN, 15));
                        x = x + 110;
                        displayOutput.setBounds(x, y, width, height);

                        y = y + 20;

                        //Pop the element on top
                        operators.pop();
                        operand1.pop();
                        operand2.pop();
                        output.pop();

                        panelPostfixOutput.add(displayOperator);
                        panelPostfixOutput.add(displayOperand1);
                        panelPostfixOutput.add(displayOperand2);
                        panelPostfixOutput.add(displayOutput);

                    }
                }
            }else {
                JOptionPane.showMessageDialog(null, symbolCheckerForPostfixInput(postfix),
                        "Error! Invalid Input.", JOptionPane.ERROR_MESSAGE);
            }
        });

        //A button to go back to the InfixToPostfix.Main Menu
        buttonMainMenu = new JButton("InfixToPostfix.Main Menu");
        buttonMainMenu.setBounds(17, 250, 100, 25);
        buttonMainMenu.setSize(220, 30);
        buttonMainMenu.setBackground(Color.decode("#355E3B"));
        buttonMainMenu.setForeground(Color.white);
        buttonMainMenu.addActionListener( e1 -> {
            framePostfixToInfix.dispose();
            try {
                mainMenu();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        panelPostfixToInfix.add(buttonMainMenu);

        //A button to exit the program
        buttonExit = new JButton("Exit");
        buttonExit.setBounds(245, 250, 100, 25);
        buttonExit.setSize(220, 30);
        buttonExit.setBackground(Color.decode("#A52A2A"));
        buttonExit.setForeground(Color.white);
        panelPostfixToInfix.add(buttonExit);
        buttonExit.addActionListener( e2 -> {
            JOptionPane.showMessageDialog(null, "Thank you for using our program!",
                    "Exit Message", JOptionPane.INFORMATION_MESSAGE);
            framePostfixToInfix.dispose();
        });
        framePostfixToInfix.setVisible(true);
    } // End of inputPostfixToInfix method
} // End of InfixToPostfix.Main class
