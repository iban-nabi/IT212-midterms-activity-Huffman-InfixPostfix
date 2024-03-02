package InfixToPostfix;

public class LinkedStack<T> implements Stack<T> {

    private Node<T> top;
    private int numElements = 0;

    @Override
    public int size() {
        return (numElements);
    }

    @Override
    public boolean isEmpty() {
        return (top == null);
    }

    @Override
    public T pop() throws StackException {
        Node<T> temp;
        if (isEmpty())
            throw new StackException("InfixToPostfix.Stack underflow.");
        temp = top;
        top = top.getLink();
        numElements--;
        return temp.getInfo();
    }

    @Override
    public void push(T item) throws StackException {
        Node<T> newNode = new Node();
        newNode.setInfo(item);
        newNode.setLink(top);
        top = newNode;
        numElements++;
    }

    @Override
    public T peek() throws StackException {
        if (isEmpty())
            throw new StackException("InfixToPostfix.Stack is empty.");
        return top.getInfo();
    }

    public String getString() {
        String contents = "";
        char op;
        String opString = "";
        Node<T> currentNode = top;

        for (int i=0; i<numElements;i++) {
            if (currentNode.getInfo() == null){
                contents = contents + "";
            }else{
                contents = contents + currentNode.getInfo() ;
            }
            currentNode=currentNode.getLink();
        }

        for (int i=0; i<contents.length(); i++) {
            op= contents.charAt(i); //extracts each character
            opString= op + opString; //adds each character in front of the existing string
        }

        return opString;
    }
}


