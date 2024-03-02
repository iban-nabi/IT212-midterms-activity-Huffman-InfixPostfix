package InfixToPostfix;

import java.text.DecimalFormat;


public class ConverterForGUI{
    private static LinkedStack<Character> operators = new LinkedStack<>();
    static int operandCounter;
    static int operatorCounter;
    static boolean isNumericExpression;
    static boolean isAlgebraicExpression;
    static LinkedStack<Character> symbolsList = new LinkedStack<>();
    static LinkedStack<String> postfixList = new LinkedStack<>();
    static LinkedStack<String> operatorList = new LinkedStack<>();
    static LinkedStack<Character> operatorListForPostfix = new LinkedStack<>();
    static LinkedStack<String> listForTheFistOperand = new LinkedStack<>();
    static LinkedStack<String> listForTheSecondOperand = new LinkedStack<>();
    static LinkedStack<String> listForOutput = new LinkedStack<>();


    public ConverterForGUI(){

    }
    /**
     * This method checks whether the character entered takes precedence.
     * @param x the character to be checked
     * @return 3 equals the greatest precedence; followed by 2; then 1. 0 is returned for unrecognized characters
     */
    private static int precedence(char x){
        if (x == '^')
            return 3;
        if (x == '*' || x == '/' || x == '%')
            return 2;
        if (x == '+' || x == '-')
            return 1;
        return 0;
    }

    /**
     * This method checks whether the character entered is an operand.
     * @param x the character to be checked
     * @return true if the character is an operand, false if not
     */
    private static boolean isOperand(char x) {
        return (x >= 'a' && x <= 'z') ||
                (x >= 'A' && x <= 'Z') || (Character.isDigit(x));
    }

    /**
     * This method checks whether the character entered is an operator.
     * @param x the character to be checked
     * @return true if the character is not an operator, false if it is an operator
     */
    private static boolean isNotAnOperator(char x){
        return (x != '*' && x != '/' && x != '%' && x != '+' && x != '-' && x != '^');
    }

    /**
     * This method checks whether the infix input is valid
     * @param infix is the string infix to be checked
     * @return "verified" if the infix input is valid or the error message if the infix input is invalid
     */
    public static String symbolCheckerForInfixInput(String infix) {
        char symbol;
        operandCounter=0;
        operatorCounter=0;
        int parenthesisChecker = 0;
        //while there is input to be read

        //If Infix is empty
        if(infix.length() == 0){
            return "No Infix Found.";
        }

        for(int i=0; i < infix.length(); ++i) {
            //if it's an operand, add it to the string
            symbol = infix.charAt(i);

            if(Character.isDigit(symbol)){
                return "The converter for Infix to Postfix only supports Algebraic Expression Inputs";
            }

            if(!isNotAnOperator(symbol)){
                operatorCounter++;
            }

            if(isOperand(symbol)){
                operandCounter++;
            }

            // checks if the current character is an operator and the next character is an operator
            if(i!=infix.length()-1){
                if(!isNotAnOperator(infix.charAt(i)) && !isNotAnOperator(infix.charAt(i+1))){
                    return "Input a proper Infix Expression, an operator must not be followed by an operator";
                }
            }

            // checks if the current character is an operand and the next character is an operand
            if(i!=infix.length()-1){
                if(isOperand(infix.charAt(i)) && isOperand(infix.charAt(i+1))){
                    return "Input a proper Infix Expression, an operand must not be followed by an operand";
                }
            }

            // checks if the current character if it is a valid operator
            if (!isOperand(symbol) && (symbol !='(' && symbol !=')')  ){
                if (isNotAnOperator(symbol)) {
                    return "This cannot be converted because "+symbol+" is not an operator";
                }
            }

            if(symbol=='(' && parenthesisChecker>=0){
                parenthesisChecker++;
            }

            if(symbol==')' ){
                parenthesisChecker--;
            }

        }

        // Checks if operator has the same number of count with the number of operand minus 1
        if(operandCounter-1 != operatorCounter){
            return "Invalid Infix Expression";
        }

        // Checks if the parenthesis has their pair
        if(parenthesisChecker!=0){
            return "Check your open and close parenthesis";
        }
        return "verified";
    }

    /**
     * Transpose the input infix expression to a postfix expression
     * @param infix is the infix expression to be converted to postfix
     * @return the postfix expression if the input infix is valid, if not return an error message from the symbolCheckerForInfixInput method
     */
    public static String toPostfix(String infix) {
        char symbol;
        String postfix = "";
        infix = infix.replaceAll("\\s+","");
        String verifiedStatus = symbolCheckerForInfixInput(infix);

        if(verifiedStatus.equals("verified")){ // if input infix is valid
            for(int i=0; i < infix.length(); ++i) {

                symbol = infix.charAt(i);

                if (isOperand(symbol)) { //if the currently read character is an operand, append it to the string
                    postfix = postfix +" "+ symbol;
                    outputPostfixTable(symbol, postfix);

                } else if (symbol=='(') { //if the currently read character is a "(", push it to the stack
                    operators.push(symbol);
                    outputPostfixTable(symbol, postfix);

                } else if (symbol==')') { //if the currently read character is a ")",pop the operand in the stack until
                    // the top of the stack is a "(". The pop operand will be appended to the string
                    while (operators.peek() != '('){
                        postfix = postfix +" "+ operators.pop();
                    }
                    operators.pop(); //pop the '(', which is at the top of the stack
                    outputPostfixTable(symbol, postfix);

                } else {
                    // append and pop the operator at the top of the stack IF the currently read operator has a lower level of precedence
                    while (!operators.isEmpty() && !(operators.peek()=='(') && precedence(symbol) <= precedence(operators.peek())) {
                        postfix = postfix + " "+ operators.pop();
                    }
                    operators.push(symbol); // push the currently read operator to the stack
                    outputPostfixTable(symbol, postfix);
                }
            }

            while (!operators.isEmpty()) //while there is an operand to be read in the stack, pop and append the remaining operators to the string
                postfix = postfix +" "+ operators.pop();
            outputPostfixTable(' ', postfix);
            return postfix.substring(1); // returns output of infix to postfix

        }else{ // if input infix is invalid
            return verifiedStatus; // returns the error message prior to the invalid input format
        }
    }

    /**
     * Pushes elements to symbolList, postfixList and operatorList to be used in printing the table
     * @param symbol will be pushed on the symbolList
     * @param postfix will be pushed on the postfixList
     */
    private static void outputPostfixTable(char symbol, String postfix){
        symbolsList.push(symbol);
        postfixList.push(postfix);
        operatorList.push(operators.getString());
    }

    /**
     * This returns a InfixToPostfix.LinkedStack object that contains the characters which will be used in the table
     * @return InfixToPostfix.LinkedStack character
     */
    public static LinkedStack<Character> symbolRetrieve(){
        LinkedStack<Character> symbols = new LinkedStack<>();
        int size = symbolsList.size();
        for(int i = 0; i < size; i++){
            symbols.push(symbolsList.peek());
            symbolsList.pop();
        }
        return symbols;
    }

    /**
     * This returns a InfixToPostfix.LinkedStack object that contains the postfix expressions which will be used in the table
     * @return InfixToPostfix.LinkedStack String
     */
    public static LinkedStack<String> postfixRetrieve(){
        LinkedStack<String> postfix = new LinkedStack<>();
        int size = postfixList.size();
        for(int i = 0; i < size; i++){
            postfix.push(postfixList.peek());
            postfixList.pop();
        }
        return postfix;
    }

    /**
     * This returns a InfixToPostfix.LinkedStack object that contains the operators which will be used in the table
     * @return InfixToPostfix.LinkedStack String
     */
    public static LinkedStack<String> operatorRetrieve(){
        LinkedStack<String> operator = new LinkedStack<>();
        int size = operatorList.size();
        for(int i = 0; i < size; i++){
            operator.push(operatorList.peek());
            operatorList.pop();
        }
        return operator;
    }

    /**
     * This method checks whether the postfix input is valid
     * @param postfix is the string infix to be checked
     * @return "verified" if the postfix input is valid or the error message if the infix input is invalid
     */
    public static String symbolCheckerForPostfixInput(String postfix){
        int j=0;
        operandCounter=0;
        operatorCounter=0;
        isNumericExpression = false;
        isAlgebraicExpression = false;

        //If postfix is empty
        if(postfix.length() == 0){
            return "No Postfix Found.";
        }

        //verify if the entered postfix is a proper postfix expression, example: A+B,
        // first character is followed by an operator
        if(postfix.length()>=2){
            int keySpace=0;
            for(int k=0; k<postfix.length(); k++){
                if(postfix.charAt(k)!=' ' && keySpace==0){
                    keySpace=1;
                }

                if(keySpace==1){
                    if(isOperand(postfix.charAt(k)) && !isNotAnOperator(postfix.charAt(k+2))){
                        return "Invalid Postfix Expression";
                    }else{
                        break;
                    }
                }
            }
        }

        // Checks if the input postfix is in numeric expression (no letters)
        for(int i=0; i<postfix.replaceAll("\\s+","").length();i++){
            char symbol = postfix.replaceAll("\\s+","").charAt(i);
            if(Character.isDigit(symbol)){
                isNumericExpression=true;
            }else if(((symbol >= 'a' && symbol <= 'z') || (symbol >= 'A' && symbol <= 'Z')) && isNumericExpression){
                return "Entered Postfix Expression is not supported";
            }
        }


        // Checks if the input postfix is in algebraic expression (no numbers)
        for(int i=0; i<postfix.replaceAll("\\s+","").length();i++){
            char symbol = postfix.replaceAll("\\s+","").charAt(i);
            if(isOperand(symbol) && !Character.isDigit(symbol)){
                isAlgebraicExpression =true;
            }else if(Character.isDigit(symbol) && isAlgebraicExpression){
                return "Entered Postfix Expression is not supported";
            }
        }


        // verify if the entered postfix has spaces for letter expression
        if(isAlgebraicExpression){
            while(j<postfix.length()-1){
                if(postfix.charAt(j)!=' ' && postfix.charAt(j+1)!=' '){
                    return "Input an Algebraic Postfix where operands and operators must be separated by a space.";
                }
                j=j+2;
            }
        }


        // verify if the entered postfix has spaces for numeric expression
        if(isNumericExpression){
            while(j<postfix.length()){
                if(!Character.isDigit(postfix.charAt(j))){
                    if(!isNotAnOperator(postfix.charAt(j))){
                        return "Check the spacing of your Numeric Postfix Expression, either add spacing or remove extra space.";
                    }
                    j=j+2;
                }else{
                    j++;
                }
            }
        }


        j=0;
        // verify if the entered postfix has proper operands
        while(j<postfix.replaceAll("\\s+","").length()){
            if(isNotAnOperator(postfix.replaceAll("\\s+","").charAt(j)) &&
                    !isOperand(postfix.replaceAll("\\s+","").charAt(j))){


                if(postfix.replaceAll("\\s+","").charAt(j)=='(' ||
                        postfix.replaceAll("\\s+","").charAt(j)==')'){
                    return "Remove parenthesis in the Postfix Expression";

                }else{
                    return "This cannot be converted because " +
                            postfix.replaceAll("\\s+","").charAt(j) + " is not an operator";
                }
            }

            j++;
        }


        for(int i=0; i<postfix.length();i++){
            //counts the number of operands
            if(Character.isDigit(postfix.charAt(i))){
                if(postfix.charAt(i+1)==' '){
                    operandCounter++;
                }
            }else{
                if(isOperand(postfix.charAt(i))){
                    operandCounter++;
                }
            }

            //counts the number of operators
            if(!isNotAnOperator(postfix.charAt(i)) && postfix.charAt(i)!=' '){
                operatorCounter++;
            }
        }


        // Checks if operator has the same number of count with the number of operand minus 1
        if(operandCounter-1!=operatorCounter || operatorCounter==0){
            return "Invalid Postfix Expression. The number of operands/operators should accommodate each other";
        }

        return "verified";
    }

    /**
     * Transpose postfix expression to infix expression
     * @param exp is the postfix expression to be converted to infix, either convert the Algebraic expression or Numeric expression depending on the input of the user
     * @return the infix expression if the input postfix is valid, if not return an error message from the symbolCheckerForPostFixInput method
     */
    public static String getInfix(String exp) {
        String verifiedStatus = symbolCheckerForPostfixInput(exp);
        String output;

        if(verifiedStatus.equals("verified")){
            if(isAlgebraicExpression){
                output= convertAlgebraicExpressionInput(exp);
            }else{
                output= convertNumericExpressionInput(exp);
            }
            return output;
        }else{
            return verifiedStatus; // returns the error message prior to the invalid input format
        }
    }


    /**
     * This method converts an algebraic postfix expression to an infix expression
     * @param exp which is the entered postfix expression
     * @return the infix value of the postfix expression
     */
    public static String convertAlgebraicExpressionInput(String exp){
        LinkedStack<String> infixStack = new LinkedStack<>();

        for (int i = 0; i < exp.length(); i++) {
            if(exp.charAt(i)!=' '){ // Push operands to the infix stack
                if (isOperand(exp.charAt(i))) {
                    infixStack.push(exp.charAt(i) + "");
                }else{ // if the currently read character is an operator, pop the first two operands from the stack,
                    // then append the operator between the two operands as a string, then push the string to the stack
                    String op2 = infixStack.peek();
                    infixStack.pop();
                    String op1 = infixStack.peek();
                    infixStack.pop();
                    infixStack.push(op1 +" "+exp.charAt(i) +" "+
                            op2);
                    outputInfixTable(exp.charAt(i),op1,op2,infixStack.peek());
                }
            }
        }
        return infixStack.peek(); // return output for postfix to infix
    }

    /**
     * This method evaluates the numeric postfix expression
     * @param exp which is the entered postfix expression
     * @return the computed value of the numeric postfix expression
     */
    public static String convertNumericExpressionInput(String exp){
        LinkedStack<Double> computedValue = new LinkedStack<>();
        DecimalFormat format = new DecimalFormat("0.###");
        String number="";
        double operator1;
        double operator2;
        double output=0;

        for (int i = 0; i < exp.length(); i++) {
            if(Character.isDigit(exp.charAt(i))){
                number=number+exp.charAt(i);
            }else if (exp.charAt(i)==' ') {// if the character is a space, it means that the numeric value is already in the String number
                try{
                    computedValue.push(Double.parseDouble(number)); // push the number in the stack
                }catch (Exception ignored){

                }
                number="";
            }else{ // if the currently read character is an operator, pop the first two operands from the stack,
                // then compute for the value of operand 1 and 2 depending on the operator.
                operator2 = computedValue.peek();
                computedValue.pop();

                operator1 = computedValue.peek();
                computedValue.pop();

                if(exp.charAt(i)=='+'){
                    output=operator1+operator2;

                }else if(exp.charAt(i)=='-'){
                    output=operator1-operator2;

                }else if(exp.charAt(i)=='*'){
                    output=operator1*operator2;

                }else if(exp.charAt(i)=='/'){
                    output=operator1/operator2;

                }else if(exp.charAt(i)=='%'){
                    output=operator1%operator2;

                }else if(exp.charAt(i)=='^'){
                    output=operator1*operator1;
                    for(int j=1; j<operator2-1; j++){
                        output=output*operator1;
                    }
                }

                computedValue.push(Double.valueOf(format.format(output)));
                outputInfixTable(exp.charAt(i),String.valueOf(format.format(operator1)),String.valueOf(format.
                                format(operator2)),
                        String.valueOf(format.format(output)));
            }
        }
        return format.format(computedValue.peek()); // return output for postfix to infix
    }

    /**
     * Pushes elements to operatorListForPostfix, listForTheFistOperand, listForTheSecondOperand, and listForOutput to be used in printing the table
     * @param operator will be pushed on the operatorListForPostfix
     * @param op1 will be pushed on the listForTheFistOperand
     * @param op2 will be pushed on the listForTheSecondOperand
     * @param output will be pushed on the listForOutput
     */
    private static void outputInfixTable(char operator, String op1, String op2, String output){
        operatorListForPostfix.push(operator);
        listForTheFistOperand.push(op1);
        listForTheSecondOperand.push(op2);
        listForOutput.push(output);
    }

    /**
     * This returns a InfixToPostfix.LinkedStack object that contains the operators which will be used in the table
     * @return InfixToPostfix.LinkedStack String
     */
    public static LinkedStack<Character> operatorForPostfixRetrieve(){
        LinkedStack<Character> operator = new LinkedStack<>();
        int size = operatorListForPostfix.size();
        for(int i = 0; i < size; i++){
            operator.push(operatorListForPostfix.peek());
            operatorListForPostfix.pop();
        }
        return operator;
    }
    /**
     * This returns a InfixToPostfix.LinkedStack object that contains the first operands which will be used in the table
     * @return InfixToPostfix.LinkedStack String
     */
    public static LinkedStack<String> retrieveFirstOperand(){
        LinkedStack<String> operand1 = new LinkedStack<>();
        int size = listForTheFistOperand.size();
        for(int i = 0; i < size; i++){
            operand1.push(listForTheFistOperand.peek());
           listForTheFistOperand.pop();
        }
        return operand1;
        }

    /**
     * This returns a InfixToPostfix.LinkedStack object that contains the second operands which will be used in the table
     * @return InfixToPostfix.LinkedStack String
     */
    public static LinkedStack<String> retrieveSecondOperand(){
        LinkedStack<String> operand2 = new LinkedStack<>();
        int size = listForTheSecondOperand.size();
        for(int i = 0; i < size; i++){
            operand2.push(listForTheSecondOperand.peek());
            listForTheSecondOperand.pop();
        }
        return operand2;
    }

    /**
     * This returns a InfixToPostfix.LinkedStack object that contains the outputs which will be used in the table
     * @return InfixToPostfix.LinkedStack String
     */
    public static LinkedStack<String> retrieveOutput(){
        LinkedStack<String> output = new LinkedStack<>();
        int size = listForOutput.size();
        for(int i = 0; i < size; i++){
            output.push(listForOutput.peek());
            listForOutput.pop();
        }
        return output;
    }
}
